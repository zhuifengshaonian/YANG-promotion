/*
 * Copyright (c) 2013 Cisco Systems, Inc. and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.onos.yangtools.yang2sources.plugin;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import com.google.common.collect.Lists;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import org.apache.maven.model.Plugin;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.project.MavenProject;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.onos.yangtools.yang.model.api.Module;
import org.onos.yangtools.yang.model.api.SchemaContext;
import org.onos.yangtools.yang2sources.plugin.ConfigArg.CodeGeneratorArg;
import org.onos.yangtools.yang2sources.plugin.YangToSourcesProcessor.YangProvider;
import org.onos.yangtools.yang2sources.spi.BasicCodeGenerator;
import org.onos.yangtools.yang2sources.spi.MavenLogAware;
import org.onos.yangtools.yang2sources.spi.MavenProjectAware;

public class GenerateSourcesTest {

    private String yang;
    private YangToSourcesMojo mojo;
    private File outDir;
    @Mock
    private MavenProject project;
    @Mock
    private Plugin plugin;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        yang = new File(getClass().getResource("/yang/mock.yang").toURI()).getParent();
        outDir = new File("/outputDir");
        YangProvider mock = mock(YangProvider.class);
        doNothing().when(mock).addYangsToMetaInf(any(Log.class), any(MavenProject.class), any(File.class),
                any(File[].class));

        YangToSourcesProcessor processor = new YangToSourcesProcessor(mock(Log.class), new File(yang), new File[] {},
                Lists.newArrayList(new CodeGeneratorArg(GeneratorMock.class.getName(), "outputDir")), project, false,
                mock);
        mojo = new YangToSourcesMojo(processor);
        doReturn(new File("")).when(project).getBasedir();
        doReturn(Collections.emptyList()).when(plugin).getDependencies();
        doReturn(plugin).when(project).getPlugin(YangToSourcesMojo.PLUGIN_NAME);
        mojo.setProject(project);
    }

    @Test
    public void test() throws Exception {
        mojo.execute();
        assertEquals(1, GeneratorMock.called);
        assertEquals(outDir, GeneratorMock.outputDir);
        assertEquals(project, GeneratorMock.project);
        assertNotNull(GeneratorMock.log);
        assertTrue(GeneratorMock.additionalCfg.isEmpty());
        assertThat(GeneratorMock.resourceBaseDir.toString(), containsString("target" + File.separator
                + "generated-sources" + File.separator + "spi"));
    }

    public static class GeneratorMock implements BasicCodeGenerator, MavenLogAware, MavenProjectAware {

        private static int called = 0;
        private static File outputDir;
        private static Log log;
        private static Map<String, String> additionalCfg;
        private static File resourceBaseDir;
        private static MavenProject project;

        @Override
        public Collection<File> generateSources(final SchemaContext context, final File outputBaseDir, final Set<Module> currentModules)
                throws IOException {
            called++;
            outputDir = outputBaseDir;
            return Lists.newArrayList();
        }

        @Override
        public void setLog(final Log log) {
            GeneratorMock.log = log;
        }

        @Override
        public void setAdditionalConfig(final Map<String, String> additionalConfiguration) {
            GeneratorMock.additionalCfg = additionalConfiguration;
        }

        @Override
        public void setResourceBaseDir(final File resourceBaseDir) {
            GeneratorMock.resourceBaseDir = resourceBaseDir;

        }

        @Override
        public void setMavenProject(final MavenProject project) {
            GeneratorMock.project = project;
        }
    }

}
