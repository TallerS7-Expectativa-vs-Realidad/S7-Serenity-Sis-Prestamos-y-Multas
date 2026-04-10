# Plan de Implementación QA Semana 9

## Objetivo

Definir una incorporación ordenada de Karate y Serenity para el sistema de préstamos y multas de biblioteca, evitando duplicidad de cobertura y dejando dos repositorios separados, entendibles y listos para conectarse luego a GitHub.

La regla de oro para este plan es simple: Karate debe probar contratos y comportamiento HTTP del backend sobre un ambiente corriendo; Serenity debe probar recorridos funcionales reales desde la UI en navegador. Si ambos hacen lo mismo, estamos desperdiciando tiempo y fabricando mantenimiento innecesario.

## Fuentes analizadas

- Carpeta local conectada con arquitectura: `C:\Users\Equipo\Documents\PROGRAMMING\Sofka Taller 0\Semana 7\S7-Sistema-de-Prestamos-y-Multas`
- Documentación del taller en Semana 9: `Docs.md`, `Review_QA.md`, `Karate_Docs.md`, `Guia_Montaje_Karate_IntelliJ.md`
- Organización GitHub: `TallerS7-Expectativa-vs-Realidad`
- Repositorios revisados:
	- `S7-Arquitectura`
	- `S7-Backend-Sis-Prestamos-y-Multas`
	- `S7-Frontend-Sis-Prestamos-y-Multas`
	- `S7-Monorepo-Sis-Prestamos-y-Multas`
	- `S7-Tests-de-uso`

## Hallazgos técnicos relevantes

### Estado actual del sistema

- La capa documental y de arquitectura está bien trabajada: PRD, historias, subtareas, plan de pruebas y criterios de aceptación están razonablemente alineados.
- El backend sí tiene implementación real y no solo documentación.
- El frontend separado no expone con la misma claridad su código fuente en el repo consultado; por eso, para Serenity conviene tomar como fuente primaria los flujos definidos en `SUBTASKS.md` y validar luego contra la app levantada en `http://localhost:8080`.
- La stack operativa actual es:
	- Frontend: React
	- Backend: Node.js + Express
	- Base de datos: PostgreSQL
	- Orquestación: Docker Compose

### Ambientes y puertos

- Frontend: `http://localhost:8080`
- Backend: `http://localhost:3000`
- Health check legado: `GET /health`
- Health checks adicionales: `GET /health/live`, `GET /health/ready`
- PostgreSQL: `localhost:5432`

### Contratos detectados en backend

Rutas reales observadas en backend:

- `GET /api/v1/loans/:name`
- `POST /api/v1/loans`
- `PATCH /api/v1/loans`
- `GET /api/v1/loans/outTime`
- `GET /api/v1/readers/debt?id=...&typeId=...&name=...`
- `GET /api/v1/debts/:id_reader`
- `PATCH /api/v1/debts/:id_debt`

### Cobertura ya existente

El backend ya tiene pruebas unitarias y de integración con Jest y Supertest para:

- HU-01: búsqueda de disponibilidad
- HU-02: creación de préstamo
- HU-03: devolución en plazo
- HU-04: devolución tardía y multa Fibonacci
- HU-05: consulta de vencidos
- HU-06: pago de deuda

Esto cambia la conversación: Karate no debe entrar a repetir exactamente la misma lógica con mocks. Su valor está en validar el sistema en ejecución real, con HTTP, configuración, serialización JSON, headers, status codes y datos verdaderos.

### Inconsistencias que hay que resolver antes de automatizar

1. HU-05 no está alineada entre documentación y backend.
	 - En subtareas aparece `GET /api/v1/loans/overdue`.
	 - En backend implementado aparece `GET /api/v1/loans/outTime`.
	 - Antes de escribir automatización se debe congelar un contrato canónico.

2. El dominio `type_id_reader` no está totalmente consistente.
	 - En documentos aparecen `CEDULA` y `DNI`.
	 - En pruebas se observan `CI`, `DNI` y hasta referencias aisladas inconsistentes.
	 - Esto debe normalizarse antes de convertirlo en datos de prueba reutilizables.

3. Existen alias singulares y plurales en backend.
	 - `loan` y `loans`
	 - `debt` y `debts`
	 - Para automatización conviene definir una sola convención pública y dejar los aliases solo como compatibilidad si deciden mantenerlos.

## Repositorios propuestos

### Nombres recomendados

- `S9-Karate-Sis-Prestamos-y-Multas`
- `S9-Serenity-Sis-Prestamos-y-Multas`

### Razonamiento

- Mantienen la convención del org: prefijo de semana + herramienta + nombre corto del sistema.
- Distinguen el esfuerzo actual de Semana 9 sin perder relación con el sistema de Semana 7.
- Evitan colisionar con los repos existentes del org.

### Carpetas creadas en el root de Semana 9

- `C:\Users\Equipo\Documents\PROGRAMMING\Sofka Taller 0\Semana 9\S9-Karate-Sis-Prestamos-y-Multas`
- `C:\Users\Equipo\Documents\PROGRAMMING\Sofka Taller 0\Semana 9\S9-Serenity-Sis-Prestamos-y-Multas`

Si luego prefieren continuidad nominal con `S7-...`, se renombran antes de `git init` y listo. No hay drama técnico ahí.

## Separación de responsabilidades

| Eje | Karate | Serenity |
| --- | --- | --- |
| Capa principal | API / contrato / integración HTTP | UI / flujo de usuario / navegador |
| Qué valida | Status codes, payloads, reglas de negocio expuestas por API, contratos JSON, smoke técnico, regresión de endpoints | Formularios, mensajes visibles, navegación, estados mostrados al usuario, integración FE-BE desde la pantalla |
| Qué no debería hacer | Validar CSS, layout, navegación visual, interacciones de navegador | Probar exhaustivamente códigos HTTP, schemas JSON internos, bordes matemáticos completos de Fibonacci |
| Grado de datos | Data-driven y parametrizado | Casos más representativos y críticos del usuario |
| Velocidad esperada | Alta | Media o más lenta |
| Frecuencia ideal | En cada cambio de API y como smoke/regresión | En flujos clave, nightly o antes de release |

## Alcance recomendado para Karate

### Objetivo

Tener una suite de pruebas API que ejecute contra el backend levantado localmente, preferiblemente con base de datos inicializada y dataset conocido.

### Qué debe cubrir

#### Smoke técnico

- `GET /health`
- `GET /health/live`
- `GET /health/ready`
- Validación de disponibilidad del backend antes de ejecutar el resto de features

#### HU-01 - Disponibilidad de libro

- `GET /api/v1/loans/:name`
- Casos:
	- libro con historial y estado `RETURNED`
	- libro con historial y estado `ON_LOAN`
	- libro sin historial
	- nombre inválido o vacío

#### HU-02 - Registrar préstamo

- `POST /api/v1/loans`
- Casos:
	- préstamo válido con `loan_days=7`
	- variante con `14`
	- variante con `21`
	- libro no disponible
	- lector con deuda pendiente
	- `loan_days` inválido
	- payload inválido

#### HU-03 - Devolución dentro del plazo

- `PATCH /api/v1/loans`
- Casos:
	- devolución anticipada
	- devolución exacta en `date_limit`
	- préstamo no encontrado
	- préstamo ya devuelto
	- payload inválido

#### HU-04 - Devolución tardía con multa Fibonacci

- `PATCH /api/v1/loans`
- Casos mínimos obligatorios:
	- 1 día de mora
	- 7 días de mora
	- 8 días de mora
	- 15 días de mora
	- 22 días de mora
- Validar:
	- `days_late`
	- creación de deuda
	- `units_fib`
	- `amount_debt`
	- comportamiento con `base_fib_amount` por defecto

#### HU-05 - Consulta de préstamos vencidos

- Ruta a confirmar entre `outTime` y `overdue`
- Casos:
	- existen préstamos vencidos
	- no existen vencidos
	- la respuesta excluye préstamos vigentes o ya devueltos

#### HU-06 - Deuda y pago

- `GET /api/v1/readers/debt`
- `GET /api/v1/debts/:id_reader`
- `PATCH /api/v1/debts/:id_debt`
- Casos:
	- deuda existente
	- lector sin deuda
	- pago total exitoso
	- deuda inexistente
	- deuda ya pagada
	- payload inválido para pago

### Qué NO debe cubrir Karate

- Validación de placeholders o textos de formularios.
- Visualización de tablas o mensajes en pantalla.
- Comportamientos de navegación del frontend.

### Stack sugerida para el repo Karate

- Java 21
- Maven
- Karate
- JUnit 5

### Estructura sugerida

```text
S9-Karate-Sis-Prestamos-y-Multas/
	pom.xml
	README.md
	.gitignore
	src/
		test/
			java/
				runners/
					LoansRunner.java
					DebtsRunner.java
					SmokeRunner.java
			resources/
				karate-config.js
				data/
					loans/
					debts/
				features/
					smoke/
						health.feature
					loans/
						search-availability.feature
						create-loan.feature
						return-loan.feature
						late-return.feature
						overdue-loans.feature
					debts/
						get-reader-debt.feature
						pay-debt.feature
```

### Convenciones recomendadas para Karate

- Tags por suite: `@smoke`, `@contracts`, `@regression`, `@fibonacci`, `@debt`
- Variables por ambiente: `baseUrl`, `dbSeedMode`, `defaultFibBaseAmount`
- Features pequeñas y especializadas; nada de un solo `.feature` gigantesco para todo el dominio

## Alcance recomendado para Serenity

El alcance funcional cambia muy poco respecto al plan original. Lo que sí cambia es la capa de framework, la organización interna del repo y la forma de obtener evidencia y reporting.

### Objetivo

Validar que el usuario bibliotecario puede operar el sistema extremo a extremo desde la interfaz, con formularios, feedback visible y consumo real del backend.

### Qué debe cubrir

#### Flujo 1 - Consulta de disponibilidad

- Ingresar nombre de libro
- Ejecutar búsqueda
- Ver resultado de disponible, prestado o sin historial

#### Flujo 2 - Registrar préstamo válido

- Completar formulario
- Seleccionar plazo
- Confirmar préstamo
- Ver mensaje exitoso
- Ver fecha límite mostrada en UI

#### Flujo 3 - Rechazo por deuda pendiente

- Intentar préstamo con lector bloqueado
- Ver mensaje funcional correspondiente

#### Flujo 4 - Devolución en plazo

- Buscar préstamo por datos disponibles en la UI
- Confirmar devolución
- Ver confirmación sin multa

#### Flujo 5 - Devolución tardía representativa

- Ejecutar un caso representativo de mora, preferiblemente 8 días
- Ver que la UI informa deuda o penalidad

Nota: aquí no hace falta que Serenity recorra todos los cortes de Fibonacci. Un caso representativo basta. Los bordes matemáticos completos deben vivir en Karate.

#### Flujo 6 - Consulta de préstamos vencidos

- Abrir pantalla o tabla de atrasos
- Ver filas renderizadas correctamente
- Validar estado vacío cuando no hay resultados

#### Flujo 7 - Pago de multa y rehabilitación

- Buscar deuda del lector
- Ejecutar pago total
- Ver cambio visible de estado
- Intentar luego un nuevo préstamo con el mismo lector y comprobar que ahora sí queda habilitado

### Qué NO debe cubrir Serenity

- Verificación exhaustiva de todos los códigos HTTP.
- Revalidación completa de schemas JSON.
- Repetición de todas las combinatorias negativas del backend.
- Todos los cortes Fibonacci; solo uno o dos casos visibles bastan.

### Stack sugerida para el repo Serenity

- Java 21
- Maven
- Serenity BDD
- Selenium WebDriver
- JUnit 5
- WebDriverManager

Se recomienda Java también aquí para compartir JDK, Maven y parte del setup del equipo. La diferencia práctica es que Serenity ya resuelve mucho mejor el reporting, screenshots y trazabilidad de ejecución que una suite Selenium pura.

### Estructura sugerida

```text
S9-Serenity-Sis-Prestamos-y-Multas/
	pom.xml
	README.md
	serenity.conf
	.gitignore
	src/
		test/
			java/
				runners/
					SearchBookTest.java
					CreateLoanTest.java
					ReturnLoanTest.java
					OverdueLoansTest.java
					PayDebtTest.java
				pages/
					SearchBookPage.java
					CreateLoanPage.java
					ReturnLoanPage.java
					OverdueLoansPage.java
					ReaderDebtPage.java
				steps/
				utils/
					TestData.java
			resources/
				features/
```

### Patrón recomendado para Serenity

- Serenity BDD con Screenplay si quieren una base más escalable
- Page Object Model si priorizan arrancar rápido con un alcance todavía acotado
- Selectores estables mediante `data-testid` o atributos dedicados si pueden agregarlos en frontend
- Nada de selectores frágiles por texto o por jerarquías ridículas de CSS si se puede evitar

## Requisitos transversales antes de automatizar

### 1. Congelar contrato público

Antes de escribir una sola prueba automática, deben acordar:

- ruta definitiva de HU-05
- valores válidos de `type_id_reader`
- payloads canónicos de préstamo, devolución y pago
- forma oficial de consultar deuda: `readers/debt` o `debts/:id_reader`

### 2. Preparar dataset estable

Se necesita una semilla QA con datos fijos para no perseguir fantasmas:

- libro disponible
- libro prestado
- préstamo vigente dentro del plazo
- préstamo vencido
- lector habilitado
- lector con deuda pendiente
- deuda pagada

Idealmente esto se resuelve con:

- script SQL de seed
- o endpoint/reset exclusivo para QA local
- o un paso de setup por suite si quieren mantenerse puristas

### 3. Asegurar observabilidad mínima

Conviene que la UI tenga:

- ids o `data-testid` en inputs, botones y tablas
- mensajes visibles consistentes
- estados de carga y error distinguibles

### 4. Definir estrategia de ejecución

- Karate: contra backend en `localhost:3000`
- Serenity: contra frontend en `localhost:8080`
- Ambas suites: con Docker Compose levantado y base inicializada

## Roadmap de implementación

## Fase 0 - Alineación contractual

Objetivo: corregir ambigüedades antes de automatizar.

- Cerrar discrepancia `outTime` vs `overdue`
- Normalizar `type_id_reader`
- Decidir endpoint oficial de deuda para consulta
- Congelar mensajes funcionales críticos que usarán las pruebas

## Fase 1 - Bootstrap de repositorios

Objetivo: dejar ambos proyectos ejecutables aunque aún no tengan cobertura completa.

- Crear estructura base
- Definir `README`
- Configurar `pom.xml`
- Configurar ejecución local
- Preparar `.gitignore`

## Fase 2 - Karate primero

Objetivo: asegurar el backend con una regresión API confiable.

- Implementar smoke técnico
- Implementar HU-01, HU-02 y HU-03
- Implementar HU-04 con bordes Fibonacci
- Implementar HU-05 y HU-06
- Clasificar con tags para smoke y regression

Razón del orden: si la API no está estable, Serenity se vuelve una máquina de producir falsos negativos.

## Fase 3 - Serenity después

Objetivo: cubrir recorridos reales del bibliotecario.

- Flujo de consulta
- Flujo de préstamo
- Flujo de devolución normal
- Flujo de atraso visible
- Flujo de consulta de vencidos
- Flujo de pago y rehabilitación

## Fase 4 - CI y evidencia

Objetivo: dejar las suites defendibles frente al taller y reutilizables después.

- Reportes de ejecución
- Reportes y capturas automáticas de Serenity
- Ejecución por comandos simples
- Pipeline mínima de verificación

## Backlog inicial sugerido

### Repo Karate

- Crear proyecto Maven con Karate y JUnit 5
- Agregar `karate-config.js`
- Implementar feature de health checks
- Implementar feature de HU-01
- Implementar feature de HU-02
- Implementar feature de HU-03
- Implementar feature de HU-04 con tabla de casos 1, 7, 8, 15 y 22
- Implementar feature de HU-05
- Implementar feature de HU-06
- Documentar ejecución y prerequisitos

### Repo Serenity

- Crear proyecto Maven con Serenity BDD, Selenium WebDriver y WebDriverManager
- Configurar `serenity.conf` y base URL
- Implementar capa base de páginas o Screenplay según la estrategia elegida
- Implementar test E2E de consulta
- Implementar test E2E de préstamo válido
- Implementar test E2E de rechazo por deuda
- Implementar test E2E de devolución en plazo
- Implementar test E2E de atraso representativo
- Implementar test E2E de vencidos
- Implementar test E2E de pago y rehabilitación
- Verificar generación de reportes y screenshots

## Riesgos y mitigaciones

| Riesgo | Impacto | Mitigación |
| --- | --- | --- |
| Contratos inconsistentes entre docs y backend | Alto | Congelar contrato antes del primer script |
| Datos inestables entre ejecuciones | Alto | Seed controlado y reset del ambiente |
| Serenity con selectores frágiles | Alto | Usar `data-testid` y un patrón consistente (Screenplay o POM) |
| Duplicidad de cobertura entre Karate y Serenity | Medio | Mantener la frontera API vs UI documentada |
| UI no completamente observable | Medio | Incorporar identificadores de test en frontend |
| Falsos negativos por ambiente no listo | Medio | Health checks y pre-flight antes de correr suites |

## Definition of Done para esta iniciativa

Se considerará bien implementada la estrategia cuando:

- Existan dos repositorios separados, uno para Karate y otro para Serenity.
- Karate cubra los endpoints críticos del dominio sin depender de mocks del backend.
- Serenity cubra los flujos críticos del bibliotecario desde navegador.
- No exista duplicación absurda entre ambas suites.
- El equipo pueda ejecutar ambas suites localmente con instrucciones simples.
- Se deje evidencia clara de qué cubre cada herramienta y qué no cubre.

## Recomendación final

El orden correcto no es empezar por Serenity porque se ve más vistoso. El orden correcto es:

1. cerrar contrato
2. estabilizar dataset
3. montar Karate
4. montar Serenity

Así se prueba primero la columna vertebral del sistema y después la experiencia de uso. Hacerlo al revés es invitar al caos con buenos modales.

## Próximos pasos inmediatos

1. Confirmar si se quedan con los nombres `S9-...` o prefieren renombrarlos a `S7-...` por continuidad histórica del sistema.
2. Decidir la ruta canónica de HU-05 y el catálogo válido de `type_id_reader`.
3. Preparar una semilla QA mínima reutilizable por ambas suites.
4. Iniciar primero el repositorio de Karate y dejar smoke técnico funcionando.
5. Cuando la API quede estable, arrancar el repositorio Serenity con el patrón elegido y selectores de prueba.
