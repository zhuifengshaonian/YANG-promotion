/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.model.api;

import org.onos.yangtools.yang.common.QName;

/**
 * 
 * Contains the methods for getting the details about the unknown node.
 * 
 */
public interface UnknownSchemaNode extends SchemaNode {

    /**
     * Returns QName instance with the name of the unknown node.
     * 
     * @return QName with name the name of the unknown node.
     */
    QName getNodeType();

    /**
     * Returns name of the unknown node.
     * 
     * @return string with the name of unknown node.
     */
    String getNodeParameter();

    /**
     * 
     * Describes whether the node was added through <code>uses</code> YANG
     * keyword.
     * 
     * @return boolean value which is <code>true</code> if the node is added by
     *         <code>uses</code> YANG keyword
     */
    boolean isAddedByUses();

    /**
     * Get extension definition which identifies this node
     * 
     * @return extension definition if exists, null otherwise
     */
    ExtensionDefinition getExtensionDefinition();

}
