package org.onos.yangtools.yang.data.impl.schema.tree;

import static com.google.common.base.Preconditions.checkArgument;

import com.google.common.base.Optional;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier;
import org.onos.yangtools.yang.data.api.schema.NormalizedNode;
import org.onos.yangtools.yang.data.api.schema.OrderedMapNode;
import org.onos.yangtools.yang.data.api.schema.tree.TreeType;
import org.onos.yangtools.yang.data.impl.schema.builder.api.NormalizedNodeContainerBuilder;
import org.onos.yangtools.yang.data.impl.schema.builder.impl.ImmutableOrderedMapNodeBuilder;
import org.onos.yangtools.yang.model.api.ListSchemaNode;

final class OrderedMapModificationStrategy extends AbstractNodeContainerModificationStrategy {
    private final Optional<ModificationApplyOperation> entryStrategy;

    OrderedMapModificationStrategy(final ListSchemaNode schema, final TreeType treeType) {
        super(OrderedMapNode.class, treeType);
        entryStrategy = Optional.<ModificationApplyOperation> of(new ListEntryModificationStrategy(schema, treeType));
    }

    @Override
    protected ChildTrackingPolicy getChildPolicy() {
        return ChildTrackingPolicy.ORDERED;
    }

    @SuppressWarnings("rawtypes")
    @Override
    protected NormalizedNodeContainerBuilder createBuilder(final NormalizedNode<?, ?> original) {
        checkArgument(original instanceof OrderedMapNode);
        return ImmutableOrderedMapNodeBuilder.create((OrderedMapNode) original);
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
        return "OrderedMapModificationStrategy [entry=" + entryStrategy + "]";
    }
}