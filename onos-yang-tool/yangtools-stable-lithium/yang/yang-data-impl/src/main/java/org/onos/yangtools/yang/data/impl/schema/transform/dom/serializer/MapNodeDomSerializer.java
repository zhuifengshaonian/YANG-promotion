/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.transform.dom.serializer;

import org.onos.yangtools.yang.data.api.schema.MapEntryNode;
import org.onos.yangtools.yang.data.api.schema.MapNode;
import org.onos.yangtools.yang.data.impl.schema.transform.FromNormalizedNodeSerializer;
import org.onos.yangtools.yang.data.impl.schema.transform.base.serializer.ListNodeBaseSerializer;
import org.onos.yangtools.yang.model.api.ListSchemaNode;
import org.w3c.dom.Element;

final class MapNodeDomSerializer extends ListNodeBaseSerializer<Element, MapNode, MapEntryNode> {

    private final FromNormalizedNodeSerializer<Element, MapEntryNode, ListSchemaNode> mapEntrySerializer;

    MapNodeDomSerializer(MapEntryNodeDomSerializer mapEntrySerializer) {
        this.mapEntrySerializer = mapEntrySerializer;
    }

    @Override
    protected FromNormalizedNodeSerializer<Element, MapEntryNode, ListSchemaNode> getListEntryNodeSerializer() {
        return mapEntrySerializer;
    }
}
