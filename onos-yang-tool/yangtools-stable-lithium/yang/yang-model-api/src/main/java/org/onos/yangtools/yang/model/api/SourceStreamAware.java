/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.model.api;

// TODO: merge into Module, makes no sense as standalone interface
public interface SourceStreamAware {

    /**
     * Get descriptive source path (usually file path) from which this module was parsed.
     */
    String getModuleSourcePath();

}
