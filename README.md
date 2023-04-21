# Android Example Instructions 

1. Clone the Repository:

```
git clone --depth 1 https://github.com/arielmazor/android-example PROJECT_NAME
```

1.  In your _**Project panel**_, click on the little gear icon ( ![Gears icon](https://i.stack.imgur.com/lkezT.png) )
    
2.  Uncheck the `Compact Empty Middle Packages` option

    ![Compact Empty Middle Packages](https://user-images.githubusercontent.com/64742335/233628354-9cb01267-dd1d-4d71-8126-2cc3465961d7.png)
    
3.  Your package directory will now be broken down into individual directories
    
4.  Individually select each directory you want to rename, and:
    

*   Right-click on it
    
*   Select `Refactor`
    
*   Click on `Rename`
    
*   In the pop-up dialog, click on `Rename Package` instead of Rename Directory
    
*   Enter the new name and hit **Refactor**
    
*   Click **Do Refactor** in the bottom
    
*   Allow a minute to let Android Studio update all changes
    
*   _Note: When renaming `com` in Android Studio, it might give a warning. In such case, select_ **Rename All**
    
    ![Enter image description here](https://i.imgur.com/PW9oZll.png)
    

5.  Now open your _**Gradle Build File**_ (`build.gradle` - Usually `app` or `mobile`). Update the `applicationId` in the `defaultConfig` to your new Package Name and Sync Gradle, if it hasn't already been updated automatically:
    
    ![Refactor Directories](https://i.imgur.com/hMx08b7.png)
    
6.  You may need to change the `package=` attribute in your manifest.
    
7.  Clean and Rebuild.
    
    ![Clean and Rebuild](https://i.stack.imgur.com/xBqWu.png)


8 Add your [Google Service File](https://support.google.com/firebase/answer/7015592?hl=en#android&zippy=%2Cin-this-article) to `/app`

9. **Done!** now you can start to code withb ease!

