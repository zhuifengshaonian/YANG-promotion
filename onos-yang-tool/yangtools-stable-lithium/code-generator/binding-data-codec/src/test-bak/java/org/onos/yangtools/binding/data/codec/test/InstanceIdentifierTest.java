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
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.augment.rev140709.TreeComplexUsesAugment;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.augment.rev140709.TreeLeafOnlyAugment;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.binding.rev140701.Top;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.binding.rev140701.two.level.list.TopLevelList;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.binding.rev140701.two.level.list.TopLevelListKey;
import org.onos.yangtools.binding.data.codec.gen.impl.StreamWriterGenerator;
import org.onos.yangtools.binding.data.codec.impl.BindingNormalizedNodeCodecRegistry;
import org.onos.yangtools.sal.binding.generator.util.JavassistUtils;
import org.onos.yangtools.yang.binding.InstanceIdentifier;
import org.onos.yangtools.yang.common.QName;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier.AugmentationIdentifier;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier.PathArgument;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class InstanceIdentifierTest extends AbstractBindingRuntimeTest {

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
    public void testComplexAugmentationSerialization() {
        YangInstanceIdentifier yangII = registry.toYangInstanceIdentifier(BA_TREE_COMPLEX_USES);
        PathArgument lastArg = yangII.getLastPathArgument();
        assertTrue("Last argument should be AugmentationIdentifier", lastArg instanceof AugmentationIdentifier);
        InstanceIdentifier<?> bindingII = registry.fromYangInstanceIdentifier(yangII);
        assertEquals(BA_TREE_COMPLEX_USES, bindingII);
    }

    @Test
    public void testLeafOnlyAugmentationSerialization() {
        PathArgument leafOnlyLastArg = registry.toYangInstanceIdentifier(BA_TREE_LEAF_ONLY).getLastPathArgument();
        assertTrue("Last argument should be AugmentationIdentifier", leafOnlyLastArg instanceof AugmentationIdentifier);
        assertTrue(((AugmentationIdentifier) leafOnlyLastArg).getPossibleChildNames().contains(SIMPLE_VALUE_QNAME));
    }

}
