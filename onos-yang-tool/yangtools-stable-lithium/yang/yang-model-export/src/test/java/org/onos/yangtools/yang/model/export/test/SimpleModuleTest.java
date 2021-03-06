package org.onos.yangtools.yang.model.export.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import org.junit.Before;
import org.junit.Test;
import org.onos.yangtools.yang.model.api.Module;
import org.onos.yangtools.yang.model.api.SchemaContext;
import org.onos.yangtools.yang.model.export.YinExportUtils;
import org.onos.yangtools.yang.model.repo.api.SchemaContextFactory;
import org.onos.yangtools.yang.model.repo.api.SchemaSourceFilter;
import org.onos.yangtools.yang.model.repo.api.SchemaSourceRepresentation;
import org.onos.yangtools.yang.model.repo.api.SourceIdentifier;
import org.onos.yangtools.yang.model.repo.api.YangTextSchemaSource;
import org.onos.yangtools.yang.model.repo.spi.PotentialSchemaSource;
import org.onos.yangtools.yang.model.repo.spi.SchemaListenerRegistration;
import org.onos.yangtools.yang.model.repo.spi.SchemaSourceListener;
import org.onos.yangtools.yang.model.repo.util.FilesystemSchemaSourceCache;
import org.onos.yangtools.yang.parser.repo.SharedSchemaRepository;
import org.onos.yangtools.yang.parser.util.TextToASTTransformer;

public class SimpleModuleTest {

    private static final File TEST_MODELS_FOLDER;

    static {
        try {
            TEST_MODELS_FOLDER = new File(SimpleModuleTest.class.getResource("/yang/").toURI());
        } catch (final URISyntaxException e) {
            throw new IllegalStateException(e);
        }
    }

    private SharedSchemaRepository schemaRegistry;
    private FilesystemSchemaSourceCache<YangTextSchemaSource> fileSourceProvider;
    private SchemaContextFactory schemaContextFactory;
    private Set<SourceIdentifier> allTestSources;

    @Before
    public void init() {
        schemaRegistry = new SharedSchemaRepository("test");
        fileSourceProvider = new FilesystemSchemaSourceCache<YangTextSchemaSource>(schemaRegistry,
                YangTextSchemaSource.class, TEST_MODELS_FOLDER);
        final TextToASTTransformer astTransformer = TextToASTTransformer.create(schemaRegistry, schemaRegistry);
        schemaRegistry.registerSchemaSourceListener(astTransformer);

        schemaContextFactory = schemaRegistry.createSchemaContextFactory(SchemaSourceFilter.ALWAYS_ACCEPT);
        allTestSources = new HashSet<>();
        final SchemaListenerRegistration reg = schemaRegistry.registerSchemaSourceListener(new SchemaSourceListener() {

            @Override
            public void schemaSourceUnregistered(final PotentialSchemaSource<?> source) {
                // NOOP
            }

            @Override
            public void schemaSourceRegistered(final Iterable<PotentialSchemaSource<?>> sources) {
                for (final PotentialSchemaSource<?> source : sources) {
                    allTestSources.add(source.getSourceIdentifier());
                }
            }

            @Override
            public void schemaSourceEncountered(final SchemaSourceRepresentation source) {
                // NOOP
            }
        });
        reg.close();
    }

    @Test
    public void testGenerateAll() throws Exception {
        testSetOfModules(allTestSources);
    }

    private void testSetOfModules(final Collection<SourceIdentifier> source) throws Exception {
        final SchemaContext schemaContext = schemaContextFactory.createSchemaContext(source).checkedGet();
        final File outDir = new File("target/collection");
        outDir.mkdirs();
        for (final Module module : schemaContext.getModules()) {
            exportModule(schemaContext, module, outDir);
        }
    }

    private File exportModule(final SchemaContext schemaContext, final Module module, final File outDir)
            throws Exception {
        final File outFile = new File(outDir, YinExportUtils.wellFormedYinName(module.getName(), module.getRevision()));
        try (OutputStream output = new FileOutputStream(outFile)) {
            YinExportUtils.writeModuleToOutputStream(schemaContext, module, output);
        }
        return outFile;
    }
}
