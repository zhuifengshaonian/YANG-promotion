/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.transform.dom.parser;

import org.onos.yangtools.yang.data.api.YangInstanceIdentifier;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier.NodeIdentifier;
import org.onos.yangtools.yang.data.api.schema.UnkeyedListEntryNode;
import org.onos.yangtools.yang.data.impl.schema.Builders;
import org.onos.yangtools.yang.data.impl.schema.builder.api.DataContainerNodeBuilder;
import org.onos.yangtools.yang.data.impl.schema.transform.base.parser.NodeParserDispatcher;
import org.onos.yangtools.yang.model.api.ListSchemaNode;
import org.w3c.dom.Element;

final class UnkeyedListEntryNodeDomParser extends ListEntryNodeDomParser<YangInstanceIdentifier.NodeIdentifier, UnkeyedListEntryNode> {

    UnkeyedListEntryNodeDomParser(final NodeParserDispatcher<Element> dispatcher) {
        super(dispatcher);
    }

    UnkeyedListEntryNodeDomParser(final BuildingStrategy<NodeIdentifier, UnkeyedListEntryNode> buildingStrategy, final NodeParserDispatcher<Element> dispatcher) {
        super(buildingStrategy, dispatcher);
    }

    @Override
    protected final DataContainerNodeBuilder<YangInstanceIdentifier.NodeIdentifier, UnkeyedListEntryNode> getBuilder(
            ListSchemaNode schema) {
        return Builders.unkeyedListEntryBuilder().withNodeIdentifier(new NodeIdentifier(schema.getQName()));
    }

}
