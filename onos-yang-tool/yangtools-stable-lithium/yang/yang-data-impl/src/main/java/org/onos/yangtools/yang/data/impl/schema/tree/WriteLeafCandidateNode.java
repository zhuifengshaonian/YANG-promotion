/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.tree;

import com.google.common.base.Optional;
import org.onos.yangtools.yang.data.api.schema.NormalizedNode;

final class WriteLeafCandidateNode extends AbstractWriteCandidate {
    WriteLeafCandidateNode(final NormalizedNode<?, ?> data) {
        super(data);
    }

    @Override
    public Optional<NormalizedNode<?, ?>> getDataBefore() {
        return Optional.absent();
    }
}