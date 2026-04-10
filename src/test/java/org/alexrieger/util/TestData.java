package org.alexrieger.util;

import java.time.LocalDate;

public final class TestData {
    private TestData() {}

    // Environment
    public static final String FRONTEND_BASE_URL = "http://localhost:8080";
    public static final String BACKEND_BASE_URL = "http://localhost:3000";
    public static final String ACTOR_BIBLIOTECARIO = "Bibliotecario";

    // Document types
    public static final String TYPE_ID_CC = "CI";
    public static final String TYPE_ID_DNI = "DNI";

    // Loan days
    public static final int LOAN_DAYS_7 = 7;
    public static final int LOAN_DAYS_14 = 14;
    public static final int LOAN_DAYS_21 = 21;

    // Book states
    public static final String STATE_ON_LOAN = "ON_LOAN";
    public static final String STATE_RETURNED = "RETURNED";

    // Debt states
    public static final String DEBT_PENDING = "PENDING";
    public static final String DEBT_PAID = "PAID";

    // Test data - Available book
    public static final String BOOK_NAME_AVAILABLE = "Introducción a bases de datos";
    public static final String BOOK_ID_AVAILABLE = "LIB-202";
    public static final String BOOK_TITLE_AVAILABLE = "Introducción a bases de datos";

    // Test data - Book on loan
    public static final String BOOK_NAME_ON_LOAN = "El principito";

    // Test data - Book not found
    public static final String BOOK_NAME_NOT_FOUND = "LibroInexistente999";

    // Test data - Clean reader (no debts)
    public static final String READER_TYPE_ID_CLEAN = "CI";
    public static final String READER_ID_CLEAN = "12345678";
    public static final String READER_NAME_CLEAN = "Ana Perez";

    // Test data - Reader with pending debt
    public static final String READER_TYPE_ID_DEBT = "CI";
    public static final String READER_ID_WITH_DEBT = "87654321";
    public static final String READER_NAME_WITH_DEBT = "Carlos Morales";

    // Repeatable Serenity seed data - Pending debt reader
    public static final String LIVE_READER_NAME_PENDING_DEBT = "Serenity Lector Deuda";
    public static final String LIVE_READER_ID_PENDING_DEBT = "R-TST-DEBT";
    public static final String LIVE_READER_TYPE_PENDING_DEBT = "CI";
    public static final String LIVE_BOOK_ID_PENDING_DEBT = "B-TST-DEBT";
    public static final String LIVE_BOOK_TITLE_PENDING_DEBT = "Serenity Libro Deuda";

    // Repeatable Serenity seed data - Reader with paid debt and no pending debt
    public static final String LIVE_READER_NAME_REHABILITATED = "Serenity Lector Rehabilitado";
    public static final String LIVE_READER_ID_REHABILITATED = "R-TST-PAID";
    public static final String LIVE_READER_TYPE_REHABILITATED = "DNI";
    public static final String LIVE_BOOK_ID_AVAILABLE_FOR_LOAN = "B-TST-AVAILABLE";
    public static final String LIVE_BOOK_TITLE_AVAILABLE_FOR_LOAN = "Serenity Libro Disponible";

    // Repeatable Serenity seed data - Return on time
    public static final String LIVE_RETURN_ON_TIME_BOOK_ID = "B-TST-ONTIME";
    public static final String LIVE_RETURN_ON_TIME_BOOK_TITLE = "Serenity Retorno En Plazo";
    public static final String LIVE_RETURN_ON_TIME_READER_ID = "R-TST-ONTIME";
    public static final String LIVE_RETURN_ON_TIME_READER_NAME = "Serenity Lector Plazo";
    public static final String LIVE_RETURN_ON_TIME_READER_TYPE = "CI";

    // Repeatable Serenity seed data - Overdue return
    public static final String LIVE_RETURN_LATE_BOOK_ID = "B-TST-LATE";
    public static final String LIVE_RETURN_LATE_BOOK_TITLE = "Serenity Retorno Tardio";
    public static final String LIVE_RETURN_LATE_READER_ID = "R-TST-LATE";
    public static final String LIVE_RETURN_LATE_READER_NAME = "Serenity Lector Mora";
    public static final String LIVE_RETURN_LATE_READER_TYPE = "CI";

    // The return UI caps the date input at the current day.
    // The seeded loan state determines whether today's return is on time or overdue.
    public static final String RETURN_DATE_ON_TIME = LocalDate.now().toString();
    public static final String RETURN_DATE_LATE_8_DAYS = LocalDate.now().toString();

    // Expected UI messages (ASSUMPTIONS - adjust when frontend messages are confirmed)
    public static final String MSG_BOOK_AVAILABLE = "disponible";
    public static final String MSG_BOOK_ON_LOAN = "prestado";
    public static final String MSG_BOOK_NOT_AVAILABLE = "El libro no está disponible para préstamo";
    public static final String MSG_BOOK_NOT_FOUND = "No se encontraron resultados";
    public static final String MSG_LOAN_SUCCESS = "Préstamo registrado exitosamente";
    public static final String MSG_LOAN_REJECTED_DEBT = "deuda pendiente";
    public static final String MSG_RETURN_SUCCESS = "Devolución registrada exitosamente";
    public static final String MSG_RETURN_NO_FINE = "sin multa";
    public static final String MSG_RETURN_WITH_FINE = "multa";
    public static final String MSG_DEBT_PAID = "Deuda pagada exitosamente";
    public static final String MSG_NO_OVERDUE = "No hay préstamos vencidos";
    public static final String MSG_READER_REHABILITATED = "habilitado";

    // Timeouts
    public static final int DEFAULT_WAIT_SECONDS = 10;
}
