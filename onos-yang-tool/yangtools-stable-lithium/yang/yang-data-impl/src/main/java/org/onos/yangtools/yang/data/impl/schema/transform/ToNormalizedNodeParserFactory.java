/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.transform;

import org.onos.yangtools.yang.data.api.schema.AnyXmlNode;
import org.onos.yangtools.yang.data.api.schema.AugmentationNode;
import org.onos.yangtools.yang.data.api.schema.ChoiceNode;
import org.onos.yangtools.yang.data.api.schema.ContainerNode;
import org.onos.yangtools.yang.data.api.schema.LeafNode;
import org.onos.yangtools.yang.data.api.schema.LeafSetEntryNode;
import org.onos.yangtools.yang.data.api.schema.LeafSetNode;
import org.onos.yangtools.yang.data.api.schema.MapEntryNode;
import org.onos.yangtools.yang.data.api.schema.MapNode;
import org.onos.yangtools.yang.data.api.schema.OrderedMapNode;
import org.onos.yangtools.yang.data.api.schema.UnkeyedListEntryNode;
import org.onos.yangtools.yang.data.api.schema.UnkeyedListNode;
import org.onos.yangtools.yang.model.api.AnyXmlSchemaNode;
import org.onos.yangtools.yang.model.api.AugmentationSchema;
import org.onos.yangtools.yang.model.api.ChoiceSchemaNode;
import org.onos.yangtools.yang.model.api.ContainerSchemaNode;
import org.onos.yangtools.yang.model.api.LeafListSchemaNode;
import org.onos.yangtools.yang.model.api.LeafSchemaNode;
import org.onos.yangtools.yang.model.api.ListSchemaNode;

/**
 * Factory for different normalized node parsers.
 *
 * @param <E>
 *            type of element to be parsed into NormalizedNode
 */
public interface ToNormalizedNodeParserFactory<E> {
    ToNormalizedNodeParser<E, AugmentationNode, AugmentationSchema> getAugmentationNodeParser();
    ToNormalizedNodeParser<E, ChoiceNode, ChoiceSchemaNode> getChoiceNodeParser();
    ToNormalizedNodeParser<E, ContainerNode, ContainerSchemaNode> getContainerNodeParser();
    ToNormalizedNodeParser<E, LeafNode<?>, LeafSchemaNode> getLeafNodeParser();
    ToNormalizedNodeParser<E, LeafSetEntryNode<?>, LeafListSchemaNode> getLeafSetEntryNodeParser();
    ToNormalizedNodeParser<E, LeafSetNode<?>, LeafListSchemaNode> getLeafSetNodeParser();
    ToNormalizedNodeParser<E, MapEntryNode, ListSchemaNode> getMapEntryNodeParser();
    ToNormalizedNodeParser<E, MapNode, ListSchemaNode> getMapNodeParser();
    ToNormalizedNodeParser<E, UnkeyedListNode, ListSchemaNode> getUnkeyedListNodeParser();
    ToNormalizedNodeParser<E, UnkeyedListEntryNode, ListSchemaNode> getUnkeyedListEntryNodeParser();
    ToNormalizedNodeParser<E, AnyXmlNode, AnyXmlSchemaNode> getAnyXmlNodeParser();
    ToNormalizedNodeParser<E, OrderedMapNode, ListSchemaNode> getOrderedListNodeParser();
}
