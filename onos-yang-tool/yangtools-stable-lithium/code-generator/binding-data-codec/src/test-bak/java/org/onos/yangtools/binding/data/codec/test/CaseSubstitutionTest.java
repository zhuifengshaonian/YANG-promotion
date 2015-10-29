/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.binding.data.codec.test;

import javassist.ClassPool;
import org.junit.Test;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.augment.rev140709.RpcComplexUsesAugment;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.augment.rev140709.RpcComplexUsesAugmentBuilder;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.augment.rev140709.TreeComplexUsesAugment;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.augment.rev140709.TreeLeafOnlyAugment;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.augment.rev140709.complex.from.grouping.ContainerWithUsesBuilder;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.augment.rev140709.complex.from.grouping.ListViaUses;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.augment.rev140709.put.top.input.top.level.list.choice.in.list.ComplexViaUsesWithDifferentNameBuilder;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.augment.rev140709.top.top.level.list.choice.in.list.ComplexViaUsesBuilder;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.binding.rev140701.Top;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.binding.rev140701.two.level.list.TopLevelList;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.binding.rev140701.two.level.list.TopLevelListBuilder;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.binding.rev140701.two.level.list.TopLevelListKey;
import org.onos.yangtools.binding.data.codec.gen.impl.StreamWriterGenerator;
import org.onos.yangtools.binding.data.codec.impl.BindingNormalizedNodeCodecRegistry;
import org.onos.yangtools.sal.binding.generator.util.JavassistUtils;
import org.onos.yangtools.yang.binding.InstanceIdentifier;
import org.onos.yangtools.yang.common.QName;
import org.onos.yangtools.yang.data.api.schema.NormalizedNode;

import java.util.Collections;

import static org.junit.Assert.assertEquals;


public class CaseSubstitutionTest extends AbstractBindingRuntimeTest {

    private static final TopLevelListKey TOP_FOO_KEY = new TopLevelListKey("foo");
    private static final InstanceIdentifier<TopLevelList> BA_TOP_LEVEL_LIST = InstanceIdentifier.builder(Top.class)
            .child(TopLevelList.class, TOP_FOO_KEY).build();
    private static final InstanceIdentifier<TreeLeafOnlyAugment> BA_TREE_LEAF_ONLY = BA_TOP_LEVEL_LIST
            .augmentation(TreeLeafOnlyAugment.class);
    private static final InstanceIdentifier<TreeComplexUsesAugment> BA_TREE_COMPLEX_USES = BA_TOP_LEVEL_LIST
            .augmentation(TreeComplexUsesAugment.class);
    private static final QName SIMPLE_VALUE_QNAME = QName.create(TreeComplexUsesAugment.QNAME, "simple-value");

    private BindingNormalizedNodeCodecRegistry registry;

    @Override
    public void setup() {
        super.setup();
        JavassistUtils utils = JavassistUtils.forClassPool(ClassPool.getDefault());
        registry = new BindingNormalizedNodeCodecRegistry(StreamWriterGenerator.create(utils));
        registry.onBindingRuntimeContextUpdated(getRuntimeContext());
    }

    @Test
    public void choiceInGroupingSubstituted() {
        TopLevelList baRpc = new TopLevelListBuilder()
            .setKey(TOP_FOO_KEY)
            .setChoiceInList(new ComplexViaUsesWithDifferentNameBuilder(createComplexData()).build())
            .build();
        TopLevelList baTree = new TopLevelListBuilder()
            .setKey(TOP_FOO_KEY)
            .setChoiceInList(new ComplexViaUsesBuilder(createComplexData()).build())
            .build();
        NormalizedNode<?, ?> domTreeEntry = registry.toNormalizedNode(BA_TOP_LEVEL_LIST, baTree).getValue();
        NormalizedNode<?, ?> domRpcEntry = registry.toNormalizedNode(BA_TOP_LEVEL_LIST, baRpc).getValue();
        assertEquals(domTreeEntry, domRpcEntry);
    }

    private RpcComplexUsesAugment createComplexData() {
        return new RpcComplexUsesAugmentBuilder()
        .setContainerWithUses(new ContainerWithUsesBuilder()
            .setLeafFromGrouping("foo")
        .build())
        .setListViaUses(Collections.<ListViaUses>emptyList())
        .build();
    }

}
