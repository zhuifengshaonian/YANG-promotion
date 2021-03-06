/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.spi;

import org.onos.yangtools.yang.common.QName;
import org.onos.yangtools.yang.model.api.meta.EffectiveStatement;
import org.onos.yangtools.yang.model.api.stmt.GroupingStatement;
import org.onos.yangtools.yang.parser.spi.meta.StatementNamespace;

/**
 * Grouping namespace
 *
 * All grouping names defined within a parent node or at the top level of the
 * module or its submodules share the same grouping identifier namespace. This
 * namespace is scoped to all descendant nodes of the parent node or module.
 * This means that any descendant node may use that grouping, and it MUST NOT
 * define a grouping with the same name.
 */
public interface GroupingNamespace extends StatementNamespace.TreeScoped<QName, GroupingStatement,EffectiveStatement<QName,GroupingStatement>> {

}
