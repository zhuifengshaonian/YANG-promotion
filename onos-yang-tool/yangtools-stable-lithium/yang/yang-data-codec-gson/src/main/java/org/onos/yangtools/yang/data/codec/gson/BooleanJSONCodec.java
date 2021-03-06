/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.codec.gson;

import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import org.onos.yangtools.concepts.Codec;

/**
 * A {@link JSONCodec} which does not need double quotes in output representation.
 *
 * @param <T> Deserialized value type
 */
final class BooleanJSONCodec extends AbstractJSONCodec<Boolean> {
    BooleanJSONCodec(final Codec<String, Boolean> codec) {
        super(codec);
    }

    @Override
    public boolean needQuotes() {
        return false;
    }

    /**
     * Serialize specified value with specified JsonWriter.
     *
     * @param writer JsonWriter
     * @param value
     */
    @Override
    public void serializeToWriter(JsonWriter writer, Boolean value) throws IOException {
        writer.value(value);
    }
}
