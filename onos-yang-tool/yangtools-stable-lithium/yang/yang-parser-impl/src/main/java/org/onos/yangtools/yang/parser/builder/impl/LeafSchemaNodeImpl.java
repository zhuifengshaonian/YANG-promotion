/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.builder.impl;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import java.util.List;
import org.onos.yangtools.yang.common.QName;
import org.onos.yangtools.yang.model.api.ConstraintDefinition;
import org.onos.yangtools.yang.model.api.DerivableSchemaNode;
import org.onos.yangtools.yang.model.api.LeafSchemaNode;
import org.onos.yangtools.yang.model.api.SchemaPath;
import org.onos.yangtools.yang.model.api.Status;
import org.onos.yangtools.yang.model.api.TypeDefinition;
import org.onos.yangtools.yang.model.api.UnknownSchemaNode;

final class LeafSchemaNodeImpl implements LeafSchemaNode, DerivableSchemaNode {
    private final QName qname;
    private final SchemaPath path;
    String description;
    String reference;
    Status status;
    boolean augmenting;
    boolean addedByUses;
    LeafSchemaNode original;
    boolean configuration;
    ConstraintDefinition constraintsDef;
    TypeDefinition<?> type;
    ImmutableList<UnknownSchemaNode> unknownNodes;
    String defaultStr;
    String unitsStr;

    LeafSchemaNodeImpl(final QName qname, final SchemaPath path) {
        this.qname = qname;
        this.path = path;
    }

    @Override
    public QName getQName() {
        return qname;
    }

    @Override
    public SchemaPath getPath() {
        return path;
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
    public boolean isAugmenting() {
        return augmenting;
    }

    @Override
    public boolean isAddedByUses() {
        return addedByUses;
    }

    @Override
    public Optional<LeafSchemaNode> getOriginal() {
        return Optional.fromNullable(original);
    }

    @Override
    public boolean isConfiguration() {
        return configuration;
    }

    @Override
    public ConstraintDefinition getConstraints() {
        return constraintsDef;
    }

    @Override
    public TypeDefinition<?> getType() {
        return type;
    }

    @Override
    public List<UnknownSchemaNode> getUnknownSchemaNodes() {
        return unknownNodes;
    }

    @Override
    public String getDefault() {
        return defaultStr;
    }

    @Override
    public String getUnits() {
        return unitsStr;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((qname == null) ? 0 : qname.hashCode());
        result = prime * result + ((path == null) ? 0 : path.hashCode());
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
        LeafSchemaNodeImpl other = (LeafSchemaNodeImpl) obj;
        if (qname == null) {
            if (other.qname != null) {
                return false;
            }
        } else if (!qname.equals(other.qname)) {
            return false;
        }
        if (path == null) {
            if (other.path != null) {
                return false;
            }
        } else if (!path.equals(other.path)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(LeafSchemaNodeImpl.class.getSimpleName());
        sb.append("[");
        sb.append("qname=").append(qname);
        sb.append(", path=").append(path);
        sb.append("]");
        return sb.toString();
    }
}