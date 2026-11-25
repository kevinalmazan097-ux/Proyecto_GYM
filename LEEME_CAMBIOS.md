# GYM KEVIN — Cambios aplicados

## Base de datos

Se conserva el esquema de las dos tablas que ya existían en el proyecto:

- `socios`: `id`, `nombre`, `telefono`, `correo`, `fechaRegistro`, `estado`.
- `usuarios`: `id`, `correo`, `passwordHash`, `salt`.

No se agregaron tablas de membresías, pagos, asistencias ni rutinas.

La base Room mantiene `version = 2` y el nombre de archivo `gym_kevin_database` para conservar los datos locales existentes. Las entidades conservan los nombres de tabla y columnas que ya utilizaba el proyecto.

## Organización adaptada al manual

- `data/database`: `AppDatabase`, entidades y DAO de Room.
- `data/di`: módulos de Hilt para Room y repositorios.
- `data/mapper`: conversión entre entidades Room y modelos de dominio.
- `data/repository`: implementaciones de repositorios.
- `domain/model`: modelos de Socio y Usuario.
- `domain/repository`: contratos de repositorios.
- `ui`: ViewModels que exponen estado con StateFlow y operaciones UDF.
- `ui/theme`: `Color.kt`, `Theme.kt` y `Type.kt`.

Se configuró Hilt en `GymKevinApp` y `MainActivity`, tal como indica el manual.

## Textos y tema

Los textos fijos visibles de las pantallas se centralizaron en `app/src/main/res/values/strings.xml` y se consumen mediante `stringResource(R.string...)`.

Las pantallas usan `MaterialTheme.colorScheme`; el tema negro/rojo se aplica desde `ui/theme/Theme.kt` y sus colores proceden de `ui/theme/Color.kt`.

## Nota de compilación

La revisión estática de XML, TOML y referencias de recursos se realizó. No fue posible ejecutar `assembleDebug` en este entorno porque Gradle necesita descargar su distribución y el entorno no tuvo conexión con `services.gradle.org`. Al abrir el proyecto en Android Studio, sincroniza Gradle y ejecuta Build > Make Project.
