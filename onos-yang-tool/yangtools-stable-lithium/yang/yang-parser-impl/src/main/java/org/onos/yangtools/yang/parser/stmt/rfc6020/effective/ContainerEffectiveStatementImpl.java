/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.stmt.rfc6020.effective;

import com.google.common.collect.ImmutableSet;

import com.google.common.collect.ImmutableList;
import org.onos.yangtools.yang.parser.stmt.rfc6020.Utils;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Collection;
import org.onos.yangtools.yang.model.api.meta.EffectiveStatement;
import org.onos.yangtools.yang.model.api.stmt.ContainerStatement;
import org.onos.yangtools.yang.parser.spi.meta.StmtContext;
import com.google.common.base.Optional;
import java.util.List;
import java.util.Set;
import org.onos.yangtools.yang.common.QName;
import org.onos.yangtools.yang.model.api.AugmentationSchema;
import org.onos.yangtools.yang.model.api.ConstraintDefinition;
import org.onos.yangtools.yang.model.api.ContainerSchemaNode;
import org.onos.yangtools.yang.model.api.DerivableSchemaNode;
import org.onos.yangtools.yang.model.api.SchemaPath;
import org.onos.yangtools.yang.model.api.UnknownSchemaNode;

public class ContainerEffectiveStatementImpl extends
        AbstractEffectiveDocumentedDataNodeContainer<QName, ContainerStatement>
        implements ContainerSchemaNode, DerivableSchemaNode {
    private final QName qname;
    private final SchemaPath path;
    private final boolean presence;

    boolean augmenting;
    boolean addedByUses;
    boolean configuration;
    ContainerSchemaNode original;
    ConstraintDefinition constraints;

    private ImmutableSet<AugmentationSchema> augmentations;
    private ImmutableList<UnknownSchemaNode> unknownNodes;

    public ContainerEffectiveStatementImpl(
            StmtContext<QName, ContainerStatement, EffectiveStatement<QName, ContainerStatement>> ctx) {
        super(ctx);

        qname = ctx.getStatementArgument();
        path = Utils.getSchemaPath(ctx);
        presence = (firstEffective(PresenceEffectiveStatementImpl.class) == null) ? false
                : true;
        // :TODO init other fields

        initSubstatementCollections();
    }

    private void initSubstatementCollections() {
        Collection<? extends EffectiveStatement<?, ?>> effectiveSubstatements = effectiveSubstatements();

        List<UnknownSchemaNode> unknownNodesInit = new LinkedList<>();
        Set<AugmentationSchema> augmentationsInit = new HashSet<>();

        for (EffectiveStatement<?, ?> effectiveStatement : effectiveSubstatements) {
            if (effectiveStatement instanceof UnknownSchemaNode) {
                UnknownSchemaNode unknownNode = (UnknownSchemaNode) effectiveStatement;
                unknownNodesInit.add(unknownNode);
            }
            if (effectiveStatement instanceof AugmentationSchema) {
                AugmentationSchema augmentationSchema = (AugmentationSchema) effectiveStatement;
                augmentationsInit.add(augmentationSchema);
            }
        }

        this.unknownNodes = ImmutableList.copyOf(unknownNodesInit);
        this.augmentations = ImmutableSet.copyOf(augmentationsInit);
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
    public boolean isAugmenting() {
        return augmenting;
    }

    @Override
    public boolean isAddedByUses() {
        return addedByUses;
    }

    @Override
    public Optional<ContainerSchemaNode> getOriginal() {
        return Optional.fromNullable(original);
    }

    @Override
    public boolean isConfiguration() {
        return configuration;
    }

    @Override
    public ConstraintDefinition getConstraints() {
        return constraints;
    }

    @Override
    public Set<AugmentationSchema> getAvailableAugmentations() {
        return augmentations;
    }

    @Override
    public boolean isPresenceContainer() {
        return presence;
    }

    @Override
    public List<UnknownSchemaNode> getUnknownSchemaNodes() {
        return unknownNodes;
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
        ContainerEffectiveStatementImpl other = (ContainerEffectiveStatementImpl) obj;
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
        return "container " + qname.getLocalName();
    }
}