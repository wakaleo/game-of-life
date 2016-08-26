##DevOps BootCamp Fall 2016

###Welcome
This is an introduction, demo, and workshop to DevOps. Today we will be working
with some common tools found in industry hopefully giving you better insight on
what can be done with modern software engineering tools.

###Goals
* Pull down, update, and deploy a functional web application via the tools commonly used in a continuous integration pipeline.
* A working pipeline will also demonstrate a continuous delivery process by automatically promoting your application to a live, production environment within minutes of committing code to a source control repository.

###How You'll Do It
1. Break up into small teams of 2-4 individuals
2. Fork and clone Application code from GitHub
3. Get the application working and commit it back to GitHub
4. See your app deploy to an app server

###Step 1: Organize
* Break up into small teams of 2-4 people.
* Look at the failing build at http://build.liatrio.com/....(Which build server are we using for this?) //NEED TO UPDATE
* Review the documentation and decide how to proceed.

###Step 2: Forking, Cloning and Building Your Application
* Get a copy of the code for the exercise.
  * Fork the application repository at github.com/liatrio/game-of-life to one or more of your team members' Github Accounts.
  * If you don't have a Github account you should create one now.
* See you repository build on our build server.
  * Clone the Liatrio Jenkins repository //NEED TO UPDATE
  * Edit the build JSON to include your app's Github URL.
  * Create a pull request to have the app added to our automation job.
  * Verify that you job is added to Jenkins and that a build starts and fails like the original application.

###Step 3: Working With the Application Code and the Delivery Pipeline
* Pull down the code from your forked repository
  * Install a Git client if you don't have one already.
  * "Clone" the repository to your computer.
* Install project dependencies that are required to build/test/run your application.
  * Select your OS from the list of README's below then return to this page after installion is complete.
    * NEED TO UPDATE
* Build and test the application locally.
  * Follow the instructions located in the applications README.md file located [here](/orig_README.markdown).
* Determine what is breaking and change the applications code
* Build again and fix the errors until they are completely gone.
  * This may take a few attempts
  * You should be able to solve them by looking at the console output.
  * Roughly the last 50 lines will have information that will help you.
* Commit the working code back to your forked repository.
* See that your build is kicked off again and that is builds successfully.

###Step 4: Deploying Your App via Jenkins
* When the application is building correctly on Jenkins, the application will deploy to an application server.

**First Draft, Will continue and make a new ticket to update**
