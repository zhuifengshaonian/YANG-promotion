/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.transform.dom.serializer;

import org.onos.yangtools.yang.data.impl.schema.transform.base.serializer.ChoiceNodeBaseSerializer;
import org.onos.yangtools.yang.data.impl.schema.transform.base.serializer.NodeSerializerDispatcher;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;

final class ChoiceNodeDomSerializer extends ChoiceNodeBaseSerializer<Element> {
    private final NodeSerializerDispatcher<Element> dispatcher;

    ChoiceNodeDomSerializer(final NodeSerializerDispatcher<Element> dispatcher) {
        this.dispatcher = Preconditions.checkNotNull(dispatcher);
    }

    @Override
    protected NodeSerializerDispatcher<Element> getNodeDispatcher() {
        return dispatcher;
    }
}
