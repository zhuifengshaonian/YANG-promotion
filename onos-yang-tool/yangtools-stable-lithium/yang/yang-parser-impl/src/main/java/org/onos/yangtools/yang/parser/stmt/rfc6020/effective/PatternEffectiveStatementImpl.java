/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.stmt.rfc6020.effective;

import org.onos.yangtools.yang.model.api.stmt.PatternStatement;
import org.onos.yangtools.yang.parser.spi.meta.StmtContext;

public class PatternEffectiveStatementImpl extends
        EffectiveStatementBase<String, PatternStatement> {

    public PatternEffectiveStatementImpl(
            StmtContext<String, PatternStatement, ?> ctx) {
        super(ctx);

    }

}