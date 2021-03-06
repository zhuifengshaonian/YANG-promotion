/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.model.util.repo;

import org.onos.yangtools.yang.model.api.SchemaContext;
import org.onos.yangtools.yang.model.api.SchemaContextHolder;


/**
 * Represent Schema Service.
 *
 *
 * @deprecated Replaced by {@link SchemaContextHolder}, which provides
 *    component-local view for actual {@link SchemaContext}.
 */
@Deprecated
public interface SchemaService {

    SchemaContext getSchemaContext();
}
