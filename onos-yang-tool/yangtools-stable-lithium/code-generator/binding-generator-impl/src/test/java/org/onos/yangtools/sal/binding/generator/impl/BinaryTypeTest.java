/*
* Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
*
* This program and the accompanying materials are made available under the
* terms of the Eclipse Public License v1.0 which accompanies this distribution,
* and is available at http://www.eclipse.org/legal/epl-v10.html
*/
package org.onos.yangtools.sal.binding.generator.impl;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.onos.yangtools.sal.binding.generator.api.BindingGenerator;
import org.onos.yangtools.sal.binding.model.api.Type;
import org.onos.yangtools.yang.model.api.SchemaContext;
import org.onos.yangtools.yang.model.parser.api.YangContextParser;
import org.onos.yangtools.yang.parser.impl.YangParserImpl;

public class BinaryTypeTest {
    private final static List<File> yangModels = new ArrayList<>();
    private final static URL yangModelsFolder = AugmentedTypeTest.class
            .getResource("/binary-type-test-models");

    @BeforeClass
    public static void loadTestResources() throws URISyntaxException {
        final File augFolder = new File(yangModelsFolder.toURI());
        for (final File fileEntry : augFolder.listFiles()) {
            if (fileEntry.isFile()) {
                yangModels.add(fileEntry);
            }
        }
    }

    @Test
    public void binaryTypeTest() throws IOException {
        final YangContextParser parser = new YangParserImpl();
        final SchemaContext context = parser.parseFiles(yangModels);

        assertNotNull("context is null", context);
        final BindingGenerator bindingGen = new BindingGeneratorImpl(true);
        final List<Type> genTypes = bindingGen.generateTypes(context);

        assertNotNull("genTypes is null", genTypes);
        assertFalse("genTypes is empty", genTypes.isEmpty());

        //TODO: implement test
    }
}
