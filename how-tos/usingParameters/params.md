# Using parameters inside our Jenkins pipelines

We will often encounter situations while working with Jenkins pipelines in which rather than making a different pipeline for say, testing code from different feature branches, it would be much more comfortable to just have one pipeline which we can parameterize in order to get the results we desire.

## Simple example of using Jenkins parameters inside a pipeline

In order to use parameters inside our pipeline, we have to previously tell the pipeline that it will be parameterized.

We do this by adding these parameters to the same properties section we might use to configure a cron trigger for a job for example, in the following way:

``` groovy

node {

    properties([
        parameters([
            string(defaultValue: 'Hello Parameters!', description: 'Pipeline parameter', name: 'testParameter', trim: false)
        ])
    ])

    stage('Echo our parameter') {
        echo "${params.testParameter}"
    }

}

```

**IMPORTANT, you must use parameters inside double quotation marks as showcased above, otherwise they won't work!!**

For the first execution, you should not be prompted for any parameterization, rather you should see something like this once you save your pipeline's definition:

![Pipeline showcase](/how-tos/usingParameters/img/capture0.PNG)

Then just click build now. The pipeline should run as intended, only using the default value for the parameter we set, as we can see in the execution's log:

![Pipeline first run](/how-tos/usingParameters/img/capture1.PNG)

After that first execution, if you go and try to build the pipeline it will say Build with Parameters instead of Build Now, and if you click that button you should see something like this:

![Pipeline parameters](/how-tos/usingParameters/img/capture2.PNG)

Where you can see how the variables that we set in our parameter, such as Name, Description and DefaultValue have taken their place.

## Using more than one parameter

Once you have configured your pipeline to use one parameter, it is easy to scale that to any number of parameters you might want to use.

You just have to append them to the list we used on the first example, like so:

``` groovy

node {

    properties([
        parameters([
            string(defaultValue: 'Hello Parameters!', description: 'Pipeline parameter', name: 'testParameter', trim: false),
            string(defaultValue: 'Hello Again Parameters!', description: 'Pipeline parameter number two', name: 'secondTestParameter', trim: false)
        ])
    ])

    stage('Echo our first parameter') {
        echo "${params.testParameter}"
    }

    stage('Echo our first parameter') {
        echo "${params.secondTestParameter}"
    }

}

```

And the first execution of this new pipeline should look something like this:

![Pipeline with multiple parameters](/how-tos/usingParameters/img/capture3.PNG)

So, there you have it, now you can work with parameters inside your Jenkins pipelines!
