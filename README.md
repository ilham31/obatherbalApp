# KMS Jamu Mobile Application

## Table of contents
* [General info](#general-info)
* [Technologies](#technologies)
* [Dependencies](#dependencies)

## General info
This project is built an mobile application that used to view details on herbal medicines, such as herbs or kampo, plants, and compounds. In addition, the application can predict the efficacy of a plant or compound, compare the formulas found in two herbs, see the usefulness of medicinal plants in certain areas to overcome a disease, and see knowledge about herbal medicines.
	
## Technologies
Project is created with:
* Android Studio 3.3.2
* Minimum sdk version 16
* Library Volley 
* Google Maps API 

	
## Dependencies
To run this project, add this dependencies on build.gradle:

```
dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.android.support:appcompat-v7:28.0.0'
    implementation 'com.android.support:cardview-v7:28.0.0'
    implementation 'com.android.support:recyclerview-v7:28.0.0'
    implementation 'com.android.support:design:28.0.0'
    implementation 'com.android.support.constraint:constraint-layout:1.1.3'
    implementation 'com.android.support:support-v4:28.0.0'
    implementation 'com.android.volley:volley:1.1.1'
    implementation("com.github.bumptech.glide:glide:4.8.0") {
        exclude group: "com.android.support"
    }
    implementation "com.android.support:support-fragment:28.0.0"
    implementation 'com.google.android.gms:play-services-maps:16.1.0'
    implementation 'com.google.android.gms:play-services-location:16.0.0'
    implementation 'com.google.maps.android:android-maps-utils:0.5+'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'

}
```
