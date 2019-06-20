# Working with repositories in Jenkins

As you go forward in your Jenkins journey, sooner or later you will find that you want to poll a repository for changes, and trigger some event in case there are any.

We should clarify that there are two ways of configuring jobs to work with repositories: webhooks and polling. Webhooks are a way of activity in your repository triggering actions on your Jenkins server, for example when there are modifications made to the code in a certain branch. Polling is a way of achieving the same result as webhooks but in a different way: the Jenkins server uses a cron expression to configure with which frequency it should check for changes in the remote repository.

## Creating credentials in Jenkins

First, we must create the credentials which Jenkins is going to use to check for changes in our repository. In order to do that, we have to go to Jenkins Home -> Credentials -> System -> Global Credentials -> Add Credentials.

It should look like this:

![Add credentials screen](/how-tos/pollingJenkins/img/Capture5.PNG)

We reccommend that you give a mnemotechnic name to the ID since otherwise Jenkins will give it an automatically generated ID, and also that you add a clear and concise description of the credentials, since you never know when someone else is going to have to work on your environment.

After you click on the OK button, you should be able to go to Jenkins Home -> Credentials and see them:

![Credentials screen](/how-tos/pollingJenkins/img/Capture6.PNG)

## Configuring a job to poll a repository

So, now that we have our credentials, we need to create a git repository using those. Once you have created it, open Jenkins Home again, go to New Item and inside it create a new pipeline.

Once you have done that, scroll to the bottom of the page and you should see something like this:

![Pipeline creation screen](/how-tos/pollingJenkins/img/Capture7.PNG)

Inside that box, paste the following code, which describes the tasks associated with this job:

``` groovy
node {

    properties([pipelineTriggers([pollSCM('* * * * *')])])

    stage('Clone repository contents') {
        git credentialsId: '$credentialsId', url: '$repositoryURL'
    }

    stage('List repository contents for verification') {
        sh 'ls'
    }

}
```

What this snippets tells Jenkins is to check for changes in the repository every minute, and if there have beeen any to trigger the pipeline which in this particular case clones the code, and then lists the current directory's contents, to verify that the repository was actually cloned.
In your execution's console output you should see something like this:

![Example execution](/how-tos/pollingJenkins/img/Capture16.PNG)

## Working with a particular branch

If you use a pipeline as the one before, it will clone master branch's contents by default. However, this may not always be what we want, and so you can tell Jenkins which branch to work with. In order to do that, you just have to change the previous pipeline to the following:

``` groovy
node {

    properties([pipelineTriggers([pollSCM('* * * * *')])])

    stage('Clone repository contents') {
        git branch: '$branchName',credentialsId: '$credentialsId', url: '$repositoryURL'
    }

    stage('List repository contents for verification') {
        sh 'ls'
    }

}
```

And you should be working with your desired branch.

## Webhooks

As we previously mentioned, there is another way of integrating Jenkins with your repositories and that is using webhooks. This is a way of configuring your repository to alert Jenkins whenever a change is pushed to a certain branch for example, and in turn Jenkins triggers a certain job or pipeline.

The issue here is that you are probably going to be working with your Jenkins repository in a private network, and in order to use Jenkins your repository should be able to notify your Jenkins that it should act, so we are skipping that part for now.
