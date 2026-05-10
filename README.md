# 🛍️ DDD E-Commerce Platform

Este proyecto es una demostración práctica y académica de la implementación de **Domain-Driven Design (DDD)** y **Arquitectura Hexagonal (Puertos y Adaptadores)**. Está compuesto por un backend robusto en Spring Boot y un frontend interactivo en React + Vite.

## 🎯 Objetivo del Proyecto
El propósito principal es ilustrar cómo aislar la lógica de negocio compleja de la infraestructura técnica. El proyecto demuestra visualmente una regla de negocio escalable:
*   **Clientes REGULARES:** Se valida estrictamente el inventario antes de confirmar una orden. Si no hay stock, la orden es rechazada.
*   **Clientes VIP:** Pueden realizar pedidos saltándose la validación de inventario, lo que genera una "deuda de reabastecimiento" (stock negativo), garantizando que su orden siempre sea procesada.

## 🏗️ Arquitectura del Sistema

### Backend (Spring Boot + Java)
El backend está estructurado siguiendo los principios de la Arquitectura Hexagonal y DDD para asegurar un alto nivel de desacoplamiento:

*   **`domain` (El Núcleo):** Contiene las reglas de negocio puras. No tiene dependencias de Spring ni de bases de datos.
    *   *Aggregates:* `Order` y `Product`. Aquí vive la lógica de VIP vs REGULAR y el manejo del stock.
    *   *Value Objects:* `OrderId`, `ProductId`, `Money`, `Quantity`.
    *   *Ports:* Interfaces de repositorios (`OrderRepository`, `ProductRepository`) que el dominio necesita para funcionar.
*   **`application` (Casos de Uso):** Orquesta el flujo de las operaciones (`PlaceOrderUseCase`, `CancelOrderUseCase`) delegando la lógica de negocio a las entidades de dominio.
*   **`api` (Adaptadores de Entrada):** Controladores REST (`OrderController`) que traducen las peticiones HTTP a comandos (`PlaceOrderCommand`) que entiende la capa de aplicación.
*   **`infrastructure` (Adaptadores de Salida y Configuración):** Implementa los puertos del dominio usando Spring Data JPA (`OrderRepositoryAdapter`). Contiene mappers explícitos para convertir entre entidades JPA y modelos de dominio, configuraciones de CORS y Seguridad.

### Frontend (React + Vite + Tailwind CSS)
Una interfaz de usuario moderna diseñada para evidenciar visualmente las reglas de negocio del backend:

*   **Tecnologías:** React 18, Vite (para un empaquetado ultra rápido), Tailwind CSS v3 (para diseño responsivo) y Axios.
*   **Características clave:**
    *   Tarjetas interactivas de clientes para seleccionar fácilmente entre perfiles predefinidos (Ej: Erik Herrera como REGULAR, Alex Chiluisa como VIP).
    *   Indicadores visuales de stock en tiempo real.
    *   Manejo de errores dinámico y explícito: captura y muestra de forma amigable los mensajes de error del dominio (ej. `InsufficientStockException`).
    *   Mapeo de UUIDs del dominio a IDs de base de datos para operaciones complejas como la cancelación de órdenes.

## 📂 Estructura de Directorios

```text
.
├── backend/
│   ├── src/main/java/com/expo/ddd/
│   │   ├── api/               # Controladores HTTP y manejo de errores globales
│   │   ├── application/       # Casos de uso y Comandos
│   │   ├── domain/            # Modelos de Dominio, Value Objects, Interfaces de Repositorios
│   │   └── infrastructure/    # Persistencia JPA, Mappers, Spring Config (Security, CORS)
│   └── pom.xml                # Configuración de dependencias Maven
└── frontend/
    ├── src/
    │   ├── components/        # Componentes UI (PlaceOrderForm, ProductGrid)
    │   ├── hooks/             # Custom hooks (useOrders.js)
    │   ├── constants/         # Datos semilla (clientes, catálogos)
    │   └── App.jsx            # Punto de entrada de la UI
    ├── package.json           # Dependencias de npm
    ├── tailwind.config.js     # Configuración de estilos
    └── vite.config.js         # Configuración del bundler
```

## 🚀 Guía de Inicio Rápido

### Requisitos Previos
*   **Java 17** o superior
*   **Maven**
*   **Node.js** (v18+) y **npm**

### 1. Iniciar el Backend
El backend utiliza una base de datos en memoria (H2) y se inicializa con datos semilla (productos por defecto) al arrancar.

```bash
cd backend
mvn clean install
mvn spring-boot:run
```
El servidor backend se ejecutará en: `http://localhost:8080`

### 2. Iniciar el Frontend
En una nueva terminal:

```bash
cd frontend
npm install
npm run dev
```
La aplicación web estará disponible en: `http://localhost:5173`

## 🔐 Seguridad y Autenticación
Para simplificar la demostración, el backend está protegido con HTTP Basic Authentication. Las credenciales están configuradas por defecto y el frontend inyecta automáticamente el token de autorización en cada petición.

*   **Usuario:** `user`
*   **Contraseña:** `user123`

## ✅ Buenas Prácticas Aplicadas
*   **Mapeo Bidireccional:** Uso de mappers manuales explícitos (ej. `OrderMapper`) para separar las entidades de persistencia (`@Entity`) de los modelos ricos del dominio, evitando el antipatrón de modelo anémico.
*   **Manejo Global de Errores:** Un `@RestControllerAdvice` atrapa las excepciones puras del dominio (`InsufficientStockException`, `OrderNotFoundException`) y las traduce a códigos HTTP adecuados (400, 404, etc.) para el frontend.
*   **CORS configurado:** Políticas permisivas locales para separar físicamente los puertos del cliente y servidor sin bloqueos de seguridad del navegador.