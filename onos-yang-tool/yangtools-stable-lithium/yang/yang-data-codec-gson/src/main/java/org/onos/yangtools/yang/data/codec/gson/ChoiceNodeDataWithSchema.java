/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.codec.gson;

import java.io.IOException;
import org.onos.yangtools.yang.data.api.schema.stream.NormalizedNodeStreamWriter;
import org.onos.yangtools.yang.model.api.ChoiceCaseNode;
import org.onos.yangtools.yang.model.api.ChoiceSchemaNode;
import org.onos.yangtools.yang.model.api.DataSchemaNode;

/**
 *
 * childs - empty augment - only one element can be
 *
 */
class ChoiceNodeDataWithSchema extends CompositeNodeDataWithSchema {

    private CaseNodeDataWithSchema caseNodeDataWithSchema;

    public ChoiceNodeDataWithSchema(final ChoiceSchemaNode schema) {
        super(schema);
    }

    @Override
    protected CompositeNodeDataWithSchema addCompositeChild(final DataSchemaNode schema) {
        CaseNodeDataWithSchema newChild = new CaseNodeDataWithSchema((ChoiceCaseNode) schema);
        caseNodeDataWithSchema = newChild;
        addCompositeChild(newChild);
        return newChild;
    }

    public CaseNodeDataWithSchema getCase() {
        return caseNodeDataWithSchema;
    }

    @Override
    public void write(final NormalizedNodeStreamWriter writer) throws IOException {
        writer.startChoiceNode(provideNodeIdentifier(), childSizeHint());
        super.write(writer);
        writer.endNode();
    }

}
