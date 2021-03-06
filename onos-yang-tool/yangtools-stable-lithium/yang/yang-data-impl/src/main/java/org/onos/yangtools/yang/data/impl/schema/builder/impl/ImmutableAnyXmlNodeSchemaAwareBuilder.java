/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.builder.impl;

import javax.xml.transform.dom.DOMSource;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier;
import org.onos.yangtools.yang.data.api.schema.AnyXmlNode;
import org.onos.yangtools.yang.data.impl.schema.builder.api.NormalizedNodeAttrBuilder;
import org.onos.yangtools.yang.model.api.AnyXmlSchemaNode;

public final class ImmutableAnyXmlNodeSchemaAwareBuilder extends ImmutableAnyXmlNodeBuilder {

    private ImmutableAnyXmlNodeSchemaAwareBuilder(AnyXmlSchemaNode schema) {
        super.withNodeIdentifier(new YangInstanceIdentifier.NodeIdentifier(schema.getQName()));
    }

    public static NormalizedNodeAttrBuilder<YangInstanceIdentifier.NodeIdentifier, DOMSource, AnyXmlNode> create(AnyXmlSchemaNode schema) {
        return new ImmutableAnyXmlNodeSchemaAwareBuilder(schema);
    }

    @Override
    public NormalizedNodeAttrBuilder<YangInstanceIdentifier.NodeIdentifier, DOMSource, AnyXmlNode> withValue(DOMSource value) {
        return super.withValue(value);
    }

    @Override
    public NormalizedNodeAttrBuilder<YangInstanceIdentifier.NodeIdentifier, DOMSource, AnyXmlNode> withNodeIdentifier(YangInstanceIdentifier.NodeIdentifier nodeIdentifier) {
        throw new UnsupportedOperationException("Node identifier created from schema");
    }
}
