##Mac OS X Environment Set Up
###Manual Installation

####Step 1: Install the Java JDK 8
1. Go to [Oracle Download](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html).
2. Accept the agreement and select the Mac dmg.
3. Once downloaded, open the install and follow the on screen steps.

####Step 2: Install Maven  
1. Browse to [Maven Downloads](https://maven.apache.org/download.cgi).
2. Download the apache maven binary tar.gz file.
3. In a terminal navigate to where the file has been downloaded.
4. Run the following command
    * ```$ tar xzvf apache-maven-3.3.9-bin.tar.gz```
5. Move Maven to the opt directory.
    * ```$ mv apache-maven-3.3.9 /opt/```
6. Add Maven to the Path
    * ```$ export PATH=/opt/apache-maven-3.3.9/bin:$PATH```
7. Verify successful install
    * ```$ mvn -v```

####Step 3: Install Jetty  
1. Browse to [Jetty on Eclipse](www.eclipse.org/jetty/download.html).
2. Move download from Downloads directory to opt directory.
    * ```$ sudo mv jetty* /opt/jetty```
3. Change to opt directory and test wheb server.
    * ```$ cd /opt/jetty```
    * ```$ sudo java -jar start.jar```

To stop the process click _ctrl+c_

####Complete
You have now finished the set up of your environment so you can return to
the previous page by [clicking here](../README.md)
