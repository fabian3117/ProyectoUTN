# MiUTN 📚 👌👍

### Problemática
Durante mi experiencia como estudiante universitario, noté una fragmentación significativa en el material de estudio, así como dificultades para acceder a apuntes para mis materias. Esta no era solo una dificultad personal, sino una situación común entre todos los `estudiantes`. Por ende, decidí abordar este problema mediante la `tecnología`.


>Mi propuesta es `desarrollar una plataforma` que unifique el material de estudio, 
permitiendo a los estudiantes organizarse y gestionarse de manera más eficiente. Además, me propongo enfrentar otra problemática persistente: la deserción universitaria. La idea es proporcionar el material de estudio de manera sincronizada con la cursada, permitiendo a los alumnos avanzar en paralelo e indicar su progreso.

Todo el desarrollo sera codigo libre.
La plataforma incluirá notificaciones periódicas que recomendarán la lectura de apuntes específicos, facilitando así el seguimiento y la mejora continua del aprendizaje. 🎓🚀.

### Interfaz
![screen-20231029-152005_exported_3768](https://github.com/fabian3117/MiUTN/assets/34560661/44d0e84b-a9f3-4589-9339-7cbcbd68b1a5)

Esta es la pantalla de inicio propuesta. Podemos observar lo siguente:
- Apartado para la busqueda de apuntes.
- En la parte superior la materia del dia.
- Apuntes de todas las materias que el estudiante esta realizando para poder mantenerse al dia.
- Fechas importantes como fechas de examenes finales
- Boton flotante para añadir mas materias.

## Login.
En el siguente video se visualiza la interfaz para el login-registro de usuario.
Se utiliza `lottiefiles` para la visualizacion de animacion en formato `json`. 

https://github.com/fabian3117/MiUTN/assets/34560661/5579b2c3-876d-4e75-8e94-6758a290cb0f

## Visualizacion de apuntes
Para esta vista utilize un perfil como estudiante de ingreso de ingenieria. Donde los temas estan en formato `Markdown`.
> En este momento los archivos tienen que añadirse posterior a instalacion de aplicacion.

En el apartado de ***build*** se describe como hacer funcionar esa funcionalidad.


https://github.com/fabian3117/ProyectoUTN/assets/34560661/ff674dca-16c7-4c56-9b7e-bb17f49e38c9
> Falta implementacion al iniciar seccion de descargar de servidor los archivos automaticamente. asi como verificar sus existencias.
## Accesos directos en longpress de icono aplicacion.
En el archivo 'MiUtn\app\src\main\res\xml\shortcuts.xml` . Se definen los accesos directos que se visualizaran al realizar longPress en el icono de la aplicacion. 
Descripcion para la generacion de uno personalizado.
```
<shortcut
        android:enabled="{Si queres que este disponible o no}"
        android:icon="{Icono}"
        android:shortcutDisabledMessage="{Mensaje por deshabilitacion}"
        android:shortcutId="{Identificador}"
        android:shortcutShortLabel="{Texto que se mostrara}">
        <intent
            android:action="{Accion de destino Tipo}"
            android:targetPackage="{Paquete que pertenece}"
            android:targetClass="{Que clase debe tomar acciones/ejecutarse}" >
            <extra
                android:name="documentID"
                android:value="probandomark.md"/>
        </intent>
    </shortcut>
```
> En el apartado de ´extra´ debe indicarse los parametros que se desean pasar al activity para de esta forma el pueda saber que acciones tomar.

![Screenshot_20231124-175334](https://github.com/fabian3117/ProyectoUTN/assets/34560661/cb41f9bb-a2c0-4af5-aa59-fc156172201b)

## Build
Posterior a clonado/descarga del repositorio. se debe proceder a introducir manualmente archivos de prueba para la funcionalidad de ***Vista de markdown files***
# Pasos
1. Localizar carpeta \data\data\com.example.miutn\files utilizando `explorador de android studio`
2. Crear 4 archivos (`probandomark.md` - `prueba.md` - `IngresoVidaUni.md` - `IngresoFuncionLineal.md` ) El contenido de los 4 archivos es irrelevante solo importa su existencia.
# Back-End.
Se tendra que poner a ejecucion un servidor local springboot y una mongodb , Sera subida y descrita en su respectivo repositorio:
Para la conexion exitosa del proyecto se debe realizar la siguente configuracion en codigo fuente:
> Clase `RetrofitClient` - Package : network.api.RetrofitClient. 
```
private static final String urlBase = "http://{Direccion IP Sevidor springboot}:{Puerto}";
```
***Importante tener en cuenta que esta utilizando protocolo http y desde `manifest` se permitio eso a fin de poder continuar con el desarrollo sin requerir certificados ssl***

### Tecnologias
- Springboot
- Springboot mail
- Maven
- Azure
- Git
- Android studio
- Mongo DB
- Retrofit
- Lottie
- Material design 3
- Markdown
