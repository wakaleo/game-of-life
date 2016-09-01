##Ubuntu Environment Set Up

####Step 1: Install the Java JDK
1. Add the Webupd8 ppa:
    * ```$ sudo add-apt-repository ppa:webupd8team/java```
2. Run the oracle installer
    * ```$ sudo apt-get -y install oracle-java8-installer```
    * It will ask you to agree to Oracle's binary agreement.
      * click _OK_.
      * Then select _Yes_.
3. Verify the install:
    * ```$ java -version```

####Step 2: Install Maven  
Run the following command:
```
$ sudo apt-get install maven
```
####Complete
You have now finished the set up of your environment so you can return to
the previous page by [clicking here](../README.md)
