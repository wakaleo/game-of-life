##DevOps BootCamp Fall 2016

###Welcome
This is an introduction, demo, and workshop of DevOps. Today we will be working
with some common tools found in industry hopefully giving you better insight on
what can be done with modern software engineering tools.

###Goals
* Pull down, update, and deploy a functional web application via the tools commonly used in a continuous integration pipeline.
* A working pipeline will also demonstrate a continuous delivery process by automatically promoting your application to a live, production environment within minutes of committing code to a source control repository.

###How You'll Do It
1. Break up into small teams of 2-4 individuals.
2. Fork and clone application code from GitHub.
3. Get the application working and commit it back to GitHub.
4. See your app deploy to an app server.

###Step 1: Organize
1. Break up into small teams of 2-4 people.
2. Look at the failing build at [jenkins.chico.liatr.io](https://jenkins.chico.liatr.io)
3. Review the documentation and decide how to proceed.
4. Install project dependencies that are required to build/test/run your application.
   * Select your OS from the list of README's below then return to this page after installion is complete.
      * [Mac OS X using Homebrew](Other-READMES/mac-homebrew-setup.md) _*Recommended_.
      * [Mac OS X manual install](Other-READMES/mac-manual-setup.md)
      * [Ubuntu](Other-READMES/ubuntu-env-setup.md)

###Step 2: Forking, Cloning and Building Your Application
1. Get a copy of the code for the exercise.
   1. Fork the application repository at github.com/liatrio/game-of-life to one or more of your team members' Github Accounts.
   2. If you don't have a Github account you should create one now.
2. See your repository build on our build server.
   1. Browse to [jenkins.chico.liatr.io](jenkins.chico.liatr.io) and sign in using the information given to you.
   2. Create a new Jenkins job with your repository.
      1. Click _New Item_ on the left hand side.
      2. Enter the name of your job.
      3. Click _Maven Project_.
      4. Scroll down to _Source Code Management_, select _Git_, and enter the link to your repository.
      5. Scroll down to _Build Triggers_ and click _Poll SCM_ and enter "* * * * *"
      6. Scroll down to _Build_  and add "clean install" to _Goals and Options_
      7. Add a _Post Step_ and insert the follow: ```scp -i /var/lib/jenkins/.ssh/id_rsa -o StrictHostKeyChecking=no \
$WORKSPACE/gameoflife-web/target/gameoflife.war \
tomcat-deploy@ip-172-31-26-108.us-west-2.compute.internal:/var/lib/tomcat8/webapps/game-of-life.war```
      8. Click _Save_
3. Verify that your job is added to Jenkins start a build.
      * It should fail like the original application.

###Step 3: Working With the Application Code and the Delivery Pipeline
1. Pull down the code from your forked repository
   1. Install a Git client if you don't have one already.
   2. "Clone" the repository to your computer.
2. Build and test the application locally.
   1. Follow the instructions located in the application's README.md file located [here](Other-READMES/orig_README.markdown).
3. Determine what is breaking and change the applications code
4. Build again and fix the errors until they are completely gone.
   1. This may take a few attempts
   2.  You should be able to solve them by looking at the console output.
   3. Roughly the last 50 lines will have information that will help you.
5. Commit the working code back to your forked repository.
6. See that your build is kicked off again and that is builds successfully.

###Step 4: Deploying Your App via Jenkins
 When the application is building correctly on Jenkins, the application will deploy to an application server.
