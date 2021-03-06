/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.schema.transform;

import javax.annotation.Nullable;
import org.onos.yangtools.yang.data.api.schema.NormalizedNode;

/**
 *
 * Generic parser for normalized nodes. NormalizedNodes can be parsed
 * e.g. from Dom APIs.
 *
 * @param <E>
 *            type of element to be parsed into NormalizedNode
 * @param <N>
 *            type of NormalizedNode to be the result of parsing
 * @param <S>
 *            schema belonging to the type N of NormalizedNode
 */
public interface ToNormalizedNodeParser<E, N extends NormalizedNode<?, ?>, S> {

    /**
     *
     * Parse a list of E elements as a NormalizedNode of type N. If the parsing
     * process expects only one E element as input e.g. container node, the
     * input element will be wrapped in a list.
     *
     * @param xmlDom
     * @param schema
     * @return NormalizedNode as a result of parsing list of E elements with schema S
     */
    @Nullable
    N parse(Iterable<E> xmlDom, S schema);
}
