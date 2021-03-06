/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.binding.util;

import com.google.common.base.Throwables;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodType;
import java.util.concurrent.Future;
import org.onos.yangtools.yang.binding.DataObject;
import org.onos.yangtools.yang.binding.RpcService;
import org.onos.yangtools.yang.common.RpcResult;

class RpcMethodInvokerWithInput extends RpcMethodInvoker {

    private static final MethodType INVOCATION_SIGNATURE = MethodType.methodType(Future.class, RpcService.class,DataObject.class);
    private final MethodHandle handle;

    RpcMethodInvokerWithInput(MethodHandle methodHandle) {
        this.handle = methodHandle.asType(INVOCATION_SIGNATURE);
    }

    @Override
    public Future<RpcResult<?>> invokeOn(RpcService impl, DataObject input) {
        try {
            return (Future<RpcResult<?>>) handle.invokeExact(impl,input);
        } catch (Throwable e) {
            throw Throwables.propagate(e);
        }
    }

}