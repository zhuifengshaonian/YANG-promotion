/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.onos.yangtools.yang.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class SimpleDateFormatUtil {

    private static final String REVISION_SIMPLE_DATE = "yyyy-MM-dd";
    private static final String DEFAULT_DATE = "1970-01-01";

    public static Date DEFAULT_DATE_REV;
    public static Date DEFAULT_DATE_IMP;

    static {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(REVISION_SIMPLE_DATE);

        try {
            DEFAULT_DATE_REV = simpleDateFormat.parse(DEFAULT_DATE);
            DEFAULT_DATE_IMP = simpleDateFormat.parse(DEFAULT_DATE);
        } catch (ParseException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private SimpleDateFormatUtil() {
        throw new UnsupportedOperationException("Utility class should not be instantiated");
    }

    private static final ThreadLocal<SimpleDateFormat> REVISION_FORMAT = new ThreadLocal<SimpleDateFormat>() {

        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(REVISION_SIMPLE_DATE);
        }

        @Override
        public void set(SimpleDateFormat value) {
            throw new UnsupportedOperationException();
        }

    };

    public static SimpleDateFormat getRevisionFormat() {
        return REVISION_FORMAT.get();
    }
}