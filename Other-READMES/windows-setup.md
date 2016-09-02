## Mac OS X Environment Set Up
### Manual Installation

#### Step 1: Install the Java JDK 8
1. Browse to the [Oracle Download Site](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).
2. Accept the license agreement and select the appropriate version of java:
    * For Windows x64 - **jdk-8u101-windows-x64.exe**
    * For Windows x86 - **jdk-8u101-windows-i586.exe**
3. Once downloaded, open the install and follow the on screen steps, accepting default install locations
4. Add Java to your PATH
    * Open a CMD prompt and enter the following commands:
    * ```setx JAVA_HOME "C:\Program Files\Java\jdk1.8.0_101”```
    * ```setx PATH "%PATH%;%JAVA_HOME%\bin";```
    * Close the CMD prompt window
5. Validate the Java Installation
    * Open a new CMD prompt and enter ```java -version``` to see if the version is installed properly.

#### Step 2: Install Maven  
1. Browse to the [Maven Download Site](https://maven.apache.org/download.cgi).
2. Download the Maven binary zip archive - **apache-maven-3.3.9-bin.zip**
1. Unzip Maven in a folder of your choice: I have chosen ```C:\Program Files\maven\```
2. Add the unzipped bin path to the PATH variables:
    1. Press **Winkey + Pause**
    2. Go to the **Advanced** tab
    3. Click the **Environment Variables** button
    4. Add the Maven bin path to User Variables: ```C:\Program Files\maven\bin7```
3. Verify successful install
    * Open a new CMD prompt window and enter ```$ mvn -v```

#### Complete
You have now finished the set up of your environment so you can return to
the previous page by [clicking here](../README.md)
