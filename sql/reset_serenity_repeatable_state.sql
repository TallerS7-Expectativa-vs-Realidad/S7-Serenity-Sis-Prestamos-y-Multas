-- WARNING:
-- Run this only against the local QA/test database used by Serenity.
-- This script recreates a small dedicated dataset for repeatable UI tests.

BEGIN;

DELETE FROM debt_reader
WHERE id_debt IN (990001, 990002)
    OR loan_id IN (900001, 900002, 900003, 900004, 900005, 900006, 900007);

DELETE FROM loan_books
WHERE loan_id IN (900001, 900002, 900003, 900004, 900005, 900006, 900007)
    OR id_book IN ('B-TST-ONTIME', 'B-TST-LATE', 'B-TST-DEBT', 'B-TST-AVAILABLE', 'B-TST-LOAN7', 'B-TST-LOAN14', 'B-TST-LOAN21')
    OR id_reader IN ('R-TST-ONTIME', 'R-TST-LATE', 'R-TST-DEBT', 'R-TST-PAID', 'R-TST-LOAN7', 'R-TST-LOAN14', 'R-TST-LOAN21');

INSERT INTO loan_books (
    loan_id,
    id_book,
    title,
    type_id_reader,
    id_reader,
    name_reader,
    loan_days,
    state,
    date_limit,
    date_return,
    created_at,
    updated_at
) VALUES
    (
        900001,
        'B-TST-ONTIME',
        'Serenity Retorno En Plazo',
        'CI',
        'R-TST-ONTIME',
        'Serenity Lector Plazo',
        7,
        'ON_LOAN',
        CURRENT_TIMESTAMP + INTERVAL '3 day',
        NULL,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        900002,
        'B-TST-LATE',
        'Serenity Retorno Tardio',
        'CI',
        'R-TST-LATE',
        'Serenity Lector Mora',
        7,
        'ON_LOAN',
        CURRENT_TIMESTAMP - INTERVAL '8 day',
        NULL,
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        900003,
        'B-TST-DEBT',
        'Serenity Libro Deuda',
        'CI',
        'R-TST-DEBT',
        'Serenity Lector Deuda',
        7,
        'RETURNED',
        CURRENT_TIMESTAMP - INTERVAL '10 day',
        CURRENT_TIMESTAMP - INTERVAL '1 day',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        900004,
        'B-TST-AVAILABLE',
        'Serenity Libro Disponible',
        'DNI',
        'R-TST-PAID',
        'Serenity Lector Rehabilitado',
        7,
        'RETURNED',
        CURRENT_TIMESTAMP - INTERVAL '14 day',
        CURRENT_TIMESTAMP - INTERVAL '7 day',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP
    ),
    (
        900005,
        'B-TST-LOAN7',
        'Serenity Prestamo Siete',
        'CI',
        'R-TST-LOAN7',
        'Serenity Lector Siete',
        7,
        'RETURNED',
        CURRENT_TIMESTAMP - INTERVAL '21 day',
        CURRENT_TIMESTAMP - INTERVAL '14 day',
        CURRENT_TIMESTAMP - INTERVAL '21 day',
        CURRENT_TIMESTAMP - INTERVAL '14 day'
    ),
    (
        900006,
        'B-TST-LOAN14',
        'Serenity Prestamo Catorce',
        'DNI',
        'R-TST-LOAN14',
        'Serenity Lector Catorce',
        14,
        'RETURNED',
        CURRENT_TIMESTAMP - INTERVAL '35 day',
        CURRENT_TIMESTAMP - INTERVAL '21 day',
        CURRENT_TIMESTAMP - INTERVAL '35 day',
        CURRENT_TIMESTAMP - INTERVAL '21 day'
    ),
    (
        900007,
        'B-TST-LOAN21',
        'Serenity Prestamo Veintiuno',
        'CI',
        'R-TST-LOAN21',
        'Serenity Lector Veintiuno',
        21,
        'RETURNED',
        CURRENT_TIMESTAMP - INTERVAL '49 day',
        CURRENT_TIMESTAMP - INTERVAL '28 day',
        CURRENT_TIMESTAMP - INTERVAL '49 day',
        CURRENT_TIMESTAMP - INTERVAL '28 day'
    );

INSERT INTO debt_reader (
    id_debt,
    loan_id,
    id_reader,
    name_reader,
    units_fib,
    amount_debt,
    state_debt,
    created_at,
    updated_at,
    type_id_reader
) VALUES
    (
        990001,
        900003,
        'R-TST-DEBT',
        'Serenity Lector Deuda',
        8,
        21.00,
        'PENDING',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'CI'
    ),
    (
        990002,
        900004,
        'R-TST-PAID',
        'Serenity Lector Rehabilitado',
        5,
        13.00,
        'PAID',
        CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP,
        'DNI'
    );

COMMIT;

-- Verification queries
SELECT loan_id, id_book, title, id_reader, name_reader, state, date_limit, date_return
FROM loan_books
WHERE loan_id BETWEEN 900001 AND 900007
ORDER BY loan_id;

SELECT id_debt, loan_id, id_reader, name_reader, amount_debt, state_debt, type_id_reader
FROM debt_reader
WHERE id_debt IN (990001, 990002)
ORDER BY id_debt;