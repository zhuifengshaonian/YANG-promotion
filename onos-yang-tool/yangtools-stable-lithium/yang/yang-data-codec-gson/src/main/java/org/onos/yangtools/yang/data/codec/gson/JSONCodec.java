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

interface JSONCodec<T> extends Codec<String, T> {
    // FIXME: Unused, remove once we are sure we do not need this anymore.
    boolean needQuotes();


    /**
     * Serialize specified value with specified JsonWriter.
     *
     * @param writer JsonWriter
     * @param value
     */
    void serializeToWriter(JsonWriter writer, T value) throws IOException;
}