/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.builder.api;

import java.util.Collection;
import java.util.Map;
import org.onos.yangtools.yang.common.QName;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier;
import org.onos.yangtools.yang.data.api.schema.LeafSetEntryNode;
import org.onos.yangtools.yang.data.api.schema.LeafSetNode;

public interface ListNodeBuilder<T, V>
        extends CollectionNodeBuilder<LeafSetEntryNode<T>, LeafSetNode<T>> {

    @Override
    ListNodeBuilder<T, V> withNodeIdentifier(YangInstanceIdentifier.NodeIdentifier nodeIdentifier);

    @Override
    ListNodeBuilder<T, V> withValue(Collection<LeafSetEntryNode<T>> value);

    @Override
    ListNodeBuilder<T, V> withChild(LeafSetEntryNode<T> child);

    @Override
    ListNodeBuilder<T, V> withoutChild(YangInstanceIdentifier.PathArgument key);

    ListNodeBuilder<T, V> withChildValue(T child);

    ListNodeBuilder<T, LeafSetEntryNode<T>> withChildValue(T value, Map<QName, String> attributes);
}
