# language: es
@deuda @pago
Característica: Pago de multa y rehabilitación de lector
  Como bibliotecario
  Quiero registrar el pago de deudas de un lector
  Para rehabilitarlo y permitir nuevos préstamos

  Antecedentes:
    Dado que el bibliotecario ingresa al sistema de biblioteca
    Y que el bibliotecario navega al módulo de "deudas y pagos"

  Escenario: Pagar multa pendiente y rehabilitar al lector
    Y que el sistema tiene al lector "Serenity Lector Deuda" con deuda pendiente por multa
    Cuando el bibliotecario busca la deuda del lector "Serenity Lector Deuda"
    Y el bibliotecario registra el pago total de la deuda
    Entonces el bibliotecario ve el mensaje "Pago registrado exitosamente"
    Y el bibliotecario ve que el estado de la deuda es "pagada"
    Y el bibliotecario ve que el lector quedó "habilitado"

  Escenario: Lector rehabilitado solicita nuevo préstamo exitosamente
    Y que el sistema tiene al lector "Serenity Lector Rehabilitado" con deuda previamente pagada
    Y que el bibliotecario navega al módulo de "registro de préstamo"
    Cuando el bibliotecario diligencia el formulario de préstamo con:
      | campo          | valor                   |
      | idLibro        | B-TST-AVAILABLE         |
      | tituloLibro    | Serenity Libro Disponible |
      | tipoDocumento  | DNI                     |
      | idLector       | R-TST-PAID              |
      | nombreLector   | Serenity Lector Rehabilitado |
      | diasPrestamo   | 7                       |
    Y el bibliotecario confirma el registro del préstamo
    Entonces el bibliotecario ve el mensaje "Préstamo registrado exitosamente"