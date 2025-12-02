# 🔧 Ferretería Moderna -- Backend

**Versión:** 3.0\
**Tecnologías:** Node.js · Express · MongoDB/MySQL · JWT · MVC
Architecture

------------------------------------------------------------------------

# 📝 Descripción General del Proyecto

El backend de **Ferretería Moderna** proporciona toda la lógica de
negocio y los servicios API que permiten que el frontend funcione
correctamente.\
El sistema está diseñado bajo principios de **modularidad,
escalabilidad, seguridad y mantenibilidad**.

------------------------------------------------------------------------

# 🎯 Objetivos del Backend

### 🛍️ Gestión de Productos

-   CRUD de productos\
-   Manejo de stock\
-   Filtros y búsquedas

### 🧾 Gestión de Categorías

-   Crear, editar y eliminar categorías

### 👤 Gestión de Usuarios

-   Autenticación JWT\
-   Roles (admin/cliente)\
-   Protección de rutas

### 🛒 Carrito y Órdenes

-   Agregar productos al carrito\
-   Actualizar cantidades\
-   Generar orden de compra

### 📡 API REST

-   Endpoints estandarizados\
-   Manejo centralizado de errores

------------------------------------------------------------------------

# 🏗️ Arquitectura del Proyecto (MVC)

    ferreteria-backend/
    │── src/
    │   ├── config/
    │   ├── controllers/
    │   ├── middlewares/
    │   ├── models/
    │   ├── routes/
    │   ├── services/
    │   ├── utils/
    │   ├── app.js
    │   └── server.js
    │
    │── .env
    │── package.json
    │── README.md

------------------------------------------------------------------------

# ⚙️ Tecnologías y Dependencias

### Backend:

-   Node.js + Express\
-   Cors\
-   Dotenv\
-   Morgan\
-   JWT\
-   Bcrypt\
-   Multer (si hay imágenes)

### Base de Datos:

-   MongoDB + Mongoose\
    **o**
-   MySQL + Sequelize

------------------------------------------------------------------------

# 🔒 Seguridad Implementada

-   Contraseñas cifradas con **bcrypt**\
-   Tokens JWT\
-   Middlewares de rol y permisos\
-   Validación de datos de entrada\
-   Sanitización para evitar inyecciones

------------------------------------------------------------------------

# 🚀 Instalación y Uso

## 1️⃣ Requisitos

-   Node.js\
-   npm/yarn\
-   Base de datos activa

------------------------------------------------------------------------

## 2️⃣ Clonar el repositorio

``` bash
git clone https://github.com/oodpipe/ferreteria-backends.git
cd ferreteria-backend
```

------------------------------------------------------------------------

## 3️⃣ Instalar dependencias

``` bash
npm install
```

------------------------------------------------------------------------

## 4️⃣ Crear el archivo .env

Ejemplo:

    PORT=4000
    DATABASE_URL=mongodb://localhost:27017/ferreteria
    JWT_SECRET=S3cr3tClave
    TOKEN_EXPIRES_IN=1d

------------------------------------------------------------------------

## 5️⃣ Ejecutar entorno de desarrollo

``` bash
npm run dev
```

Servidor en:

    http://localhost:4000/

------------------------------------------------------------------------

## 6️⃣ Levantar producción

``` bash
npm run build
npm start
```

------------------------------------------------------------------------

# 🌐 Ejemplos de Endpoints

### Obtener productos

    GET /api/products

### Crear producto (admin)

    POST /api/products
    Authorization: Bearer <token>

### Login

    POST /api/auth/login

### Crear orden

    POST /api/orders

------------------------------------------------------------------------

# 🧪 Testing

Compatible con:

-   Jest\
-   Supertest

Ejemplo:

``` bash
npm run test
```

------------------------------------------------------------------------

# 👨‍💻 Autores

-   Felipe Espinoza\
-   Diego Yañez

------------------------------------------------------------------------