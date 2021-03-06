/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.stmt.rfc6020.effective;

import org.onos.yangtools.yang.model.api.meta.DeclaredStatement;

import org.onos.yangtools.yang.parser.spi.meta.StmtContext;
import org.onos.yangtools.yang.model.api.DocumentedNode;
import org.onos.yangtools.yang.model.api.Status;

public abstract class AbstractEffectiveDocumentedNode<A, D extends DeclaredStatement<A>>
        extends EffectiveStatementBase<A, D> implements DocumentedNode {

    private final String description;
    private final String reference;
    private final Status status;

    AbstractEffectiveDocumentedNode(final StmtContext<A, D, ?> ctx) {
        super(ctx);

        DescriptionEffectiveStatementImpl descStmt = firstEffective(DescriptionEffectiveStatementImpl.class);
        if (descStmt != null) {
            description = descStmt.argument();
        } else {
            description = "";
        }

        ReferenceEffectiveStatementImpl refStmt = firstEffective(ReferenceEffectiveStatementImpl.class);
        if (refStmt != null) {
            reference = refStmt.argument();
        } else {
            reference = "";
        }

        // :TODO
        status = null;
    }

    @Override
    public final String getDescription() {
        return description;
    }

    @Override
    public final String getReference() {
        return reference;
    }

    @Override
    public final Status getStatus() {
        return status;
    }

}
