/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.repo;

import org.onos.yangtools.concepts.AbstractObjectRegistration;
import org.onos.yangtools.yang.model.repo.api.YangTextSchemaSource;

public abstract class AbstractURLRegistration extends AbstractObjectRegistration<YangTextSchemaSource> implements URLRegistration{
    protected AbstractURLRegistration(final YangTextSchemaSource text) {
        super(text);
    }
}