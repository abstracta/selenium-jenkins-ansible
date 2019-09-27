# Using Zalenium to run Selenium tests

## Installing Docker

In order to use Zalenium, we are going to need to have Docker installed in our system first. We are going to give you easy to follow instructions here, but you can always follow the [official documentation](https://docs.docker.com/install/linux/docker-ce/ubuntu/). As usual, these instructions are for Ubuntu based distributions.

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

![docker run hello-world output](img/Capture.PNG)

Once you have installed Docker, you need to give Jenkins permission to use it.

First, we need to create a Docker user group:

``` sh
$ sudo groupadd docker
```

Then we need to add jenkins user to the docker group:

``` sh
$ sudo usermod -aG docker jenkins
```

Then restart your Jenkins server and create a pipeline with the following code:

``` groovy
node {

    stage('Verify docker permissions') {
        sh 'docker run hello-world'
    }

}
```

Run it, and if everything went well you should see output similar to this:

![Pipeline execution output](img/Capture1.PNG)

Now we are ready almost ready to start using zalenium [Zalenium](https://github.com/zalando/zalenium).

The only thing missing is installing docker-compose which we will use to manage our Zalenium containers. As with the Docker installation section, you can either follow the [official documentation](https://docs.docker.com/compose/install/) or this guide which is based on it.

First run this command in order to download Docker Compose

``` sh
$ sudo curl -L "https://github.com/docker/compose/releases/download/1.24.1/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
```

Then apply execution permissions to the binary

``` sh
$ sudo chmod +x /usr/local/bin/docker-compose
```

In order to test that docker-compose was properly installed, run this command:

```sh
$ docker-compose -version
```

If everything was properly installed, you can move on!

## Zalenium

There are two ways to work with Zalenium: having a running grid all the time or dynamically setting it up inside a pipeline, running your tests and then tearing it down. 

We are going to show you how to do the second.

First, let's take a look at the file **docker-compose.yml** located in templates/projects/zalenium. 

``` yaml
version: '2.1'

services:
#--------------#
  zalenium:
    image: "dosel/zalenium"
    container_name: zalenium
    hostname: zalenium
    tty: true
    volumes:
      - /tmp/videos:/home/seluser/videos
      - /var/run/docker.sock:/var/run/docker.sock
    ports:
      - 4444:4444
    command: >
      start --desiredContainers 2
      --maxDockerSeleniumContainers 8
      --screenWidth 1024 --screenHeight 768
      --timeZone "America/Montevideo"
      --videoRecordingEnabled true
      --sauceLabsEnabled false
      --browserStackEnabled false
      --testingBotEnabled false
      --startTunnel false

networks:
  prod-network:
    driver: bridge
  backup-network:
    driver: bridge
```

It describes the environment you are going to deploy: a Zalenium node with a max of eight running dockerSelenium slave containers(two are initially deployed alongside the Zalenium node).

In order to use Zalenium, we will need to have a docker-selenium image locally available. 

You can get it by running the following command:

```sh
$ docker pull elgalu/selenium
```

After that, you should cd into the /templates/projects/gettingStarted folder and run this command in order to set the grid up:

```sh
$ docker-compose up
```

and the output should be something like this:

![Pipeline execution output](img/Capture2.PNG)

Now you have checked that your zalenium setup is working.

## Using Zalenium inside a pipeline

In order to use this grid, we are going to have to modify the tests' setup we were running before. 

Here you can see the before and the after:

``` java
// Here is how it was before
@Before
public void setUp() {
    FirefoxBinary firefoxBinary = new FirefoxBinary();
    firefoxBinary.addCommandLineOptions("--headless");
    System.setProperty("webdriver.gecko.driver", "/home/juanpa/drivers/geckodriver");
    FirefoxOptions firefoxOptions = new FirefoxOptions();
    firefoxOptions.setBinary(firefoxBinary);
    driver = new FirefoxDriver(firefoxOptions);
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    driver.get("http://opencart.abstracta.us");
    homePage = new HomePage(driver);
}

// Here is how it is now
@Before
public void setUp() throws Exception{
    DesiredCapabilities capabilities;
    capabilities = DesiredCapabilities.firefox();

    driver = new RemoteWebDriver(new URL("http://172.18.0.2:4445/wd/hub"), capabilities);
    driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    driver.get("http://opencart.abstracta.us");
    homePage = new HomePage(driver);
}
```

We are basically telling our tests that their driver will no longer be found locally, but it will be provided by the grid we will deploy.

We will be providing you with the same test project we used in the [Getting Started](../../getting-started/README.md) section, except now the driver selenium will be using to run its tests will be provided by the Zalenium node we will be deploying inside our pipeline.

The pipeline itself will look like this:

``` groovy
node {

    try {
        stage('Copy repository maven project to workspace') {
            sh 'cp $routeToRepository/templates/projects/zalenium/* ./'
        }

        stage('Create containers to run tests') {
            sh 'docker-compose up -d'
        }

        stage('Run maven tests') {
            sh 'mvn clean test'
        }

    } catch(Exception e) {

        throw e

    } finally {

        stage('Stop running containers') {
            sh 'containers=$(docker ps -aq) && docker stop $containers'
        }

        stage('Remove remaining containers') {
            sh 'containers=$(docker ps -aq) && docker rm $containers'
        }

    }

}
```

As you can see, we put our pipeline inside a try-catch block.

That is done in order to prevent the pipeline from stopping while leaving the containers running, which would cause every build after the one that failed to fail since we would be trying to deploy already deployed containers.

## Zalenium/Docker common issues

Problems with, 

* *docker-compose up*

A common issue that we can find is the following one, 

![Pipeline execution output](img/docker_zalenium_error.PNG)

Simply restaring Docker is not going to fix the problem, so what we should do is follow the exact steps below:   

- Close "Docker Desktop" 

- Run the commands below: 
```xml
net stop com.docker.service 
net start com.docker.service 
```

- Launch "Docker Desktop" again
 