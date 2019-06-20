# Running JUnit tests inside a maven project

Normally you would go to your maven project directory(the one with the pom.xml file inside it), and execute the following command:

``` sh
mvn clean test
```

if you don't have maven installed, you can install it by executing this command:

``` sh
sudo apt install maven
```

your tests should run, and you should see an output similar to this one:

![Maven test execution](/how-tos/mavenTests/img/Capture8.PNG)

After you've run your tests, if you were using JUnit, you will find your test results' XML inside the /target/surefire-reports/TEST*.xml file. If you have more than one test suite, you will find one xml for each suite.

Now let's do it with Jenkins.

## Analazing XUnit style results

First let's create a new pipeline which clones this maven project into its workspace, and runs the tests. You have to change the repository url and credentials in order for this pipeline to work with your repository.

``` groovy
node {

    stage('Clone maven project from repository.') {
        git credentialsId: 'githubCredentials', url: 'https://github.com/sobraljuanpa/mavenTest.git'
    }

    stage('Run tests using maven.') {
        sh 'mvn clean test'
    }

}
```

As you can see, all this pipeline does is the manual process we have just seen. Now, to add some value to this process, it would be nice to be able to see the result of the test executions without having to get into the job execution.

For that we will use the JUnit plugin, which comes included in the suite of suggested plugins jenkins offers to install during its configuration.

We just need to add a section to our pipeline, leaving it looking like this:

``` groovy
node {

    stage('Clone maven project from repository.') {
        git credentialsId: 'githubCredentials', url: 'https://github.com/sobraljuanpa/mavenTest.git'
    }

    stage('Run tests using maven.') {
        sh 'mvn clean test'
    }

    stage('Analyze test results.') {
        junit 'target/surefire-reports/**/*.xml'
    }

}
```

After a couple of executions you should start seeing a graph similar to this one in your job:

![Test results graph](/how-tos/mavenTests/img/Capture9.PNG)

Now I'm gonna add a couple of passing unit tests, to let you know what changes to expect in the graph in that kind of situation:

![Improved test results graph](/how-tos/mavenTests/img/Capture10.PNG)

Now I'm gonna add a couple of failing tests, and if that were the case in your project you should see something like this:

![Not working test results](/how-tos/mavenTests/img/Capture11.PNG)

Our test report isn't showing anything because it never gets to the analyze test result steps. In order to get that to happen we will leverage the fact that our pipeline supports groovy syntax to do the following:

``` groovy

node {

    try{
  
        stage('Clone maven project from repository') {
            git credentialsId: 'githubCredentials', url: 'https://github.com/sobraljuanpa/mavenTest.git'
        }

        stage('Run tests using maven') {
            sh 'mvn clean test'
        }

    } catch(Exception e) {

        throw e

    } finally {

        stage('Analyze test results.') {
            junit 'target/surefire-reports/**/*.xml'
        }

    }

}

```

Now, what we have in the snippet is basically a common try-catch block, in this case used to avoid Jenkins skipping our result analysis' step because of tests failing. So, we put that inside a try block, and in the catch block we throw the exception anyways in order to mark the build as failing, even though we are preventing it from failing. The finally block includes steps that must always be executed, in this particular case we move our result analysis step there.

Once you have that working, you should see something like this:

![Failing test results reported](/how-tos/mavenTests/img/Capture12.PNG)

As you can see, now the build fails but still executes the last step, and you can also see that our test result trends changed due to the failures.

As you can imagine, this is a great way of visualizing progress in a project developed using tdd practices, and also a way of automatically testing code in order to implement CI for example.
