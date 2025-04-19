# Notes Backend API

Un sistema backend para gestión de notas con autenticación OAuth2, grupos colaborativos y notificaciones en tiempo real.

## Características Principales

- ✅ **Autenticación OAuth2** (Google, GitHub, etc.)
- 📝 **Gestión de notas** con recordatorios
- 👥 **Grupos colaborativos**
- ⚡ **Actualizaciones en tiempo real** (WebSocket/SSE)
- 🚀 **Base de datos Turso** (libSQL) para alto rendimiento
- 📅 **Sistema de recordatorios programados**
- 🔐 **Autorización basada en roles y propiedad**

## Tecnologías

- **Backend**: Spring Boot 3.2
- **Autenticación**: Spring Security OAuth2 Resource Server
- **Base de datos**: Turso (libSQL)
- **Comunicación en tiempo real**: WebSocket/STOMP + SSE
- **ORM**: Spring Data JPA
- **Herramientas**: Lombok, H2 (dev), Swagger

## Requisitos

- Java 17+
- Turso CLI (para gestión de base de datos)
- Cuenta en proveedor OAuth2 (Google, Auth0, etc.)
- Node.js (opcional para scripts)



## Requerimientos de la ENV
- **POSTGRES_DB_PWD : Postgres password**

## Actualizacion 29 / 03

- Migrando a Supabase 

## Actualizacion 04 / 04

- Usando MapStruct
- Fix: Uso de MapStruct Lombok binding como procesador de anotaciones.


## Notas importantes

- AuthenticationProvider, es la manera en como Spring valida accesos y credenciales de usuarios.


- ProviderManager class que maneja multiples `AuthenticationProvider` para validar usuarios y accesos en la app. 


- BearerTokenResolver: Manera en que Spring extrae el token tipo bearer y tiene una clase Default, No Bean.


- <code>@(Spring auto configure)</code> annotation + @ExtendWith(SpringExtension.class) + @ContextConfiguration. Pruebas de integracion con beans especificos.