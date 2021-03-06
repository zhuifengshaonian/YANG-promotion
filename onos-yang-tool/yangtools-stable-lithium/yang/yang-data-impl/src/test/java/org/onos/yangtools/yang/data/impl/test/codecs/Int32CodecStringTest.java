/*
 * Copyright (c) 2014 Brocade Communications Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.onos.yangtools.yang.data.impl.test.codecs;

import static org.junit.Assert.*;
import static org.onos.yangtools.yang.data.impl.test.codecs.TypeDefinitionAwareCodecTestHelper.deserializeWithExpectedIllegalArgEx;
import static org.onos.yangtools.yang.data.impl.test.codecs.TypeDefinitionAwareCodecTestHelper.getCodec;

import org.junit.Test;
import org.onos.yangtools.yang.data.api.codec.Int32Codec;
import org.onos.yangtools.yang.model.util.Int32;

/**
 * Unit tests for Int32CodecString.
 *
 * @author Thomas Pantelis
 */
public class Int32CodecStringTest {

    @SuppressWarnings("unchecked")
    @Test
    public void testSerialize() {
        Int32Codec<String> codec = getCodec(Int32.getInstance(), Int32Codec.class);

        assertEquals("serialize", "10", codec.serialize(Integer.valueOf( 10 )));
        assertEquals("serialize", "", codec.serialize(null));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testDeserialize() {
        final String hexa = "0x45FFFCDE";
        final String negHexa = "-0x45FFFCDE";
        final String octal = "010577776336";
        final String negOctal = "-010577776336";
        final String integer = "1174404318";
        final String negInteger = "-1174404318";

        Int32Codec<String> codec = getCodec(Int32.getInstance(), Int32Codec.class);

        assertEquals("deserialize", codec.deserialize(hexa), Integer.valueOf("+045FFFCDE", 16));
        assertEquals("deserialize", codec.deserialize(negHexa), Integer.valueOf("-045FFFCDE", 16));
        assertEquals("deserialize", codec.deserialize(octal), Integer.valueOf(octal, 8));
        assertEquals("deserialize", codec.deserialize(negOctal), Integer.valueOf(negOctal, 8));
        assertEquals("deserialize", codec.deserialize(integer), Integer.valueOf(integer, 10));
        assertEquals("deserialize", codec.deserialize(negInteger), Integer.valueOf(negInteger, 10));

        deserializeWithExpectedIllegalArgEx(codec, "1o");
        deserializeWithExpectedIllegalArgEx(codec, "");
        deserializeWithExpectedIllegalArgEx(codec, null);
    }
}
