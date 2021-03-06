/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.api.schema.tree;

import org.onos.yangtools.yang.data.api.YangInstanceIdentifier;

/**
 * Factory interface for creating data trees.
 */
public interface DataTreeFactory {
    /**
     * Create a new data tree.
     *
     * @return A data tree instance.
     */
    DataTree create();

    /**
     * Create a new data tree.
     *
     * @return A data tree instance.
     */
    DataTree create(TreeType type);

    /**
     * Create a new data tree rooted at a particular node.
     *
     * @return A data tree instance.
     */
    DataTree create(TreeType treeType, YangInstanceIdentifier rootPath);
}
