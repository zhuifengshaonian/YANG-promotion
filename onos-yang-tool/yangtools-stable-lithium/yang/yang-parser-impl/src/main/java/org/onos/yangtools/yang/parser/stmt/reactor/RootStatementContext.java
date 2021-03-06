/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.stmt.reactor;

import java.util.List;

import java.util.LinkedList;
import org.onos.yangtools.yang.parser.spi.meta.StmtContext;
import org.onos.yangtools.yang.common.QNameModule;
import java.util.Collection;
import org.onos.yangtools.yang.model.api.meta.DeclaredStatement;
import org.onos.yangtools.yang.model.api.meta.EffectiveStatement;
import org.onos.yangtools.yang.parser.spi.meta.NamespaceBehaviour.NamespaceStorageNode;
import org.onos.yangtools.yang.parser.spi.meta.NamespaceBehaviour.Registry;
import org.onos.yangtools.yang.parser.spi.source.SourceException;

class RootStatementContext<A, D extends DeclaredStatement<A>, E extends EffectiveStatement<A, D>>
        extends StatementContextBase<A, D, E> {

    private final SourceSpecificContext sourceContext;
    private final A argument;

    RootStatementContext(ContextBuilder<A, D, E> builder,
            SourceSpecificContext sourceContext) throws SourceException {
        super(builder);
        this.sourceContext = sourceContext;
        this.argument = builder.getDefinition().parseArgumentValue(this,
                builder.getRawArgument());
    }

    RootStatementContext(RootStatementContext<A, D, E> original,
            QNameModule newQNameModule) throws SourceException {
        super(original);

        sourceContext = original.sourceContext;
        this.argument = original.argument;

        copyDeclaredStmts(original, newQNameModule);

        copyEffectiveStmts(original, newQNameModule);

    }

    private void copyDeclaredStmts(RootStatementContext<A, D, E> original,
            QNameModule newQNameModule) throws SourceException {
        Collection<? extends StmtContext<?, ?, ?>> originalDeclaredSubstatements = original
                .declaredSubstatements();
        for (StmtContext<?, ?, ?> stmtContext : originalDeclaredSubstatements) {
            this.addEffectiveSubstatement(stmtContext
                    .createCopy(newQNameModule,this));
        }
    }

    private void copyEffectiveStmts(RootStatementContext<A, D, E> original,
            QNameModule newQNameModule) throws SourceException {
        Collection<? extends StmtContext<?, ?, ?>> originalEffectiveSubstatements = original
                .effectiveSubstatements();
        for (StmtContext<?, ?, ?> stmtContext : originalEffectiveSubstatements) {
            this.addEffectiveSubstatement(stmtContext
                    .createCopy(newQNameModule,this));
        }
    }

    @Override
    public StatementContextBase<?, ?, ?> getParentContext() {
        return null;
    }

    @Override
    public NamespaceStorageNode getParentNamespaceStorage() {
        return sourceContext;
    }

    @Override
    public Registry getBehaviourRegistry() {
        return sourceContext;
    }

    @Override
    public RootStatementContext<?, ?, ?> getRoot() {
        return this;
    }

    SourceSpecificContext getSourceContext() {
        return sourceContext;
    }

    @Override
    public A getStatementArgument() {
        return argument;
    }

    @Override
    public StatementContextBase<A, D, E> createCopy(QNameModule newQNameModule, StatementContextBase<?, ?, ?> newParent)
            throws SourceException {

        return new RootStatementContext<>(this, newQNameModule);
    }

    @Override
    public List<Object> getArgumentsFromRoot() {
        List<Object> argumentList = new LinkedList<Object>();
        argumentList.add(argument);
        return argumentList;
    }

}
