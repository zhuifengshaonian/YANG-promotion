/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.api.codec;

import org.onos.yangtools.concepts.Codec;

public interface EmptyCodec<T>  extends Codec<T,Void> {
    @Override
    T serialize(Void data);

    @Override
    Void deserialize(T data);
}
