/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.transform.base.serializer;

import java.util.Set;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier;
import org.onos.yangtools.yang.data.api.schema.AugmentationNode;
import org.onos.yangtools.yang.data.api.schema.DataContainerChild;
import org.onos.yangtools.yang.data.api.schema.DataContainerNode;
import org.onos.yangtools.yang.data.impl.schema.SchemaUtils;
import org.onos.yangtools.yang.model.api.AugmentationSchema;
import org.onos.yangtools.yang.model.api.AugmentationTarget;
import org.onos.yangtools.yang.model.api.DataSchemaNode;
import org.onos.yangtools.yang.model.api.ListSchemaNode;

/**
 * Abstract(base) serializer for ListEntryNodes (MapEntryNode, UnkeyedListEntryNode), serializes elements of type E.
 *
 * @param <E>
 *            type of serialized elements
 */
public abstract class ListEntryNodeBaseSerializer<E, N extends DataContainerNode<?>> extends
        BaseDispatcherSerializer<E, N, ListSchemaNode> {

    @Override
    protected final DataSchemaNode getSchemaForChild(ListSchemaNode schema,
            DataContainerChild<? extends YangInstanceIdentifier.PathArgument, ?> childNode) {
        return SchemaUtils.findSchemaForChild(schema, childNode.getNodeType());
    }

    @Override
    protected final AugmentationSchema getAugmentedCase(ListSchemaNode schema, AugmentationNode augmentationNode) {
        return SchemaUtils.findSchemaForAugment(schema, augmentationNode.getIdentifier().getPossibleChildNames());
    }

    @Override
    protected final Set<DataSchemaNode> getRealSchemasForAugment(ListSchemaNode schema,
            AugmentationSchema augmentationSchema) {
        return SchemaUtils.getRealSchemasForAugment((AugmentationTarget) schema, augmentationSchema);
    }

}
