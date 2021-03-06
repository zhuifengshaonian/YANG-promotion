/**
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.data.impl.leafref;

public class LeafRefDataValidationFailedException extends Exception {

    private static final long serialVersionUID = 1L;
    private int errorsCount = 1;

    public LeafRefDataValidationFailedException(String message) {
        super(message);
    }

    public LeafRefDataValidationFailedException(String message, int errorsCount) {
        super(message);
        this.errorsCount = errorsCount;
    }

    public LeafRefDataValidationFailedException(String message,
            final Throwable cause) {
        super(message, cause);
    }

    public int getValidationsErrorsCount() {
        return errorsCount;
    }
}
