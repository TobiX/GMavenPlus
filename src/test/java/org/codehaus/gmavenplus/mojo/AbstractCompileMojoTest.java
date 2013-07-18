/*
 * Copyright (C) 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.codehaus.gmavenplus.mojo;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.project.MavenProject;
import org.apache.maven.shared.model.fileset.FileSet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.util.List;
import java.util.Set;


/**
 * Unit tests for the AbstractCompileMojo class.
 *
 * @author Keegan Witt
 */
@RunWith(MockitoJUnitRunner.class)
public class AbstractCompileMojoTest {
    @Mock
    private MavenProject project;

    @Mock
    private FileSet fileSet;

    private TestMojo testMojo;

    @Before
    public void setup() {
        Mockito.doReturn("STUBBED_DIRECTORY").when(fileSet).getDirectory();
        Mockito.doReturn(new String[] { "STUBBED_INCLUDES" }).when(fileSet).getIncludesArray();
        testMojo = new TestMojo();
        testMojo.project = project;
        testMojo.sources = new FileSet[] { };
        testMojo.testSources = new FileSet[] { };
    }

    @Test
    public void testGetSources() {
        Set<File> sources = testMojo.getSources();
        Assert.assertEquals(0, sources.size());
    }

    @Test
    public void testGetTestSources() {
        Set<File> testSources = testMojo.getTestSources();
        Assert.assertEquals(0, testSources.size());
    }

    private class TestMojo extends AbstractCompileMojo {

        @Override
        protected List getProjectClasspathElements() throws DependencyResolutionRequiredException {
            return null;
        }

        public void execute() throws MojoExecutionException, MojoFailureException { }

    }

}
