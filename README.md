# Cluedo Organiser

## About

This app allows you to organise a variant of the live-action game Assassin called Cluedo.
In this game, each player is assigned a person, place, and a "weapon" (e.g. Alice needs to get Bob by the gate with an umbrella), hence the name "Cluedo".
They then have to tag their target in that place with that item.
The person who they just tags then tells them who their next person, place, and weapon is, and the game goes on until there is only one person left.

## Using the App

In the "Players" tab, you can create new groups of players for different events.
An email address can be added to each player, allowing the app to email their person, place, and weapon to them.
You can also mark a player as inactive if they don't want to be included in the next game but still want to be kept in the group.

Similarly, you can create groups of places and weapons for specific locations in the "Places" and "Weapons" tabs.

After creating the player, place, and weapon groups for your game, go to the "Games" tab and create a new game by selecting these groups.
After creating the game, you can press the email button to email all players their targets, places, and weapons.
If players figure out who is getting them, you can edit the game and press the shuffle button to change everyone's target.

## Building the App

If you wish to clone this repo and build the app, you will need to add a `gradle.properties` file to the root directory and set the `GMAIL_ADDRESS` and `GMAIL_APP_PASSWORD`
to the address and app password of the gmail account you wish the app to send emails from.

```
# Project-wide Gradle settings.
# IDE (e.g. Android Studio) users:
# Gradle settings configured through the IDE *will override*
# any settings specified in this file.
# For more details on how to configure your build environment visit
# http://www.gradle.org/docs/current/userguide/build_environment.html
# Specifies the JVM arguments used for the daemon process.
# The setting is particularly useful for tweaking memory settings.
org.gradle.jvmargs=-Xmx2048m -Dfile.encoding=UTF-8
# When configured, Gradle will run in incubating parallel mode.
# This option should only be used with decoupled projects. For more details, visit
# https://developer.android.com/r/tools/gradle-multi-project-decoupled-projects
# org.gradle.parallel=true
# AndroidX package structure to make it clearer which packages are bundled with the
# Android operating system, and which are packaged with your app's APK
# https://developer.android.com/topic/libraries/support-library/androidx-rn
android.useAndroidX=true
# Kotlin code style for this project: "official" or "obsolete":
kotlin.code.style=official
# Enables namespacing of each library's R class so that its R class includes only the
# resources declared in the library itself and none from the library's dependencies,
# thereby reducing the size of the R class for that library
android.nonTransitiveRClass=true
# GMail address and app password
GMAIL_ADDRESS=
GMAIL_APP_PASSWORD=
```
