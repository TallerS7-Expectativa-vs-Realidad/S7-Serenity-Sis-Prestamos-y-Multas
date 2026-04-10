# language: es
@prestamo
Característica: Registro de préstamo de libros
  Como bibliotecario
  Quiero registrar un préstamo válido
  Para asignar la fecha límite de devolución al lector

  Antecedentes:
    Dado que el bibliotecario ingresa al sistema de biblioteca
    Y que el bibliotecario navega al módulo de "registro de préstamo"

  Esquema del escenario: Registrar préstamo válido con plazo de <diasPrestamo> días
    Y que el sistema tiene el libro "<tituloLibro>" identificado con "<idLibro>" en estado "disponible"
    Y que el sistema tiene al lector "<nombreLector>" con "<tipoDocumento>" "<idLector>" sin deuda pendiente
    Cuando el bibliotecario diligencia el formulario de préstamo con:
      | campo          | valor           |
      | idLibro        | <idLibro>       |
      | tituloLibro    | <tituloLibro>   |
      | tipoDocumento  | <tipoDocumento> |
      | idLector       | <idLector>      |
      | nombreLector   | <nombreLector>  |
      | diasPrestamo   | <diasPrestamo>  |
    Y el bibliotecario confirma el registro del préstamo
    Entonces el bibliotecario ve el mensaje "Préstamo registrado exitosamente"
    Y el bibliotecario ve la fecha límite del préstamo

    Ejemplos:
      | idLibro       | tituloLibro                  | tipoDocumento                         | idLector      | nombreLector               | diasPrestamo |
      | B-TST-LOAN7   | Serenity Prestamo Siete      | Cédula de Identidad                   | R-TST-LOAN7   | Serenity Lector Siete      | 7            |
      | B-TST-LOAN14  | Serenity Prestamo Catorce    | Documento Nacional de Identificación  | R-TST-LOAN14  | Serenity Lector Catorce    | 14           |
      | B-TST-LOAN21  | Serenity Prestamo Veintiuno  | Cédula de Identidad                   | R-TST-LOAN21  | Serenity Lector Veintiuno  | 21           |