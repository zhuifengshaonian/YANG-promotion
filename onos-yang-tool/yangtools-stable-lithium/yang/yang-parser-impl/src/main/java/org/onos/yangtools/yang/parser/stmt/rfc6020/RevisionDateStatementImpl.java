/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.stmt.rfc6020;

import org.onos.yangtools.yang.parser.stmt.rfc6020.effective.RevisionDateEffectiveStatementImpl;

import org.onos.yangtools.yang.model.api.Rfc6020Mapping;
import org.onos.yangtools.yang.model.api.meta.EffectiveStatement;
import org.onos.yangtools.yang.model.api.stmt.RevisionDateStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractDeclaredStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractStatementSupport;
import org.onos.yangtools.yang.parser.spi.meta.StmtContext;

public class RevisionDateStatementImpl extends
        AbstractDeclaredStatement<String> implements RevisionDateStatement {

    protected RevisionDateStatementImpl(
            StmtContext<String, RevisionDateStatement, ?> context) {
        super(context);
    }

    public static class Definition
            extends
            AbstractStatementSupport<String, RevisionDateStatement, EffectiveStatement<String, RevisionDateStatement>> {

        public Definition() {
            super(Rfc6020Mapping.REVISION_DATE);
        }

        @Override
        public String parseArgumentValue(StmtContext<?, ?, ?> ctx, String value) {
            return value;
        }

        @Override
        public RevisionDateStatement createDeclared(
                StmtContext<String, RevisionDateStatement, ?> ctx) {
            return new RevisionDateStatementImpl(ctx);
        }

        @Override
        public EffectiveStatement<String, RevisionDateStatement> createEffective(
                StmtContext<String, RevisionDateStatement, EffectiveStatement<String, RevisionDateStatement>> ctx) {
            return new RevisionDateEffectiveStatementImpl(ctx);
        }

    }

    @Override
    public String getDate() {
        return argument();
    }

}
