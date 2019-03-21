# AndroidUtil

![AndroidUtil](https://img.shields.io/badge/TailoredApps-AndroidUtil-blue.svg)

AndroidUtil is a set of utility classes for Android application development.

## Installation

```groovy
allprojects {
    repositories {
        jcenter()
    }
}

dependencies {
    /**
     * Core Util package, containing multiple extensions. 
     */
    implementation 'com.tailoredapps.androidutil:core:{currentVersion}'
    
    /**
     * Helper for asynchronous resource loading.
     */
    implementation 'com.tailoredapps.androidutil:async:{currentVersion}'
    
    /**
     * NetworkResponse and Retrofit extensions.
     */    
    implementation 'com.tailoredapps.androidutil:network:{currentVersion}'
    
    /**
     * A wrapper for nullable types. Mostly needed for Kotlin compliance with Java APIs such as RxJava.
     */    
    implementation 'com.tailoredapps.androidutil:optional:{currentVersion}'
    
    /**
     * Android Manifest Permissions, only better.
     */    
    implementation 'com.tailoredapps.androidutil:permissions:{currentVersion}'
    
    /**
     * Validator and Rules for Value validation.
     */    
    implementation 'com.tailoredapps.androidutil:validation:{currentVersion}'
    
    /**
     * Easily save and restore properties of views. 
     */    
    implementation 'com.tailoredapps.androidutil:viewstate:{currentVersion}'
}
```

For the `currentVersion` refer to **releases tab** above.

## Contribute

Pull requests are welcome. Do not forget to add proguard rules and `deploy.settings` to your module.

## Build and Deploy

To build and deploy the library to bintray, add your changes, commit and then call `fastlane deploy`. This will automatically update the version, commit and push to git and then upload to bintray.

## License

```
Copyright 2019 Tailored Media GmbH.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
