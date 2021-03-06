/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.stmt.rfc6020;

import org.onos.yangtools.yang.parser.stmt.rfc6020.effective.YinElementEffectiveStatementImpl;

import org.onos.yangtools.yang.model.api.Rfc6020Mapping;
import org.onos.yangtools.yang.model.api.meta.EffectiveStatement;
import org.onos.yangtools.yang.model.api.stmt.YinElementStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractDeclaredStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractStatementSupport;
import org.onos.yangtools.yang.parser.spi.meta.StmtContext;

public class YinElementStatementImpl extends AbstractDeclaredStatement<Boolean>
        implements YinElementStatement {

    protected YinElementStatementImpl(
            StmtContext<Boolean, YinElementStatement, ?> context) {
        super(context);
    }

    public static class Definition
            extends
            AbstractStatementSupport<Boolean, YinElementStatement, EffectiveStatement<Boolean, YinElementStatement>> {

        public Definition() {
            super(Rfc6020Mapping.YIN_ELEMENT);
        }

        @Override
        public Boolean parseArgumentValue(StmtContext<?, ?, ?> ctx, String value) {
            return Boolean.valueOf(value);
        }

        @Override
        public YinElementStatement createDeclared(
                StmtContext<Boolean, YinElementStatement, ?> ctx) {
            return new YinElementStatementImpl(ctx);
        }

        @Override
        public EffectiveStatement<Boolean, YinElementStatement> createEffective(
                StmtContext<Boolean, YinElementStatement, EffectiveStatement<Boolean, YinElementStatement>> ctx) {
            return new YinElementEffectiveStatementImpl(ctx);
        }

    }

    @Override
    public Boolean getValue() {
        return argument();
    }

}
