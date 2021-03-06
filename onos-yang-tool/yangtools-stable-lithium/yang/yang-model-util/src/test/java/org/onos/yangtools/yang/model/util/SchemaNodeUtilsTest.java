/*
 * Copyright (c) 2014 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.model.util;

import com.google.common.base.Optional;
import org.onos.yangtools.yang.model.api.SchemaNode;
import org.mockito.MockitoAnnotations;
import org.mockito.Mock;
import org.junit.Before;
import org.onos.yangtools.yang.model.api.DerivableSchemaNode;
import org.junit.Test;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.doReturn;
import static org.hamcrest.CoreMatchers.instanceOf;

public class SchemaNodeUtilsTest {

    @Mock
    private DerivableSchemaNode derivableNode;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHandleNullGetOriginalIfPossible() {
        Optional<SchemaNode> originalIfPossible = SchemaNodeUtils
                .getOriginalIfPossible(null);
        assertNotNull(originalIfPossible);
        assertThat(originalIfPossible, instanceOf(Optional.class));
    }

    @Test
    public void testHandleNodeGetOriginalIfPossible() {
        Optional<DerivableSchemaNode> of = Optional.of(derivableNode);
        doReturn(of).when(derivableNode).getOriginal();
        Optional<SchemaNode> originalIfPossible = SchemaNodeUtils
                .getOriginalIfPossible(derivableNode);
        assertNotNull(originalIfPossible);
        assertThat(originalIfPossible, instanceOf(Optional.class));
    }

    @Test
    public void testHandleNullGetRootOriginalIfPossible() {
        SchemaNode rootOriginalIfPossible = SchemaNodeUtils
                .getRootOriginalIfPossible(null);
        assertNull(rootOriginalIfPossible);
    }
}
