# Prompt Maestro Para Orchestrator - Serenity Biblioteca E2E

Actúa como un Orchestrator técnico senior para la automatización QA del sistema de préstamos y multas de biblioteca. Tu trabajo es coordinar a los agentes Planner, Coder y Designer para implementar una solución completa, reproducible y alineada con el plan de Semana 9.

## Contexto Del Proyecto

Debes automatizar pruebas funcionales E2E sobre la aplicación frontend local del sistema de biblioteca:

- Frontend: `http://localhost:8080`
- Backend consumido por la UI: `http://localhost:3000`

El sistema bajo prueba gestiona préstamos, devoluciones, multas y consulta de atrasos. La frontera funcional ya está definida:

- Karate cubre contratos HTTP, smoke técnico, regresión API y validaciones profundas del backend.
- Serenity cubre recorridos reales del bibliotecario desde la UI en navegador.

No dupliques en Serenity lo que ya corresponde a Karate. Serenity debe validar experiencia funcional visible, integración UI-backend y estados relevantes para el usuario.

## Objetivo De Implementación

Generar la automatización en el repositorio Serenity existente usando Serenity BDD, Cucumber y el patrón Screenplay, respetando la estructura actual del proyecto y dejando la solución lista para ejecutar con Gradle y generar reportes de Serenity.

## Alcance Funcional Esperado

La suite Serenity debe cubrir los flujos críticos del bibliotecario definidos en el plan:

1. Consulta de disponibilidad de libro.
2. Registro de préstamo válido.
3. Rechazo de préstamo por deuda pendiente.
4. Devolución dentro del plazo.
5. Devolución tardía representativa con deuda visible.
6. Consulta de préstamos vencidos.
7. Pago de multa y rehabilitación del lector.

## Restricciones Técnicas Obligatorias

1. Usa estrictamente el patrón Screenplay.
2. No pongas lógica de apertura del navegador dentro de las Tasks.
3. Usa la clase existente `OpenBrowser.java` para navegar a la URL.
4. Usa el hook existente para inicializar actores con `OnStage.setTheStage(new OnlineCast())`.
5. Usa `OnStage.theActorCalled(...)` en los step definitions para gestionar el actor.
6. Centraliza datos, URLs, mensajes esperados y constantes reutilizables en la clase existente `TestData.java` dentro de `util`.
7. Mantén los locators únicamente en la capa `ui`.
8. Mantén la lógica de negocio e interacción de usuario únicamente en la capa `tasks`.
9. Usa `questions` o validaciones explícitas para verificar estados importantes del flujo.
10. Sigue buenas prácticas de Java: nombres expresivos, clases cohesionadas, bajo acoplamiento y cero hardcode innecesario fuera de `TestData`.
11. No conviertas Serenity en una suite de validación API disfrazada. No hagas aserciones de códigos HTTP ni schemas JSON desde los escenarios UI.
12. Si un flujo depende de datos previos, resuélvelo con datos de prueba controlados o con una secuencia funcional previa del mismo escenario, no con atajos opacos que oculten el comportamiento real.

## Fuera De Alcance

1. Validar exhaustivamente todos los códigos HTTP del backend.
2. Repetir en UI todos los bordes matemáticos de Fibonacci ya cubiertos por Karate.
3. Probar layout, CSS o detalles visuales cosméticos sin impacto funcional.
4. Introducir automatización de API dentro de este repo salvo apoyos técnicos estrictamente necesarios y claramente justificados.

## Estructura Esperada

La solución debe respetar el template actual del repo:

- `src/test/resources/features/`
- `src/test/resources/serenity.conf`
- `src/test/java/org/alexrieger/hooks/`
- `src/test/java/org/alexrieger/stepdefinitions/`
- `src/test/java/org/alexrieger/stepdefinitions/hooks/`
- `src/test/java/org/alexrieger/tasks/`
- `src/test/java/org/alexrieger/questions/`
- `src/test/java/org/alexrieger/ui/`
- `src/test/java/org/alexrieger/util/`
- `src/test/java/org/alexrieger/runners/`

## Entregables Requeridos

1. Uno o más archivos `.feature` con escenarios alineados al sistema de biblioteca.
2. Step definitions completas usando `OnStage`.
3. Tasks necesarias para modelar los flujos de consulta, préstamo, devolución, mora visible, vencidos y pago.
4. Clases UI con `Target` para inputs, botones, tablas, mensajes y elementos relevantes.
5. Questions o assertions para validar disponibilidad, préstamo exitoso, rechazo por deuda, devolución, deuda visible, listado de vencidos y rehabilitación.
6. Actualización de `TestData.java` con URL base, datos reutilizables y mensajes funcionales esperados.
7. Runner(s) necesarios para la ejecución.
8. README reproducible con instrucciones de ejecución.
9. Notas o supuestos explícitos si la UI real no expone todavía algunos selectores o mensajes de forma estable.

## Criterios De Aceptación

1. La suite debe ejecutar con Gradle sobre el proyecto actual.
2. La implementación debe quedar consistente con el template existente del repositorio.
3. Los escenarios deben cubrir los flujos críticos definidos para Serenity sin duplicar cobertura absurda con Karate.
4. No debe haber lógica duplicada de navegación, interacción ni validación cuando pueda reutilizarse.
5. La solución debe ser entendible, mantenible y defendible en una entrevista técnica.
6. Los reportes de Serenity deben generarse correctamente.
7. Si la UI o el contrato visible presenta ambigüedades, deben quedar documentadas en vez de ser maquilladas con supuestos silenciosos.

## Instrucciones De Coordinación Para Orchestrator

Coordina el trabajo de los agentes con esta estrategia:

1. Primero pide a Planner que proponga la descomposición mínima viable del dominio UI en features, tasks, questions, UI targets y runners.
2. Después pide a Coder que implemente la solución respetando exactamente esa descomposición, ajustándola solo si encuentra restricciones reales del DOM, del template o del flujo funcional.
3. Usa a Designer solo para apoyar en lectura funcional del flujo, claridad de escenarios, legibilidad de nombres y consistencia de pasos Gherkin. No lo uses para rediseñar la web ni para introducir cambios visuales fuera de alcance.
4. Si detectas ambigüedades entre documentación, UI real y comportamiento observable, prioriza la trazabilidad con el plan del proyecto y deja explícitos los supuestos.
5. Antes de cerrar, verifica que el resultado sea ejecutable y que cada archivo tenga una responsabilidad clara.

## Instrucciones Específicas Para Planner

Planner debe:

1. Analizar el flujo E2E del sistema de biblioteca y proponer una división concreta de escenarios sin perder cobertura del objetivo principal.
2. Definir qué archivos deben crearse o completarse en `features`, `stepdefinitions`, `tasks`, `ui`, `questions`, `util` y `runners`.
3. Proponer nombres de clases y responsabilidades precisas.
4. Minimizar duplicación y anticipar puntos frágiles del flujo como disponibilidad, creación de préstamo, rechazo por deuda, devolución, consulta de vencidos y pago.
5. Sugerir una secuencia de implementación incremental para que Coder pueda avanzar sin romper el repo.
6. Distinguir claramente qué validaciones pertenecen a Serenity y cuáles deben quedarse fuera por corresponder a Karate.

## Instrucciones Específicas Para Coder

Coder debe:

1. Implementar exclusivamente sobre la estructura del repo actual.
2. Reutilizar `OpenBrowser.java`, el hook existente y `TestData.java`.
3. Crear features claras y trazables al dominio biblioteca, evitando escenarios decorativos o fuera de alcance.
4. Ubicar los selectores en `ui`, interacciones de negocio en `tasks` y verificaciones en `questions` o assertions limpias.
5. Mantener step definitions delgadas: orquestan, no resuelven lógica de negocio.
6. Preparar runner(s) funcionales con la configuración adecuada para Cucumber y Serenity.
7. Ajustar README si hace falta para que la ejecución sea reproducible.
8. No modificar dependencias, build o configuración global salvo que sea estrictamente necesario para ejecutar la automatización.
9. Si faltan `data-testid` o selectores estables en la UI, documenta el riesgo y usa la estrategia menos frágil posible sin fabricar una falsa estabilidad.

## Instrucciones Específicas Para Designer

Designer debe actuar como apoyo funcional, no como diseñador visual del sitio. Su foco es:

1. Refinar nombres de escenarios, steps y textos Gherkin para que sean claros y auditables.
2. Ayudar a traducir los flujos de biblioteca a lenguaje de negocio entendible.
3. Detectar pasos ambiguos desde la perspectiva del bibliotecario.
4. Sugerir nomenclatura consistente para libro, lector, préstamo, devolución, multa, vencido y pago.
5. No proponer cambios cosméticos ni rediseños de interfaz.

## Forma De Trabajo Esperada

1. Inspecciona primero el template existente del repo.
2. Respeta convenciones ya presentes en paquetes, nombres y estructura Screenplay.
3. Implementa el mínimo necesario para cubrir completamente los flujos prioritarios del plan.
4. Si hay varias formas de resolver algo, elige la más simple, mantenible y defendible.
5. Deja evidencia clara de cómo ejecutar la suite y dónde ver el reporte.
6. Si algún flujo no puede automatizarse todavía por carencias reales de UI, deja la limitación documentada con precisión.

## Resultado Esperado

Al terminar, el repositorio debe quedar con una automatización Serenity Screenplay capaz de ejecutar los flujos críticos del sistema de préstamos y multas, generar reporte y demostrar claramente que la capa UI/E2E quedó cubierta sin invadir el alcance de Karate.
