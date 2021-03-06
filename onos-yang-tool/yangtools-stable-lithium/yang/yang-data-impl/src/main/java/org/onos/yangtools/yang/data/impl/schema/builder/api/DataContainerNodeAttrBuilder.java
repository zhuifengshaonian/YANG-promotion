/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.builder.api;

import java.util.Collection;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier;
import org.onos.yangtools.yang.data.api.schema.DataContainerChild;
import org.onos.yangtools.yang.data.api.schema.DataContainerNode;

public interface DataContainerNodeAttrBuilder<I extends YangInstanceIdentifier.PathArgument, R extends DataContainerNode<I>>
        extends DataContainerNodeBuilder<I, R>,
        AttributesBuilder<DataContainerNodeAttrBuilder<I, R>> {

    @Override
    DataContainerNodeAttrBuilder<I, R> withValue(Collection<DataContainerChild<? extends YangInstanceIdentifier.PathArgument, ?>> value);

    @Override
    DataContainerNodeAttrBuilder<I, R> withNodeIdentifier(I nodeIdentifier);

    @Override
    DataContainerNodeAttrBuilder<I, R> withChild(DataContainerChild<?, ?> child);
}
