##Mac OS X Environment Set Up
###Using Homebrew

_This is the recommended means of installation. Homebrew is a repository
maintained regularly that works very similarly to package managers like
apt-get and yum. It is definitely worth having on your system moving
forward._

####Step 1: Install Homebrew (If not already installed)
Install command line tools
```bash
$ xcode-select -install
```

Install Homebrew
```bash
$ ruby -e “$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/master/install)”
```

Ensure Homebrew is working properly.
```bash
$ brew doctor
```

Install Cask via Homebrew
```bash
$ brew install caskroom/cask/brew-cask
```

####Step 2: Install the Dependencies for Game of Life via Brew
```bash
$ brew install maven
$ brew install jetty
$ brew cask install java
```

####Complete
You have now finished the set up of your environment so you can return to
the previous page by [clicking here](../README.md)
