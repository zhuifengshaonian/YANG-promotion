/*
 * Copyright (c) 2015 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang.stmt.test;

import org.junit.Test;
import org.onos.yangtools.yang.parser.spi.meta.ReactorException;
import org.onos.yangtools.yang.parser.spi.source.SourceException;
import org.onos.yangtools.yang.parser.spi.source.StatementStreamSource;
import org.onos.yangtools.yang.parser.stmt.reactor.CrossSourceStatementReactor;
import org.onos.yangtools.yang.parser.stmt.reactor.EffectiveModelContext;
import org.onos.yangtools.yang.parser.stmt.rfc6020.YangInferencePipeline;
import org.onos.yangtools.yang.parser.stmt.rfc6020.YangStatementSourceImpl;

import static org.junit.Assert.assertNotNull;

public class YangFileStmtTest {
    //basic statements to parse and write
    private static final YangStatementSourceImpl YANGFILE = new YangStatementSourceImpl("/semantic-statement-parser/test.yang");
    private static final YangStatementSourceImpl IMPORTEDYANGFILE = new YangStatementSourceImpl("/semantic-statement-parser/importedtest.yang");
    private static final YangStatementSourceImpl SIMPLENODES = new YangStatementSourceImpl("/semantic-statement-parser/simple-nodes-semantic.yang");
    private static final YangStatementSourceImpl FOOBAR = new YangStatementSourceImpl("/semantic-statement-parser/foobar.yang");
    //extension statement to parse and write
    private static final YangStatementSourceImpl EXTFILE = new YangStatementSourceImpl("/semantic-statement-parser/ext-typedef.yang");
    private static final YangStatementSourceImpl EXTUSE = new YangStatementSourceImpl("/semantic-statement-parser/ext-use.yang");

    @Test
    public void readAndParseYangFileTest() throws SourceException, ReactorException {
        CrossSourceStatementReactor.BuildAction reactor = YangInferencePipeline.RFC6020_REACTOR.newBuild();
        addSources(reactor, YANGFILE, SIMPLENODES, IMPORTEDYANGFILE, FOOBAR);
        addSources(reactor, EXTFILE, EXTUSE);
        EffectiveModelContext result = reactor.build();
        assertNotNull(result);
    }

    private void addSources(CrossSourceStatementReactor.BuildAction reactor, StatementStreamSource... sources) {
        for (StatementStreamSource source : sources) {
            reactor.addSource(source);
        }
    }
}