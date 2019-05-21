# Sending emails using Jenkins

All of the stuff that gets automated with Jenkins is amazing, but if it has no visibility it is worth a lot less. That's why now we are going to learn how to notify interested parts via email.

## Configuring the SMTP server

In order to send emails, we need to use a SMTP(Simple Mail Transfer Protocol) server, which offers that specialized service.

First, we need to figure out wether we are going to use an email service (eg: GMail) provider's SMTP server or if we are going to configure our own. After this is settled, we have to configure Jenkins in order to use this SMTP server.

Go to Manage Jenkins -> Configure System and scroll down to the Extended E-Mail notification section. We use this plugin, but you can use another one if you feel like it better suits your needs.

One thing that the default email notification plugin has(not extended email notification plugin, just email notification) is the posibility of sending a test email using the configuration you input into it, which comes in really handy to test wether that part works before building a pipeline which sends emails and trying to figure out where the error is.

![How we configured our account](https://github.com/abstracta/selenium-jenkins-ansible/blob/develop/learning/sendingMails/img/Capture13.PNG)

This is how our configuration looks, as you can see we did what we advise you do, which is testing that the config is correct before trying to use it inside a pipeline. If you are using gmail's smtp service, you will have to enable insecure applications(your local jenkins qualifies as that) to send email using your credentials. This can be checked by just going into your inbox and following the instructions they will send you in an email if jenkins tells you that it failed to send the test email.

For this demo we are still going to use extended email notification since it provides some interesting functionality by default.
We just need to configure that plugin exactly the same as we configured the email notification one.

## Setting up a pipeline that notifies via email

Now we are going to create a pipeline that clones the maven repository we used in the run tests with maven section, and cats a file, sending the execution log via email as an attachment and providing a link to the execution.

``` groovy

node {
    try{  
        stage('Clone maven project from repository') {
            git credentialsId: 'githubCredentials', url: 'https://github.com/sobraljuanpa/mavenTest.git'
        }
        stage('Check contents of pom.xml') {
            sh 'cat pom.xml'
        }
    } catch(Exception e) {
        throw e
    } finally {
        stage('Notify results') {
            emailext attachLog: true, body: '''$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS:
            Check console output at $BUILD_URL to view the results.''', 
            subject: '$PROJECT_NAME - Build # $BUILD_NUMBER - $BUILD_STATUS!', 
            to: 'juan.sobral@abstracta.com.uy'
        }
    }
}

```

After two executions, I got this email in my inbox:

![Recieved email](https://github.com/abstracta/selenium-jenkins-ansible/blob/develop/learning/sendingMails/img/Capture14.PNG)

And this is how the log looks, side by side with the execution log:

![Recieved log comparison with execution](https://github.com/abstracta/selenium-jenkins-ansible/blob/develop/learning/sendingMails/img/Capture15.PNG)
