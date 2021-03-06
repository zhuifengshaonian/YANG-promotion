/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.transform.dom.serializer;

import java.util.Collections;

import org.onos.yangtools.yang.data.api.schema.ContainerNode;
import org.onos.yangtools.yang.data.impl.codec.xml.XmlDocumentUtils;
import org.onos.yangtools.yang.data.impl.schema.transform.base.serializer.ContainerNodeBaseSerializer;
import org.onos.yangtools.yang.data.impl.schema.transform.base.serializer.NodeSerializerDispatcher;
import org.onos.yangtools.yang.model.api.ContainerSchemaNode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

final class ContainerNodeDomSerializer extends
        ContainerNodeBaseSerializer<Element> {

    private final NodeSerializerDispatcher<Element> dispatcher;
    private final Document doc;

    ContainerNodeDomSerializer(final Document doc, final NodeSerializerDispatcher<Element> dispatcher) {
        this.doc = Preconditions.checkNotNull(doc);
        this.dispatcher = Preconditions.checkNotNull(dispatcher);
    }

    @Override
    public Iterable<Element> serialize(ContainerSchemaNode schema, ContainerNode containerNode) {
        Element itemEl = XmlDocumentUtils.createElementFor(doc, containerNode);

        for (Element element : super.serialize(schema, containerNode)) {
            itemEl.appendChild(element);
        }
        return Collections.singletonList(itemEl);
    }

    @Override
    protected NodeSerializerDispatcher<Element> getNodeDispatcher() {
        return dispatcher;
    }

}
