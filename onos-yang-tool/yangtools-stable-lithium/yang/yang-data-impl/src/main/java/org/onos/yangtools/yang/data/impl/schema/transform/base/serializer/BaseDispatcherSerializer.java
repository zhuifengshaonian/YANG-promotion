/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.transform.base.serializer;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Set;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier;
import org.onos.yangtools.yang.data.api.schema.AugmentationNode;
import org.onos.yangtools.yang.data.api.schema.DataContainerChild;
import org.onos.yangtools.yang.data.api.schema.DataContainerNode;
import org.onos.yangtools.yang.data.impl.schema.transform.FromNormalizedNodeSerializer;
import org.onos.yangtools.yang.model.api.AugmentationSchema;
import org.onos.yangtools.yang.model.api.DataSchemaNode;
import org.onos.yangtools.yang.model.util.EffectiveAugmentationSchema;

/**
 * Abstract(base) Serializer for DataContainerNodes e.g. ContainerNode, AugmentationNode.
 */
public abstract class BaseDispatcherSerializer<E, N extends DataContainerNode<?>, S> implements
        FromNormalizedNodeSerializer<E, N, S> {

    /**
     *
     * @param schema
     * @param augmentationSchema
     * @return Set of real schema objects that represent child nodes of an
     *         augmentation. Augmentation schema child nodes, if further
     *         augmented, do not contain further augmented, that are crucial for
     *         parsing. The real schema object can be retrieved from parent schema: schema.
     */
    protected abstract Set<DataSchemaNode> getRealSchemasForAugment(S schema, AugmentationSchema augmentationSchema);

    /**
     *
     * @param schema
     * @param childNode
     * @return Schema object associated with child node identified as: childNode.
     *         Schema should be retrieved from parent schema: schema.
     */
    protected abstract DataSchemaNode getSchemaForChild(S schema,
            DataContainerChild<? extends YangInstanceIdentifier.PathArgument, ?> childNode);

    /**
     *
     * @param schema
     * @param augmentationNode
     * @return Schema object associated with augmentation child node identified as: augmentationNode.
     *         Schema should be retrieved from parent schema: schema.
     */
    protected abstract AugmentationSchema getAugmentedCase(S schema, AugmentationNode augmentationNode);

    /**
     *
     * @return Dispatcher object to dispatch serialization of child elements, might be
     *         the same instance if provided serializers are immutable.
     */
    protected abstract NodeSerializerDispatcher<E> getNodeDispatcher();

    @Override
    public Iterable<E> serialize(S schema, N node) {
        List<Iterable<E>> choiceChildren = Lists.newArrayList();

        for (DataContainerChild<? extends YangInstanceIdentifier.PathArgument, ?> choiceChild : node.getValue()) {

            Object childSchema;

            if (choiceChild instanceof AugmentationNode) {

                AugmentationSchema augSchema = getAugmentedCase(schema, (AugmentationNode) choiceChild);
                Set<DataSchemaNode> realChildSchemas = getRealSchemasForAugment(schema, augSchema);
                childSchema = new EffectiveAugmentationSchema(augSchema, realChildSchemas);

            } else {
                childSchema = getSchemaForChild(schema, choiceChild);
            }

            Iterable<E> childElements = getNodeDispatcher().dispatchChildElement(childSchema, choiceChild);
            choiceChildren.add(Preconditions.checkNotNull(childElements));
        }

        return Iterables.concat(choiceChildren);
    }
}