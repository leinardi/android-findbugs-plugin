package com.leinardi.android.findbugs

import com.android.build.gradle.AppPlugin

/*
    Copyright 2017 Roberto Leinardi

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.quality.FindBugs
import org.gradle.api.plugins.quality.FindBugsPlugin
import org.gradle.api.tasks.StopExecutionException

/**
 * A plugin that lets you use the findbugs plugin for Android projects.
 */
class AndroidFindBugsPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.plugins.with {
            apply FindBugsPlugin
        }

        def variants
        if (hasPlugin(project, AppPlugin)) {
            variants = project.android.applicationVariants
        } else if (hasPlugin(project, LibraryPlugin)) {
            variants = project.android.libraryVariants
        } else {
            throw new StopExecutionException(
                    "Must be applied with 'android' or 'android-library' plugin.")
        }

        variants.all { variant ->
            def name = variant.name
            def findbugs = project.tasks.create "findbugs${name.capitalize()}", FindBugs
            findbugs.group = "Verification"
            findbugs.dependsOn variant.javaCompile
            findbugs.source variant.javaCompile.source
            findbugs.classpath = project.fileTree(variant.javaCompile.destinationDir)
            findbugs.classes = project.fileTree("$project.buildDir/intermediates/classes/${variant.dirName}")
                    .exclude('**/*R$*.class')
                    .exclude('**/R.class')
                    .exclude('**/BR.class')
                    .exclude('**/Manifest.class')
                    .exclude('**/Manifest$*.class')
            project.tasks.getByName("check").dependsOn findbugs
        }
    }

    static def hasPlugin(Project project, Class<? extends Plugin> plugin) {
        return project.plugins.hasPlugin(plugin)
    }
}