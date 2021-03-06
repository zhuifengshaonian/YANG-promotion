/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.builder.impl;

import org.onos.yangtools.yang.data.api.YangInstanceIdentifier;
import org.onos.yangtools.yang.data.api.schema.AugmentationNode;
import org.onos.yangtools.yang.data.api.schema.DataContainerChild;
import org.onos.yangtools.yang.data.impl.schema.builder.api.DataContainerNodeBuilder;
import org.onos.yangtools.yang.data.impl.schema.builder.impl.valid.DataNodeContainerValidator;
import org.onos.yangtools.yang.data.impl.schema.SchemaUtils;
import org.onos.yangtools.yang.model.api.AugmentationSchema;

public class ImmutableAugmentationNodeSchemaAwareBuilder extends ImmutableAugmentationNodeBuilder {

    private final DataNodeContainerValidator validator;

    protected ImmutableAugmentationNodeSchemaAwareBuilder(AugmentationSchema schema) {
        this.validator = new DataNodeContainerValidator(schema);
        super.withNodeIdentifier(SchemaUtils.getNodeIdentifierForAugmentation(schema));
    }

    @Override
    public DataContainerNodeBuilder<YangInstanceIdentifier.AugmentationIdentifier, AugmentationNode> withNodeIdentifier(YangInstanceIdentifier.AugmentationIdentifier nodeIdentifier) {
        throw new UnsupportedOperationException("Node identifier created from schema");
    }

    @Override
    public DataContainerNodeBuilder<YangInstanceIdentifier.AugmentationIdentifier, AugmentationNode> withChild(DataContainerChild<?, ?> child) {
        return super.withChild(validator.validateChild(child));
    }

    public static DataContainerNodeBuilder<YangInstanceIdentifier.AugmentationIdentifier, AugmentationNode> create(AugmentationSchema schema) {
        return new ImmutableAugmentationNodeSchemaAwareBuilder(schema);
    }


}
