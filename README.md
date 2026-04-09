# S7-Serenity-Sis-Prestamos-y-Multas

Template base para la automatización funcional E2E del sistema de préstamos y multas de biblioteca con **Serenity BDD**, **Cucumber**, **JUnit 5** y patrón **Screenplay**.

## Requisitos previos

| Herramienta | Versión mínima |
|---|---|
| Java (JDK) | 17 |
| Gradle | 9.0 (incluido via wrapper) |
| Google Chrome | última estable |

## Estructura del proyecto

```
src/
├── main/
│   ├── java/                          # Código fuente principal (si aplica)
│   └── resources/                     # Recursos principales
└── test/
    ├── java/org/<paquete>/
    │   ├── hooks/                     # Tasks de apertura de navegador, setup
    │   ├── questions/                 # Questions del patrón Screenplay
    │   ├── runners/                   # Runners de Cucumber (JUnit 5 @Suite)
    │   ├── stepdefinitions/           # Glue de Cucumber (step definitions)
    │   │   └── hooks/                 # Hooks de Cucumber (@Before, @After)
    │   ├── tasks/                     # Tasks del patrón Screenplay
    │   ├── ui/                        # Page Objects / Target definitions
    │   └── util/                      # Utilidades y datos de prueba
    └── resources/
        ├── features/                  # Archivos .feature (Gherkin)
        ├── logback-test.xml           # Configuración de logging
        └── serenity.conf              # Configuración de Serenity y WebDriver
```

## Dependencias

Todas las versiones han sido verificadas como compatibles entre sí (resolución exitosa con Gradle 9.0).

### Dependencias directas

| Dependencia | Grupo | Versión | Alcance |
|---|---|---|---|
| serenity-core | net.serenity-bdd | 5.3.3 | testImplementation |
| serenity-cucumber | net.serenity-bdd | 5.3.3 | testImplementation |
| serenity-screenplay | net.serenity-bdd | 5.3.3 | testImplementation |
| serenity-screenplay-webdriver | net.serenity-bdd | 5.3.3 | testImplementation |
| serenity-ensure | net.serenity-bdd | 5.3.3 | testImplementation |
| serenity-rest-assured | net.serenity-bdd | 5.3.3 | testImplementation |
| serenity-screenplay-rest | net.serenity-bdd | 5.3.3 | testImplementation |
| cucumber-junit-platform-engine | io.cucumber | 7.34.2 | testImplementation |
| junit-platform-suite | org.junit.platform | 1.11.4 | testImplementation |
| junit-platform-launcher | org.junit.platform | 1.11.4 | testRuntimeOnly |
| assertj-core | org.assertj | 3.27.7 | testImplementation |
| logback-classic | ch.qos.logback | 1.5.20 | implementation |

### Plugins de Gradle (buildscript)

| Plugin | Versión |
|---|---|
| serenity-gradle-plugin | 5.3.3 |
| serenity-single-page-report | 5.3.3 |

### Notas de compatibilidad

- **Serenity BDD 5.3.3** es compatible con **Cucumber 7.34.x** y **JUnit Platform 1.11.x+**.
- Gradle 9 puede promover transitividades a versiones superiores (ej. `junit-platform-suite` 1.11.4 → 1.14.2); esto es normal y compatible.
- **Java 17** es el mínimo requerido por Serenity BDD 5.x.
- La versión más reciente de Serenity BDD disponible en Maven Central es **5.3.10** (al momento de creación de este template).

## Cómo usar este template

1. **Clonar o copiar** este repositorio.
2. **Usar el paquete actual** `org/alexrieger` en `src/test/java/` como base del proyecto.
3. **Actualizar** `build.gradle`:
   - Cambiar `group` al grupo de tu proyecto.
   - Ajustar versiones si es necesario.
4. **Actualizar** `settings.gradle`:
   - Cambiar `rootProject.name` al nombre de tu proyecto.
5. **Actualizar `TestData.java`** con la URL base del frontend, datos de prueba y mensajes esperados del sistema de biblioteca.
6. **Crear** tus archivos `.feature` en `src/test/resources/features/`.
7. **Implementar** las clases en cada carpeta según el patrón Screenplay:
   - `runners/` — Runner con `@Suite`, `@IncludeEngines("cucumber")`, `@SelectClasspathResource`.
   - `stepdefinitions/` — Step definitions con `@Dado`, `@Cuando`, `@Entonces`.
   - `tasks/` — Clases que implementan `Task`.
   - `questions/` — Clases que implementan `Question<T>`.
   - `ui/` — Clases con `Target` estáticos para localizar elementos.

## Componentes incluidos

### Hook de escenario (`stepdefinitions/hooks/Hook.java`)

Este hook se ejecuta automáticamente **antes de cada escenario** gracias a la anotación `@Before` de Cucumber. Su función es inicializar el escenario de Serenity Screenplay llamando a `OnStage.setTheStage(new OnlineCast())`, lo que crea el "escenario" donde los actores van a actuar.

```java
@Before
public void startStage() {
    OnStage.setTheStage(new OnlineCast());
}
```

> **No es necesario modificar este archivo.** Ya viene configurado y funciona para cualquier prueba que use el patrón Screenplay.

### Task de apertura de navegador (`hooks/OpenBrowser.java`)

Task reutilizable que abre una URL en el navegador. Se usa en los step definitions para navegar a la página bajo prueba:

```java
OnStage.theActorCalled("usuario").attemptsTo(
    OpenBrowser.withUrl(TestData.MI_URL)
);
```

> **No es necesario modificar este archivo.** Solo se invoca desde los step definitions pasándole la URL deseada.

### Datos de prueba (`util/TestData.java`)

Clase utilitaria que centraliza **todas las constantes y datos** que necesitan tus pruebas. Es **el primer archivo que debes actualizar** al iniciar un nuevo proyecto.

```java
public final class TestData {

    // Insertar aquí las constantes de datos de prueba, por ejemplo:
    // public static final String BASE_URL = "https://mi-app.com/";
    // public static final String ADMIN_EMAIL = "admin@example.com";
    // public static final String ADMIN_PASSWORD = "password123";

    private TestData() {
    }
}
```

**¿Qué agregar aquí?**
- **URL base** del frontend a probar.
- **Datos del lector** y del libro que se reutilicen en múltiples escenarios.
- **Mensajes funcionales** esperados para préstamo, deuda, devolución y pago.
- **Métodos utilitarios** para generar datos dinámicos (timestamps, IDs únicos, etc.).

> **Importante:** Mantener los datos centralizados en `TestData` evita hardcodear valores en los step definitions y facilita cambiarlos en un solo lugar cuando el entorno cambia.

## Ejecutar tests

```bash
./gradlew clean test aggregate
```

Los reportes de Serenity se generan en `target/site/serenity/`.

## Configuración de WebDriver

Editar `src/test/resources/serenity.conf` para cambiar el navegador, modo headless y opciones de Chrome.
# S7-Serenity-Sis-Prestamos-y-Multas
