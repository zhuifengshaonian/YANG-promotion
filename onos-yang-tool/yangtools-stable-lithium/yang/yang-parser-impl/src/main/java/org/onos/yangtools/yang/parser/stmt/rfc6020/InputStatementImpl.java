/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.stmt.rfc6020;

import org.onos.yangtools.yang.parser.stmt.rfc6020.effective.InputEffectiveStatementImpl;

import org.onos.yangtools.yang.common.QName;
import org.onos.yangtools.yang.parser.spi.source.SourceException;
import java.util.Collection;
import org.onos.yangtools.yang.model.api.stmt.DataDefinitionStatement;
import org.onos.yangtools.yang.model.api.stmt.GroupingStatement;
import org.onos.yangtools.yang.model.api.stmt.TypedefStatement;
import org.onos.yangtools.yang.model.api.stmt.InputStatement;
import org.onos.yangtools.yang.model.api.Rfc6020Mapping;
import org.onos.yangtools.yang.model.api.meta.EffectiveStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractDeclaredStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractStatementSupport;
import org.onos.yangtools.yang.parser.spi.meta.StmtContext;

public class InputStatementImpl extends AbstractDeclaredStatement<QName>
        implements InputStatement {

    protected InputStatementImpl(StmtContext<QName, InputStatement, ?> context) {
        super(context);
    }

    public static class Definition
            extends
            AbstractStatementSupport<QName, InputStatement, EffectiveStatement<QName, InputStatement>> {

        public Definition() {
            super(Rfc6020Mapping.INPUT);
        }

        @Override
        public QName parseArgumentValue(StmtContext<?, ?, ?> ctx, String value)
                throws SourceException {
            return Utils.qNameFromArgument(ctx, "input");
        }

        @Override
        public InputStatement createDeclared(
                StmtContext<QName, InputStatement, ?> ctx) {
            return new InputStatementImpl(ctx);
        }

        @Override
        public EffectiveStatement<QName, InputStatement> createEffective(
                StmtContext<QName, InputStatement, EffectiveStatement<QName, InputStatement>> ctx) {
            return new InputEffectiveStatementImpl(ctx);
        }

    }

    @Override
    public Collection<? extends TypedefStatement> getTypedefs() {
        return allDeclared(TypedefStatement.class);
    }

    @Override
    public Collection<? extends GroupingStatement> getGroupings() {
        return allDeclared(GroupingStatement.class);
    }

    @Override
    public Collection<? extends DataDefinitionStatement> getDataDefinitions() {
        return allDeclared(DataDefinitionStatement.class);
    }
}
