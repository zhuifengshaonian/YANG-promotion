/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.onos.yangtools.yang.common.QName;
import org.onos.yangtools.yang.data.api.ModifyAction;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier.AugmentationIdentifier;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier.NodeIdentifier;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier.NodeIdentifierWithPredicates;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier.NodeWithValue;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier.PathArgument;
import org.onos.yangtools.yang.data.api.schema.AugmentationNode;
import org.onos.yangtools.yang.data.api.schema.ChoiceNode;
import org.onos.yangtools.yang.data.api.schema.ContainerNode;
import org.onos.yangtools.yang.data.api.schema.MapEntryNode;
import org.onos.yangtools.yang.data.api.schema.MapNode;
import org.onos.yangtools.yang.data.api.schema.NormalizedNode;
import org.onos.yangtools.yang.data.api.schema.OrderedMapNode;
import org.onos.yangtools.yang.data.api.schema.UnkeyedListEntryNode;
import org.onos.yangtools.yang.data.impl.schema.builder.api.AttributesBuilder;
import org.onos.yangtools.yang.data.impl.schema.builder.api.CollectionNodeBuilder;
import org.onos.yangtools.yang.data.impl.schema.builder.api.DataContainerNodeAttrBuilder;
import org.onos.yangtools.yang.data.impl.schema.builder.api.DataContainerNodeBuilder;
import org.onos.yangtools.yang.data.impl.schema.builder.api.ListNodeBuilder;
import org.onos.yangtools.yang.data.impl.schema.builder.api.NormalizedNodeContainerBuilder;
import org.onos.yangtools.yang.model.api.AugmentationSchema;
import org.onos.yangtools.yang.model.api.ChoiceCaseNode;
import org.onos.yangtools.yang.model.api.ChoiceSchemaNode;
import org.onos.yangtools.yang.model.api.ContainerSchemaNode;
import org.onos.yangtools.yang.model.api.DataNodeContainer;
import org.onos.yangtools.yang.model.api.DataSchemaNode;
import org.onos.yangtools.yang.model.api.LeafListSchemaNode;
import org.onos.yangtools.yang.model.api.ListSchemaNode;
import org.onos.yangtools.yang.model.util.EffectiveAugmentationSchema;

/**
* Base strategy for converting an instance identifier into a normalized node structure for container-like types.
*/
abstract class InstanceIdToCompositeNodes<T extends PathArgument> extends InstanceIdToNodes<T> {

    protected InstanceIdToCompositeNodes(final T identifier) {
        super(identifier);
    }

    private static AugmentationIdentifier augmentationIdentifierFrom(final AugmentationSchema augmentation) {
        final ImmutableSet.Builder<QName> potentialChildren = ImmutableSet.builder();
        for (final DataSchemaNode child : augmentation.getChildNodes()) {
            potentialChildren.add(child.getQName());
        }
        return new AugmentationIdentifier(potentialChildren.build());
    }

    private static DataNodeContainer augmentationProxy(final AugmentationSchema augmentation, final DataNodeContainer schema) {
        final Set<DataSchemaNode> children = new HashSet<>();
        for (final DataSchemaNode augNode : augmentation.getChildNodes()) {
            children.add(schema.getDataChildByName(augNode.getQName()));
        }
        return new EffectiveAugmentationSchema(augmentation, children);
    }

    @Override
    @SuppressWarnings("unchecked")
    public final NormalizedNode<?, ?> create(final YangInstanceIdentifier instanceId, final Optional<NormalizedNode<?, ?>> lastChild, final Optional<Map.Entry<QName,ModifyAction>> operation) {
        checkNotNull(instanceId);
        final Iterator<PathArgument> iterator = instanceId.getPathArguments().iterator();
        final PathArgument legacyData = iterator.next();

        if (!isMixin() && getIdentifier().getNodeType() != null) {
            checkArgument(getIdentifier().getNodeType().equals(legacyData.getNodeType()),
                    "Node QName must be %s was %s", getIdentifier().getNodeType(), legacyData.getNodeType());
        }
        final NormalizedNodeContainerBuilder builder = createBuilder(legacyData);

        if (iterator.hasNext()) {
            final PathArgument childPath = iterator.next();
            final InstanceIdToNodes<?> childOp = getChildOperation(childPath);

            final YangInstanceIdentifier childId = YangInstanceIdentifier.create(Iterables.skip(instanceId.getPathArguments(), 1));
            builder.addChild(childOp.create(childId, lastChild, operation));
        } else {
            if (lastChild.isPresent()) {
                builder.withValue(Lists.newArrayList((Collection<?>) lastChild.get().getValue()));
            }
            if (operation.isPresent()) {
                Preconditions.checkArgument(builder instanceof AttributesBuilder<?>);
                addModifyOpIfPresent(operation, ((AttributesBuilder<?>) builder));
            }
        }

        return builder.build();
    }

    private InstanceIdToNodes<?> getChildOperation(final PathArgument childPath) {
        final InstanceIdToNodes<?> childOp;
        try {
            childOp = getChild(childPath);
        } catch (final RuntimeException e) {
            throw new IllegalArgumentException(String.format("Failed to process child node %s", childPath), e);
        }
        checkArgument(childOp != null, "Node %s is not allowed inside %s", childPath, getIdentifier());
        return childOp;
    }

    protected abstract NormalizedNodeContainerBuilder<?, ?, ?, ?> createBuilder(final PathArgument compositeNode);

    static abstract class DataContainerNormalizationOperation<T extends PathArgument> extends
            InstanceIdToCompositeNodes<T> {

        private final DataNodeContainer schema;
        private final Map<PathArgument, InstanceIdToNodes<?>> byArg;

        protected DataContainerNormalizationOperation(final T identifier, final DataNodeContainer schema) {
            super(identifier);
            this.schema = schema;
            this.byArg = new ConcurrentHashMap<>();
        }

        @Override
        public InstanceIdToNodes<?> getChild(final PathArgument child) {
            InstanceIdToNodes<?> potential = byArg.get(child);
            if (potential != null) {
                return potential;
            }
            potential = fromLocalSchema(child);
            return register(potential);
        }

        private InstanceIdToNodes<?> fromLocalSchema(final PathArgument child) {
            if (child instanceof AugmentationIdentifier) {
                return fromSchemaAndQNameChecked(schema, ((AugmentationIdentifier) child).getPossibleChildNames()
                        .iterator().next());
            }
            return fromSchemaAndQNameChecked(schema, child.getNodeType());
        }

        private InstanceIdToNodes<?> register(final InstanceIdToNodes<?> potential) {
            if (potential != null) {
                byArg.put(potential.getIdentifier(), potential);
            }
            return potential;
        }
    }

    static final class ListItemNormalization extends DataContainerNormalizationOperation<NodeIdentifierWithPredicates> {
        protected ListItemNormalization(final NodeIdentifierWithPredicates identifier, final ListSchemaNode schema) {
            super(identifier, schema);
        }

        @Override
        protected DataContainerNodeAttrBuilder<NodeIdentifierWithPredicates, MapEntryNode> createBuilder(final PathArgument currentArg) {
            final DataContainerNodeAttrBuilder<NodeIdentifierWithPredicates, MapEntryNode> builder = Builders
                    .mapEntryBuilder().withNodeIdentifier((NodeIdentifierWithPredicates) currentArg);
            for (final Map.Entry<QName, Object> keyValue : ((NodeIdentifierWithPredicates) currentArg).getKeyValues().entrySet()) {
                builder.addChild(Builders.leafBuilder()
                        //
                        .withNodeIdentifier(new NodeIdentifier(keyValue.getKey())).withValue(keyValue.getValue())
                        .build());
            }
            return builder;
        }

        @Override
        boolean isMixin() {
            return false;
        }
    }

    static final class UnkeyedListItemNormalization extends DataContainerNormalizationOperation<NodeIdentifier> {

        protected UnkeyedListItemNormalization(final ListSchemaNode schema) {
            super(new NodeIdentifier(schema.getQName()), schema);
        }

        @Override
        protected DataContainerNodeAttrBuilder<NodeIdentifier, UnkeyedListEntryNode> createBuilder(final PathArgument compositeNode) {
            return Builders.unkeyedListEntryBuilder().withNodeIdentifier(getIdentifier());
        }

        @Override
        boolean isMixin() {
            return false;
        }
    }

    static final class ContainerTransformation extends DataContainerNormalizationOperation<NodeIdentifier> {
        protected ContainerTransformation(final ContainerSchemaNode schema) {
            super(new NodeIdentifier(schema.getQName()), schema);
        }

        @Override
        protected DataContainerNodeAttrBuilder<NodeIdentifier, ContainerNode> createBuilder(final PathArgument compositeNode) {
            return Builders.containerBuilder().withNodeIdentifier(getIdentifier());
        }

        @Override
        boolean isMixin() {
            return false;
        }
    }

    static final class OrderedLeafListMixinNormalization extends UnorderedLeafListMixinNormalization {


        public OrderedLeafListMixinNormalization(final LeafListSchemaNode potential) {
            super(potential);
        }

        @Override
        protected ListNodeBuilder<?, ?> createBuilder(final PathArgument compositeNode) {
            return Builders.orderedLeafSetBuilder().withNodeIdentifier(getIdentifier());
        }
    }

    static class UnorderedLeafListMixinNormalization extends InstanceIdToCompositeNodes<NodeIdentifier> {

        private final InstanceIdToNodes<?> innerOp;

        public UnorderedLeafListMixinNormalization(final LeafListSchemaNode potential) {
            super(new NodeIdentifier(potential.getQName()));
            innerOp = new InstanceIdToSimpleNodes.LeafListEntryNormalization(potential);
        }

        @Override
        protected ListNodeBuilder<?, ?> createBuilder(final PathArgument compositeNode) {
            return Builders.leafSetBuilder().withNodeIdentifier(getIdentifier());
        }

        @Override
        public InstanceIdToNodes<?> getChild(final PathArgument child) {
            if (child instanceof NodeWithValue) {
                return innerOp;
            }
            return null;
        }

        @Override
        boolean isMixin() {
            return true;
        }
    }

    static final class AugmentationNormalization extends DataContainerNormalizationOperation<AugmentationIdentifier> {

        public AugmentationNormalization(final AugmentationSchema augmentation, final DataNodeContainer schema) {
            super(augmentationIdentifierFrom(augmentation), augmentationProxy(augmentation, schema));
        }

        @Override
        protected DataContainerNodeBuilder<AugmentationIdentifier, AugmentationNode> createBuilder(final PathArgument compositeNode) {
            return Builders.augmentationBuilder().withNodeIdentifier(getIdentifier());
        }

        @Override
        boolean isMixin() {
            return true;
        }
    }

    static class UnorderedMapMixinNormalization extends InstanceIdToCompositeNodes<NodeIdentifier> {

        private final ListItemNormalization innerNode;

        public UnorderedMapMixinNormalization(final ListSchemaNode list) {
            super(new NodeIdentifier(list.getQName()));
            this.innerNode = new ListItemNormalization(new NodeIdentifierWithPredicates(list.getQName(),
                    Collections.<QName, Object>emptyMap()), list);
        }

        @Override
        protected CollectionNodeBuilder<MapEntryNode, ? extends MapNode> createBuilder(final PathArgument compositeNode) {
            return Builders.mapBuilder().withNodeIdentifier(getIdentifier());
        }

        @Override
        public InstanceIdToNodes<?> getChild(final PathArgument child) {
            if (child.getNodeType().equals(getIdentifier().getNodeType())) {
                return innerNode;
            }
            return null;
        }

        @Override
        boolean isMixin() {
            return true;
        }
    }

    static final class OrderedMapMixinNormalization extends UnorderedMapMixinNormalization {

        public OrderedMapMixinNormalization(final ListSchemaNode list) {
            super(list);
        }

        @Override
        protected CollectionNodeBuilder<MapEntryNode, OrderedMapNode> createBuilder(final PathArgument compositeNode) {
            return Builders.orderedMapBuilder().withNodeIdentifier(getIdentifier());
        }

    }

    static final class ChoiceNodeNormalization extends InstanceIdToCompositeNodes<NodeIdentifier> {

        private final ImmutableMap<PathArgument, InstanceIdToNodes<?>> byArg;

        protected ChoiceNodeNormalization(final ChoiceSchemaNode schema) {
            super(new NodeIdentifier(schema.getQName()));
            final ImmutableMap.Builder<PathArgument, InstanceIdToNodes<?>> byArgBuilder = ImmutableMap.builder();

            for (final ChoiceCaseNode caze : schema.getCases()) {
                for (final DataSchemaNode cazeChild : caze.getChildNodes()) {
                    final InstanceIdToNodes<?> childOp = fromDataSchemaNode(cazeChild);
                    byArgBuilder.put(childOp.getIdentifier(), childOp);
                }
            }
            byArg = byArgBuilder.build();
        }

        @Override
        public InstanceIdToNodes<?> getChild(final PathArgument child) {
            return byArg.get(child);
        }

        @Override
        protected DataContainerNodeBuilder<NodeIdentifier, ChoiceNode> createBuilder(final PathArgument compositeNode) {
            return Builders.choiceBuilder().withNodeIdentifier(getIdentifier());
        }

        @Override
        boolean isMixin() {
            return true;
        }
    }
}