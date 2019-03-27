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
     * Core Util package, containing multiple Android extensions. 
     */
    implementation "com.tailoredapps.androidutil:core:${versions.androidutil}"
    
    /**
     * Helper for asynchronous resource loading.
     */
    implementation "com.tailoredapps.androidutil:async:${versions.androidutil}"
    
    /**
     * NetworkResponse and Retrofit extensions.
     */    
    implementation "com.tailoredapps.androidutil:network:${versions.androidutil}"
    
    /**
     * A wrapper for nullable types. Mostly needed for Kotlin compliance with Java APIs such as RxJava.
     */    
    implementation "com.tailoredapps.androidutil:optional:${versions.androidutil}"
    
    /**
     * Android Manifest Permissions, only better.
     */    
    implementation "com.tailoredapps.androidutil:permissions:${versions.androidutil}"
    
    /**
     * Validator and Rules for Value validation.
     */    
    implementation "com.tailoredapps.androidutil:validation:${versions.androidutil}"
    
    /**
     * Easily save and restore properties of views. 
     */    
    implementation "com.tailoredapps.androidutil:viewstate:${versions.androidutil}"
}
```

For the `currentVersion` refer to **releases tab** above.  
Example: `implementation com.tailoredapps.androidutil:core:3`

## Contribute

Pull requests are welcome. 

When creating a new module, do not forget to:
* add a `proguard-rules.pro` with the proguard rules for dependencies in your new module 
* add a `deploy.settings` to your module with information for bintray

## Build and Deploy

1. Increment `libraryVersion` in `build.gradle` of `Project: AndroidAppUtil`.
2. Tag your new version, commit and push to repository.
3. `fastlane deploy` to build and upload to bintray.

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
