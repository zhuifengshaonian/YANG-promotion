/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.spi;

import org.onos.yangtools.yang.common.QName;
import org.onos.yangtools.yang.model.api.meta.IdentifierNamespace;
import org.onos.yangtools.yang.model.api.stmt.IdentityStatement;

/**
 *
 * Identity namespace
 *
 * All identity names defined in a module and its submodules share the same
 * identity identifier namespace.
 *
 */
public interface IdentityNamespace extends IdentifierNamespace<QName, IdentityStatement> {

}
