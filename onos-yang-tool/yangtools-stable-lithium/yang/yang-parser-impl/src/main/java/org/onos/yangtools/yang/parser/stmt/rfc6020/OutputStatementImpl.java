/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.stmt.rfc6020;

import org.onos.yangtools.yang.common.QName;

import org.onos.yangtools.yang.parser.stmt.rfc6020.effective.OutputEffectiveStatementImpl;
import org.onos.yangtools.yang.parser.spi.source.SourceException;
import java.util.Collection;
import org.onos.yangtools.yang.model.api.stmt.DataDefinitionStatement;
import org.onos.yangtools.yang.model.api.stmt.GroupingStatement;
import org.onos.yangtools.yang.model.api.stmt.TypedefStatement;
import org.onos.yangtools.yang.model.api.stmt.OutputStatement;
import org.onos.yangtools.yang.model.api.Rfc6020Mapping;
import org.onos.yangtools.yang.model.api.meta.EffectiveStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractDeclaredStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractStatementSupport;
import org.onos.yangtools.yang.parser.spi.meta.StmtContext;

public class OutputStatementImpl extends AbstractDeclaredStatement<QName>
        implements OutputStatement {

    protected OutputStatementImpl(
            StmtContext<QName, OutputStatement, ?> context) {
        super(context);
    }

    public static class Definition
            extends
            AbstractStatementSupport<QName, OutputStatement, EffectiveStatement<QName, OutputStatement>> {

        public Definition() {
            super(Rfc6020Mapping.OUTPUT);
        }

        @Override
        public QName parseArgumentValue(StmtContext<?, ?, ?> ctx, String value)
                throws SourceException {
            return Utils.qNameFromArgument(ctx, "output");
        }

        @Override
        public OutputStatement createDeclared(
                StmtContext<QName, OutputStatement, ?> ctx) {
            return new OutputStatementImpl(ctx);
        }

        @Override
        public EffectiveStatement<QName, OutputStatement> createEffective(
                StmtContext<QName, OutputStatement, EffectiveStatement<QName, OutputStatement>> ctx) {
            return new OutputEffectiveStatementImpl(ctx);
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

