/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.builder.api;

import java.util.Map;

import org.onos.yangtools.yang.common.QName;

public interface AttributesBuilder<B> {
    /**
     * Attach a map of attributes. The map is expected to remain unchanged,
     * otherwise undefined behavior may occur.
     *
     * @param attributes Attribute map
     * @return Builder instance, such that fluent use is possible
     */
    B withAttributes(Map<QName, String> attributes);
}
