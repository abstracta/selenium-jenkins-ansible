# Using Zalenium to run Selenium tests

## Installing Docker

In order to use Zalenium, we are going to have Docker installed in our system first. We are going to give you easy to follow instructions here, but you can always follow the [Official documentation](https://docs.docker.com/install/linux/docker-ce/ubuntu/). As usual, these instructions are for Ubuntu based distributions.

First, we need to install some packages to allow ```apt``` to use repositories over HTTPS:

``` sh
$ sudo apt-get install apt-transport-https ca-certificates curl gnupg-agent software-properties-common
```

Then we add Docker's GPG key:

``` sh
$ curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -
```

Now we have to set up the stable repository:

``` sh
$ sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable"
```

Now we have to install Docker Engine's Community version

``` sh
$ sudo apt-get update && sudo apt-get install docker-ce docker-ce-cli containerd.io
```

Now to check that the installation was successful, run this command:

``` sh
$ sudo docker run hello-world
```

If the installation was succesful, you should see output similar to this:

![docker run hello-world output](img/Capture.PNG).