/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 * <p/>
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.stmt.rfc6020.effective;

import org.onos.yangtools.yang.model.api.stmt.RefineStatement;
import org.onos.yangtools.yang.model.api.stmt.SchemaNodeIdentifier;
import org.onos.yangtools.yang.parser.spi.meta.StmtContext;

public class RefineEffectiveStatementImpl extends
        EffectiveStatementBase<SchemaNodeIdentifier, RefineStatement> {

    public RefineEffectiveStatementImpl(
            StmtContext<SchemaNodeIdentifier, RefineStatement, ?> ctx) {
        super(ctx);

    }

}