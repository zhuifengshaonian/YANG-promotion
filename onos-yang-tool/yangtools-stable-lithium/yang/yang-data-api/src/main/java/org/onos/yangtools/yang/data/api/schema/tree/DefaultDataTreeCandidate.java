/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.api.schema.tree;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier;

/**
 * Default utility implementation of the {@link DataTreeCandidate} contract.
 */
final class DefaultDataTreeCandidate implements DataTreeCandidate {
    private final YangInstanceIdentifier rootPath;
    private final DataTreeCandidateNode rootNode;

    DefaultDataTreeCandidate(final YangInstanceIdentifier rootPath, final DataTreeCandidateNode rootNode) {
        this.rootPath = Preconditions.checkNotNull(rootPath);
        this.rootNode = Preconditions.checkNotNull(rootNode);
    }

    @Override
    public DataTreeCandidateNode getRootNode() {
        return rootNode;
    }

    @Override
    public YangInstanceIdentifier getRootPath() {
        return rootPath;
    }

    @Override
    public final String toString() {
        return MoreObjects.toStringHelper(this).add("rootPath", getRootPath()).add("rootNode", getRootNode()).toString();
    }
}
