/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.stmt.rfc6020;

import org.onos.yangtools.yang.model.api.stmt.ImportStatement;
import org.onos.yangtools.yang.model.api.stmt.PrefixStatement;
import org.onos.yangtools.yang.model.api.stmt.RevisionDateStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractDeclaredStatement;
import org.onos.yangtools.yang.parser.spi.meta.StmtContext;

public class ImportStatementImpl extends AbstractDeclaredStatement<String> implements ImportStatement {

    ImportStatementImpl(StmtContext<String, ImportStatement,?> context) {
        super(context);
    }

    @Override
    public String getModule() {
        return rawArgument();
    }

    @Override
    public PrefixStatement getPrefix() {
        return firstDeclared(PrefixStatement.class);
    }

    @Override
    public RevisionDateStatement getRevisionDate() {
        return firstDeclared(RevisionDateStatement.class);
    }
}
