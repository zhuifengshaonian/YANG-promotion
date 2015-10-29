/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.stmt.rfc6020;

import org.onos.yangtools.yang.parser.stmt.rfc6020.effective.EnumEffectiveStatementImpl;

import org.onos.yangtools.yang.model.api.stmt.ValueStatement;
import org.onos.yangtools.yang.model.api.stmt.DescriptionStatement;
import org.onos.yangtools.yang.model.api.stmt.ReferenceStatement;
import org.onos.yangtools.yang.model.api.stmt.StatusStatement;
import org.onos.yangtools.yang.model.api.Rfc6020Mapping;
import org.onos.yangtools.yang.model.api.meta.EffectiveStatement;
import org.onos.yangtools.yang.model.api.stmt.EnumStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractDeclaredStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractStatementSupport;
import org.onos.yangtools.yang.parser.spi.meta.StmtContext;

public class EnumStatementImpl extends AbstractDeclaredStatement<String>
        implements EnumStatement {

    protected EnumStatementImpl(
            StmtContext<String, EnumStatement, ?> context) {
        super(context);
    }

    public static class Definition
            extends
            AbstractStatementSupport<String, EnumStatement, EffectiveStatement<String, EnumStatement>> {

        public Definition() {
            super(Rfc6020Mapping.ENUM);
        }

        @Override
        public String parseArgumentValue(StmtContext<?, ?, ?> ctx, String value) {
            return value;
        }

        @Override
        public EnumStatement createDeclared(
                StmtContext<String, EnumStatement, ?> ctx) {
            return new EnumStatementImpl(ctx);
        }

        @Override
        public EffectiveStatement<String, EnumStatement> createEffective(
                StmtContext<String, EnumStatement, EffectiveStatement<String, EnumStatement>> ctx) {
            return new EnumEffectiveStatementImpl(ctx);
        }

    }

    @Override
    public StatusStatement getStatus() {
        return firstDeclared(StatusStatement.class);
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
    public String getName() {
        return argument();
    }

    @Override
    public ValueStatement getValue() {
        return firstDeclared(ValueStatement.class);
    }

}
