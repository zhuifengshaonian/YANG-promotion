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
import org.onos.yangtools.yang.data.api.schema.AugmentationNode;
import org.onos.yangtools.yang.data.impl.schema.Builders;
import org.onos.yangtools.yang.data.impl.schema.builder.api.DataContainerNodeBuilder;
import org.onos.yangtools.yang.data.impl.schema.SchemaUtils;
import org.onos.yangtools.yang.model.api.AugmentationSchema;

final class AugmentationNodeModification extends AbstractContainerNodeModification<AugmentationSchema, AugmentationNode> {

    @Override
    protected QName getQName(AugmentationSchema schema) {
        // FIXME null qname for AUGMENT
        return null;
    }

    @Override
    protected Object findSchemaForChild(AugmentationSchema schema, QName nodeType) {
        return SchemaUtils.findSchemaForChild(schema, nodeType);
    }

    @Override
    protected Object findSchemaForAugment(AugmentationSchema schema, YangInstanceIdentifier.AugmentationIdentifier childToProcessId) {
        throw new UnsupportedOperationException("Augmentation cannot be augmented directly, " + schema);
    }

    @Override
    protected DataContainerNodeBuilder<?, AugmentationNode> getBuilder(AugmentationSchema schema) {
        return Builders.augmentationBuilder(schema);
    }
}
