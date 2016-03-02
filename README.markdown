Git.Jenkins Testing pull request####.Testing
This is a simple demonstration application used in the [Jenkins: The Definitive Guide](http://wakaleo.com/books/jenkins-the-definitive-guide) book.

## Building the project

The project is a simple multi-module Maven project. To build the whole project, just run `mvn install` from the root directory.

## Running the game

The application is a very simple online version of [Conway's 'game of life'](http://en.wikipedia.org/wiki/Conway's_Game_of_Life). To see what the game does, run `mvn install` as described above, thengo to the gameoflife-web directory and run `mvn jetty:run`. The application will be running on http://localhost:9090.

## Running the acceptance tests

The acceptance tests are written using Webdriver and [Thucydides](http://thucydides.info). They are designed to run against a running server. Run the jetty instance as described about, then, in another window, go to the gameoflife-acceptance-tests directory and run `mvn clean verify`. The test reports will be generated in the `target/site/thucydides` directory.

## The book

Streamline software development with Jenkins, the popular Java-based open source tool that has revolutionized the way teams think about Continuous Integration (CI). This complete guide shows you how to automate your build, integration, release, and deployment processes with Jenkins—and demonstrates how CI can save you time, money, and many headaches.

Ideal for developers, software architects, and project managers, Jenkins: The Definitive Guide is both a CI tutorial and a comprehensive Jenkins reference. Through its wealth of best practices and real-world tips, you'll discover how easy it is to set up a CI service with Jenkins.

 - Learn how to install, configure, and secure your Jenkins server
 - Organize and monitor general-purpose build jobs
 - Integrate automated tests to verify builds, and set up code quality reporting
 - Establish effective team notification strategies and techniques
 - Configure build pipelines, parameterized jobs, matrix builds, and other advanced jobs
 - Manage a farm of Jenkins servers to run distributed builds
 - Implement automated deployment and continuous delivery

## The author

John is an experienced consultant and trainer specialising in Enterprise Java, Web Development, and Open Source technologies, based in Sydney, Australia. Well known in the Java community for his many published articles, and as author of Java Power Tools, John helps organisations around the world to optimize their Java development processes and infrastructures and provides training and mentoring in open source technologies, SDLC tools, and agile development processes. John is CEO of [Wakaleo Consulting](http://www.wakaleo.com), a company that provides consulting, training and mentoring services in Enterprise Java and Agile Development. He is also part of the founding team of [Test Automation](http://www.testautomation.com.au/), a service that automates the manual regression tests conducted during the integration and acceptance testing stages of a web site release.
