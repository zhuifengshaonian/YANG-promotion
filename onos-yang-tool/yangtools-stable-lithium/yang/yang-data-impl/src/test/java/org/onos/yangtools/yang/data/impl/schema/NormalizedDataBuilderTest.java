/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.io.InputStream;
import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.onos.yangtools.yang.common.QName;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier;
import org.onos.yangtools.yang.data.api.schema.AugmentationNode;
import org.onos.yangtools.yang.data.api.schema.ChoiceNode;
import org.onos.yangtools.yang.data.api.schema.ContainerNode;
import org.onos.yangtools.yang.data.api.schema.LeafNode;
import org.onos.yangtools.yang.data.api.schema.LeafSetNode;
import org.onos.yangtools.yang.data.api.schema.MapEntryNode;
import org.onos.yangtools.yang.data.api.schema.MapNode;
import org.onos.yangtools.yang.data.impl.schema.builder.api.DataContainerNodeBuilder;
import org.onos.yangtools.yang.data.impl.schema.builder.impl.ImmutableChoiceNodeSchemaAwareBuilder;
import org.onos.yangtools.yang.data.impl.schema.builder.impl.ImmutableMapNodeSchemaAwareBuilder;
import org.onos.yangtools.yang.model.api.AugmentationSchema;
import org.onos.yangtools.yang.model.api.ChoiceSchemaNode;
import org.onos.yangtools.yang.model.api.ContainerSchemaNode;
import org.onos.yangtools.yang.model.api.DataNodeContainer;
import org.onos.yangtools.yang.model.api.DataSchemaNode;
import org.onos.yangtools.yang.model.api.LeafListSchemaNode;
import org.onos.yangtools.yang.model.api.LeafSchemaNode;
import org.onos.yangtools.yang.model.api.ListSchemaNode;
import org.onos.yangtools.yang.model.api.Module;
import org.onos.yangtools.yang.model.api.SchemaContext;
import org.onos.yangtools.yang.parser.impl.YangParserImpl;

public class NormalizedDataBuilderTest {

    private ContainerSchemaNode containerNode;
    private SchemaContext schema;

    SchemaContext parseTestSchema(final String... yangPath) {
        YangParserImpl yangParserImpl = new YangParserImpl();
        Set<Module> modules = yangParserImpl.parseYangModelsFromStreams(getTestYangs(yangPath));
        return yangParserImpl.resolveSchemaContext(modules);
    }

    List<InputStream> getTestYangs(final String... yangPaths) {

        return Lists.newArrayList(Collections2.transform(Lists.newArrayList(yangPaths),
                new Function<String, InputStream>() {
            @Override
            public InputStream apply(final String input) {
                InputStream resourceAsStream = getClass().getResourceAsStream(input);
                Preconditions.checkNotNull(resourceAsStream, "File %s was null", resourceAsStream);
                return resourceAsStream;
            }
        }));
    }

    @Before
    public void setUp() throws Exception {
        schema = parseTestSchema("test.yang");
        containerNode = (ContainerSchemaNode) getSchemaNode(schema, "test", "container");
    }

    @Test
    public void testSchemaUnaware() throws Exception {
        // Container
        DataContainerNodeBuilder<YangInstanceIdentifier.NodeIdentifier, ContainerNode> builder = Builders
                .containerBuilder().withNodeIdentifier(getNodeIdentifier("container"));

        // leaf
        LeafNode<String> leafChild = Builders.<String> leafBuilder().withNodeIdentifier(getNodeIdentifier("leaf"))
                .withValue("String").build();
        builder.withChild(leafChild);

        // leafList
        LeafSetNode<Integer> leafList = Builders
                .<Integer> leafSetBuilder()
                .withNodeIdentifier(getNodeIdentifier("leaf"))
                .withChildValue(1)
                .withChild(
                        Builders.<Integer> leafSetEntryBuilder()
                        .withNodeIdentifier(getNodeWithValueIdentifier("leaf", 3)).withValue(3).build())
                        .build();
        builder.withChild(leafList);

        // list
        MapEntryNode listChild1 = Builders
                .mapEntryBuilder()
                .withChild(
                        Builders.<Integer> leafBuilder().withNodeIdentifier(getNodeIdentifier("uint32InList"))
                        .withValue(1).build())
                        .withChild(Builders.containerBuilder().withNodeIdentifier(getNodeIdentifier("containerInList")).build())
                        .withNodeIdentifier(
                                new YangInstanceIdentifier.NodeIdentifierWithPredicates(getNodeIdentifier("list").getNodeType(),
                                        Collections.singletonMap(getNodeIdentifier("uint32InList").getNodeType(), (Object) 1)))
                                        .build();

        MapNode list = Builders.mapBuilder().withChild(listChild1).withNodeIdentifier(getNodeIdentifier("list"))
                .build();
        builder.withChild(list);

        AugmentationNode augmentation = Builders
                .augmentationBuilder()
                .withNodeIdentifier(
                        new YangInstanceIdentifier.AugmentationIdentifier(Sets.newHashSet(getQName("augmentUint32"))))
                        .withChild(
                                Builders.<Integer> leafBuilder().withNodeIdentifier(getNodeIdentifier("augmentUint32"))
                                .withValue(11).build()).build();

        builder.withChild(augmentation);

        // This works without schema (adding child from augment as a direct
        // child)
        builder.withChild(Builders.<Integer> leafBuilder().withNodeIdentifier(getNodeIdentifier("augmentUint32"))
                .withValue(11).build());
    }

    @Test
    public void testSchemaAware() throws Exception {
        DataContainerNodeBuilder<YangInstanceIdentifier.NodeIdentifier, ContainerNode> builder = Builders
                .containerBuilder(containerNode);

        LeafSchemaNode schemaNode = (LeafSchemaNode) getSchemaNode(schema, "test", "uint32");
        LeafNode<String> leafChild = Builders.<String> leafBuilder(schemaNode).withValue("String").build();
        builder.withChild(leafChild);

        LeafListSchemaNode leafListSchemaNode = (LeafListSchemaNode) getSchemaNode(schema, "test", "leafList");
        LeafSetNode<Integer> leafList = Builders.<Integer> leafSetBuilder(leafListSchemaNode).withChildValue(1)
                .withChild(Builders.<Integer> leafSetEntryBuilder(leafListSchemaNode).withValue(3).build()).build();
        builder.withChild(leafList);

        ListSchemaNode listSchema = (ListSchemaNode) getSchemaNode(schema, "test", "list");
        LeafSchemaNode uint32InListSchemaNode = (LeafSchemaNode) getSchemaNode(schema, "test", "uint32InList");
        ContainerSchemaNode containerInListSchemaNode = (ContainerSchemaNode) getSchemaNode(schema, "test",
                "containerInList");

        MapEntryNode listChild1 = Builders.mapEntryBuilder(listSchema)
                .withChild(Builders.<Integer> leafBuilder(uint32InListSchemaNode).withValue(1).build())
                .withChild(Builders.containerBuilder(containerInListSchemaNode).build()).build();

        MapNode list = ImmutableMapNodeSchemaAwareBuilder.create(listSchema).withChild(listChild1).build();
        builder.withChild(list);

        LeafSchemaNode augmentUint32SchemaNode = (LeafSchemaNode) getSchemaNode(schema, "test", "augmentUint32");
        AugmentationSchema augmentationSchema = getAugmentationSchemaForChild(containerNode,
                augmentUint32SchemaNode.getQName());

        AugmentationNode augmentation = Builders.augmentationBuilder(augmentationSchema)
                .withChild(Builders.<Integer>leafBuilder(augmentUint32SchemaNode).withValue(11).build()).build();

        builder.withChild(augmentation);

        // This should fail with schema, since the leaf comes from augmentation
        // builder.withChild(ImmutableLeafNodeSchemaAwareBuilder.<Integer>get(augmentUint32SchemaNode).withValue(11).build());

        LeafSchemaNode augumentString1SchemaNode = (LeafSchemaNode) getSchemaNode(schema, "test", "augmentString1");
        LeafSchemaNode augumentString2SchemaNode = (LeafSchemaNode) getSchemaNode(schema, "test", "augmentString2");

        ChoiceSchemaNode choice1SchemaNode = (ChoiceSchemaNode) getSchemaNode(schema, "test", "choice");
        ChoiceNode choice = ImmutableChoiceNodeSchemaAwareBuilder
                .create(choice1SchemaNode)
                .withChild(Builders.<String> leafBuilder(augumentString1SchemaNode).withValue("case1").build())
                // This should fail, since child node belongs to different case
                // .withChild(Builders.<String>leafBuilder(augumentString2SchemaNode).withValue("case2")
                // .build())
                .build();

        ;
        builder.withChild(choice);

        // This should fail, child from case
        // builder.withChild(Builders.<String>leafBuilder(augumentString1SchemaNode).withValue("case1")
        // .build());
    }

    private AugmentationSchema getAugmentationSchemaForChild(final ContainerSchemaNode containerNode, final QName qName) {
        for (AugmentationSchema augmentationSchema : containerNode.getAvailableAugmentations()) {
            if (augmentationSchema.getDataChildByName(qName) != null) {
                return augmentationSchema;
            }
        }
        throw new IllegalStateException("Unable to find child augmentation in " + containerNode);
    }

    private YangInstanceIdentifier.NodeWithValue getNodeWithValueIdentifier(final String localName, final Object value) {
        return new YangInstanceIdentifier.NodeWithValue(getQName(localName), value);
    }

    private QName getQName(final String localName) {
        String namespace = "namespace";
        return new QName(URI.create(namespace), localName);
    }

    private YangInstanceIdentifier.NodeIdentifier getNodeIdentifier(final String localName) {
        return new YangInstanceIdentifier.NodeIdentifier(getQName(localName));
    }

    public static DataSchemaNode getSchemaNode(final SchemaContext context, final String moduleName, final String childNodeName) {
        for (Module module : context.getModules()) {
            if (module.getName().equals(moduleName)) {
                DataSchemaNode found = findChildNode(module.getChildNodes(), childNodeName);
                Preconditions.checkState(found != null, "Unable to find %s", childNodeName);
                return found;
            }
        }
        throw new IllegalStateException("Unable to find child node " + childNodeName);
    }

    private static DataSchemaNode findChildNode(final Iterable<DataSchemaNode> children, final String name) {
        List<DataNodeContainer> containers = Lists.newArrayList();

        for (DataSchemaNode dataSchemaNode : children) {
            if (dataSchemaNode.getQName().getLocalName().equals(name)) {
                return dataSchemaNode;
            }
            if (dataSchemaNode instanceof DataNodeContainer) {
                containers.add((DataNodeContainer) dataSchemaNode);
            } else if (dataSchemaNode instanceof ChoiceSchemaNode) {
                containers.addAll(((ChoiceSchemaNode) dataSchemaNode).getCases());
            }
        }

        for (DataNodeContainer container : containers) {
            DataSchemaNode retVal = findChildNode(container.getChildNodes(), name);
            if (retVal != null) {
                return retVal;
            }
        }

        return null;
    }
}
