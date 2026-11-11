# Consultas JPQL con JPA / Hibernate — Modelo de facturación

Práctica de **JPA con Hibernate** sobre un dominio de facturación y artículos. El foco está en escribir consultas **JPQL**: agregaciones, joins, filtros dinámicos y consultas tipadas.

> **Origen del proyecto:** las entidades, la configuración de Gradle/Hibernate y el esqueleto de los managers provienen del proyecto base entregado en la cursada. Mi aporte son las consultas JPQL implementadas en `ClienteManager`, `FacturaManager` y `MainConsultasJPQL`.

## Stack

Java · Hibernate 5.6 (JPA) · Hibernate Envers · MySQL 8 · Gradle · Lombok · JUnit 5

## Modelo de dominio

`Cliente` → `Factura` → `FacturaDetalle` → `Articulo` (con `ArticuloInsumo` y `ArticuloManufacturado` + `ArticuloManufacturadoDetalle`), más `Categoria`, `UnidadMedida` e `Imagen`.

## Qué se practica

- Consultas con `TypedQuery` y parámetros nombrados.
- Funciones de agregación: `SUM`, `COUNT`, `MAX`.
- `JOIN` y `LEFT OUTER JOIN` entre entidades relacionadas, y `DISTINCT`.
- Consultas con resultado compuesto (por ejemplo, artículo + cantidad total vendida).
- Filtros construidos dinámicamente con `StringBuilder`.
- Borrado lógico (`fechaBaja IS NULL`) al consultar.
- Relaciones `@ManyToOne` / `@OneToMany` con `cascade` y carga `LAZY`.

## Estructura

```
src/main/java/
├── org/example/            # Entidades del dominio y Main de carga de datos
├── managers/               # Acceso a datos con JPQL (ClienteManager, FacturaManager)
│   └── MainConsultasJPQL   # Punto de entrada con las consultas de la práctica
└── funciones/              # Utilidades
src/main/resources/META-INF/persistence.xml
```

## Cómo correrlo

1. Tener MySQL corriendo en el puerto `3307` con una base llamada `factura` (el esquema se genera solo con `hbm2ddl.auto=update`).
2. Revisar usuario y contraseña en `src/main/resources/META-INF/persistence.xml`.
3. Abrir el proyecto en IntelliJ IDEA (Gradle) y ejecutar:
   - `org.example.Main` para cargar datos de ejemplo.
   - `managers.MainConsultasJPQL` para correr las consultas.
