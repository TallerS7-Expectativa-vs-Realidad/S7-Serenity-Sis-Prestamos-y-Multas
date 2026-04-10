-- Queries validated against the live PostgreSQL schema used by the S7 library system.
-- Database: postgres
-- Tables: public.loan_books, public.debt_reader

-- Readers present in loan history
SELECT DISTINCT
    id_reader,
    name_reader,
    type_id_reader
FROM loan_books
ORDER BY name_reader, id_reader;

-- Books present in loan history
SELECT DISTINCT
    id_book,
    title
FROM loan_books
ORDER BY title, id_book;

-- Full loan inventory
SELECT
    loan_id,
    id_book,
    title,
    id_reader,
    name_reader,
    type_id_reader,
    loan_days,
    state,
    date_limit,
    date_return
FROM loan_books
ORDER BY loan_id;

-- Overdue loans still active
SELECT
    loan_id,
    id_book,
    title,
    id_reader,
    name_reader,
    type_id_reader,
    date_limit,
    date_return
FROM loan_books
WHERE state = 'ON_LOAN'
  AND date_limit < CURRENT_TIMESTAMP
ORDER BY date_limit;

-- All debts
SELECT
    id_debt,
    loan_id,
    id_reader,
    name_reader,
    type_id_reader,
    units_fib,
    amount_debt,
    state_debt,
    created_at,
    updated_at
FROM debt_reader
ORDER BY id_debt;

-- Pending debts only
SELECT
    id_debt,
    loan_id,
    id_reader,
    name_reader,
    type_id_reader,
    units_fib,
    amount_debt,
    state_debt
FROM debt_reader
WHERE state_debt = 'PENDING'
ORDER BY amount_debt DESC, id_debt;

-- Current debt scenario candidate lookup
SELECT
    id_debt,
    loan_id,
    id_reader,
    name_reader,
    type_id_reader,
    amount_debt,
    state_debt
FROM debt_reader
WHERE id_reader = 'R-2201'
   OR name_reader ILIKE '%Lector mora 1 día%';

-- Current paid-debt reader candidate lookup
SELECT
    id_debt,
    loan_id,
    id_reader,
    name_reader,
    type_id_reader,
    amount_debt,
    state_debt
FROM debt_reader
WHERE id_reader = 'R-2302'
   OR name_reader ILIKE '%Luis Pardo%'
ORDER BY id_debt;

-- Current loan candidate after rehabilitation
SELECT
    loan_id,
    id_book,
    title,
    id_reader,
    name_reader,
    state,
    date_limit,
    date_return
FROM loan_books
WHERE id_book = 'B-1601'
   OR title ILIKE '%Crimen y castigo%'
ORDER BY loan_id;

-- Current on-time return candidate
SELECT
    loan_id,
    id_book,
    title,
    id_reader,
    name_reader,
    type_id_reader,
    state,
    date_limit,
    date_return
FROM loan_books
WHERE id_book = 'B-1001'
   OR title ILIKE '%Cien años de soledad%'
   OR id_reader = 'R-2001'
   OR name_reader ILIKE '%Ana Torres%'
ORDER BY loan_id;

-- Current overdue return candidate
SELECT
    loan_id,
    id_book,
    title,
    id_reader,
    name_reader,
    type_id_reader,
    state,
    date_limit,
    date_return
FROM loan_books
WHERE id_book = 'B-1251'
   OR title ILIKE '%La Odisea%'
   OR id_reader = 'R-2251'
   OR name_reader ILIKE '%Sara Mena%'
ORDER BY loan_id;

-- Readers with paid debts and no pending debts
WITH eligible_readers AS (
    SELECT
        id_reader,
        name_reader,
        type_id_reader,
        MAX(updated_at) FILTER (WHERE state_debt = 'PAID') AS last_paid_at
    FROM debt_reader
    GROUP BY id_reader, name_reader, type_id_reader
    HAVING BOOL_OR(state_debt = 'PAID')
       AND NOT BOOL_OR(state_debt = 'PENDING')
)
SELECT
    id_reader,
    name_reader,
    type_id_reader,
    last_paid_at
FROM eligible_readers
ORDER BY last_paid_at DESC NULLS LAST, id_reader;

-- Books whose latest known state is RETURNED
WITH latest_book_state AS (
    SELECT
        id_book,
        title,
        loan_id,
        state,
        COALESCE(updated_at, created_at) AS latest_at,
        ROW_NUMBER() OVER (
            PARTITION BY id_book
            ORDER BY COALESCE(updated_at, created_at) DESC, loan_id DESC
        ) AS rn
    FROM loan_books
)
SELECT
    id_book,
    title,
    loan_id AS latest_loan_id,
    state AS latest_state,
    latest_at
FROM latest_book_state
WHERE rn = 1
  AND state = 'RETURNED'
ORDER BY latest_at DESC, id_book;