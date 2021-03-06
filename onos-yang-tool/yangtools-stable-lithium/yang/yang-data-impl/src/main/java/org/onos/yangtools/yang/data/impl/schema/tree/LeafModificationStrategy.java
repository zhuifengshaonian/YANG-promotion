/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.tree;

import org.onos.yangtools.yang.data.api.schema.LeafNode;
import org.onos.yangtools.yang.data.api.schema.tree.TreeType;
import org.onos.yangtools.yang.model.api.LeafSchemaNode;

final class LeafModificationStrategy extends AbstractValueNodeModificationStrategy<LeafSchemaNode> {
    @SuppressWarnings({ "unchecked", "rawtypes" })
    LeafModificationStrategy(final LeafSchemaNode schema, final TreeType treeType) {
        super(schema, (Class) LeafNode.class);
    }
}