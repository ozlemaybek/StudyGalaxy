# StudyGalaxy

> Study Galaxy; bitirme projem olan bir android uygulamasıdır. 

> Projedeki Teknolojiler: MVVM Mimarisi, Data Binding

# Geliştirirken Yapılanlar

# GRADLE DOSYALARI DÜZENLEMELERİ

> Önce projenin gradle dosyalarında gerekebilecek değişiklikleri yaptım. 

> Android Studio güncellemesinden sonra artık proje bağımlılıklarını settings.gradle dosyasındaki dependencyResolutionManagament bloğunda belirtmeliyiz. bu yüzden jcenter() satırını settings.gradle dosyasına ekledim. 

> settings.gradle dosyası : 

```kotlin
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        jcenter()
        google()
        mavenCentral()
    }
}
rootProject.name = "StudyGalaxy"
include ':app'
```

> build.gradle (project) dosyası :

```kotlin
/* Sonradan yaptığım eklemeler: */
// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    ext.kotlin_version = "1.7.21"
    repositories {
        google()
        jcenter()
    }
    dependencies {
        classpath "com.android.tools.build:gradle:4.0.2"
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlin_version"
        def nav_version = "2.3.0"
        classpath "androidx.navigation:navigation-safe-args-gradle-plugin:$nav_version"

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
/* Sonradan yaptığım eklemeler buraya kadardı. */


// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id 'com.android.application' version '7.4.0' apply false
    id 'com.android.library' version '7.4.0' apply false
    id 'org.jetbrains.kotlin.android' version '1.7.21' apply false
}

task clean(type: Delete) {
    delete rootProject.buildDir
}
```

> build.gradle (app-module) dosyası :

```kotlin
plugins {
    id 'com.android.application'
    id 'org.jetbrains.kotlin.android'
    // Eklemelerim :
    id 'kotlin-android-extensions'
    id 'androidx.navigation.safeargs.kotlin'
    id 'kotlin-kapt'
}

android {
    namespace 'com.example.studygalaxy'
    compileSdk 33

    defaultConfig {
        applicationId "com.example.studygalaxy"
        minSdk 23
        targetSdk 33
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }

    // DataBinding'i aktif hale getirelim:
    dataBinding{
        enabled = true
    }

}

dependencies {

    // Güncellemeleri yapmadan önce gerekli tanımlamaları yapalım:
    def retrofitVersion = '2.8.1'
    def glideVersion = '4.11.0'
    def rxJavaVersion = '2.2.8'
    def rxAndroidVersion = '2.1.1'
    def roomVersion = '2.5.0'
    def navVersion = '2.5.3'
    def preference_version= '1.2.0'

    implementation 'androidx.core:core-ktx:1.9.0'
    implementation 'androidx.appcompat:appcompat:1.6.0'
    implementation 'com.google.android.material:material:1.8.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.5'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'

    // Sonradan eklediğim implementation'lar:
    implementation "androidx.room:room-runtime:$roomVersion"
    kapt "androidx.room:room-compiler:$roomVersion"
    implementation "androidx.room:room-ktx:$roomVersion"
    implementation "androidx.navigation:navigation-fragment-ktx:$navVersion"
    implementation "androidx.navigation:navigation-ui-ktx:$navVersion"
    implementation "com.squareup.retrofit2:retrofit:$retrofitVersion"
    implementation "com.squareup.retrofit2:converter-gson:$retrofitVersion"
    implementation "com.squareup.retrofit2:adapter-rxjava2:$retrofitVersion"
    implementation "io.reactivex.rxjava2:rxjava:$rxJavaVersion"
    implementation "io.reactivex.rxjava2:rxandroid:$rxAndroidVersion"
    implementation "com.github.bumptech.glide:glide:$glideVersion"
    implementation "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
    implementation "androidx.preference:preference-ktx:$preference_version"

}
```

> build.gradle dosyalarının düzenlemelerini ilerde kullanabileceğim şeyleride düşünerek şimdilik böyle yaptım. Fakat sonradan değişebilir. 

## LOGO 

> Uygulama logosunu figmada oluşturup svg ve png formatlarında indirdim. svg formatında olanı bikgisayarda manuel olarak proje dosyasına ekledim. png olanı ise android studio'dan drawable kalsörüne ekledim. Sonrasında drawable klasörü içinde bir vector asset oluşturarak svg formatındaki logonun xml dosyasını elde ettim: 

![image](https://user-images.githubusercontent.com/109730490/215202018-3ebd5691-007b-438b-a844-5e05d916332b.png)

## COLORS

> Seçtiğim renkleri colors dosyasına ekledim:

![image](https://user-images.githubusercontent.com/109730490/215202241-628e158a-5b69-472f-8240-6f56d7f0adc6.png)

![image](https://user-images.githubusercontent.com/109730490/215202307-3471af97-2df0-4a0b-a138-57f3e113fba1.png)

> Çalışmaya başlamadan önce Fragment dosyalarını aşağıdaki hale getirdim:

![image](https://user-images.githubusercontent.com/109730490/215200870-044681bb-ec75-466b-8636-184f663aef26.png)

## BUTTONS

> drawable -> new -> drawable resources file :

![image](https://user-images.githubusercontent.com/109730490/215207143-2a2d73cf-637a-4a61-bb96-0cdb916bf136.png)

> Bir xml dosyası açmış olduk burada button'lar için bir arkaplan style'ı belirledim: 

![image](https://user-images.githubusercontent.com/109730490/215208009-d041a59e-aaf5-429f-8507-3ad469309f6c.png)

![image](https://user-images.githubusercontent.com/109730490/215208141-d8f3e703-6131-4b0d-b797-4e5f0dd33be9.png)

## Themes.xml

> Themes.xml dosyasında butonlar için bir style belirledim:

![image](https://user-images.githubusercontent.com/109730490/215210616-159f70e1-e0c2-43cb-b701-c941108c0380.png)


## Strings.xml

> App genelinde kullanacağım stringleri bu şekilde tanımlıyorum:

![image](https://user-images.githubusercontent.com/109730490/215209370-afecfeaa-a0af-45a9-94c4-af866838fb69.png)

## Fragment Düzenlemeleri Devam

> Şimdi sign in için kullanacağım fragment'ı düzenlemeye başlıyorum:

> Frame Layout'a bir ID ekledim.

> Fragmentları activity'ye bağlamadan emülatörde göremeyiz!

> Fragment'ı kullanabilmek için FragmentManager denen yapıyı çağırmalıyız. ve yapacağımız işlemleri başlatmak için fragmentTransaction denen bir yapı oluşturuyoruz.
