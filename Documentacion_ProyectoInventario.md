# Documentación Técnica — ProyectoInventario

**Fecha:** October 03, 2025  
**Autor:** Equipo de Desarrollo

## 1. Resumen Ejecutivo
Este documento describe la arquitectura, el ciclo de vida de desarrollo, la configuración y la operación del servicio backend basado en **Spring Boot** y **Java**, empaquetado con **Gradle** y desplegado en **Railway**. Se incluyen los endpoints expuestos, el modelo de datos, dependencias clave, consideraciones de seguridad y lineamientos de operación como en una empresa formal.

## 2. Alcance y Objetivo
- Proveer una referencia única y actual del estado del proyecto.  
- Facilitar onboarding de nuevos miembros del equipo.  
- Estandarizar procesos de build, prueba y despliegue (CI/CD con GitHub → Railway).

## 3. Stack Tecnológico
- **Lenguaje:** Java (versión detectada: 17)
- **Framework:** Spring Boot
- **Build Tool:** Gradle
- **Repositorio:** Git + GitHub
- **Despliegue:** Railway (PaaS)
- **Configuración:** `application.yml`/`application.properties`
- **(Opcional)** Docker (si aplica; ver Dockerfile)

## 4. Arquitectura de Alto Nivel
- **API REST** con controladores anotados (`@RestController`).  
- **Capa de datos** con entidades JPA (`@Entity`) y repositorios Spring Data.  
- **Gestión de configuración** según perfiles (`application-*.yml/properties`).  
- **CI/CD**: GitHub (origen) → Railway (deploy automatizado).

## 5. Dependencias (Gradle)
- **implementation**: `org.springframework.boot:spring-boot-starter-web`  
  _(Endpoints/build.gradle)_
- **implementation**: `org.springframework.boot:spring-boot-starter-validation`  
  _(Endpoints/build.gradle)_
- **implementation**: `org.springframework.boot:spring-boot-starter-data-jpa`  
  _(Endpoints/build.gradle)_
- **implementation**: `mysql:mysql-connector-java:8.0.33`  
  _(Endpoints/build.gradle)_
- **implementation**: `org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0`  
  _(Endpoints/build.gradle)_
- **testImplementation**: `org.springframework.boot:spring-boot-starter-test`  
  _(Endpoints/build.gradle)_

## 6. Endpoints de la API
Los siguientes endpoints se extrajeron automáticamente del código fuente (anotaciones `@GetMapping`, `@PostMapping`, etc.).  
> Referencia rápida en CSV: `endpoints_inventory.csv`

**AlmacenController**  
`Endpoints/src/main/java/com/example/ProyectoInventario/controller/AlmacenController.java`
- Base path(s): `/api/almacenes`
- `GET` `/api/almacenes/{id}`
- `PUT` `/api/almacenes/{id}`
- `DELETE` `/api/almacenes/{id}`

**CategoriaController**  
`Endpoints/src/main/java/com/example/ProyectoInventario/controller/CategoriaController.java`
- Base path(s): `/api/categorias`
- `GET` `/api/categorias/{id}`
- `PUT` `/api/categorias/{id}`
- `DELETE` `/api/categorias/{id}`

**EntregaController**  
`Endpoints/src/main/java/com/example/ProyectoInventario/controller/EntregaController.java`
- Base path(s): `/api/entregas`
- `GET` `/api/entregas/{id}`
- `PUT` `/api/entregas/{id}`
- `DELETE` `/api/entregas/{id}`

**ProductoController**  
`Endpoints/src/main/java/com/example/ProyectoInventario/controller/ProductoController.java`
- Base path(s): `/api/productos`
- `GET` `/api/productos/{id}`
- `GET` `/api/productos/by-name`
- `GET` `/api/productos/categoria/{categoriaId}`
- `PUT` `/api/productos/{id}`
- `PUT` `/api/productos/increase-stock/{id}`
- `PUT` `/api/productos/decrease-stock/{id}`
- `DELETE` `/api/productos/{id}`
- `PATCH` `/api/productos/{id}/activar`
- `PATCH` `/api/productos/{id}/desactivar`

**GlobalExceptionHandler**  
`Endpoints/src/main/java/com/example/ProyectoInventario/exception/GlobalExceptionHandler.java`
  - _Sin métodos expuestos_


## 7. Modelo de Datos (Entidades JPA)
**Almacen**  
`Endpoints/src/main/java/com/example/ProyectoInventario/entity/Almacen.java`
- `almacenId`: Long
- `codigo`: String
- `nombre`: String
- `tipo`: String
- `direccion`: String
- `ciudad`: String
- `pais`: String

**Categoria**  
`Endpoints/src/main/java/com/example/ProyectoInventario/entity/Categoria.java`
- `categoriaId`: Long
- `nombre`: String
- `descripcion`: String

**Entrega**  
`Endpoints/src/main/java/com/example/ProyectoInventario/entity/Entrega.java`
- `entregaId`: Long
- `producto`: Producto
- `almacen`: Almacen
- `cantidad`: Integer

**Producto**  
`Endpoints/src/main/java/com/example/ProyectoInventario/entity/Producto.java`
- `productoId`: Long
- `sku`: String
- `nombre`: String
- `categoriaId`: Long
- `codigoBarras`: String
- `stockMinimo`: BigDecimal
- `stockMaximo`: BigDecimal
- `precio`: BigDecimal
- `activo`: boolean
- `creadoEn`: LocalDateTime
- `actualizadoEn`: LocalDateTime


## 8. Configuración de Aplicación
Archivos detectados y claves relevantes:
- `Endpoints/system.properties`
- `Endpoints/.gradle/8.14.3/gc.properties`
- `Endpoints/.gradle/buildOutputCleanup/cache.properties`
- `Endpoints/.gradle/vcs-1/gc.properties`
- `Endpoints/gradle/wrapper/gradle-wrapper.properties`
- `Endpoints/src/main/resources/application-local.properties` → claves relevantes: spring.datasource, spring.jpa, server.port
- `Endpoints/src/main/resources/application.properties` → claves relevantes: spring.datasource, spring.jpa, server.port

### Variables de Entorno (Railway)
- `SPRING_DATASOURCE_URL`, `SPRING_DATASOURCE_USERNAME`, `SPRING_DATASOURCE_PASSWORD`
- `SPRING_JPA_HIBERNATE_DDL_AUTO` (p. ej. `update`/`validate`)
- `SERVER_PORT` (Railway asigna un puerto, usar `${PORT}` si corresponde)
- Otras variables (JWT, correos, etc.) según configuración real

> **Buenas prácticas:** Nunca commitear secretos; usar variables en Railway y/o GitHub Actions Secrets.

## 9. Ejecución Local (Desarrollo)
```bash
# Requisitos: JDK 17+ (o según Gradle), Gradle Wrapper
./gradlew clean bootRun
# o
./gradlew clean build && java -jar build/libs/*.jar
```

## 10. Despliegue en Railway
1. Conectar el repositorio de GitHub al proyecto en Railway.  
2. Definir variables de entorno y servicios (p.ej., Postgres).  
3. Configurar `Build & Deploy` con Gradle/Jar o Dockerfile.  
4. Al hacer push a la rama principal, Railway construye e inicia el servicio.  
5. Monitorear logs y métricas desde el panel de Railway.

## 11. Flujo de Trabajo con Git/GitHub
- **Ramas:** `main` estable, `feature/*` para nuevas funcionalidades, `fix/*` para correcciones.  
- **Commits:** mensajes descriptivos (Convencional Commits recomendado).  
- **Pull Requests:** revisión obligatoria + checks de CI.  
- **Versionado:** SemVer (MAJOR.MINOR.PATCH).

## 12. Seguridad y Cumplimiento
- **Gestión de secretos** en Railway/GitHub Secrets.  
- **CORS** restringido a dominios confiables.  
- **Validación** en DTOs y controladores (Bean Validation).  
- **Manejo de errores** con `@ControllerAdvice`.  
- **Logs** sin datos sensibles; rotación y niveles adecuados.  
- **Backups** de base de datos (si aplica).

## 13. Observabilidad
- **Actuator** (si está incluido): `/actuator/health`, `/actuator/info`.  
- **Métricas** expuestas para APM/monitoreo.  
- **Trazas** opcionales con Sleuth/Zipkin (si se usa).

## 14. Pruebas
- **Unitarias** con JUnit/Mockito.  
- **Integración** con contexto Spring.  
- **Colección de Postman** (recomendado) alineada con los endpoints detectados.

## 15. Procedimientos Operativos
- **Rollback:** desplegar tag anterior desde GitHub o activar build previo en Railway.  
- **Mantenimiento:** ventanas definidas, notificación a stakeholders.  
- **Capacidad:** revisar recursos (RAM/CPU) y escalar en Railway.

## 16. Troubleshooting (FAQ)
- **El puerto no responde en Railway:** usar `${PORT}` y en `application.properties`: `server.port=${PORT:8080}`.  
- **Error de driver DB:** validar `spring.datasource.*` y dependencias.  
- **CORS 403:** revisar configuración de CORS.  
- **Build falla:** verificar versión de Java/Gradle y wrapper ejecutable.

## 17. Estructura del Repositorio
```
    .
    ./Endpoints
    ./Endpoints/.git
    ./Endpoints/.git/hooks
    ./Endpoints/.git/info
    ./Endpoints/.git/logs
    ./Endpoints/.git/objects
    ./Endpoints/.git/refs
    ./Endpoints/.gradle
    ./Endpoints/.gradle/8.14.3
    ./Endpoints/.gradle/buildOutputCleanup
    ./Endpoints/.gradle/nb-cache
    ./Endpoints/.gradle/vcs-1
    ./Endpoints/build
    ./Endpoints/build/classes
    ./Endpoints/build/reports
    ./Endpoints/build/resources
    ./Endpoints/build/tmp
    ./Endpoints/gradle
    ./Endpoints/gradle/wrapper
    ./Endpoints/src
    ./Endpoints/src/main
    ./Endpoints/src/test
```

## 18. Glosario
- **PaaS:** Plataforma como Servicio (Railway).  
- **CI/CD:** Integración/Entrega continua.  
- **DTO:** Objeto de Transferencia de Datos.  
- **Entity:** Clase mapeada a tabla de base de datos.  
- **Controller:** Clase que expone endpoints HTTP.

---

**Anexos**  
- `endpoints_inventory.csv` — lista tabular de endpoints detectados.
- Archivos de configuración en `src/main/resources/`.

> Generado automáticamente el October 03, 2025.
