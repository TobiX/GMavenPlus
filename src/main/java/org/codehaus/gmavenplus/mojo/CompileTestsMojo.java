/*
 * Copyright (C) 2011 the original author or authors.
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

import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.util.List;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;


/**
 * Compiles the test sources.
 * Note that this mojo requires Groovy >= 1.5.0, and >= 2.0.0-beta-3 (the indy version) for compiling with invokedynamic option.
 *
 * @author Keegan Witt
 *
 * @phase test-compile
 * @goal testCompile
 * @requiresDependencyResolution test
 * @threadSafe
 */
public class CompileTestsMojo extends AbstractCompileMojo {

    /**
     * Flag to allow test compilation to be skipped.
     *
     * @parameter property="maven.test.skip" default-value="false"
     */
    protected boolean skip;

    /**
     * Executes this mojo.
     *
     * @throws MojoExecutionException
     * @throws MojoFailureException
     */
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (!skip) {
            logGroovyVersion("testCompile");

            try {
                doCompile(getTestSources(), getProjectClasspathElements(), project.getBuild().getTestOutputDirectory(), testOutputDirectory);
            } catch (ClassNotFoundException e) {
                throw new MojoExecutionException("Unable to get a Groovy class from classpath.  Do you have Groovy as a compile dependency in your project?", e);
            } catch (InvocationTargetException e) {
                throw new MojoExecutionException("Error occurred while calling a method on a Groovy class from classpath.", e);
            } catch (InstantiationException e) {
                throw new MojoExecutionException("Error occurred while instantiating a Groovy class from classpath.", e);
            } catch (IllegalAccessException e) {
                throw new MojoExecutionException("Unable to access a method on a Groovy class from classpath.", e);
            } catch (DependencyResolutionRequiredException e) {
                throw new MojoExecutionException("Test dependencies weren't resolved.", e);
            } catch (MalformedURLException e) {
                throw new MojoExecutionException("Unable to add project dependencies to classpath.", e);
            }
        } else {
            getLog().info("Skipping compilation of tests because ${maven.test.skip} was set to true.");
        }
    }

    /**
     * @see org.codehaus.gmavenplus.mojo.AbstractCompileMojo#getProjectClasspathElements()
     */
    protected List getProjectClasspathElements() throws DependencyResolutionRequiredException {
        return project.getTestClasspathElements();
    }

}