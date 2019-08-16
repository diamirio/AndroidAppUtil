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
    def versions = [
        androidutil: 16
    ]
    
    /**
     * Base Util package, containing multiple extensions for Rx and Kotlin. 
     */
    implementation "com.tailoredapps.androidutil:util-base:${versions.androidutil}"

    /**
     * UI Util package, containing multiple extensions. 
     */
    implementation "com.tailoredapps.androidutil:util-ui:${versions.androidutil}"
    
    /**
     * Helper for asynchronous resource loading.
     */
    implementation "com.tailoredapps.androidutil:util-async:${versions.androidutil}"
    
    /**
     * Helper for Firebase libraries. 
     */    
    implementation "com.tailoredapps.androidutil:util-firebase:${versions.androidutil}"
    
    /**
     * NetworkResponse and Retrofit extensions.
     */    
    implementation "com.tailoredapps.androidutil:util-network:${versions.androidutil}"
    
    /**
     * A wrapper for nullable types. Mostly needed for Kotlin compliance with Java APIs such as RxJava.
     */    
    implementation "com.tailoredapps.androidutil:util-optional:${versions.androidutil}"
    
    /**
     * Android Manifest Permissions, only better.
     */    
    implementation "com.tailoredapps.androidutil:util-permissions:${versions.androidutil}"
    
    /**
     * Validator and Rules for Value validation.
     */    
    implementation "com.tailoredapps.androidutil:util-validation:${versions.androidutil}"
    
    /**
     * Easily save and restore properties of views. 
     */    
    implementation "com.tailoredapps.androidutil:util-viewstate:${versions.androidutil}"
}
```

## Contribute

Pull requests are welcome. 

When creating a new module, do not forget to:
* add a `proguard-rules.pro` with the proguard rules for dependencies in your new module 
* add a `deploy.settings` to your module with information for bintray

## Build and Deploy

1. Increment `libraryVersion` in `build.gradle` of `Project: AndroidAppUtil`. Increment version in *README.md*
2. `fastlane deploy` to build and upload to bintray.
3. Commit your changes, tag your new version and push to repository.

## AboutLibraries

``` xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="define_aautil" />
    <string name="library_aautil_author">Tailored Apps</string>
    <string name="library_aautil_authorWebsite">https://www.tailored-apps.com/</string>
    <string name="library_aautil_libraryName">AndroidAppUtil</string>
    <string name="library_aautil_libraryDescription">AndroidUtil is a set of utility classes for Android application development.</string>
    <string name="library_aautil_libraryWebsite">https://github.com/tailoredmedia/AndroidAppUtil</string>
    <string name="library_aautil_libraryVersion">16</string>
    <string name="library_aautil_isOpenSource">true</string>
    <string name="library_aautil_repositoryLink">https://github.com/tailoredmedia/AndroidAppUtil.git</string>
    <string name="library_aautil_classPath">com.tailoredapps.androidutil</string>
    <string name="library_aautil_licenseId">apache_2_0</string>
</resources>
```

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
