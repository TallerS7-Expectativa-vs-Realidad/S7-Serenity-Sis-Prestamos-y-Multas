# S7-Serenity-Sis-Prestamos-y-Multas

Automatización E2E con Serenity BDD para el sistema de préstamos y multas de biblioteca.

Este repositorio valida recorridos funcionales reales desde la interfaz web, usando Cucumber, JUnit Platform Suite y el patrón Screenplay sobre el frontend local en `http://localhost:8080` y el backend local en `http://localhost:3000`.

Repositorios correlacionados:

- Frontend: https://github.com/TallerS7-Expectativa-vs-Realidad/S7-Frontend-Sis-Prestamos-y-Multas
- Backend: https://github.com/TallerS7-Expectativa-vs-Realidad/S7-Backend-Sis-Prestamos-y-Multas
- Karate: https://github.com/TallerS7-Expectativa-vs-Realidad/S7-Karate-Sis-Prestamos-y-Multas
- Arquitectura: https://github.com/TallerS7-Expectativa-vs-Realidad/S7-Arquitectura
- Tests de uso: https://github.com/TallerS7-Expectativa-vs-Realidad/S7-Tests-de-uso

---

## Descripción

La suite cubre los flujos principales de préstamos, devoluciones, consulta de disponibilidad, consulta de vencidos y pago de multas desde navegador.

El glue de Cucumber vive en `org.alexrieger.stepdefinitions` y orquesta `Task` y `Question` existentes sin meter lógica de negocio en los step definitions. El actor principal es `Bibliotecario`, obtenido con `OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO)`.

---

## Cobertura funcional automatizada

1. Consulta de disponibilidad de libros.
2. Registro de préstamo válido.
3. Rechazo de préstamo por deuda pendiente.
4. Devolución en plazo sin multa.
5. Devolución tardía con multa.
6. Consulta de préstamos vencidos.
7. Pago de multa y rehabilitación del lector.

---

## Stack técnico

- Java 17
- Gradle Wrapper
- Serenity BDD 5.3.3
- Cucumber 7.34.2
- JUnit Platform Suite 1.11.4
- Screenplay Pattern
- Selenium WebDriver con Google Chrome

---

## Prerrequisitos

Antes de ejecutar la suite:

1. Tener Java 17 o superior disponible en `PATH`.
2. Tener Google Chrome instalado.
3. Tener el SUT realmente levantado en estas URLs:
   - Frontend: `http://localhost:8080`
   - Backend: `http://localhost:3000`
4. Si vas a usar el stack completo del taller, levantar primero la arquitectura base con Docker Compose desde el repositorio correspondiente.
5. Ejecutar los comandos desde la raíz del repositorio.

La suite valida disponibilidad del frontend y del backend al inicio de cada escenario y falla temprano con un mensaje explícito cuando el entorno no está arriba.

---

## Configuración

La configuración relevante del proyecto está distribuida así:

- `build.gradle`: dependencias, versiones y configuración de reportes Serenity.
- `src/test/resources/serenity.conf`: configuración base de Serenity.
- `src/test/java/org/alexrieger/util/TestData.java`: constantes funcionales usadas por actores y escenarios.
- `src/test/java/org/alexrieger/hooks/OpenBrowser.java`: navegación inicial y apertura del navegador.
- `src/test/java/org/alexrieger/stepdefinitions/hooks/Hook.java`: preparación del actor y reseteo repetible del dataset para escenarios etiquetados.

Reset repetible actual:

- Se ejecuta automáticamente `sql/reset_serenity_repeatable_state.sql` antes de escenarios etiquetados con `@consulta`, `@vencidos`, `@devolucion`, `@deuda` y `@prestamo`.
- Eso permite relanzar la suite sin depender de reseteos manuales entre features.

---

## Ejecución

### Windows

```powershell
gradlew.bat clean test aggregate
```

### Linux o macOS

```bash
./gradlew clean test aggregate
```

### Validaciones útiles

Compilar únicamente las fuentes de prueba:

```powershell
gradlew.bat testClasses --no-daemon
```

Validar conectividad básica del entorno antes de correr UI:

```powershell
Test-NetConnection localhost -Port 8080
Test-NetConnection localhost -Port 3000
curl.exe -I http://localhost:8080
```

---

## Resultado esperado

Una ejecución correcta debe:

- finalizar con código de salida `0`
- dejar los escenarios en verde
- generar el reporte consolidado de Serenity en `target/site/serenity/`

Ubicación principal:

- `target/site/serenity/index.html`

---

## Reportes

El proyecto genera reportes de Serenity y artefactos de prueba en:

- `target/site/serenity/`
- `build/reports/tests/test/`
- `build/test-results/test/`

---

## Estructura relevante

```text
S7-Serenity-Sis-Prestamos-y-Multas/
├── build.gradle
├── gradlew
├── gradlew.bat
├── sql/
│   └── reset_serenity_repeatable_state.sql
├── src/
│   ├── main/
│   └── test/
│       ├── java/
│       │   └── org/alexrieger/
│       │       ├── hooks/
│       │       ├── questions/
│       │       ├── runners/
│       │       ├── stepdefinitions/
│       │       ├── tasks/
│       │       ├── ui/
│       │       └── util/
│       └── resources/
│           ├── features/
│           ├── junit-platform.properties
│           ├── logback-test.xml
│           └── serenity.conf
└── target/
	└── site/
		└── serenity/
```

---

## Convenciones importantes del proyecto

- Features en español Gherkin.
- Glue del dominio concentrado en `org.alexrieger.stepdefinitions`.
- Step definitions delgados: la lógica funcional vive en `Task`, `Question` y clases `ui/`.
- El runner principal es JUnit Platform Suite con engine Cucumber.
- Los selectores priorizan `data-testid` y mantienen selectores fallback para tolerar variaciones del frontend.

---

## Consideraciones del proyecto

- La suite depende de datos semilla precargados y de un dataset controlado por el SUT.
- Los pasos de precondición documentan estado esperado; no crean ni limpian datos por API.
- En este ambiente compartido, el flujo de vencidos depende de un estado razonablemente estable del dataset; la validación prioriza el total observable en UI.
- Si el frontend o el backend no están disponibles, Serenity aborta temprano con una excepción legible en lugar de propagar errores tardíos de Selenium.
- Los registros dedicados `B-TST-*`, `R-TST-*`, `90000*` y `99000*` deben reservarse para Serenity si se quiere mantener repetibilidad del entorno.

---

## Documentación relacionada

- `README.md`
- `S9_Plan.md`
- `Prompt_Orchestrator_Serenity.md`

---

## Equipo

- Organización: TallerS7-Expectativa-vs-Realidad
- Mantenimiento actual del repositorio: `AlexRieger47`
