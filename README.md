 
![Banner del Proyecto](src/main/resources/static/img/banner.png)

Proyecto desarrollado en **Java 23** + **Spring Boot 3** para detectar mutantes mediante el análisis de secuencias de ADN.  
Incluye arquitectura limpia, tests unitarios, Swagger, base de datos H2 y cobertura de código.

---

## Tecnologías principales

- Java 23  
- Spring Boot 3  
- Spring Web  
- Spring Data JPA  
- H2 Database  
- Lombok  
- Swagger / OpenAPI  
- JUnit 5 + Mockito  
- Jacoco  
- Gradle Kotlin DSL  

---
## Estructura Completa PROYECTO
```txt
EntregaMutantes_51164/
├── .gradle/
├── .idea/
├── build/
├── gradle/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── org/
│   │   │       └── example/
│   │   │           ├── Application.java
│   │   │           ├── config/
│   │   │           │   └── SwaggerConfig.java
│   │   │           ├── controller/
│   │   │           │   ├── MutantController.java
│   │   │           │   └── StatsController.java
│   │   │           ├── dto/
│   │   │           │   ├── DnaRequest.java
│   │   │           │   └── StatsResponse.java
│   │   │           ├── entity/
│   │   │           │   └── DnaRecord.java
│   │   │           ├── exception/
│   │   │           │   ├── GlobalExceptionHandler.java
│   │   │           │   └── DnaHashCalculationException.java
│   │   │           ├── repository/
│   │   │           │   └── DnaRecordRepository.java
│   │   │           └── service/
│   │   │               ├── MutantDetector.java
│   │   │               ├── MutantService.java
│   │   │               └── StatsService.java
│   │   └── resources/
│   │       ├── static.img/
│   │       │   ├── banner.png
│   │       │   ├── diagramas.png
│   │       │   └── swagger-ui.png
│   │       └── application.properties
│
├── test/
│   ├── java/
│   │   └── org/
│   │       └── example/
│   │           ├── controller/
│   │           │   ├── MutantControllerTest.java
│   │           │   └── StatsControllerTest.java
│   │           └── service/
│   │               ├── MutantDetectorTest.java
│   │               ├── MutantServiceTest.java
│   │               └── StatsServiceTest.java
│   └── resources/
│
├── .gitignore
├── build.gradle.kts
├── gradlew
├── gradlew.bat
├── README.md
└── settings.gradle.kts

```
---
## Documentación con Swagger

Acceso local: 
http://localhost:8080/swagger-ui/index.html


Vista previa:

![Swagger UI](src/main/resources/static/img/swagger-ui.png)

---

## Endpoints principales

### **POST /mutant**  
Determina si una secuencia de ADN pertenece a un mutante.

### **GET /stats**  
Devuelve estadísticas globales del sistema.

---

## Base de Datos H2

Acceso:
http://localhost:8080/h2-console


## Configuración:

```properties
spring.datasource.url=jdbc:h2:mem:mutantsdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=create-drop
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```
---
## Tests Unitarios

• **MutantDetectorTest** — Verifica la lógica de detección de mutantes en matrices ADN.  
• **MutantServiceTest** — Prueba el servicio encargado de procesar ADN y persistir resultados.  
• **StatsServiceTest** — Valida la obtención de estadísticas desde la base de datos.  
• **MutantControllerTest** — Comprueba que el endpoint POST /mutant funcione correctamente.  
• **StatsControllerTest** — Testea el endpoint GET /stats y su formato de respuesta.  

Ejecutar:
./gradlew test

---
## Diagramas del Proyecto

• **Diagrama UML General del Sistema**  
• **Diagramas de Arquitectura y Flujo de Datos**  
• **Estructura del Proyecto (Tree View)**  

### Vista General 
1. Diagrama de flujo del algoritmo
```txt
               ┌──────────┐
               │   Start   │
               └─────┬────┘
                     │
                     ▼
           ┌────────────────────┐
           │ Validate DNA input │
           │ (NxN, A/T/C/G)     │
           └─────┬──────────────┘
                 │Yes
                 │
                 │       No
                 ▼      ───────▶  ┌─────────────────────┐
       ┌────────────────────┐     │ Return 400 BadRequest │
       │ Detect mutant DNA  │     │ (invalid format)      │
       │ using scanner      │     └───────────────────────┘
       └─────┬──────────────┘
             │
             │Yes
             ▼
 ┌──────────────────────────────┐
 │ Mark is_mutant = true        │
 │ Save record into H2 database │
 └───────────┬──────────────────┘
             │
             │No
             ▼
 ┌──────────────────────────────┐
 │ Mark is_mutant = false       │
 │ Save record into H2 database │
 └───────────┬──────────────────┘
             │
             ▼
      ┌──────────────┐
      │   Return     │
      │   Response   │
      └──────┬───────┘
             ▼
          ┌──────┐
          │  End │
          └──────┘
```
2. Diagrama de la arquitectura del proyecto
```txt
                    ┌──────────────────────────┐
                    │        CONTROLLER         │
                    │ MutantController          │
                    │ StatsController           │
                    └───────────────┬───────────┘
                                    │
                    ┌───────────────▼──────────────┐
                    │            SERVICE             │
                    │ MutantService                 │
                    │ StatsService                  │
                    └───────────────┬──────────────┘
                                    │
                    ┌───────────────▼──────────────┐
                    │        BUSINESS LOGIC         │
                    │       MutantDetector          │
                    └───────────────┬──────────────┘
                                    │
                    ┌───────────────▼──────────────┐
                    │          REPOSITORY           │
                    │    DnaRecordRepository        │
                    └───────────────┬──────────────┘
                                    │
                    ┌───────────────▼──────────────┐
                    │            ENTITY             │
                    │          DnaRecord            │
                    └───────────────┬──────────────┘
                                    │
                    ┌───────────────▼──────────────┐
                    │            DATABASE           │
                    │               H2              │
                    └──────────────────────────────┘

```
3. Diagrama de clases del proyecto 
```txt
+----------------------+
|   MutantController   |
+----------------------+
| + POST /mutant       |
| + GET /stats         |
+----------+-----------+
           |
           ▼
+----------------------+
|    MutantService     |
+----------------------+
| + analyzeDna()       |
+----------+-----------+
           |
           ▼
+----------------------+
|   MutantDetector     |
+----------------------+
| + isMutant()         |
| + isValidDna()       |
+----------------------+

+----------------------+
|   StatsController    |
+----------------------+
| + GET /stats         |
+----------+-----------+
           |
           ▼
+----------------------+
|    StatsService      |
+----------------------+
| + getStats()         |
+----------+-----------+
           |
           ▼
+-------------------------------+
|      DnaRecordRepository      |
+-------------------------------+
| + findByDnaHash()             |
| + countByIsMutant()           |
+-------------------------------+
           |
           ▼
+----------------------+
|      DnaRecord       |
+----------------------+
| id: Long             |
| dnaHash: String      |
| isMutant: boolean    |
| createdAt: LocalDate |
+----------------------+

```
4. Diagrama de secuencia (Mutant)
```txt
Client
  |
  | POST /mutant
  ▼
MutantController
  |
  | analyzeDna(dna)
  ▼
MutantService
  |
  | calculateHash()
  | findByDnaHash()
  ▼
DnaRecordRepository
  |
  | return Optional<DnaRecord>
  ▼
MutantService
  |
  | isMutant(dna)
  ▼
MutantDetector
  |
  | return boolean
  ▼
MutantService
  |
  | save(record)
  ▼
DnaRecordRepository
  |
  | return saved
  ▼
MutantController
  |
  | return 200/403
  ▼
Client

```
4.1. Diagrama de secuencia (Stats)
```txt
Client
  |
  | GET /stats
  ▼
StatsController
  |
  | getStats()
  ▼
StatsService
  |
  | countByIsMutant(true)
  ▼
DnaRecordRepository
  |
  | return count
  ▼
StatsService
  |
  | countByIsMutant(false)
  ▼
DnaRecordRepository
  |
  | return count
  ▼
StatsService
  |
  | build StatsResponse
  ▼
StatsController
  |
  | return JSON
  ▼
Client

```
5. Diagrama de caso de uso
```txt
          +------------------+
          |      Usuario     |
          +--------+---------+
                   |
                   |
                   ▼
        +------------------------+
        |   Verificar ADN        |
        |   (POST /mutant)       |
        +------------------------+

                   |
                   ▼
        +------------------------+
        | Registrar resultado    |
        | (Mutante / Humano)     |
        +------------------------+

                   |
                   ▼
        +------------------------+
        | Obtener estadísticas   |
        |     (GET /stats)       |
        +------------------------+

```
---
🎯 <span style="color:#4CAF50">Mensaje Final del Proyecto</span>

Este proyecto representa una solución completa, profesional y altamente mantenible para la detección automática de mutantes mediante análisis de secuencias de ADN.
Cada capa del sistema fue diseñada con precisión, aplicando las mejores prácticas de ingeniería de software en arquitectura, testing, documentación y persistencia.

🧬 <span style="color:#00BCD4">Tecnología y Arquitectura</span>

El sistema implementa una arquitectura modular basada en:

<span style="color:#FFEB3B">Spring Boot (3.x)</span>

<span style="color:#FFC107">Controladores y Servicios separados</span>

<span style="color:#FF9800">Repositorio H2 embebido para pruebas</span>

<span style="color:#F44336">Global Exception Handler profesional</span>

<span style="color:#9C27B0">DTOs, Entities y Validaciones bien definidas</span>

Este enfoque permite un código limpio, escalable y altamente testeable.

🧪 <span style="color:#3F51B5">Calidad del Software</span>

Se incluye una batería de tests unitarios que garantizan:

✔️ Comportamiento correcto de servicios

✔️ Validación del algoritmo detector de mutantes

✔️ Respuestas del controlador

✔️ Manejo centralizado de errores

Todo pensado para asegurar robustez y confiabilidad del sistema.

📊 <span style="color:#E91E63">Documentación Interactiva</span>

La API puede explorarse fácilmente mediante:

🚀 Swagger UI → http://localhost:8080/swagger-ui/index.html
Incluye endpoints, modelos, ejemplos y resultados esperados.

🗄️ <span style="color:#009688">Persistencia y Visualización</span>

La base de datos H2 permite examinar en vivo:

Registros de ADN

Histórico de consultas

Estadísticas del sistema

🌐 Consola H2 → http://localhost:8080/h2-console

🌟 <span style="color:#8BC34A">Resultado Final</span>

Este proyecto cumple con los estándares modernos de la industria, ofreciendo:

Código claro y mantenible

Arquitectura sólida y extensible

Documentación completa

Pruebas exhaustivas

Interfaz interactiva mediante Swagger

Base de datos integrada y accesible

En conjunto, constituye una solución lista para presentar, lista para subir a GitHub y lista para producción.
---
##  Autor

• **Rodrigo Ángel Zonca**  
• Estudiante de Ingeniería en Sistemas de Información (UTN-FRM)  
• Desarrollador Full Stack 
• Master Swimming






