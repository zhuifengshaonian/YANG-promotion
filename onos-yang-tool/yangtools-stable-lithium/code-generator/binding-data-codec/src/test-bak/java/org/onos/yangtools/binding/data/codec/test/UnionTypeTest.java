/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.onos.yangtools.binding.data.codec.test;

import javassist.ClassPool;
import org.junit.Assert;
import org.junit.Test;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.union.rev150121.TopLevel;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.union.rev150121.TopLevelBuilder;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.union.rev150121.Wrapper;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.union.rev150121.WrapperBuilder;
import org.onos.yangtools.binding.data.codec.gen.impl.StreamWriterGenerator;
import org.onos.yangtools.binding.data.codec.impl.BindingNormalizedNodeCodecRegistry;
import org.onos.yangtools.sal.binding.generator.util.JavassistUtils;
import org.onos.yangtools.yang.binding.InstanceIdentifier;
import org.onos.yangtools.yang.common.QName;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier;
import org.onos.yangtools.yang.data.api.schema.ContainerNode;
import org.onos.yangtools.yang.data.api.schema.NormalizedNode;
import org.onos.yangtools.yang.data.impl.schema.ImmutableNodes;
import org.onos.yangtools.yang.data.impl.schema.builder.impl.ImmutableContainerNodeBuilder;

public class UnionTypeTest extends AbstractBindingRuntimeTest {

    private static final String testString = "testtesttest";

    public static final QName WRAPPER_QNAME = QName.create("urn:onos:params:xml:ns:yang:yangtools:test:union", "2015-01-21", "wrapper");
    public static final QName WRAP_LEAF_QNAME = QName.create(WRAPPER_QNAME, "wrap");

    private BindingNormalizedNodeCodecRegistry registry;

    @Override
    public void setup() {
        super.setup();
        JavassistUtils utils = JavassistUtils.forClassPool(ClassPool.getDefault());
        registry = new BindingNormalizedNodeCodecRegistry(StreamWriterGenerator.create(utils));
        registry.onBindingRuntimeContextUpdated(getRuntimeContext());
    }

    @Test
    public void unionTest() {
        TopLevel topLevel = TopLevelBuilder.getDefaultInstance(testString);
        Wrapper wrapper = new WrapperBuilder().setWrap(topLevel).build();
        NormalizedNode<?, ?> topLevelEntry = registry.toNormalizedNode(InstanceIdentifier.builder(Wrapper.class).build(), wrapper).getValue();

        ContainerNode containerNode = ImmutableContainerNodeBuilder.create()
                .withNodeIdentifier(new YangInstanceIdentifier.NodeIdentifier(WRAPPER_QNAME))
                .withChild(ImmutableNodes.leafNode(WRAP_LEAF_QNAME, testString))
                .build();
        Assert.assertEquals(topLevelEntry, containerNode);
    }
}
