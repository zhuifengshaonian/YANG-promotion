package org.onos.yangtools.yang.stmt.test;

import org.junit.Test;
import org.onos.yangtools.yang.parser.spi.meta.ReactorException;
import org.onos.yangtools.yang.parser.spi.source.SourceException;
import org.onos.yangtools.yang.parser.spi.source.StatementStreamSource;
import org.onos.yangtools.yang.parser.stmt.reactor.CrossSourceStatementReactor;
import org.onos.yangtools.yang.parser.stmt.reactor.EffectiveModelContext;
import org.onos.yangtools.yang.parser.stmt.rfc6020.YangInferencePipeline;

import static org.junit.Assert.assertNotNull;

public class AugmentSimplestTest {

    private static final TestYangFileStatementSource AUGMENTED = new TestYangFileStatementSource("/semantic-statement-parser/augmented.yang");
    private static final TestYangFileStatementSource ROOT = new TestYangFileStatementSource("/semantic-statement-parser/root.yang");

    @Test
    public void readAndParseYangFileTest() throws SourceException, ReactorException {
        CrossSourceStatementReactor.BuildAction reactor = YangInferencePipeline.RFC6020_REACTOR.newBuild();
        addSources(reactor, AUGMENTED, ROOT);

        EffectiveModelContext result = reactor.build();
        assertNotNull(result);
    }

    private void addSources(CrossSourceStatementReactor.BuildAction reactor, StatementStreamSource... sources) {
        for (StatementStreamSource source : sources) {
            reactor.addSource(source);
        }
    }
}
