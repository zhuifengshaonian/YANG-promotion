/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.model.api.stmt;

import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;



public interface ChoiceStatement extends DataDefinitionStatement {

    @Nullable DefaultStatement getDefault();

    @Nullable ConfigStatement getConfig();

    @Nullable MandatoryStatement getMandatory();

    @Nonnull Collection<? extends CaseStatement> getCases();
}
