/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.codec;

import com.google.common.base.Optional;
import org.onos.yangtools.yang.data.api.codec.Uint8Codec;
import org.onos.yangtools.yang.model.api.type.UnsignedIntegerTypeDefinition;

class Uint8StringCodec extends AbstractIntegerStringCodec<Short, UnsignedIntegerTypeDefinition> implements
        Uint8Codec<String> {

    protected Uint8StringCodec(final Optional<UnsignedIntegerTypeDefinition> typeDef) {
        super(typeDef, extractRange(typeDef.orNull()), Short.class);
    }

    @Override
    public final String serialize(final Short data) {
        return data == null ? "" : data.toString();
    }

    @Override
    public final Short deserialize(final String stringRepresentation, final int base) {
        return Short.valueOf(stringRepresentation, base);
    }

    @Override
    protected Short convertValue(final Number value) {
        return value.shortValue();
    }
}
