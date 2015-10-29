/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.parser.spi.meta;

import javax.annotation.Nonnull;
import org.onos.yangtools.yang.parser.spi.source.SourceException;
import org.onos.yangtools.yang.parser.spi.source.StatementSourceReference;

/**
 *
 * Thrown when there was inference error
 *
 */
public class InferenceException extends SourceException {


    private static final long serialVersionUID = 1L;

    public InferenceException(@Nonnull String message, @Nonnull StatementSourceReference source, Throwable cause) {
        super(message, source, cause);
    }

    public InferenceException(@Nonnull String message, @Nonnull StatementSourceReference source) {
        super(message, source);
    }

}
