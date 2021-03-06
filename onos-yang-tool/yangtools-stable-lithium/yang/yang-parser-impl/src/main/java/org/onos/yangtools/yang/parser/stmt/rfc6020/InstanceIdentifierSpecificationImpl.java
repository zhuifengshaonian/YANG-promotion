/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.stmt.rfc6020;

import org.onos.yangtools.yang.model.api.stmt.RequireInstanceStatement;

import org.onos.yangtools.yang.parser.spi.source.SourceException;
import org.onos.yangtools.yang.parser.spi.meta.AbstractStatementSupport;
import org.onos.yangtools.yang.parser.spi.meta.AbstractDeclaredStatement;
import org.onos.yangtools.yang.parser.spi.meta.StmtContext;
import org.onos.yangtools.yang.model.api.Rfc6020Mapping;
import org.onos.yangtools.yang.model.api.meta.EffectiveStatement;
import org.onos.yangtools.yang.model.api.stmt.TypeStatement;

public class InstanceIdentifierSpecificationImpl extends
        AbstractDeclaredStatement<String> implements
        TypeStatement.InstanceIdentifierSpecification {

    protected InstanceIdentifierSpecificationImpl(
            StmtContext<String, TypeStatement.InstanceIdentifierSpecification, ?> context) {
        super(context);
    }

    public static class Definition
            extends
            AbstractStatementSupport<String, TypeStatement.InstanceIdentifierSpecification, EffectiveStatement<String, TypeStatement.InstanceIdentifierSpecification>> {

        public Definition() {
            super(Rfc6020Mapping.REQUIRE_INSTANCE);
        }

        @Override
        public String parseArgumentValue(StmtContext<?, ?, ?> ctx, String value)
                throws SourceException {
            return value;
        }

        @Override
        public TypeStatement.InstanceIdentifierSpecification createDeclared(
                StmtContext<String, TypeStatement.InstanceIdentifierSpecification, ?> ctx) {
            return new InstanceIdentifierSpecificationImpl(ctx);
        }

        @Override
        public EffectiveStatement<String, TypeStatement.InstanceIdentifierSpecification> createEffective(
                StmtContext<String, TypeStatement.InstanceIdentifierSpecification, EffectiveStatement<String, TypeStatement.InstanceIdentifierSpecification>> ctx) {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public String getName() {
        return argument();
    }

    @Override
    public RequireInstanceStatement getRequireInstance() {
        return firstDeclared(RequireInstanceStatement.class);
    }

}
