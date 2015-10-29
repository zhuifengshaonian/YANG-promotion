/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.websocket.client.callback;

/**
 * {@link ClientMessageCallback} notifies client that some event has occurred.
 */
public interface ClientMessageCallback {

    /**
     * Notifies client that some event has occurred.
     * 
     * @param message
     *            the message which aid to will be client notified
     */
    void onMessageReceived(Object message);
}