/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.tree;

import org.onos.yangtools.yang.data.api.YangInstanceIdentifier;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier.PathArgument;
import org.onos.yangtools.yang.data.api.schema.tree.DataTreeCandidateNode;
import org.onos.yangtools.yang.data.api.schema.tree.spi.TreeNode;

final class InMemoryDataTreeCandidate extends AbstractDataTreeCandidate {

    private static final class RootNode extends AbstractModifiedNodeBasedCandidateNode {
        public RootNode(final ModifiedNode mod, final TreeNode oldMeta, final TreeNode newMeta) {
            super(mod, oldMeta, newMeta);
        }

        @Override
        public PathArgument getIdentifier() {
            throw new IllegalStateException("Attempted to get identifier of the root node");
        }
    }

    private final RootNode root;

    InMemoryDataTreeCandidate(final YangInstanceIdentifier rootPath, final ModifiedNode modificationRoot,
            final TreeNode beforeRoot, final TreeNode afterRoot) {
        super(rootPath);
        this.root = new RootNode(modificationRoot, beforeRoot, afterRoot);
    }

    @Override
    protected TreeNode getTipRoot() {
        return root.getNewMeta();
    }

    TreeNode getBeforeRoot() {
        return root.getOldMeta();
    }

    @Override
    public DataTreeCandidateNode getRootNode() {
        return root;
    }
}
