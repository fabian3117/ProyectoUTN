# MiUTN
### Problemática
Durante mi experiencia como estudiante universitario, noté una fragmentación significativa en el material de estudio, así como dificultades para acceder a apuntes para mis materias. Esta no era solo una dificultad personal, sino una situación común entre todos los `estudiantes`. Por ende, decidí abordar este problema mediante la `tecnología`.


>Mi propuesta es `desarrollar una plataforma` que unifique el material de estudio, 
permitiendo a los estudiantes organizarse y gestionarse de manera más eficiente. Además, me propongo enfrentar otra problemática persistente: la deserción universitaria. La idea es proporcionar el material de estudio de manera sincronizada con la cursada, permitiendo a los alumnos avanzar en paralelo e indicar su progreso.

Todo el desarrollo sera codigo libre. Por lo cual estara alojado el codigo tanto del `servidor` como de la `aplicacion android` en sus respectivos repositorios en `git`
La plataforma incluirá notificaciones periódicas que recomendarán la lectura de apuntes específicos, facilitando así el seguimiento y la mejora continua del aprendizaje. ¡Estoy entusiasmado/a por contribuir a resolver estos desafíos! 🎓🚀.
# Pronto estara libre

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
Para esta vista utilize un perfil como estudiante de ingreso de ingenieria. Donde el temario esta en formato `Markdown`.
> En este momento los archivos tienen que añadirse posterior a instalacion de aplicacion.
# Pasos
1. Localizar carpeta \data\data\com.example.miutn\files utilizando `explorador de android studio`
2. Crear 4 archivos (`probandomark.md` - `prueba.md` - `IngresoVidaUni.md` - `IngresoFuncionLineal.md` ) El contenido de los 4 archivos es irrelevante solo importa su existencia.


https://github.com/fabian3117/ProyectoUTN/assets/34560661/ff674dca-16c7-4c56-9b7e-bb17f49e38c9


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
