package org.onos.yangtools.yang.parser.stmt.rfc6020.effective;

import org.onos.yangtools.yang.model.api.RevisionAwareXPath;
import org.onos.yangtools.yang.model.api.stmt.MustStatement;
import org.onos.yangtools.yang.parser.spi.meta.StmtContext;

public class MustEffectiveStatementImpl extends
        EffectiveStatementBase<RevisionAwareXPath, MustStatement> {

    public MustEffectiveStatementImpl(
            StmtContext<RevisionAwareXPath, MustStatement, ?> ctx) {
        super(ctx);

    }

}