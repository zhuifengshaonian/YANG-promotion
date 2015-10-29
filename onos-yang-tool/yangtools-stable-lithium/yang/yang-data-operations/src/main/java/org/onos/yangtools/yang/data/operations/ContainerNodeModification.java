/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.operations;

import org.onos.yangtools.yang.common.QName;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier;
import org.onos.yangtools.yang.data.api.schema.ContainerNode;
import org.onos.yangtools.yang.data.impl.schema.Builders;
import org.onos.yangtools.yang.data.impl.schema.builder.api.DataContainerNodeBuilder;
import org.onos.yangtools.yang.data.impl.schema.SchemaUtils;
import org.onos.yangtools.yang.model.api.ContainerSchemaNode;

final class ContainerNodeModification extends AbstractContainerNodeModification<ContainerSchemaNode, ContainerNode> {

    // FIXME normalized nodes as a result from merge contain attributes e.g. operation="merge" from modification

    @Override
    protected QName getQName(ContainerSchemaNode schema) {
        return schema.getQName();
    }

    @Override
    protected Object findSchemaForChild(ContainerSchemaNode schema, QName nodeType) {
        return SchemaUtils.findSchemaForChild(schema, nodeType);
    }

    @Override
    protected Object findSchemaForAugment(ContainerSchemaNode schema,
            YangInstanceIdentifier.AugmentationIdentifier childToProcessId) {
        return SchemaUtils.findSchemaForAugment(schema, childToProcessId.getPossibleChildNames());
    }

    @Override
    protected DataContainerNodeBuilder<?, ContainerNode> getBuilder(ContainerSchemaNode schema) {
        return Builders.containerBuilder(schema);
    }

}
