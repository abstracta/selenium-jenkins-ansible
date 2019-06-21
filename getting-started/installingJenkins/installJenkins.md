# Installing your first Jenkins server

So, you read the post and wanted to do a little POC to see for yourself how this stack worked together, or maybe just want to try the playbooks. Here you are gonna learn how!

There are two playbooks in this project, the first one takes care of the Jenkins installation and the second one sets it up to run headless Selenium tests.

The only requirement is that the server's distribution is Ubuntu based and that you have ansible previously installed. In case you haven't, you just need to open your terminal and execute the following command:

``` sh
sudo apt install ansible
```

After you have ansible, you need to clone the repository. If you haven't got git installed, now is the time to do it executing this command:

``` sh
sudo apt install git
```

Now we are all set!

Execute the following command in order to clone this repository:

``` sh
git clone https://github.com/abstracta/selenium-jenkins-ansible.git
```

Then, move into the directory which contains the playbooks:

``` sh
cd provisioning/playbooks
```

And run the playbook with the following command:

``` sh
ansible-playbook installJenkins.yml --ask-become-pass
```

You should see an output similar to the following one:

![Playbook execution](/getting-started/installingJenkins/img/Capture1.PNG)

These messages tell us that java and jenkins were installed correctly, that jenkins is running and also tell us the initial admin password, which we'll need in order to configure our Jenkins instance.

![Initial jenkins page](/getting-started/installingJenkins/img/Capture2.PNG)

Now just copy the password that the playbook conveniently shows you, paste it in the field and click on Continue.

You will be presented with this screen:

![Jenkins plugins](/getting-started/installingJenkins/img/Capture3.PNG)

Since we assume little to no Jenkins experience here, we reccomend installing all suggested plugins, but if you would rather not install bloat that you won't use, you can just select which plugins to install.

After this you will be prompted to create a user, and after you are done with that you will see this screen, which indicates that you are now ready to start doing amazing things with Jenkins!

![Jenkins initial page](/getting-started/installingJenkins/img/Capture4.PNG)

## Installing on a non Ubuntu based operating system

In order to install Jenkins on a non Ubuntu based OS you have to follow these steps:

1. Install Java8
2. Add Jenkins repository key
3. Update repository list
4. Install Jenkins
5. Start Jenkins service
