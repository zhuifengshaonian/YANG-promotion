/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.stmt.rfc6020;

import static org.onos.yangtools.yang.parser.spi.meta.ModelProcessingPhase.EFFECTIVE_MODEL;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.annotation.Nonnull;

import org.onos.yangtools.yang.model.api.Rfc6020Mapping;
import org.onos.yangtools.yang.model.api.meta.EffectiveStatement;
import org.onos.yangtools.yang.model.api.stmt.AugmentStatement;
import org.onos.yangtools.yang.model.api.stmt.DataDefinitionStatement;
import org.onos.yangtools.yang.model.api.stmt.SchemaNodeIdentifier;
import org.onos.yangtools.yang.parser.spi.meta.AbstractDeclaredStatement;
import org.onos.yangtools.yang.parser.spi.meta.AbstractStatementSupport;
import org.onos.yangtools.yang.parser.spi.meta.InferenceException;
import org.onos.yangtools.yang.parser.spi.meta.ModelActionBuilder;
import org.onos.yangtools.yang.parser.spi.meta.ModelProcessingPhase;
import org.onos.yangtools.yang.parser.spi.meta.StmtContext;
import org.onos.yangtools.yang.parser.spi.source.SourceException;
import org.onos.yangtools.yang.parser.stmt.reactor.StatementContextBase;
import org.onos.yangtools.yang.parser.stmt.rfc6020.effective.AugmentEffectiveStatementImpl;

public class AugmentStatementImpl extends AbstractDeclaredStatement<SchemaNodeIdentifier> implements AugmentStatement {

    private static final Logger LOG = LoggerFactory.getLogger(AugmentStatementImpl.class);

    protected AugmentStatementImpl(StmtContext<SchemaNodeIdentifier, AugmentStatement, ?> context) {
        super(context);
    }

    public static class Definition extends AbstractStatementSupport<SchemaNodeIdentifier, AugmentStatement, EffectiveStatement<SchemaNodeIdentifier, AugmentStatement>> {

        public Definition() {
            super(Rfc6020Mapping.AUGMENT);
        }

        @Override
        public SchemaNodeIdentifier parseArgumentValue(StmtContext<?, ?, ?> ctx, String value) throws SourceException {
            return SchemaNodeIdentifier.create(AugmentUtils.parseAugmentPath(ctx, value), Utils.isXPathAbsolute(value));
        }

        @Override
        public AugmentStatement createDeclared(StmtContext<SchemaNodeIdentifier, AugmentStatement, ?> ctx) {
            return new AugmentStatementImpl(ctx);
        }

        @Override
        public EffectiveStatement<SchemaNodeIdentifier, AugmentStatement> createEffective(
                StmtContext<SchemaNodeIdentifier, AugmentStatement, EffectiveStatement<SchemaNodeIdentifier, AugmentStatement>> ctx) {
            return new AugmentEffectiveStatementImpl(ctx);
        }

        @Override
        public void onFullDefinitionDeclared(
                final StmtContext.Mutable<SchemaNodeIdentifier, AugmentStatement, EffectiveStatement<SchemaNodeIdentifier, AugmentStatement>> augmentNode)
                throws SourceException {

            final ModelActionBuilder augmentAction = augmentNode.newInferenceAction(EFFECTIVE_MODEL);
            final ModelActionBuilder.Prerequisite<StmtContext<SchemaNodeIdentifier, AugmentStatement, EffectiveStatement<SchemaNodeIdentifier, AugmentStatement>>> sourceCtxPrereq = augmentAction
                    .requiresCtx(augmentNode, ModelProcessingPhase.FULL_DECLARATION);

            augmentAction.apply(new ModelActionBuilder.InferenceAction() {

                @Override
                public void apply() throws InferenceException {

                    final StatementContextBase<?, ?, ?> augmentTargetCtx = AugmentUtils.getAugmentTargetCtx(augmentNode);
                    final StatementContextBase<?, ?, ?> augmentSourceCtx = (StatementContextBase<?, ?, ?>) sourceCtxPrereq.get();

                    try {
                        AugmentUtils.copyFromSourceToTarget(augmentSourceCtx, augmentTargetCtx);
                    } catch (SourceException e) {
                        LOG.warn(e.getMessage(), e);
                    }
                }

                @Override
                public void prerequisiteFailed(final Collection<? extends ModelActionBuilder.Prerequisite<?>> failed)
                        throws InferenceException {
                    if (failed.contains(augmentAction)) {
                        throw new InferenceException("Augment action failed", augmentNode.getStatementSourceReference());
                    }
                }
            });
        }
    }

    @Nonnull
    @Override
    public SchemaNodeIdentifier getTargetNode() {
        return argument();
    }

    @Override
    public Collection<? extends DataDefinitionStatement> getDataDefinitions() {
        return allDeclared(DataDefinitionStatement.class);
    }
}
