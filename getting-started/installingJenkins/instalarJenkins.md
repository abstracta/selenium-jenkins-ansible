# Instalando nuestro primer servidor Jenkins

Para instalar un servidor Jenkins en un servidor Linux con distribucion basada en Ubuntu, el único prerequisito es tener ansible instalado previamente.

De no poseerlo, lo instalamos con el siguiente comando:

``` sh

sudo apt install ansible

```

Una vez instalado ansible, clona este repositorio. De no tener git instalado, lo obtenemos con el siguiente comando:

``` sh

sudo apt install git

```

Y para clonar el repositorio hacemos:

``` sh

git clone $repoURL

```

Luego me muevo al directorio que contiene los playbooks:

``` sh

cd $playbookDirectory

```

Y lo ejecutamos con el siguiente comando:

``` sh

ansible-playbook $playbook.yml --ask-become-pass

```

Nos va a promptear para que le demos la contraseña de sudo de manera de poder ejecutar ciertos comandos dentro del playbook. La ingresamos, tocamos enter y deberíamos ver una salida similar a la siguiente:

![Playbook execution](https://github.com/abstracta/selenium-jenkins-ansible/blob/develop/learning/installingJenkins/img/Capture1.PNG)

Esto nos avisa la version de java que tenemos, que jenkins se instaló correctamente y nos deja fácilmente accesible el initialAdminPassword necesario para la configuración inicial de nuestro Jenkins.

Luego abrimos un navegador, y vamos a http://localhost:8080 si estamos accediendo desde el servidor mismo, de lo contrario vamos a http://$ipServidor:8080. Deberías ver una pantalla similar a la siguiente:

![Initial jenkins page](https://github.com/abstracta/selenium-jenkins-ansible/blob/develop/learning/installingJenkins/img/Capture2.PNG)

Una vez ahí, ingresamos el initialAdminPassword que ansible nos dio como output de su ejecución, y clickeamos en continue.

Te debería aparecer una pantalla similar a la siguiente:

![Jenkins plugins](https://github.com/abstracta/selenium-jenkins-ansible/blob/develop/learning/installingJenkins/img/Capture3.PNG)

A un usuario más avanzado le recomendaría solamente instalar los plugins que crea necesarios, pero de momento vamos a instalar todos.

Luego de la instalación de los plugins, se nos va a promptear para crear un usuario, y luego de eso deberíamos poder ver algo así, lo cual nos indica que estamos prontos para iniciar nuestra travesía con esta herramienta:

![Jenkins initial page](https://github.com/abstracta/selenium-jenkins-ansible/blob/develop/learning/installingJenkins/img/Capture4.PNG)