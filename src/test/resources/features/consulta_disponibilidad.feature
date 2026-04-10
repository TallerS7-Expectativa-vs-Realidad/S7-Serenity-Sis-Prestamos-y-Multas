# language: es
@consulta
Característica: Consulta de disponibilidad de libros
  Como bibliotecario
  Quiero consultar el estado de disponibilidad de un libro
  Para informar al lector antes de registrar un préstamo

  Antecedentes:
    Dado que el bibliotecario ingresa al sistema de biblioteca
    Y que el bibliotecario navega al módulo de "consulta de disponibilidad"

  Escenario: Consultar disponibilidad de un libro disponible
    Y que el sistema tiene el libro "Serenity Libro Disponible" en estado "disponible"
    Cuando el bibliotecario busca el libro "Serenity Libro Disponible"
    Entonces el bibliotecario ve que el libro está "disponible"

  Escenario: Consultar disponibilidad de un libro prestado
    Y que el sistema tiene el libro "Serenity Retorno En Plazo" en estado "prestado"
    Cuando el bibliotecario busca el libro "Serenity Retorno En Plazo"
    Entonces el bibliotecario ve que el libro está "prestado"

  Escenario: Consultar disponibilidad de un libro sin historial de préstamo
    Y que el sistema no tiene registrado el libro "Arquitectura hexagonal aplicada"
    Cuando el bibliotecario busca el libro "Arquitectura hexagonal aplicada"
    Entonces el bibliotecario ve el mensaje "No se encontraron libros con ese nombre"