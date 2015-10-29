/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.transform.dom.parser;

import org.onos.yangtools.yang.data.api.YangInstanceIdentifier;
import org.onos.yangtools.yang.data.api.schema.UnkeyedListEntryNode;
import org.onos.yangtools.yang.data.api.schema.UnkeyedListNode;
import org.onos.yangtools.yang.data.impl.schema.transform.ToNormalizedNodeParser;
import org.onos.yangtools.yang.data.impl.schema.transform.base.parser.UnkeyedListNodeBaseParser;
import org.onos.yangtools.yang.model.api.ListSchemaNode;
import org.w3c.dom.Element;

final class UnkeyedListNodeDomParser extends UnkeyedListNodeBaseParser<Element> {

    private final UnkeyedListEntryNodeDomParser unkeyedListEntryNodeParser;

    UnkeyedListNodeDomParser(UnkeyedListEntryNodeDomParser unkeyedListEntryNodeParser) {
        this.unkeyedListEntryNodeParser = unkeyedListEntryNodeParser;
    }

    UnkeyedListNodeDomParser(final BuildingStrategy<YangInstanceIdentifier.NodeIdentifier, UnkeyedListNode> buildingStrategy, final UnkeyedListEntryNodeDomParser unkeyedListEntryNodeParser) {
        super(buildingStrategy);
        this.unkeyedListEntryNodeParser = unkeyedListEntryNodeParser;
    }

    @Override
    protected ToNormalizedNodeParser<Element, UnkeyedListEntryNode, ListSchemaNode> getListEntryNodeParser() {
        return unkeyedListEntryNodeParser;
    }

}
