/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.spi.source;

import org.onos.yangtools.yang.model.api.ModuleIdentifier;

import org.onos.yangtools.yang.model.api.meta.IdentifierNamespace;

/**
 *
 * Source-specific mapping of prefixes to namespaces
 *
 */
public interface ImpPrefixToModuleIdentifier extends IdentifierNamespace<String, ModuleIdentifier> {


}
