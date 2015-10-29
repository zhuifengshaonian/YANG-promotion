/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.stmt.rfc6020;

import org.onos.yangtools.yang.parser.stmt.rfc6020.effective.LengthEffectiveStatementImpl;

import org.onos.yangtools.yang.model.api.stmt.DescriptionStatement;
import org.onos.yangtools.yang.model.api.stmt.ErrorAppTagStatement;
import org.onos.yangtools.yang.model.api.stmt.ErrorMessageStatement;
import org.onos.yangtools.yang.model.api.stmt.ReferenceStatement;
import org.onos.yangtools.yang.model.api.Rfc6020Mapping;
import org.onos.yangtools.yang.model.api.meta.EffectiveStatement;
import org.onos.yangtools.yang.model.api.stmt.LengthStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractDeclaredStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractStatementSupport;
import org.onos.yangtools.yang.parser.spi.meta.StmtContext;

public class LengthStatementImpl extends AbstractDeclaredStatement<String>
        implements LengthStatement {

    protected LengthStatementImpl(
            StmtContext<String, LengthStatement, ?> context) {
        super(context);
    }

    public static class Definition
            extends
            AbstractStatementSupport<String, LengthStatement, EffectiveStatement<String, LengthStatement>> {

        public Definition() {
            super(Rfc6020Mapping.LENGTH);
        }

        @Override
        public String parseArgumentValue(StmtContext<?, ?, ?> ctx, String value) {
            return value;
        }

        @Override
        public LengthStatement createDeclared(
                StmtContext<String, LengthStatement, ?> ctx) {
            return new LengthStatementImpl(ctx);
        }

        @Override
        public EffectiveStatement<String, LengthStatement> createEffective(
                StmtContext<String, LengthStatement, EffectiveStatement<String, LengthStatement>> ctx) {
            return new LengthEffectiveStatementImpl(ctx);
        }

    }

    @Override
    public ErrorAppTagStatement getErrorAppTagStatement() {
        return firstDeclared(ErrorAppTagStatement.class);
    }

    @Override
    public ErrorMessageStatement getErrorMessageStatement() {
        return firstDeclared(ErrorMessageStatement.class);
    }

    @Override
    public DescriptionStatement getDescription() {
        return firstDeclared(DescriptionStatement.class);
    }

    @Override
    public ReferenceStatement getReference() {
        return firstDeclared(ReferenceStatement.class);
    }

    @Override
    public String getValue() {
        return argument();
    }

}