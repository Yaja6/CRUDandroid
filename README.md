# CRUD CON ANDROID STUDIO Y FIREBASE
## Desarrollo de aplicación móvil: Agenda de Contactos
- Link Video explicativo: https://youtu.be/weTBN_ZE9Rg

Descripción de las principales funciones usadas.


## Conexión con Firebase

1. Se agrega el documento **google-services.json** dentro de la carpeta **appPrueba/app** del proyecto android


![image](https://user-images.githubusercontent.com/56648539/107134916-4080e580-68c4-11eb-86ff-b2b1b551f767.png)

2. Se añade el clas path de google services dentro de las dependencias del archivo **build.gradle**

![image](https://user-images.githubusercontent.com/56648539/107134944-8342bd80-68c4-11eb-9746-401a34221f8f.png)

3. Para añadir la Autenticación de FIrebase y Real Time Database añade las dependencias de las mismas dentro del archivo **appPrueba/app/build.gradle**

![image](https://user-images.githubusercontent.com/56648539/107134958-ae2d1180-68c4-11eb-85db-079e7a7666ca.png)


# Diseño de la Aplicación 
Layouts creados: Registro, Inicio de Sesión, Inicio, insertar, lista de contactos

**1. Registro**

![image](https://user-images.githubusercontent.com/56648539/107135058-a91c9200-68c5-11eb-8c86-4607bc9ec1bb.png)

**2. Inicio de sesión**

![image](https://user-images.githubusercontent.com/56648539/107135074-c94c5100-68c5-11eb-8ed5-cec76a7f7679.png)

**3. Inicio**

![image](https://user-images.githubusercontent.com/56648539/107135116-06b0de80-68c6-11eb-9d23-a79b6b4fdef1.png)

**4. insertar (Para usarlo con un Alert Dialog y crear nuevo contacto)

![image](https://user-images.githubusercontent.com/56648539/107135141-2516da00-68c6-11eb-97cf-0924f3a376d6.png)

**5. Lista de contactos (Layout que forma el modelo en que se mostrarán los contactos dentro de List View)**

![image](https://user-images.githubusercontent.com/56648539/107135160-4a0b4d00-68c6-11eb-99de-3866187b3856.png)

# Codificación de la Aplicación

Se crea una clase o modelo Contacto que tendrá todos los atributos necesarios: 
```
- Id
- Nombre
- Teléfono
- Id del usuario que registra el contacto
- Fecha de registro
- timestamp
```
**contacto.java**

![image](https://user-images.githubusercontent.com/56648539/107135298-89866900-68c7-11eb-9f18-a38974aeb199.png)

## Uso de Adaptador para que actúe como enlace entre los datos y la vista

En este Adaptador se hace uso de un **Context**: que me permite acceder a recursos específicos de la aplcación y a sus clases. En este caso para acceder la lista de contactos.xml

**ListaContactosAdaptador.java**

![image](https://user-images.githubusercontent.com/56648539/107135383-2e08ab00-68c8-11eb-88da-072661bb513d.png)

Además se hace uso de un **LayoutInflater**: que me permite crear una instancia de diseño en objetos de vista correspondiente (esta vista no necesita estar específicamente en el actividad que se lo implemente) por lo que lo uso para usar el Layout de lista de contactos.xml junto con el **Context**

![image](https://user-images.githubusercontent.com/56648539/107135469-fcdcaa80-68c8-11eb-881b-f3f59e5ed5b3.png)

### Dentro del método **GetView** 
Aquí se crea una variable **rowView** de tipo Vista (view) que em permitirá obtener la Vista **lista contactos** que es donde irán almacenándose cada uno  de los contactos tomado de la base de datos

![image](https://user-images.githubusercontent.com/56648539/107135631-598c9500-68ca-11eb-8a73-8d8743da175b.png)

A continuación se enlazan las vistas con los elementos de la vista **lista_contactos**

![image](https://user-images.githubusercontent.com/56648539/107135638-66a98400-68ca-11eb-8c02-c8f268a602d3.png)

Luego a la variable de tipo Contacto le paso los datos del contacto en la posición dada como parámetro y a las variables enlazadas anteriormente le paso cada uno de los datos correspondientes: **nombre, teléfono, fecha**

![image](https://user-images.githubusercontent.com/56648539/107135707-ea637080-68ca-11eb-980b-246367c42bb5.png)

# Actividades creadas: inicio de sesión, registro, inicio 

## Registro: RegistroActivity.java

Declaro variables de tipo EditText y Button para tomar los inputs y los botones dentro de la interfaz de Registro

![image](https://user-images.githubusercontent.com/56648539/107136013-a6be3600-68cd-11eb-873a-aebad02f6ce3.png) => ![image](https://user-images.githubusercontent.com/56648539/107136021-b63d7f00-68cd-11eb-8966-331d7956747c.png)

Declaro variables de tipo String que tomarán los valores ingresados por el usuario y otra variable de la dependencia de FirebaseAuth
![image](https://user-images.githubusercontent.com/56648539/107136030-c6555e80-68cd-11eb-898d-311d8bcfad08.png)

Inicializo la instancia de Firebase auth y de RealtimeDatabase:
![image](https://user-images.githubusercontent.com/56648539/107136032-d705d480-68cd-11eb-93b1-bdbef336ffab.png)

Asigno a las variables, los elementos de la interfaz:
![image](https://user-images.githubusercontent.com/56648539/107136039-ec7afe80-68cd-11eb-948d-e1664ddcaeca.png)

_Cuando el usuario dé click en el botón de Registrar asigno a las variables declaradas anteriormente los valores ingresados por el usuario  y valido si están vacíos o no. En el caso de haber llenado los campos, llamará a la función de regitrarUsuario()

![image](https://user-images.githubusercontent.com/56648539/107136051-0e748100-68ce-11eb-9c28-1b4add077c33.png)

### Función **registrarUsuario()**

Utilizo la variable que instancia Firebaseauth y uso el método **CreateUserWithEmailAndPassword** dondele paso los valores ingresados por el usuario para crear un usuario. Además de un envento CompleteListener para verificar que la tarea se haya realizado correctamente, en este caso, se crea un objeto con los datos ingresados por el usuario para poder enviarlos a registrar al RealTimeDatabase 
![image](https://user-images.githubusercontent.com/56648539/107136198-15e85a00-68cf-11eb-9d38-e12525ad4bdb.png)

Para registrar en la base de datos tomo la variable de la instancia de real time datase y creo una rama llamada "Usuarios" y dentro de esta, otra rama con el id del usuario registrado y dentro de este los datos del objeto
![image](https://user-images.githubusercontent.com/56648539/107136238-74add380-68cf-11eb-9e48-05d736bbdb4d.png)

## Inicio de sesión: IniciarSesionActivity.java

Declaro variables de tipo EditText y Button para tomar los inputs y los botones dentro de la interfaz de Inicio de Sesión

![image](https://user-images.githubusercontent.com/56648539/107135511-66f54f80-68c9-11eb-8289-2b7974e8d0ad.png) => ![image](https://user-images.githubusercontent.com/56648539/107135525-7bd1e300-68c9-11eb-85bc-4198364ab61e.png)

Declaro variables de tipo String que tomarán los valores ingresados por el usuario y otra variable de la dependencia de FirebaseAuth

![image](https://user-images.githubusercontent.com/56648539/107135774-87bea480-68cb-11eb-862a-4d4858d11e4f.png)

Inicializo la instancia de Firebase auth:
![image](https://user-images.githubusercontent.com/56648539/107135883-74600900-68cc-11eb-8a27-1c78d5196b11.png)

Asigno a las variables, los elementos de la interfaz:
![image](https://user-images.githubusercontent.com/56648539/107135895-9063aa80-68cc-11eb-9373-5895f069e000.png)

_Cuando el usuario dé click en el botón de Ingresar asigno a las variables declaradas anteriormente los valores ingresados por el usuario  y valido si están vacíos o no. En el caso de haber llenado los campos, llamará a la función de iniciar sesión().

![image](https://user-images.githubusercontent.com/56648539/107135931-d9b3fa00-68cc-11eb-904a-4a46569ff364.png)

## Función **Iniciar Sesión**

Utilizo la variable que instancia Firebaseauth y uso el método **SignInWithEmailAndPassword** dondele paso los valores ingresados por el usuario. Además de un envento CompleteListener para verificar que la tarea de iniciar sesión se ha realizado correctamente y pueda redirigirse a la pantalla de **Inicio**
![image](https://user-images.githubusercontent.com/56648539/107135976-462ef900-68cd-11eb-8532-773e8dcf52ea.png)

## Inicio: CRUD de los contactos - **InicioActivity**

1. Declaro variables 

**Arreglo de tipo Contacto** para almacenar los contactos 
**Una variable del tipo ArrayAdapter** para enlistar los datos tomados de la base de datos y enviarlos a la vista de lista_contactos.xml
**Una variable de tipo ListaContactosAdaptador** para usar los datos enlazados dentro del adaptador
**Variable de tipo LinearLayout** para usar un Layout creado para editar un usuario

![image](https://user-images.githubusercontent.com/56648539/107136414-f18d7d00-68d0-11eb-877c-b1c9865c5fb4.png)

Declaro variables de tipo EditText, Button y ListView para tomar los inputs y los botones y Listas dentro de la interfaz de Inicio

![image](https://user-images.githubusercontent.com/56648539/107136438-1da8fe00-68d1-11eb-87a9-557b07606021.png)

Declaro variables de tipo String que tomarán los valores ingresados por el usuario 

![image](https://user-images.githubusercontent.com/56648539/107136445-3ca79000-68d1-11eb-955d-819b32be1050.png)

Una variable de tipo Contacto para tomar los datos del ontacto Seleccionado. Y otra variable para tomar el id del usuario Actual

![image](https://user-images.githubusercontent.com/56648539/107136463-5ea11280-68d1-11eb-91bd-728bf2f1a7b8.png)

Inicializo la instancia de Firebase auth y de RealtimeDatabase:
![image](https://user-images.githubusercontent.com/56648539/107136032-d705d480-68cd-11eb-93b1-bdbef336ffab.png)

Asigno a las variables, los elementos de la interfaz:
![image](https://user-images.githubusercontent.com/56648539/107136511-ea1aa380-68d1-11eb-96c5-3b9a8afeda95.png)

## **Función InicializarFirebase()**
Inicaliza la app de firebase y las variblaes que Aith y Realtime Database 
![image](https://user-images.githubusercontent.com/56648539/107136520-0ae2f900-68d2-11eb-86a3-793e41c775b3.png)

## **Función para Crear un Contacto: nuevTelf()**
Utilizo un alterdialog para poder mostrar una vista al Layout _insertar.xml_ que tiene los campos para ingresar un nuevo contacto 

![image](https://user-images.githubusercontent.com/56648539/107136562-762ccb00-68d2-11eb-8820-f175bd5dbc84.png) => 
![image](https://user-images.githubusercontent.com/56648539/107135141-2516da00-68c6-11eb-97cf-0924f3a376d6.png)

Asigno a las variables, los elementos de la interfaz. Asigno el tipo final a las variables que se asignan a los inputs de la interfaz _insertar.xml_ ya que no se podrán sobreescribir:
![image](https://user-images.githubusercontent.com/56648539/107136621-108d0e80-68d3-11eb-824b-d425023c5718.png)

**Una vez que el usuario dé clic en el botón de _Agregar contacto_ se le mostrará el alertdialog para que pueda ingresar los datos y se crea una variable de tipo **Contacto** para que obtenga los datos ingresados**

![image](https://user-images.githubusercontent.com/56648539/107136672-6cf02e00-68d3-11eb-9795-e908d339c4c0.png)

Una vez que la variable tenga los datos, se creará un registro dentro de la base de datos. Se creará una rama "Contactos" y dentro de este otra rama con los id de los contactos con cada uno de sus datos.
![image](https://user-images.githubusercontent.com/56648539/107136710-be002200-68d3-11eb-8923-321de1870e69.png)

## **Función ver contactos: verContactos()**

Tomo los registros dentro de la rama "Contatos" y se los ordena descendentemente. Para eso se usa un For que recorre los datos registrados. Además se comprueba que el id del usuario que registró el contacto sea el usuario autenticado actulmente para mostrar su lista de contactos

![image](https://user-images.githubusercontent.com/56648539/107136863-ef2d2200-68d4-11eb-963e-39770786255e.png)

## **Función para editar contactos: guardarTelf()**
Primero se abirá un Layout con los campos llenos con los datos del contato que se haya seleccionado.
Creo una variable de tipo Contacto que obtendrá los nuevos datos que ingrese el usuario. En este caso solo se modificará el nombre del contacto y el número de teléfono, los demás datos continuarán siendo los mismos. 

![image](https://user-images.githubusercontent.com/56648539/107136935-a45fda00-68d5-11eb-99ac-49ee27299340.png)

Posteriormente guarda el contacto en la base de datos

![image](https://user-images.githubusercontent.com/56648539/107136948-b0e43280-68d5-11eb-9df0-2c4178b3fd56.png)

## **Función para eliminar un contacto: eliminarContacto()**

Igualmente se crea una variable de tipo Contacto al cual se le asigna la id del Contacto seleccionado y con el método **removeValue()** se lo borra de la base de datos "Contactos"

![image](https://user-images.githubusercontent.com/56648539/107137026-7b8c1480-68d6-11eb-92a0-39b7085e1801.png)
