# Project Title
An Android Application that functions as a mood tracker, but can also 
detect the emotional state of an individual based on their physiological signals.

### Prerequisites
What do I need in order to get your project running? For example,
* Android Studio 3.5.3

### Build Gradle
```
android {
    compileSdkVersion 29
    buildToolsVersion "29.0.2"
    defaultConfig {
        applicationId "com.example.smartwatch_app"
        minSdkVersion 23
        targetSdkVersion 29
        versionCode 2
        versionName "1.0.1"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    testOptions {
        unitTests {
            includeAndroidResources = true
        }
    }
}
```

#### Install libraries and run program

```



```

### Running Tests
Run application through Android Studio 3.5.3.

## Authors
Provide your names here
* Pasquale Iuliano

## References
The following APIs and libraries were used for completion of application:

Java:
* [MPAndroidChart](https://github.com/PhilJay/MPAndroidChart)
* [CompactCalendarView](https://github.com/SundeepK/CompactCalendarView)
* [LIBSVM](https://www.csie.ntu.edu.tw/~cjlin/libsvm/)

Python
* [HeartPy - Python Heart Rate Analysis Toolkit](https://github.com/paulvangentcom/heartrate_analysis_python)















