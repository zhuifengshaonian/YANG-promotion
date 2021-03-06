/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.builder.impl;

import java.util.Map;

import org.onos.yangtools.yang.common.QName;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier;
import org.onos.yangtools.yang.data.api.schema.LeafNode;
import org.onos.yangtools.yang.data.impl.schema.builder.api.NormalizedNodeAttrBuilder;
import org.onos.yangtools.yang.data.impl.schema.nodes.AbstractImmutableNormalizedValueAttrNode;

public class ImmutableLeafNodeBuilder<T> extends AbstractImmutableNormalizedNodeBuilder<YangInstanceIdentifier.NodeIdentifier, T, LeafNode<T>> {

    public static <T> NormalizedNodeAttrBuilder<YangInstanceIdentifier.NodeIdentifier, T, LeafNode<T>> create() {
        return new ImmutableLeafNodeBuilder<>();
    }

    @Override
    public LeafNode<T> build() {
        return new ImmutableLeafNode<>(getNodeIdentifier(), getValue(), getAttributes());
    }

    private static final class ImmutableLeafNode<T> extends AbstractImmutableNormalizedValueAttrNode<YangInstanceIdentifier.NodeIdentifier, T> implements LeafNode<T> {

        ImmutableLeafNode(final YangInstanceIdentifier.NodeIdentifier nodeIdentifier, final T value, final Map<QName, String> attributes) {
            super(nodeIdentifier, value, attributes);
        }
    }
}
