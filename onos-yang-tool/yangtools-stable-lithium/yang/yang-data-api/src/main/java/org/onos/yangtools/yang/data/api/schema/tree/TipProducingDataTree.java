/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.api.schema.tree;

/**
 * A {@link DataTree} which produces {@link DataTreeCandidateTip} candidates,
 * thus allowing existence of multiple candidates, which can be {@link #commit(DataTreeCandidate)}ed
 * in sequence.
 */
public interface TipProducingDataTree extends DataTree {
    @Override
    DataTreeCandidateTip prepare(DataTreeModification modification);
}
