/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.stmt.rfc6020;

import org.onos.yangtools.yang.parser.stmt.rfc6020.effective.RevisionEffectiveStatementImpl;

import org.onos.yangtools.yang.common.SimpleDateFormatUtil;
import java.text.ParseException;
import java.util.Date;
import org.onos.yangtools.yang.model.api.Rfc6020Mapping;
import org.onos.yangtools.yang.model.api.meta.EffectiveStatement;
import org.onos.yangtools.yang.model.api.stmt.DescriptionStatement;
import org.onos.yangtools.yang.model.api.stmt.ReferenceStatement;
import org.onos.yangtools.yang.model.api.stmt.RevisionStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractDeclaredStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractStatementSupport;
import org.onos.yangtools.yang.parser.spi.meta.StmtContext;
import org.onos.yangtools.yang.parser.spi.source.SourceException;
import javax.annotation.Nullable;

public class RevisionStatementImpl extends AbstractDeclaredStatement<Date>
        implements RevisionStatement {

    protected RevisionStatementImpl(
            StmtContext<Date, RevisionStatement, ?> context) {
        super(context);
    }

    public static class Definition
            extends
            AbstractStatementSupport<Date, RevisionStatement, EffectiveStatement<Date, RevisionStatement>> {

        public Definition() {
            super(Rfc6020Mapping.REVISION);
        }

        @Override
        public Date parseArgumentValue(StmtContext<?, ?, ?> ctx, String value)
                throws SourceException {
            Date revision;
            try {
                revision = SimpleDateFormatUtil.getRevisionFormat()
                        .parse(value);
            } catch (ParseException e) {
                throw new IllegalArgumentException(e);
            }

            return revision;
        }

        @Override
        public RevisionStatement createDeclared(
                StmtContext<Date, RevisionStatement, ?> ctx) {
            return new RevisionStatementImpl(ctx);
        }

        @Override
        public EffectiveStatement<Date, RevisionStatement> createEffective(
                StmtContext<Date, RevisionStatement, EffectiveStatement<Date, RevisionStatement>> ctx) {
            return new RevisionEffectiveStatementImpl(ctx);
        }
    }

    @Override
    public Date getDate() {
        return argument();
    }

    @Nullable
    @Override
    public DescriptionStatement getDescription() {
        return firstDeclared(DescriptionStatement.class);
    }

    @Nullable
    @Override
    public ReferenceStatement getReference() {
        return firstDeclared(ReferenceStatement.class);
    }
}
