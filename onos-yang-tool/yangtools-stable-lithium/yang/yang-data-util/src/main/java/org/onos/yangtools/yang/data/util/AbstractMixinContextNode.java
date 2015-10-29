/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.util;

import org.onos.yangtools.yang.data.api.YangInstanceIdentifier.PathArgument;
import org.onos.yangtools.yang.model.api.DataSchemaNode;

abstract class AbstractMixinContextNode<T extends PathArgument> extends
        AbstractInteriorContextNode<T> {

    protected AbstractMixinContextNode(final T identifier, final DataSchemaNode schema) {
        super(identifier, schema);
    }

    @Override
    public final boolean isMixin() {
        return true;
    }

}