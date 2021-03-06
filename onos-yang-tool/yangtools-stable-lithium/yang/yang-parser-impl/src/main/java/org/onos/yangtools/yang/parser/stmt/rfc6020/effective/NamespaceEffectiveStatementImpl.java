/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.stmt.rfc6020.effective;

import java.net.URI;

import org.onos.yangtools.yang.model.api.stmt.NamespaceStatement;
import org.onos.yangtools.yang.parser.spi.meta.StmtContext;

public class NamespaceEffectiveStatementImpl extends EffectiveStatementBase<URI, NamespaceStatement>  {

    public NamespaceEffectiveStatementImpl(
            StmtContext<URI, NamespaceStatement, ?> ctx) {
        super(ctx);
    }

}
