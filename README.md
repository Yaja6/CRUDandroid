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

![image](https://user-images.githubusercontent.com/56648539/107135383-2e08ab00-68c8-11eb-88da-072661bb513d.png)

Además se hace uso de un **LayoutInflater**: que me permite crear una instancia de diseño en objetos de vista correspondiente (esta vista no necesita estar específicamente en el actividad que se lo implemente) por lo que lo uso para usar el Layout de lista de contactos.xml junto con el **Context**

![image](https://user-images.githubusercontent.com/56648539/107135469-fcdcaa80-68c8-11eb-881b-f3f59e5ed5b3.png)

# Actividades creadas: inicio de sesión, registro, inicio 

## Inicio de sesión: IniciarSesionActivity

Declaro variables de tipo EditText y Button para tomar los inputs y los botones dentro de la interfaz de Inicio de Sesión

![image](https://user-images.githubusercontent.com/56648539/107135511-66f54f80-68c9-11eb-8289-2b7974e8d0ad.png) ![image](https://user-images.githubusercontent.com/56648539/107135525-7bd1e300-68c9-11eb-85bc-4198364ab61e.png)



