/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.stmt.rfc6020;

import java.util.Collection;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.onos.yangtools.yang.common.QName;
import org.onos.yangtools.yang.model.api.Rfc6020Mapping;
import org.onos.yangtools.yang.model.api.meta.EffectiveStatement;
import org.onos.yangtools.yang.model.api.stmt.CaseStatement;
import org.onos.yangtools.yang.model.api.stmt.DataDefinitionStatement;
import org.onos.yangtools.yang.model.api.stmt.DescriptionStatement;
import org.onos.yangtools.yang.model.api.stmt.IfFeatureStatement;
import org.onos.yangtools.yang.model.api.stmt.ReferenceStatement;
import org.onos.yangtools.yang.model.api.stmt.StatusStatement;
import org.onos.yangtools.yang.model.api.stmt.WhenStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractDeclaredStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractStatementSupport;
import org.onos.yangtools.yang.parser.spi.meta.StmtContext;
import org.onos.yangtools.yang.parser.spi.source.SourceException;
import org.onos.yangtools.yang.parser.stmt.rfc6020.effective.CaseEffectiveStatementImpl;

public class CaseStatementImpl extends AbstractDeclaredStatement<QName> implements CaseStatement {

    protected CaseStatementImpl(
            StmtContext<QName, CaseStatement, ?> context) {
        super(context);
    }

    public static class Definition extends AbstractStatementSupport<QName,CaseStatement,EffectiveStatement<QName,CaseStatement>> {

        public Definition() {
            super(Rfc6020Mapping.CASE);
        }

        @Override public QName parseArgumentValue(StmtContext<?, ?, ?> ctx,
                String value) throws SourceException {
            return Utils.qNameFromArgument(ctx, value);
        }

        @Override public CaseStatement createDeclared(
                StmtContext<QName, CaseStatement, ?> ctx) {
            return new CaseStatementImpl(ctx);
        }

        @Override public EffectiveStatement<QName, CaseStatement> createEffective(
                StmtContext<QName, CaseStatement, EffectiveStatement<QName, CaseStatement>> ctx) {
            return new CaseEffectiveStatementImpl(ctx);
        }
    }

    @Nonnull @Override
    public QName getName() {
        return argument();
    }

    @Override
    public WhenStatement getWhenStatement() {
        return firstDeclared(WhenStatement.class);
    }

    @Nonnull @Override
    public Collection<? extends IfFeatureStatement> getIfFeatures() {
        return allDeclared(IfFeatureStatement.class);
    }

    @Override
    public Collection<? extends DataDefinitionStatement> getDataDefinitions() {
        return allDeclared(DataDefinitionStatement.class);
    }

    @Nullable @Override
    public StatusStatement getStatus() {
        return firstDeclared(StatusStatement.class);
    }

    @Nullable @Override
    public DescriptionStatement getDescription() {
        return firstDeclared(DescriptionStatement.class);
    }

    @Nullable @Override
    public ReferenceStatement getReference() {
        return firstDeclared(ReferenceStatement.class);
    }
}
