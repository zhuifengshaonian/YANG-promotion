/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.transform.base.serializer;

import java.util.Collections;
import org.onos.yangtools.yang.data.api.schema.AnyXmlNode;
import org.onos.yangtools.yang.data.impl.schema.transform.FromNormalizedNodeSerializer;
import org.onos.yangtools.yang.model.api.AnyXmlSchemaNode;

/**
 * Abstract(base) serializer for AnyXmlNodes, serializes elements of type E.
 *
 * @param <E> type of serialized elements
 */
public abstract class AnyXmlNodeBaseSerializer<E> implements
        FromNormalizedNodeSerializer<E, AnyXmlNode, AnyXmlSchemaNode> {

    @Override
    public final Iterable<E> serialize(AnyXmlSchemaNode schema, AnyXmlNode node) {
        return Collections.singletonList(serializeAnyXml(node));
    }

    /**
     * Serialize the inner value of a AnyXmlNode into element of type E.
     *
     * @param node to be serialized
     * @return serialized inner value as an Element
     */
    protected abstract E serializeAnyXml(AnyXmlNode node);
}
