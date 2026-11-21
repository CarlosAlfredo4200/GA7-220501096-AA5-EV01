# API REST de registro e inicio de sesión

**Evidencia:** GA7-220501096-AA5-EV01 – Diseño y desarrollo de servicios web - caso  
**Programa:** Análisis y Desarrollo de Software – SENA  
**Aprendiz:** Carlos Alfredo Montoya Goez

## Descripción

Proyecto desarrollado con Spring Boot para exponer servicios web reutilizables de **registro de usuarios** e **inicio de sesión**. La información se almacena en Microsoft SQL Server mediante Spring Data JPA/Hibernate y las contraseñas se protegen con BCrypt.

## Servicios disponibles

| Método | Ruta | Descripción | Respuesta esperada |
|---|---|---|---|
| GET | `/api/auth` | Comprueba que la API esté disponible | 200 |
| POST | `/api/auth/registro` | Registra un usuario | 201 / 400 / 409 |
| POST | `/api/auth/login` | Valida usuario y contraseña | 200 / 400 / 401 |

### Registro

`POST /api/auth/registro`

```json
{
  "usuario": "carlos",
  "password": "Carlos123"
}
```

Respuesta correcta:

```json
{
  "mensaje": "Usuario registrado correctamente."
}
```

### Inicio de sesión

`POST /api/auth/login`

```json
{
  "usuario": "carlos",
  "password": "Carlos123"
}
```

Respuesta correcta:

```json
{
  "mensaje": "Autenticación satisfactoria."
}
```

Cuando las credenciales no coinciden se devuelve HTTP `401 Unauthorized`:

```json
{
  "mensaje": "Error en la autenticación."
}
```

## Validaciones

- Usuario obligatorio.
- Usuario entre 4 y 50 caracteres.
- Contraseña obligatoria.
- Contraseña entre 6 y 100 caracteres.
- No se permite registrar dos veces el mismo nombre de usuario, incluso si cambia el uso de mayúsculas/minúsculas.
- El inicio de sesión verifica que el usuario exista y que la contraseña coincida con el hash BCrypt almacenado.

## Tecnologías

- Java 21
- Spring Boot
- Spring Web MVC
- Spring Data JPA / Hibernate
- Jakarta Validation
- Spring Security Crypto (BCrypt)
- Microsoft SQL Server
- Maven
- Git / GitHub

## Configuración de la base de datos

Crear la base `auth_sena`. Se incluye el script:

```text
database/crear_base_datos.sql
```

La conexión de desarrollo utiliza:

```text
jdbc:sqlserver://localhost:14330;databaseName=auth_sena;encrypt=true;trustServerCertificate=true
```

La contraseña de SQL Server **no está escrita en el repositorio**. Debe definirse mediante la variable de entorno:

```text
PAPELERIA_DB_PASSWORD
```

En IntelliJ puede configurarse en **Run > Edit Configurations > Environment variables**.

## Ejecución

En Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

O ejecutar `AuthApiSenaApplication` desde IntelliJ.

## Pruebas

Con la aplicación iniciada pueden ejecutarse las pruebas manuales incluidas:

```powershell
powershell -ExecutionPolicy Bypass -File .\pruebas\pruebas-api.ps1
```

También se incluye `pruebas/pruebas-api.http` para clientes HTTP compatibles.

Las pruebas automáticas se ejecutan con:

```powershell
.\mvnw.cmd test
```

## Estructura principal

```text
src/main/java/com/sena/authapisena/
├── config/       Configuración de BCrypt
├── controller/   Endpoints REST
├── dto/          Datos de entrada y respuesta
├── exception/    Manejo global de validaciones
├── model/        Entidad Usuario
├── repository/   Acceso a SQL Server
└── service/      Lógica de registro y autenticación
```

## Criterios de la evidencia

- Servicio de registro: `POST /api/auth/registro`.
- Servicio de inicio de sesión: `POST /api/auth/login`.
- Validaciones: Jakarta Validation, usuario duplicado y comprobación de contraseña BCrypt.
- Código documentado con comentarios/JavaDoc.
- Proyecto administrado con Git y preparado para publicarse en GitHub.

## Seguridad

Las contraseñas no se almacenan en texto plano. Antes de persistirlas se genera un hash BCrypt y durante el login se valida mediante `PasswordEncoder.matches()`.
