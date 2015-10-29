/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.builder.api;

import org.onos.yangtools.yang.data.api.YangInstanceIdentifier;
import org.onos.yangtools.yang.data.api.schema.NormalizedNode;

public interface NormalizedNodeBuilder<I extends YangInstanceIdentifier.PathArgument, V, R extends NormalizedNode<I, ?>> {

    NormalizedNodeBuilder<I, V, R> withValue(V value);

    NormalizedNodeBuilder<I, V, R> withNodeIdentifier(I nodeIdentifier);

    R build();
}