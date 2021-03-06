/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.codec.gson;

import com.google.common.base.Preconditions;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier.NodeIdentifier;
import org.onos.yangtools.yang.model.api.SchemaContext;

/**
 * A single recursion level of {@link JSONNormalizedNodeStreamWriter} representing
 * a list.
 */
final class JSONStreamWriterListContext extends JSONStreamWriterQNameContext {
    protected JSONStreamWriterListContext(final JSONStreamWriterContext parent, final NodeIdentifier id) {
        super(Preconditions.checkNotNull(parent), id.getNodeType(), false);
    }

    @Override
    protected void emitStart(final SchemaContext schema, final JsonWriter writer) throws IOException {
        writeMyJsonIdentifier(schema, writer, getQName());
        writer.beginArray();
    }

    @Override
    protected void emitEnd(final JsonWriter writer) throws IOException {
        writer.endArray();
    }
}
