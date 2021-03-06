/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.api.codec;

import java.math.BigInteger;

import org.onos.yangtools.concepts.Codec;

import com.google.common.primitives.UnsignedLong;

/**
 * FIXME: Should be changed to {@link UnsignedLong}
 *
 * @author ttkacik
 *
 * @param <T>
 */
public interface Uint64Codec<T> extends Codec<T,BigInteger> {
    @Override
    T serialize(BigInteger data);

    @Override
    BigInteger deserialize(T data);
}
