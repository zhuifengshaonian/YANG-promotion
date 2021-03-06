/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.stmt.rfc6020;

import org.onos.yangtools.yang.parser.stmt.rfc6020.effective.BaseEffectiveStatementImpl;

import org.onos.yangtools.yang.common.QName;
import org.onos.yangtools.yang.model.api.Rfc6020Mapping;
import org.onos.yangtools.yang.model.api.meta.EffectiveStatement;
import org.onos.yangtools.yang.model.api.stmt.BaseStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractDeclaredStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractStatementSupport;
import org.onos.yangtools.yang.parser.spi.meta.StmtContext;

public class BaseStatementImpl extends AbstractDeclaredStatement<QName>
        implements BaseStatement {

    protected BaseStatementImpl(StmtContext<QName, BaseStatement, ?> context) {
        super(context);
    }

    public static class Definition
            extends
            AbstractStatementSupport<QName, BaseStatement, EffectiveStatement<QName, BaseStatement>> {

        public Definition() {
            super(Rfc6020Mapping.BASE);
        }

        @Override
        public QName parseArgumentValue(StmtContext<?, ?, ?> ctx, String value) {
            return Utils.qNameFromArgument(ctx, value);
        }

        @Override
        public BaseStatement createDeclared(
                StmtContext<QName, BaseStatement, ?> ctx) {
            return new BaseStatementImpl(ctx);
        }

        @Override
        public EffectiveStatement<QName, BaseStatement> createEffective(
                StmtContext<QName, BaseStatement, EffectiveStatement<QName, BaseStatement>> ctx) {
            return new BaseEffectiveStatementImpl(ctx);
        }

    }

    @Override
    public QName getName() {
        return argument();
    }

}
