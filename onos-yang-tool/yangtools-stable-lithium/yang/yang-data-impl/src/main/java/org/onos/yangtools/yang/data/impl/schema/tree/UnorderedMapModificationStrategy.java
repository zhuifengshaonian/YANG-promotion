/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse
 * Public License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.tree;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Optional;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier;
import org.onos.yangtools.yang.data.api.schema.MapNode;
import org.onos.yangtools.yang.data.api.schema.NormalizedNode;
import org.onos.yangtools.yang.data.api.schema.tree.TreeType;
import org.onos.yangtools.yang.data.impl.schema.builder.api.NormalizedNodeContainerBuilder;
import org.onos.yangtools.yang.data.impl.schema.builder.impl.ImmutableMapNodeBuilder;
import org.onos.yangtools.yang.model.api.ListSchemaNode;

final class UnorderedMapModificationStrategy extends AbstractNodeContainerModificationStrategy {
    private final Optional<ModificationApplyOperation> entryStrategy;

    UnorderedMapModificationStrategy(final ListSchemaNode schema, final TreeType treeType) {
        super(MapNode.class, treeType);
        entryStrategy = Optional.<ModificationApplyOperation>of(new ListEntryModificationStrategy(schema, treeType));
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected NormalizedNodeContainerBuilder createBuilder(final NormalizedNode<?, ?> original) {
        checkArgument(original instanceof MapNode);
        return ImmutableMapNodeBuilder.create((MapNode) original);
    }

    @Override
    public Optional<ModificationApplyOperation> getChild(final YangInstanceIdentifier.PathArgument identifier) {
        if (identifier instanceof YangInstanceIdentifier.NodeIdentifierWithPredicates) {
            return entryStrategy;
        }
        return Optional.absent();
    }

    @Override
    public String toString() {
        return "UnorderedMapModificationStrategy [entry=" + entryStrategy + "]";
    }
}
