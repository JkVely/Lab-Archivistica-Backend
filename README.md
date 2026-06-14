<p align="center">
	<img src="https://capsule-render.vercel.app/api?type=waving&height=220&color=0:1a1b27,50:7aa2f7,100:bb9af7&text=LAB%20ARCHIVISTICA&fontColor=ffffff&fontSize=56&fontAlignY=40&desc=Backend%20API%20Engine%20for%20Resource%20and%20Schedule%20Management&descAlignY=62&animation=fadeIn" alt="Lab Archivistica Banner" />
</p>

<p align="center">
	<a href="https://www.java.com/" target="_blank"><img alt="Java" src="https://img.shields.io/badge/Backend-Java%2021-1a1b27?style=for-the-badge&logo=openjdk&logoColor=7aa2f7"></a>
	<a href="https://spring.io/projects/spring-boot" target="_blank"><img alt="Spring Boot" src="https://img.shields.io/badge/Framework-Spring%20Boot-1a1b27?style=for-the-badge&logo=spring&logoColor=7aa2f7"></a>
	<a href="https://www.postgresql.org/" target="_blank"><img alt="PostgreSQL" src="https://img.shields.io/badge/Database-PostgreSQL-1a1b27?style=for-the-badge&logo=postgresql&logoColor=7aa2f7"></a>
</p>

<p align="center">
	<img alt="GitOps" src="https://img.shields.io/badge/GitOps-Ready-bb9af7?style=flat-square">
	<img alt="Flyway" src="https://img.shields.io/badge/Migrations-Flyway-bb9af7?style=flat-square&logo=flyway&logoColor=white">
	<img alt="Spring Security" src="https://img.shields.io/badge/Security-JWT-bb9af7?style=flat-square&logo=json-web-tokens&logoColor=white">
	<img alt="Developer" src="https://img.shields.io/badge/Developer-JkVely-7aa2f7?style=flat-square&logo=github&logoColor=white">
</p>

<h1 align="center">Lab Archivística UD — Backend</h1>
<p align="center"><strong>Secure and automated API engine for the Archival Laboratory at Universidad Distrital Francisco José de Caldas.</strong></p>

---

### `> cat about.md`

Este proyecto constituye el motor de servicios REST que alimenta la plataforma del Laboratorio de Archivística de la Universidad Distrital Francisco José de Caldas. Está diseñado bajo principios de modularidad y escalabilidad para resolver de manera robusta la autenticación, control de inventario de equipos y aplicativos, gestión de documentos y flujos de reserva académica.

---

### `> cat tech_stack.json`

```json
{
  "runtime": "Java 21 (OpenJDK)",
  "framework": "Spring Boot 3.x",
  "security": "Spring Security + JWT",
  "database": "PostgreSQL",
  "migrations": "Flyway",
  "build_tool": "Maven",
  "reverse_proxy": "Apache HTTP Server"
}
```

---

### `> git status --modules`

| Módulo | Descripción | Tabla SQL |
|---|---|---|
| **Autenticación** | Registro y login seguro con tokens JWT, exclusivo para correos `@udistrital.edu.co`. | `usuarios` |
| **Equipos** | Control de inventario físico del laboratorio (marca, modelo, categoría y estado). | `equipos` |
| **Software** | Registro de licencias y documentación del software académico disponible. | `software` |
| **Aplicativos** | Catálogo informativo de herramientas archivísticas instaladas. | `aplicativos` |
| **Documentos** | Carga y descarga de archivos PDF/DOCX almacenados de forma local en el FileSystem. | `documentos` |
| **Salas y Reservas** | Control de disponibilidad semanal de salas y flujo de préstamos de la universidad. | `salas`, `horarios_salas`, `reservas_salas` |

---

### `> git log --reservation-flow`

El control de disponibilidad y préstamo de espacios sigue un ciclo de estados controlados para estudiantes y docentes universitarios:

```mermaid
flowchart TD
    A[Consulta Disponibilidad] --> B[Creación de Reserva - PENDING]
    B --> C{Revisión ADMIN}
    C -->|Aprobada| D[Reserva Activa - APPROVED]
    C -->|Rechazada| E[Reserva Denegada - REJECTED]
    D -->|Cancelación por Estudiante| F[Cancelada - CANCELLED]
    D -->|Expiración del Horario| G[Finalizada]
```

---

### `> cat config_guide.yml`

Para iniciar el servidor localmente, debes crear el archivo `application.yml` en la ruta de recursos con la siguiente estructura base:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/lab_archivistica
    username: tu_usuario
    password: tu_password
  jpa:
    hibernate:
      ddl-auto: validate
  servlet:
    multipart:
      max-file-size: 20MB
app:
  jwt-secret: "clave-muy-segura-de-al-menos-32-caracteres"
  jwt-expiration: 86400000
  upload-dir: /var/www/lab-archivistica/documentos/
```

### Ejecutar proyecto

```bash
mvn clean compile
mvn spring-boot:run
```

---

### `> sonar --quality-gate`

<div align="center">

![SonarQube Gate](https://img.shields.io/sonar/quality_gate/JkVely_Lab-Archivistica-Backend?server=https%3A%2F%2Fsonarcloud.io&style=for-the-badge&logo=sonarqube&logoColor=white&label=Quality%20Gate&color=7aa2f7&labelColor=1a1b27)

*(Métrica ilustrativa de calidad y control estático de código)*

</div>

---

<div align="center">
  <code>// Precision in every commit. Reliability in every deploy.</code>
</div>