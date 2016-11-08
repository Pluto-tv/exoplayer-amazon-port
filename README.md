Amazon ExoPlayer Port - https://github.com/amzn/exoplayer-amazon-port
This repository is a port of the ExoPlayer project for Amazon devices.
See https://github.com/google/ExoPlayer for the original project.
See 'README_ORIGINAL.md' for the original ExoPlayer README.


[Pluto TV]

We're using http://jitpack.io to build and serve the `aar` library files.
This way, everytime we update this repository its necessary to add a new 
git tag, access jitpack and re-generate a new `compile` instruction (and 
then update the `build.gradle` file of the project that is importing our
`Pluto-tv/exoplayer-amazon-port` version.

Jitpacks build log could be checked at:
https://jitpack.io/com/github/Pluto-tv/exoplayer-amazon-port/pluto-r1.5.11.1/build.log 
(replace `pluto-r1.5.11.1` for your latest TAG)

Thus, we can import the dependencies in our Android projects:

Project's build.gradle:

```groovy
allprojects {
    repositories {
        maven { url 'http://appboy.github.io/appboy-android-sdk/sdk'}
    }
```

Module's build.gradle file:

```groovy
dependencies {
    compile 'com.google.android.exoplayer:amazon-exoplayer:1.5.10@aar'
    compile 'com.google.android.exoplayer:amazon-exoplayer-ext-okhttp:1.5.10@aar'
}
``` 


