/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.stmt.rfc6020;

import org.onos.yangtools.yang.parser.stmt.rfc6020.effective.ErrorMessageEffectiveStatementImpl;

import org.onos.yangtools.yang.model.api.Rfc6020Mapping;
import org.onos.yangtools.yang.model.api.meta.EffectiveStatement;
import org.onos.yangtools.yang.model.api.stmt.ErrorMessageStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractDeclaredStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractStatementSupport;
import org.onos.yangtools.yang.parser.spi.meta.StmtContext;

public class ErrorMessageStatementImpl extends
        AbstractDeclaredStatement<String> implements ErrorMessageStatement {

    protected ErrorMessageStatementImpl(
            StmtContext<String, ErrorMessageStatement, ?> context) {
        super(context);
    }

    public static class Definition
            extends
            AbstractStatementSupport<String, ErrorMessageStatement, EffectiveStatement<String, ErrorMessageStatement>> {

        public Definition() {
            super(Rfc6020Mapping.ERROR_MESSAGE);
        }

        @Override
        public String parseArgumentValue(StmtContext<?, ?, ?> ctx, String value) {
            return value;
        }

        @Override
        public ErrorMessageStatement createDeclared(
                StmtContext<String, ErrorMessageStatement, ?> ctx) {
            return new ErrorMessageStatementImpl(ctx);
        }

        @Override
        public EffectiveStatement<String, ErrorMessageStatement> createEffective(
                StmtContext<String, ErrorMessageStatement, EffectiveStatement<String, ErrorMessageStatement>> ctx) {
            return new ErrorMessageEffectiveStatementImpl(ctx);
        }

    }

    @Override
    public String getValue() {
        return argument();
    }
}
