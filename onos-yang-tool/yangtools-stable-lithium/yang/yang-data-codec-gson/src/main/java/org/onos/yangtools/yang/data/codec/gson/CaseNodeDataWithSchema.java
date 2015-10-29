/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.codec.gson;
import org.onos.yangtools.yang.model.api.ChoiceCaseNode;

class CaseNodeDataWithSchema extends CompositeNodeDataWithSchema {

    public CaseNodeDataWithSchema(final ChoiceCaseNode schema) {
        super(schema);
    }

}