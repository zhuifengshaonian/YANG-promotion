/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.transform.base.parser;

import org.onos.yangtools.yang.data.api.YangInstanceIdentifier.NodeIdentifier;
import org.onos.yangtools.yang.data.api.schema.UnkeyedListEntryNode;
import org.onos.yangtools.yang.data.api.schema.UnkeyedListNode;
import org.onos.yangtools.yang.data.impl.schema.Builders;
import org.onos.yangtools.yang.data.impl.schema.builder.api.CollectionNodeBuilder;
import org.onos.yangtools.yang.model.api.ListSchemaNode;

/**
 * Abstract(base) parser for UnkeyedListNodes, parses elements of type E.
 *
 * @param <E>
 *            type of elements to be parsed
 */
public abstract class UnkeyedListNodeBaseParser<E> extends
        ListNodeBaseParser<E, UnkeyedListEntryNode, UnkeyedListNode, ListSchemaNode> {

    public UnkeyedListNodeBaseParser(final BuildingStrategy<NodeIdentifier, UnkeyedListNode> buildingStrategy) {
        super(buildingStrategy);
    }

    public UnkeyedListNodeBaseParser() {
    }

    @Override
    protected CollectionNodeBuilder<UnkeyedListEntryNode, UnkeyedListNode> provideBuilder(ListSchemaNode schema) {
        CollectionNodeBuilder<UnkeyedListEntryNode, UnkeyedListNode> listBuilder = Builders.unkeyedListBuilder();
        return listBuilder.withNodeIdentifier(new NodeIdentifier(schema.getQName()));
    }
}
