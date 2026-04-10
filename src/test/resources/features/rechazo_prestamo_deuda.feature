# language: es
@prestamo @rechazo
Característica: Rechazo de préstamo por deuda pendiente
  Como bibliotecario
  Quiero validar deuda del lector antes de prestar un libro
  Para evitar préstamos a lectores no habilitados

  Antecedentes:
    Dado que el bibliotecario ingresa al sistema de biblioteca
    Y que el bibliotecario navega al módulo de "registro de préstamo"

  Escenario: Rechazar préstamo para lector con deuda pendiente
    Y que el sistema tiene el libro "Serenity Libro Deuda" identificado con "B-TST-DEBT" en estado "disponible"
    Y que el sistema tiene al lector "Serenity Lector Deuda" con "Cédula de Identidad" "R-TST-DEBT" y deuda pendiente
    Cuando el bibliotecario diligencia el formulario de préstamo con:
      | campo          | valor                   |
      | idLibro        | B-TST-DEBT              |
      | tituloLibro    | Serenity Libro Deuda    |
      | tipoDocumento  | Cédula de Identidad     |
      | idLector       | R-TST-DEBT              |
      | nombreLector   | Serenity Lector Deuda   |
      | diasPrestamo   | 14                      |
    Y el bibliotecario confirma el registro del préstamo
    Entonces el bibliotecario ve el mensaje "Préstamo rechazado por deuda pendiente del lector"

  Escenario: Rechazar préstamo cuando el libro ya está prestado
    Y que el sistema tiene el libro "El principito" identificado con "LIB-101" en estado "prestado"
    Y que el sistema tiene al lector "Ana Torres" con "Cédula de ciudadanía" "1002003001" sin deuda pendiente
    Cuando el bibliotecario diligencia el formulario de préstamo con:
      | campo          | valor                |
      | idLibro        | LIB-101              |
      | tituloLibro    | El principito        |
      | tipoDocumento  | Cédula de ciudadanía |
      | idLector       | 1002003001           |
      | nombreLector   | Ana Torres           |
      | diasPrestamo   | 7                    |
    Y el bibliotecario confirma el registro del préstamo
    Entonces el bibliotecario ve el mensaje "El libro no está disponible para préstamo"