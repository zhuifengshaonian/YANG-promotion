/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.codec;

import org.onos.yangtools.yang.binding.BaseIdentity;
import org.onos.yangtools.yang.binding.BindingCodec;
import org.onos.yangtools.yang.common.QName;

public interface IdentityCodec<T extends BaseIdentity> extends BindingCodec<QName, Class<T>>{
    @Override
    QName serialize(Class<T> input);

    @Override
    Class<T> deserialize(QName input);
}
