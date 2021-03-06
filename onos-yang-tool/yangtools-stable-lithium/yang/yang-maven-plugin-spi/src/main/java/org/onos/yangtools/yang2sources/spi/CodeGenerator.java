/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang2sources.spi;

/**
 * Classes implementing this interface can be submitted to maven-yang-plugin's
 * generate-sources goal.
 *
 * @deprecated Use {@link BasicCodeGenerator} with appropriate traits.
 */
@Deprecated
public interface CodeGenerator extends BasicCodeGenerator, MavenLogAware, MavenProjectAware {

}
