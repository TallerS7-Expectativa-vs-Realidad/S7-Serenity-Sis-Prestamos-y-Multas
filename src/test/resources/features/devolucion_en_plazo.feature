# language: es
@devolucion
Característica: Registro de devolución dentro del plazo
  Como bibliotecario
  Quiero registrar la devolución de un libro en fecha
  Para cerrar el préstamo sin generar multa

  Antecedentes:
    Dado que el bibliotecario ingresa al sistema de biblioteca
    Y que el bibliotecario navega al módulo de "devoluciones"

  Escenario: Registrar devolución dentro del plazo sin multa
    Y que el sistema tiene un préstamo activo del libro "Serenity Retorno En Plazo" para el lector "Serenity Lector Plazo" con fecha límite vigente
    Cuando el bibliotecario registra la devolución del libro "Serenity Retorno En Plazo"
    Entonces el bibliotecario ve el mensaje "Devolución registrada exitosamente"
    Y el bibliotecario ve que no se generó multa