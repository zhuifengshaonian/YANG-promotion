/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.transform.base.serializer;

import java.util.Collections;

import org.onos.yangtools.yang.data.api.schema.LeafSetEntryNode;
import org.onos.yangtools.yang.data.impl.schema.transform.FromNormalizedNodeSerializer;
import org.onos.yangtools.yang.model.api.LeafListSchemaNode;

/**
 * Abstract(base) serializer for LeafSetEntryNodes, serializes elements of type E.
 *
 * @param <E> type of serialized elements
 */
public abstract class LeafSetEntryNodeBaseSerializer<E> implements
        FromNormalizedNodeSerializer<E, LeafSetEntryNode<?>, LeafListSchemaNode> {

    @Override
    public final Iterable<E> serialize(LeafListSchemaNode schema, LeafSetEntryNode<?> node) {
        return Collections.singletonList(serializeLeaf(schema, node));
    }

    /**
     *
     * Serialize the inner value of a LeafSetEntryNode into element of type E.
     *
     * @param node to be serialized
     * @param schema schema for leaf-list
     * @return serialized inner value as an Element
     */
    protected abstract E serializeLeaf(LeafListSchemaNode schema, LeafSetEntryNode<?> node);
}
