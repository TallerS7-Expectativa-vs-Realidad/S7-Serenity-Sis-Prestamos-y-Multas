# language: es
@vencidos
Característica: Consulta de préstamos vencidos
  Como bibliotecario
  Quiero visualizar los préstamos vencidos
  Para gestionar devoluciones tardías y seguimiento de deudas

  Antecedentes:
    Dado que el bibliotecario ingresa al sistema de biblioteca
    Y que el bibliotecario navega al módulo de "préstamos vencidos"

  Escenario: Visualizar listado de préstamos vencidos con resultados
    Y que el sistema tiene préstamos vencidos registrados
    Cuando el bibliotecario consulta el listado de préstamos vencidos
    Entonces el bibliotecario visualiza la tabla de préstamos vencidos
    Y el bibliotecario ve filas con datos de libro, lector y fecha límite

  Escenario: Consultar préstamos vencidos muestra el total encontrado
    Y que el sistema tiene préstamos vencidos registrados
    Cuando el bibliotecario consulta el listado de préstamos vencidos
    Entonces el bibliotecario ve el mensaje "Total de préstamos vencidos"