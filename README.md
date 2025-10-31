# Code Crafters

## Objetivos

Code Crafters es una plataforma web que conecta a la comunidad tecnológica mediante eventos online y presenciales.
Promueve la colaboración, el aprendizaje y la innovación en un entorno moderno y seguro, desarrollado con Spring Boot, React.js y PostgreSQL.

## Competencias Técnicas

Este proyecto desarrolla las siguientes competencias técnicas:

- **Backend Development:** Implementación de la lógica del servidor y los endpoints RESTful.
- **Database Creation:** Creación y estructuración de la base de datos PostgreSQL.
- **Data Access Components:** Desarrollo de componentes que permiten la comunicación entre la API y la base de datos.
- **Tests:** Validación del comportamiento del sistema utilizando herramientas como JUnit y Mockito.

  
## Tecnologías y Herramientas

Este proyecto fue desarrollado utilizando un conjunto moderno de tecnologías y herramientas que garantizan rendimiento, escalabilidad y buenas prácticas en el desarrollo backend:

- **Lenguaje de Programación:** Java 21
- **Framework Backend:** Spring Boot 3.3.5
- **Base de Datos:** PostgreSQL 42.7.3
- **Gestión de Proyectos:** Jira
- **Control de Versiones:** Git - GitHub
- **Pruebas de API:** Postman 11.41
- **Testing:**
  - spring-boot-starter-test 
  - Mockito
  - JUnit

## Funcionalidades

WIP

**Relaciones:**
 WIP

## Cómo iniciar el proyecto

### Requisitos previos
- Java 21 instalado
- PostgreSQL instalado y en ejecución
- Maven 

### Pasos para iniciar el proyecto

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/CodeCraftwerkers/CCBackEnd.git
   ```

2. **Configurar la base de datos**
   - Crear una base de datos en PostgreSQL:
   ```sql
   CREATE DATABASE codecrafters;
   ```

3. **Verificar la instalación**
   - La API estará disponible en: `http://localhost:8080`
   - Puedes probar los endpoints con Postman o cualquier cliente HTTP
  
## Estructura del Proyecto

A continuación se muestra la estructura del proyecto Periódico de Ayer, organizada por capas siguiendo la arquitectura estándar de una aplicación Spring Boot

```
CCBACKEND
├── .mvn/
│   └── wrapper/
│       └── maven-wrapper.properties
├── src/
│   └── main/
│       ├── java/com/codecrafters/ccbackend/
│       │   ├── controller/
│       │   │   ├── EventController.java
│       │   │   └── UserController.java
│       │   ├── dto/
│       │   │   ├── request/
│       │   │   │   ├── EventRequestDTO.java
│       │   │   │   └── UserRequestDTO.java
│       │   │   └── response/
│       │   │       ├── EventResponseDTO.java
│       │   │       └── UserResponseDTO.java
│       │   ├── entity/
│       │   │   ├── Event.java
│       │   │   └── User.java
│       │   ├── exception/
│       │   │   └── GlobalExceptionHandler.java
│       │   ├── mapper/
│       │   │   ├── EventMapper.java
│       │   │   └── UserMapper.java
│       │   ├── repository/
│       │   │   ├── ArticleRepository.java
│       │   │   └── UserRepository.java
│       │   ├── service/
│       │   │   ├── event/
│       │   │   │   ├── EventService.java
│       │   │   │   └── EventServiceImpl.java
│       │   │   └── user/
│       │   │       ├── UserService.java
│       │   │       └── UserServiceImpl.java
│       │   └── CcbackendApplication.java
│       └── resources/
│           └── application.properties
├── test/java/com/femcoders/periodico_ayer/
│   └── CcbackendApplicationTests.java
├── target/
├── .env
└── .gitattributes
```
## Contactos

¿Tienes dudas o quieres saber más sobre el proyecto?

Puedes contactar a las desarrolladoras a través de sus perfiles profesionales:

<table style="width:100%; border-collapse: collapse; border: none; text-align:center;">
  <tr>
     <td style="border: none; padding: 10px;">
      <b>Suraya Mattar - PO/Developer</b><br>
      <a href="https://www.linkedin.com/in/suraya-mattar/" target="_blank">LinkedIn</a> |
      <a href="https://github.com/surayac" target="_blank">GitHub</a>
    </td>
     <td style="border: none; padding: 10px;">
      <b>Daniella Pacheco - SM/Developer</b><br>
      <a href="https://www.linkedin.com/in/daniellapacheco/" target="_blank">LinkedIn</a> |
      <a href="https://github.com/DaniPacheco8" target="_blank">GitHub</a>
    </td>
    <td style="border: none; padding: 10px;">
      <b>Àngela Bello - Developer</b><br>
      <a href="https://www.linkedin.com/in/angela-bello-developer/" target="_blank">LinkedIn</a> |
      <a href="https://github.com/AngelaBello-creator" target="_blank">GitHub</a>
    </td>
    <td style="border: none; padding: 10px;">
      <b>Erika Montoya - Developer</b><br>
      <a href="https://www.linkedin.com/in/erikamontoya/" target="_blank">LinkedIn</a> |
      <a href="https://github.com/DevErika" target="_blank">GitHub</a>
    </td>
    <td style="border: none; padding: 10px;">
      <b>Estefanía Secanell - Developer</b><br>
      <a href="https://www.linkedin.com/in/stef-secanell/" target="_blank">LinkedIn</a> |
      <a href="https://github.com/Abaraira" target="_blank">GitHub</a>
    </td>
     <td style="border: none; padding: 10px;">
      <b>Luisa Moreno - Developer</b><br>
      <a href="https://www.linkedin.com" target="_blank">LinkedIn</a> |
      <a href="https://github.com/LuMorenoM" target="_blank">GitHub</a>
    </td>
  </tr>
</table>
