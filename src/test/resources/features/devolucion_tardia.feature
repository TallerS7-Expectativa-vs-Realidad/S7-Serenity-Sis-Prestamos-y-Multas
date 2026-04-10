# language: es
@devolucion @multa
Característica: Registro de devolución tardía con multa
  Como bibliotecario
  Quiero registrar devoluciones fuera del plazo
  Para visualizar la multa generada al lector

  Antecedentes:
    Dado que el bibliotecario ingresa al sistema de biblioteca
    Y que el bibliotecario navega al módulo de "devoluciones"

  Escenario: Registrar devolución tardía con generación automática de multa
    Y que el sistema tiene un préstamo vencido hace "8" días del libro "Serenity Retorno Tardio" para el lector "Serenity Lector Mora"
    Cuando el bibliotecario registra la devolución del libro "Serenity Retorno Tardio"
    Entonces el bibliotecario ve el mensaje "Devolución registrada con multa"
    Y el bibliotecario visualiza el valor de la multa generada
    Y el bibliotecario ve la deuda pendiente del lector