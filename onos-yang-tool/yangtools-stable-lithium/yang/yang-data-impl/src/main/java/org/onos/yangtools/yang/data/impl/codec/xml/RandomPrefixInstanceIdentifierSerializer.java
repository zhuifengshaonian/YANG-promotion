/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.codec.xml;

import java.net.URI;
import java.util.Map;
import org.onos.yangtools.yang.common.QName;
import org.onos.yangtools.yang.data.util.AbstractStringInstanceIdentifierCodec;
import org.onos.yangtools.yang.data.util.DataSchemaContextTree;
import org.onos.yangtools.yang.model.api.SchemaContext;

final class RandomPrefixInstanceIdentifierSerializer extends AbstractStringInstanceIdentifierCodec {
    private final RandomPrefix prefixes = new RandomPrefix();
    private final DataSchemaContextTree schemaTree;


    RandomPrefixInstanceIdentifierSerializer(SchemaContext ctx) {
        schemaTree = DataSchemaContextTree.from(ctx);
    }

    Iterable<Map.Entry<URI, String>> getPrefixes() {
        return prefixes.getPrefixes();
    }

    @Override
    protected String prefixForNamespace(final URI namespace) {
        return prefixes.encodePrefix(namespace);
    }

    @Override
    protected QName createQName(final String prefix, final String localName) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Override
    protected DataSchemaContextTree getDataContextTree() {
        return schemaTree;
    }

}
