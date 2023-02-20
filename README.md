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

![image](https://user-images.githubusercontent.com/109730490/216053297-354d3b7d-e8df-4acb-a4be-8b5ab75ce275.png)

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

> NOT: Fragment'ı kullanabilmek için FragmentManager denen yapıyı çağırmalıyız. ve yapacağımız işlemleri başlatmak için fragmentTransaction denen bir yapı oluşturuyoruz.

> Sign in ve sign up işlemleri için iki farklı fragment tasarımı yaptım:

![image](https://user-images.githubusercontent.com/109730490/216053805-cfe6a7b9-2feb-4c2a-8fc7-79e14a655f2f.png)

## Fragment'ları Activity İçinde Gösterelim

> Sign In ve Sign Up ekranları arasındaki geçişi textView'lara onClick metodu vererek yapacağım. Aşağıdaki resimde kırmızı işaretli yerlere koyduğum textView'lara tıklandığında iki ekran arası geçiş sağlanacak:

![image](https://user-images.githubusercontent.com/109730490/216060744-2be5b644-6e3b-4b08-85d6-45de2e9bf8a0.png)

> activity_main.xml dosyasında constraint layout'un içine fragment'ları göstermek için bir frame layout ekleyelim:

![image](https://user-images.githubusercontent.com/109730490/216066291-daaadaaf-397b-45e8-9559-67ae14512ab4.png)

> Ekledikten sonra frameLayout'a bir id verelim:

![image](https://user-images.githubusercontent.com/109730490/216066666-2ec045b6-e3fb-4c27-9632-0b2bbd9e967c.png)

## NAVIGATION

> Şimdi oluşturduğum fragment'lar için navigation framework'ünü kullanacağım. 

> Önce navigation için gerekli gradle  eklemelerini kontrol ettim. Bir eksik olmadığı için devam ettim. 

> Şimdi fragment'lar arası geçiş ve veri aktarımını ayarlayacağım. RES klasörü altında bir navigation grafiği oluşturalım. Bunun için RES -> new -> android resource file (aşağıda işaretli 3 alanı belirledik): 

![image](https://user-images.githubusercontent.com/109730490/216058532-e7b76157-eab8-4ee8-a08e-174f9ff0d255.png)

> Bir projenin içinde birden fazla navigation grafiği oluşturulabileceği için isimlendirmeye dikkat ettim.


> MainActivity.kt'de onClickSignInText ve onClickSignUpText metodlarını implemente edelim:

```kotlin
package com.example.studygalaxy.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.studygalaxy.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun onClickSignInText(view : View){

        // Fragment'ı kullanabilmek için FragmentManager denen yapıyı çağırmalıyız:
        val fragmentManager = supportFragmentManager

        // Yapacağımız işlemleri başlatmak için fragmentTransaction denen bir yapı oluşturuyoruz.
        // Bu fragment ile ilgili değişiklik yapacağız demiş oluyoruz:
        val fragmentTransaction = fragmentManager.beginTransaction()
        // Artık işlemlerimizi fragmentTransaction kullanarak yapabiliriz.

        // Fragment oluşturalım:
        // Eşitliğin sağ tarafı Fragment'ın ismi:
        val signInFragment = SignInFragment()

        // replace: Öncesinde bir fragment varsa onu kaldırır ve istediğimiz fragment'ı ekler.
        // ilk parametre: Bu fragment'ı kim kullanacak
        // ikinci fragment: hangi fragment'ı gösterecek
        // .commit() diyerek transaction'ı commit ediyorum yani burada yaptığım işlemi artık sonlandırıyorum:
        fragmentTransaction.replace(R.id.frameLayoutID, signInFragment).commit()
    }

    fun onClickSignUpText(view : View){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        // Fragment oluşturalım:
        // Eşitliğin sağ tarafı Fragment'ın ismi:
        val signUpFragment = SignUpFragment()

        // replace: Öncesinde bir fragment varsa onu kaldırır ve istediğimiz fragment'ı ekler.
        // ilk parametre: Bu fragment'ı kim kullanacak
        // ikinci fragment: hangi fragment'ı gösterecek
        // .commit() diyerek transaction'ı commit ediyorum yani burada yaptığım işlemi artık sonlandırıyorum:
        fragmentTransaction.replace(R.id.frameLayoutID, signUpFragment).commit()

    }
}
```
> Oluşturduğumuz frameLayout içine bir NavHostFragment ekledik ve hazırladığımız navigation graph'ı seçtik:

![image](https://user-images.githubusercontent.com/109730490/216093746-1b2b1ade-99ec-4e30-aa62-b7c11001e16b.png)

> Oluşturduğumuz Mainactivity'den action bar'ı kaldırmak istiyoruz bu yüzden Android.Manifest.xml dosyasına aşağıda işaretlediğimiz satırı ekledik:

![image](https://user-images.githubusercontent.com/109730490/216103768-644ab6ef-564c-4ec2-a799-8d8332970002.png)

## BOTTOM NAVIGATION BAR

> MainActivity2 içinde bir bottom navigation bar oluşturacağım. Araştırdığımda bottom navigation bar içinde 3 ila 5 tane item olabileceğini gördüm. Ben 5 tane kullanacağım. Bir activity içinde 5 fragment ile fraklı işlevleri göstereceğim. 

> Bir yandan da bu activity'yi oluşturduğum andan itibaren view binding kullanmaya başlayacağım. 

### VIEW BINDING

> View Binding'i kullanmak için öncelikle build.gradle (app-module) dosyasında view binding’i aktive etmemiz gerekiyor bunun için android bloğunun içine aşağıdaki kodu eklememiz gerekiyor:

```kotlin
// ViewBinding'i aktif hale getirelim:
    viewBinding{
        enabled = true
    }
```

> Sonrasında MainActivity2'de view binding kullanmak istediğim için aşağıdaki işlemleri yapıyorum:

```kotlin
package com.ozlem.studygalaxy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ozlem.studygalaxy.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    // view binding için :
    private lateinit var binding2 : ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // view binding için :
        binding2 = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding2.root)
    }
```

### BOTTOM NAVIGATION BAR DEVAM

> activity_main2.xml dosyasına gidiyoruz ve arama çubuğundan aratıp bularak ekrana bir bottom navigation view ekliyoruz. Sonra constraint layout içine bir frame layout ekliyoruz sonuç olarak component tree şu hali alıyor:

![image](https://user-images.githubusercontent.com/109730490/219958884-52801b04-6dad-4f08-8e24-a726fd79ca27.png)

> Elde etmek istediğimiz görünüm bu:

![image](https://user-images.githubusercontent.com/109730490/219960997-47a8fcdf-9edf-4a35-87ff-d0b1351e04fc.png)

> Bu yüzden activity_main2.xml dosyasının kod bölümünü aşağıdaki hale getiriyoruz:

```kotlin
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity2">

    <FrameLayout
        android:id="@+id/frameLayout2ID"
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintBottom_toTopOf="@+id/bottomNavigationViewId"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

    </FrameLayout>

    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/bottomNavigationViewId"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent">

    </com.google.android.material.bottomnavigation.BottomNavigationView>

</androidx.constraintlayout.widget.ConstraintLayout>
```

> Şimdi fragment'lar için bottom navigation view'da görüncek icon'lara ihtiyacımız var. Bu yüzden her icon için bir vector asset oluşturmalıyız. Bunu şu şekilde yapıyorum:

![image](https://user-images.githubusercontent.com/109730490/219961418-0a6195e2-0ff1-4d31-aa2f-46c116d1830e.png)

> Açılan ekranda clip_art'ı işaretledikten sonra işaretli yere tıklayarak icon seçiyoruz:

![image](https://user-images.githubusercontent.com/109730490/219961493-3603febe-e3fa-4da3-8cb2-fd08ee061a51.png)

> İstediğimiz icon'u bu şekilde aratarak bulabiliriz:

![image](https://user-images.githubusercontent.com/109730490/219961559-cbc5f1cc-4475-4efe-9eba-903ed65e2083.png)

> Rengi için sonradan xml dosyasından değişiklik yapabileceğim için değiştirmiyorum:

![image](https://user-images.githubusercontent.com/109730490/219961673-4f86146e-ac05-4b80-a489-751aa977385c.png)

> Toplam 5 icon için 5 adet vector asset oluşturdum :

![image](https://user-images.githubusercontent.com/109730490/219962529-95b02b99-2ef4-480e-ad22-b1d7c872798d.png)

> Şimdi bottom navigation view için bir menu'ye ihtiyacımız var bu yüzden yeni bir android resource directory oluşturuyoruz:

![image](https://user-images.githubusercontent.com/109730490/219962723-fc228233-8620-4daf-8609-48eb7a9e5cd1.png)

![image](https://user-images.githubusercontent.com/109730490/219963488-79c10727-7ef7-4b7c-84f2-0e1a2935b560.png)

> Şimdi menu > new > menu resource file:

![image](https://user-images.githubusercontent.com/109730490/219963607-79c8ee29-b582-48b8-8421-ceabb834390f.png)

> Sonrasında bottom navigation bar'da kaç bölüm olmasını istiyorsak o kadar menu item’ı şekilde göründüğü gibi component tree’nin içine sürüklemeliyiz:

![image](https://user-images.githubusercontent.com/109730490/219963753-c8370d41-f043-4f02-a7ba-6f873331440b.png)

> Şuanda elimizde bu var:

![image](https://user-images.githubusercontent.com/109730490/219963835-792a4e88-9a8d-4682-9318-baa0ad10669c.png)

> Sonrasında title (başlık) bölümlerini değiştirdik:

![image](https://user-images.githubusercontent.com/109730490/219963947-fe4a3588-79d0-4ba4-8b2c-2d34636c653b.png)

> Icon'ları ve id'leri ekleyelim:

![image](https://user-images.githubusercontent.com/109730490/219964277-97b90fee-8874-415c-ab90-6bc7e16fadee.png)

> activity_main2.xml dosyasına gidip bunu ekliyoruz:

![image](https://user-images.githubusercontent.com/109730490/219964354-4921be23-8cb2-4745-b348-f942b30e29d5.png)

> Şuanda elimizde olan görüntü bu:

![image](https://user-images.githubusercontent.com/109730490/219964703-d0233136-9d60-4350-815d-b0a6f71d6e97.png)

> Şimdi bottom navigation bar’daki bölüm sayısı kadar fragment oluşturmalıyız. 

![image](https://user-images.githubusercontent.com/109730490/219964943-90ffbe32-fc8c-4ad1-a550-c080ba9186ba.png)

> Bu fragment'ları belirlediğim özelliklere göre yapılandıracağım. 

> MainActivity2 için gerekli şeyleri ekleyelim:

```kotlin
package com.ozlem.studygalaxy.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ozlem.studygalaxy.*
import com.ozlem.studygalaxy.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {

    // view binding için :
    private lateinit var binding2 : ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // view binding için :
        binding2 = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding2.root)

        // İlk açıldığında home page'i göstermek için:
        replaceFragment(HomeFragment())

        // bottom navigation bar'ın listener'ını yazalım:
        binding2.bottomNavigationViewId.setOnItemSelectedListener {

            when(it.itemId){

                // Burada sol tarafta bottom_navigation_menu.xml'deki id'leri kullanıyoruz,
                // Sağ tarafta ise Fragment'ların dosya isimlerini kullanıyoruz:
                R.id.statistics_icon_id -> replaceFragment(StatisticsFragment())
                R.id.goals_icon_id -> replaceFragment(GoalsFragment())
                R.id.home_icon_id -> replaceFragment(HomeFragment())
                R.id.calender_icon_id -> replaceFragment(CalenderFragment())
                R.id.profile_icon_id -> replaceFragment(ProfileFragment())

                else ->{ }

            }
            true
        }

    }

    private fun replaceFragment(fragment : Fragment){

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        // activity_main2.xml'deki frame layout'un id'si: frameLayout2ID :
        fragmentTransaction.replace(R.id.frameLayout2ID,fragment)
        fragmentTransaction.commit()

    }

}
```

> Manifest dosyasında MainActivity2 için action bar'ı kapattım:

![image](https://user-images.githubusercontent.com/109730490/219982205-94c70ab7-15d8-40ec-a2a9-653d4773bd0e.png)

> Şimdi önce fragment'lar için UI kısmını düzenleyeceğim sonrasında navigation_graph'lerini çıkaracağım. 
