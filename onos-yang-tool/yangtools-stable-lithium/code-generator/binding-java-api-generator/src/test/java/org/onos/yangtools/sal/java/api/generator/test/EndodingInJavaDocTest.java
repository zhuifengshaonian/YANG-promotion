package org.onos.yangtools.sal.java.api.generator.test;

import static org.junit.Assert.assertTrue;
import static org.onos.yangtools.sal.java.api.generator.test.CompilationTestUtils.COMPILER_OUTPUT_PATH;
import static org.onos.yangtools.sal.java.api.generator.test.CompilationTestUtils.FS;
import static org.onos.yangtools.sal.java.api.generator.test.CompilationTestUtils.GENERATOR_OUTPUT_PATH;
import static org.onos.yangtools.sal.java.api.generator.test.CompilationTestUtils.cleanUp;
import static org.onos.yangtools.sal.java.api.generator.test.CompilationTestUtils.getSourceFiles;
import static org.onos.yangtools.sal.java.api.generator.test.CompilationTestUtils.testCompilation;
import com.google.common.collect.ImmutableSet;
import java.io.File;
import java.util.List;
import org.junit.Test;
import org.onos.yangtools.sal.binding.model.api.Type;
import org.onos.yangtools.sal.java.api.generator.GeneratorJavaFile;
import org.onos.yangtools.yang.model.api.SchemaContext;

/**
 * Test if generated classes from yang file is compilable, generated javadoc comments contains
 * symbols as javadoc comment tag, which caused of compilation problem.
 */
public class EndodingInJavaDocTest extends BaseCompilationTest {

    @Test
    public void testAugmentToUsesInAugment() throws Exception {
        final File sourcesOutputDir = new File(GENERATOR_OUTPUT_PATH + FS + "encoding-javadoc");
        assertTrue("Failed to create test file '" + sourcesOutputDir + "'", sourcesOutputDir.mkdir());
        final File compiledOutputDir = new File(COMPILER_OUTPUT_PATH + FS + "encoding-javadoc");
        assertTrue("Failed to create test file '" + compiledOutputDir + "'", compiledOutputDir.mkdir());

        final List<File> sourceFiles = getSourceFiles("/compilation/encoding-javadoc");
        final SchemaContext context = parser.parseFiles(sourceFiles);
        final List<Type> types = bindingGenerator.generateTypes(context);
        final GeneratorJavaFile generator = new GeneratorJavaFile(ImmutableSet.copyOf(types));
        generator.generateToFile(sourcesOutputDir);

        // Test if sources are compilable
        testCompilation(sourcesOutputDir, compiledOutputDir);
        cleanUp(sourcesOutputDir, compiledOutputDir);
    }

}
