/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.stmt.rfc6020;

import org.onos.yangtools.yang.parser.stmt.rfc6020.effective.RequireInstanceEffectiveStatementImpl;

import org.onos.yangtools.yang.model.api.Rfc6020Mapping;
import org.onos.yangtools.yang.model.api.meta.EffectiveStatement;
import org.onos.yangtools.yang.model.api.stmt.RequireInstanceStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractDeclaredStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractStatementSupport;
import org.onos.yangtools.yang.parser.spi.meta.StmtContext;

public class RequireInstanceStatementImpl extends
        AbstractDeclaredStatement<Boolean> implements RequireInstanceStatement {

    protected RequireInstanceStatementImpl(
            StmtContext<Boolean, RequireInstanceStatement, ?> context) {
        super(context);
    }

    public static class Definition
            extends
            AbstractStatementSupport<Boolean, RequireInstanceStatement, EffectiveStatement<Boolean, RequireInstanceStatement>> {

        public Definition() {
            super(Rfc6020Mapping.REQUIRE_INSTANCE);
        }

        @Override
        public Boolean parseArgumentValue(StmtContext<?, ?, ?> ctx, String value) {
            return Boolean.valueOf(value);
        }

        @Override
        public RequireInstanceStatement createDeclared(
                StmtContext<Boolean, RequireInstanceStatement, ?> ctx) {
            return new RequireInstanceStatementImpl(ctx);
        }

        @Override
        public EffectiveStatement<Boolean, RequireInstanceStatement> createEffective(
                StmtContext<Boolean, RequireInstanceStatement, EffectiveStatement<Boolean, RequireInstanceStatement>> ctx) {
            return new RequireInstanceEffectiveStatementImpl(ctx);
        }

    }

    @Override
    public Boolean getValue() {
        return argument();
    }

}
