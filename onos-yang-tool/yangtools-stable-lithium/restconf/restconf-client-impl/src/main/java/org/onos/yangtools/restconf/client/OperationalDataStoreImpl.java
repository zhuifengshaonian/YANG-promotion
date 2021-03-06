/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.restconf.client;

import org.onos.yangtools.restconf.client.api.data.OperationalDatastore;
import org.onos.yangtools.restconf.common.ResourceUri;

public class OperationalDataStoreImpl extends AbstractDataStore implements OperationalDatastore {

    @Override
    protected String getStorePrefix() {
        return ResourceUri.OPERATIONAL.getPath();
    }

    public OperationalDataStoreImpl(RestconfClientImpl client) {
        super(client);
    }

}
