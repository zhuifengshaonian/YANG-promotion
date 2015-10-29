/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.math.BigInteger;
import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.onos.yangtools.yang.common.QName;
import org.onos.yangtools.yang.model.api.IdentitySchemaNode;
import org.onos.yangtools.yang.model.api.LeafSchemaNode;
import org.onos.yangtools.yang.model.api.Module;
import org.onos.yangtools.yang.model.api.Status;
import org.onos.yangtools.yang.model.api.TypeDefinition;
import org.onos.yangtools.yang.model.api.type.BitsTypeDefinition.Bit;
import org.onos.yangtools.yang.model.api.type.EnumTypeDefinition.EnumPair;
import org.onos.yangtools.yang.model.api.type.LengthConstraint;
import org.onos.yangtools.yang.model.api.type.PatternConstraint;
import org.onos.yangtools.yang.model.api.type.StringTypeDefinition;
import org.onos.yangtools.yang.model.parser.api.YangContextParser;
import org.onos.yangtools.yang.model.util.BitsType;
import org.onos.yangtools.yang.model.util.EnumerationType;
import org.onos.yangtools.yang.model.util.ExtendedType;
import org.onos.yangtools.yang.model.util.IdentityrefType;
import org.onos.yangtools.yang.model.util.InstanceIdentifierType;
import org.onos.yangtools.yang.model.util.UnionType;

public class Bug2291Test {

    @Test
    public void testRevisionWithExt() throws Exception {
        File extdef = new File(getClass().getResource("/bugs/bug2291/bug2291-ext.yang").toURI());
        File bug = new File(getClass().getResource("/bugs/bug2291/bug2291.yang").toURI());
        File inet = new File(getClass().getResource("/ietf/ietf-inet-types@2010-09-24.yang").toURI());
        YangContextParser parser = new YangParserImpl();
        parser.parseFiles(Arrays.asList(extdef, bug, inet));
    }

}
