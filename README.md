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

## fragment_home.xml Tasarımı

> Ekranın başındaki tasarımlar kodlara bakılarak anlaşılabilir ancak nestedScrollView içine constraint Layout atınca constraint layout'un içindeki tasarımı yapmak biraz zorlayabiliyor çünkü eklediğim view'ların constraint'lerini xml'de yazarak belirlemem gerekti. 

> RecylerView'lu bölüme geçiyorum. Önce layout klasöründe bir layout resource file oluşturdum burada recycler view'un tek satırının tasarımını yapacağım. Dosyanın adını "goals_recycler_row.xml" olarak belirledim. 

> goals_recyler_row.xml

```kotlin
<?xml version="1.0" encoding="utf-8"?>

<!--Bu dosyada recyclerView'umuzun bir satırını tasarlayacağız.-->
<!--Bu xml her bir recyclerView sırasında kullanılacak.-->

<androidx.constraintlayout.widget.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/constraint_layout_id_0"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="20dp"
    android:layout_marginTop="20dp"
    android:background="@drawable/info_space_style">

    <LinearLayout
        android:id="@+id/linear_layout_id_0"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="20dp"
        android:orientation="vertical"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent">

        <TextView
            android:id="@+id/textview_0_id"
            android:layout_width="wrap_content"
            android:layout_height="40dp"
            android:layout_gravity="center"
            android:fontFamily="@font/roboto_bold"
            android:gravity="center"
            android:text="Final Project"
            android:textAlignment="center"
            android:textColor="@color/white"
            android:textSize="18dp" />

        <TextView
            android:id="@+id/textview_1_id"
            android:layout_width="wrap_content"
            android:layout_height="40dp"
            android:layout_gravity="center"
            android:fontFamily="@font/roboto_bold"
            android:gravity="center"
            android:text="Everyday"
            android:textAlignment="center"
            android:textColor="@color/white"
            android:textSize="18dp" />

    </LinearLayout>

    <LinearLayout
        android:id="@+id/linear_layout_id_1"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_margin="20dp"
        android:orientation="vertical"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toStartOf="@+id/imageView"
        app:layout_constraintStart_toEndOf="@+id/linear_layout_id_0"
        app:layout_constraintTop_toTopOf="parent">

        <TextView
            android:id="@+id/textview_2_id"
            android:layout_width="wrap_content"
            android:layout_height="40dp"
            android:layout_gravity="center"
            android:fontFamily="@font/roboto_bold"
            android:gravity="center"
            android:text="%99"
            android:textAlignment="center"
            android:textColor="@color/white"
            android:textSize="18dp" />

        <TextView
            android:id="@+id/textview_3_id"
            android:layout_width="wrap_content"
            android:layout_height="40dp"
            android:layout_gravity="center"
            android:fontFamily="@font/roboto_bold"
            android:gravity="center"
            android:text="04.00.00"
            android:textAlignment="center"
            android:textColor="@color/white"
            android:textSize="18dp" />

    </LinearLayout>

    <ImageView
        android:id="@+id/imageView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:src="@drawable/menu_icon"
        android:layout_margin="20dp"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

</androidx.constraintlayout.widget.ConstraintLayout>
```
> Tasarımı şu şekilde oldu:

![image](https://user-images.githubusercontent.com/109730490/220799336-94f9fa09-bd71-4d18-865e-fa847327b8f9.png)

> Şimdi menu klasöründe bir menu resource file açalım ve ismini goals_menu.xml koyalım:

> goals_menu.xml dosyasını aşağıdaki şekilde yapılandırdım:

![image](https://user-images.githubusercontent.com/109730490/220800275-1d0feeea-b5b1-4249-899f-9062e9a86d1f.png)

## recylerView işlemleri devam

> Önce build.gradle(app-module) dosyasına ilgili eklemeyi yaptım:

![image](https://user-images.githubusercontent.com/109730490/220801991-7afbdad2-5e0a-43b1-8d04-6e97a1370485.png)

> Şimdi recylerView'u görüntülemek istediğimiz yere yani "fragment_home.xml" dosyasına recyclerView ekleyelim:

> constraint layout içine bir tane frame layout attım onun içinede recycler view ekledim:

![image](https://user-images.githubusercontent.com/109730490/220803865-5c828926-320d-49f0-a6d3-cc985b5a7e85.png)

## FIREBASE AKTİVASYON İŞLEMLERİ

> Diğer aşamalara devam etmeden önce projenin firebase aktivasyonunu yapmak istiyorum çünkü artık yavaş yavaş kullanıcı işlemleri ile iligli sonuçları görebilmem gerekiyor. 

![image](https://user-images.githubusercontent.com/109730490/222226413-f1208014-a966-4763-b6a8-fba3eb05ea27.png)

![image](https://user-images.githubusercontent.com/109730490/222226501-f1433681-354a-464e-b586-11c6a4849668.png)

![image](https://user-images.githubusercontent.com/109730490/222226648-197a7085-981b-45ad-97b6-aed1917373d2.png)

![image](https://user-images.githubusercontent.com/109730490/222226889-76d524cd-a2af-4141-aeb0-0afa440456d1.png)

> Firebase’e android uygulamamız ile firebase’i birleştireceğimizi belirtelim:

![image](https://user-images.githubusercontent.com/109730490/222227240-8ffda280-3049-48b0-b477-37dba89092b1.png)

> Uygulamanın paket ismini girdim ve "Register App" butonuna tıkladım:

![image](https://user-images.githubusercontent.com/109730490/222227700-92eef6b0-1ca1-462e-8417-51ba6f8e0296.png)

> İkinci adımda bizim için bir dosya oluşturuluyor. Bu  dosya bizim android projemiz ile firebase sunucusunu birbirine bağlayan dosya ve bu dosya olmadan hiçbir işlem yapamıyoruz. Bu yüzden bu dosyayı indiriyorum:

![image](https://user-images.githubusercontent.com/109730490/222228266-fd247b95-1a83-4053-a894-bc49964ed297.png)

> Sonrasında google-services.json dosyasını projemin içine alıyorum bunun için Android Studio'da android görünümünden project görünümüne geçiyorum ve dosyayı app bölümüne yapıştırıyorum:

![image](https://user-images.githubusercontent.com/109730490/222230517-42e7871d-c7ed-4882-a04e-47f7678e5cc6.png)

> Aslında google-services.json dosyasının içindekilerin herhangi birine gösterilmemesi gerekiyor fakat bu projeyi Google Play Store'a yüklemeyeceğim. 

> Şimdi 3. adıma geçiyorum. Bu adımda projeme Firebase SDK'sını ekliyorum. Bu adım 2 aşamalı. 

> 1. ADIM: Bunun için build.gradle (project) dosyasına; aşağıda işaretlediğim kısmı dependencies bloğuna ekliyorum. Çünkü güncellemelerden sonra artık allProjects bloğu settings.gradle dosyasında bulunuyor ve buildScript kısmıda zaten build.gradle (project) dosyamda mevcut. 

![image](https://user-images.githubusercontent.com/109730490/222234212-7f4ea56f-af4d-4774-82e7-54440730f405.png)

> sync now işlemini yaptıktan sonra ikinci adıma geçiyorum.

> 2. ADIM: Sonra 2. adımda ilgili kodları build.gradle (module-app) dosyasına ekliyorum:

![image](https://user-images.githubusercontent.com/109730490/222234254-a7ae9fbb-13df-40e6-b54f-a095499f42f3.png)

> Artık işlem tamamlandı "Continue to Console" butonu ile console'a geri dönüyoruz:

![image](https://user-images.githubusercontent.com/109730490/222235842-dcae8f34-8f5f-44cc-9cd6-3339a7162ac9.png)

> Firebase'in projeye entegre olup olmadığını kontrol etmek amacıyla MainActivity.kt içindeki onCreate metodunun içine Firebase yazıp ilgili özellikler geldi mi diye bakıyoruz:

![image](https://user-images.githubusercontent.com/109730490/222241906-b5bcb323-5e8b-4af0-bb9b-1e3ba9556ac6.png)

## FIREBASE AUTHENTICATION (Kullanıcı İşlemleri)MODÜLÜ ENTEGRASYONU

> Firebase Authentication modülü; kullanıcıların kayıt olabilmesi, giriş yapabilmesi, giriş yapılıp yapılmadığının algılanması gibi özellikleri barındırır.  

> Firebase Console’da soldaki bölümden Authentication’a tıklayalım:

![image](https://user-images.githubusercontent.com/109730490/222244528-22cb0baf-5e9a-48d0-aa58-249beb1af7ab.png)

![image](https://user-images.githubusercontent.com/109730490/222244683-6e363c1d-eb08-4cf7-b907-f4a67eaec741.png)

> Buradaki “Get started” butonu ile authentication yani kullanıcı işlemlerini başlatalım. Get started butonuna basınca şöyle bir ekranla karşılaşıyoruz:

![image](https://user-images.githubusercontent.com/109730490/222244925-18f67763-04da-49c5-b42e-4284864249a6.png)

> Sign-in method’lardan birini, birden fazlasını ya da hepsini uygulamamızda kullanmak isteyebiliriz. Ben email ve parola kullanacağım. Yaptığımız etkinleştirmeleri  android tarafında da uygulamam gerekiyor.

> Email/password seçeneğine tıkladıktan sonra karşımıza gelen ekranda enable yaparak aktif hale getiriyoruz. Resimdeki 2. enable direkt email’e giriş linki atarak password kullanmadan giriş yapmayı sağlıyor ama bunu kullanmayacağım. “Save” butonuna basalım:

![image](https://user-images.githubusercontent.com/109730490/222245720-74ac6ec1-a5d9-4922-944a-941e4d2f9737.png)

> Etkinleştirildi:

![image](https://user-images.githubusercontent.com/109730490/222246011-922f3a7c-88c5-40d3-967e-faa137632018.png)

> Bütün modüllerin ayrı SDK’sı var. Firebase’in bir sürü modülü var ve hepsini kullanmıyoruz. Yük olmaması içinde modüllere ait SDK’lar tek tek kuruluyor. 

> Documentation kısmından Build > Aythentication bölümüne gittik. Sonra aşağıdaki bölüme geçelim:

![image](https://user-images.githubusercontent.com/109730490/222246613-a1fbff8b-0102-41a6-b5ac-86d907d8a4fd.png)

> Ve Android kodları tarafında gerekli authentication işlemlerini yapmaya başlayalım:

> Aşağıda işaretli satırı build.gradle (app-module) içine ekleyelim:

![image](https://user-images.githubusercontent.com/109730490/222247331-276a4f07-c0dc-4ed3-a882-190e8eac7ade.png)

> Şimdi dokümanda bir FirebaseAuth objesi oluşturmamız isteniyor. Sırayla aşağıdaki işlemleri MainActivity.kt içinde yapıyoruz:

![image](https://user-images.githubusercontent.com/109730490/222250393-9308a77e-d11f-472c-a1a6-39969f880399.png)

> Şİmdi güncel kullanıcı yani current user'ı alabilirsin diyor fakat henüz hiçbir kullanıcı oluşturmadım. 

> Bu yüzden SignInFragment ve SignUpFragment'ta kod düzenlemelerini ayarlayalım:

> Önce iki fragment'a ait kt dosyalarında FirebaseAuth objeleri oluşturma işlemini yaptım:

![image](https://user-images.githubusercontent.com/109730490/222252383-799b5fe4-62d3-44ab-a983-81afec481fc2.png)

> Fragment'ta view binding işlemi activity'ye göre farklı işliyor bunun için onCreate yerine onCreateView metodunu kullanıyoruz. Aynı zamanda onDestroy metodundada binding'i null yapıyoruz: 

```kotlin
class SignInFragment : Fragment() {

    // Bir FirebaseAuth objesi oluşturalım:
    private lateinit var auth: FirebaseAuth
    // view binding için:
    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Oluşturduğumuz FirebaseAuth objesini initialize edelim:
        auth = Firebase.auth
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // view binding için:
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
```

## Firebase Firestore Database Entegrasyonu

![image](https://user-images.githubusercontent.com/109730490/224027805-639db0a4-dcd4-45eb-ad96-e65e66d74de2.png)

> Başlatmak için “Create Database” butonuna basmalıyız. Bunu yaptığımızda güvenlik ayarlarını nasıl seçmek istediğimizi soruyor. Burada güvenlikten kastedilen şey veritabanımıza kimin erişebileceği ve kimin erişemeyeceği. Bunun için iki farklı seçeneğimiz var. 

![image](https://user-images.githubusercontent.com/109730490/224028774-5582aa8e-4939-4dfa-8725-8844e71e957f.png)

> 1) Production Mode: Hiçbir şeye izin vermez.

> 2) Test Mode: Herkesin erişimine izin verir ve bunun için 30 günlük bir süre tanımlar. Bunu github’daki token’lara benziyor.

> Şimdilik kodlama aşamasında olduğum için test modunda başlatıyorum.

> “Test Mode” u seçtikten sonra Next butonuna basalım.

> İkinci adımda lokasyon seçmemizi istiyor. Bunu uygulamayı yapacağımız yere yakın seçmemiz gerekiyor.

![image](https://user-images.githubusercontent.com/109730490/224029085-a90d4ad3-2cdc-43e0-acce-ed2caa9a51e2.png)

> eur3 seçeneğini seçip Enable butonuna bastım. Bu işlemi sadece bir kez yapıyoruz bir daha karşımıza gelmeyecek. Şu anda bizim için firestore veritabanı oluşturuluyor. 

> Şimdi oluşturulan alanda kendi database'imi kuracağım. 
