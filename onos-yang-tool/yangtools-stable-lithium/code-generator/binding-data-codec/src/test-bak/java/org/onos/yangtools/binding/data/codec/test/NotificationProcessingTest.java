/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 *  and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.binding.data.codec.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.google.common.collect.ImmutableList;
import javassist.ClassPool;
import org.junit.Before;
import org.junit.Test;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.binding.rev140701.TwoLevelListChanged;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.binding.rev140701.TwoLevelListChangedBuilder;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.binding.rev140701.two.level.list.TopLevelListBuilder;
import org.onos.yang.gen.v1.urn.onos.params.xml.ns.yang.yangtools.test.binding.rev140701.two.level.list.TopLevelListKey;
import org.onos.yangtools.binding.data.codec.gen.impl.StreamWriterGenerator;
import org.onos.yangtools.binding.data.codec.impl.BindingNormalizedNodeCodecRegistry;
import org.onos.yangtools.sal.binding.generator.util.JavassistUtils;
import org.onos.yangtools.yang.binding.Notification;
import org.onos.yangtools.yang.data.api.schema.ContainerNode;
import org.onos.yangtools.yang.model.api.SchemaPath;

public class NotificationProcessingTest extends AbstractBindingRuntimeTest {

    private BindingNormalizedNodeCodecRegistry registry;

    @Override
    @Before
    public void setup() {
        super.setup();
        final JavassistUtils utils = JavassistUtils.forClassPool(ClassPool.getDefault());
        registry = new BindingNormalizedNodeCodecRegistry(StreamWriterGenerator.create(utils));
        registry.onBindingRuntimeContextUpdated(getRuntimeContext());
    }

    private TwoLevelListChanged createTestData() {
        final TwoLevelListChangedBuilder tb = new TwoLevelListChangedBuilder();
        tb.setTopLevelList(ImmutableList.of(new TopLevelListBuilder().setKey(new TopLevelListKey("test")).build()));
        return tb.build();
    }

    @Test
    public void testNotificationToNormalized() {
        final TwoLevelListChanged bindingOriginal = createTestData();
        final ContainerNode dom = registry.toNormalizedNodeNotification(bindingOriginal);
        assertNotNull("Serialization must not return null obejct.",dom);
        assertEquals(TwoLevelListChanged.QNAME,dom.getIdentifier().getNodeType());

        final Notification bindingDeserialized = registry.fromNormalizedNodeNotification(SchemaPath.create(true, TwoLevelListChanged.QNAME),dom);
        assertNotNull(bindingDeserialized);
        assertTrue(bindingDeserialized instanceof TwoLevelListChanged);
        assertEquals(bindingOriginal,bindingDeserialized);
    }

}
