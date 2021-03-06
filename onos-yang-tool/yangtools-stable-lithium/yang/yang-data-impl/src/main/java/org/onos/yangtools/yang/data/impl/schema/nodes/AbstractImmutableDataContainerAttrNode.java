/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.nodes;

import java.util.Map;

import org.onos.yangtools.yang.common.QName;
import org.onos.yangtools.yang.data.api.AttributesContainer;
import org.onos.yangtools.yang.data.api.YangInstanceIdentifier;
import org.onos.yangtools.yang.data.api.schema.DataContainerChild;

import com.google.common.base.MoreObjects.ToStringHelper;

public abstract class AbstractImmutableDataContainerAttrNode<K extends YangInstanceIdentifier.PathArgument>
        extends AbstractImmutableDataContainerNode<K>
    implements AttributesContainer {

    private final Map<QName, String> attributes;

    public AbstractImmutableDataContainerAttrNode(
            final Map<YangInstanceIdentifier.PathArgument, DataContainerChild<? extends YangInstanceIdentifier.PathArgument, ?>> children,
            final K nodeIdentifier, final Map<QName, String> attributes) {
        super(children, nodeIdentifier);
        this.attributes = attributes;
    }

    @Override
    public final Map<QName, String> getAttributes() {
        return attributes;
    }

    @Override
    public final Object getAttributeValue(final QName value) {
        return attributes.get(value);
    }

    @Override
    protected ToStringHelper addToStringAttributes(final ToStringHelper toStringHelper) {
        return super.addToStringAttributes(toStringHelper).add("attributes", attributes);
    }

// FIXME: are attributes part of hashCode/equals?
//    @Override
//    protected int valueHashCode() {
//        int result = super.valueHashCode();
//        for (final Entry<?, ?> a : attributes.entrySet()) {
//            result = 31 * result + a.hashCode();
//        }
//        return result;
//    }

 // FIXME: are attributes part of hashCode/equals?
//    @Override
//    protected boolean valueEquals(final NormalizedNode<?, ?> other) {
//        if (!super.valueEquals(other)) {
//            return false;
//        }
//        final Set<Entry<QName, String>> tas = getAttributes().entrySet();
//        final Set<Entry<QName, String>> oas = container.getAttributes().entrySet();
//
//        return tas.containsAll(oas) && oas.containsAll(tas);
//        return true;
//    }
}
