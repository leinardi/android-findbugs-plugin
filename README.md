Android FindBugs Plugin
==================

A plugin that lets you use the findbugs plugin for Android projects.


Usage
-----

Apply the plugin in your `build.gradle` along with the regular 'findbugs' plugin:
```groovy
buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath 'com.leinardi.android:findbugs:1.0.0'
    }
}

apply plugin: 'findbugs'
apply plugin: 'com.leinardi.android.findbugs'

findbugs {
    ignoreFailures = true
    effort = "max"
    reportLevel = "medium"
}
```





License
--------

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
