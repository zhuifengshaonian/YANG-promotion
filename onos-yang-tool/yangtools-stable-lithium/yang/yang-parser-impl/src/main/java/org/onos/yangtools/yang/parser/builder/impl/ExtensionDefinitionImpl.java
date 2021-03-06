/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.builder.impl;

import com.google.common.collect.ImmutableList;
import java.util.List;
import org.onos.yangtools.yang.common.QName;
import org.onos.yangtools.yang.model.api.ExtensionDefinition;
import org.onos.yangtools.yang.model.api.SchemaPath;
import org.onos.yangtools.yang.model.api.Status;
import org.onos.yangtools.yang.model.api.UnknownSchemaNode;

final class ExtensionDefinitionImpl implements ExtensionDefinition {
    private final QName qname;
    String argument;
    private final SchemaPath schemaPath;
    String description;
    String reference;
    Status status;
    ImmutableList<UnknownSchemaNode> unknownNodes;
    boolean yin;

    ExtensionDefinitionImpl(final QName qname, final SchemaPath path) {
        this.qname = qname;
        this.schemaPath = path;
    }

    @Override
    public QName getQName() {
        return qname;
    }

    @Override
    public SchemaPath getPath() {
        return schemaPath;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public String getReference() {
        return reference;
    }

    @Override
    public Status getStatus() {
        return status;
    }

    @Override
    public List<UnknownSchemaNode> getUnknownSchemaNodes() {
        return unknownNodes;
    }

    @Override
    public String getArgument() {
        return argument;
    }

    @Override
    public boolean isYinElement() {
        return yin;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((qname == null) ? 0 : qname.hashCode());
        result = prime * result + ((schemaPath == null) ? 0 : schemaPath.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        ExtensionDefinitionImpl other = (ExtensionDefinitionImpl) obj;
        if (qname == null) {
            if (other.qname != null) {
                return false;
            }
        } else if (!qname.equals(other.qname)) {
            return false;
        }
        if (schemaPath == null) {
            if (other.schemaPath != null) {
                return false;
            }
        } else if (!schemaPath.equals(other.schemaPath)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(ExtensionDefinitionImpl.class.getSimpleName());
        sb.append("[");
        sb.append("argument=").append(argument);
        sb.append(", qname=").append(qname);
        sb.append(", schemaPath=").append(schemaPath);
        sb.append(", extensionSchemaNodes=").append(unknownNodes);
        sb.append(", yin=").append(yin);
        sb.append("]");
        return sb.toString();
    }
}