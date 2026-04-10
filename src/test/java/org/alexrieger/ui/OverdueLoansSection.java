package org.alexrieger.ui;

import net.serenitybdd.screenplay.targets.Target;

public final class OverdueLoansSection {

    private OverdueLoansSection() {
    }

    // ASSUMPTION: Overdue rows are displayed in a table/list with test id and common container fallbacks.
    public static final Target TABLE_OVERDUE_ROWS = Target.the("rows of overdue loans table")
            .locatedBy("[data-testid='overdue-rows'] tr, [data-testid='overdue-table'] tbody tr, #overdueTable tbody tr, .overdue-table tbody tr, table tbody tr");

    // ASSUMPTION: Empty state message appears when there are no overdue loans.
    public static final Target MSG_NO_OVERDUE = Target.the("no overdue loans message")
            .locatedBy("//*[@data-testid='no-overdue-message' or contains(normalize-space(.),'No hay prestamos vencidos') or contains(normalize-space(.),'No hay préstamos vencidos') or contains(normalize-space(.),'No overdue loans')]");

    // ASSUMPTION: Book title column has semantic class/data attributes or can be identified by header context.
    public static final Target CELL_BOOK_TITLE = Target.the("book title cell in overdue table")
            .locatedBy("[data-testid='cell-book-title'], td.book-title, td[data-column='bookTitle'], table tbody tr:first-child td:nth-child(1)");

    // ASSUMPTION: Reader name column has semantic class/data attributes or is the second column.
    public static final Target CELL_READER_NAME = Target.the("reader name cell in overdue table")
            .locatedBy("[data-testid='cell-reader-name'], td.reader-name, td[data-column='readerName'], table tbody tr:first-child td:nth-child(2)");

    // ASSUMPTION: Due date column has semantic class/data attributes or is the third column.
    public static final Target CELL_DUE_DATE = Target.the("due date cell in overdue table")
            .locatedBy("[data-testid='cell-due-date'], td.due-date, td[data-column='dueDate'], table tbody tr:first-child td:nth-child(3)");

    // ASSUMPTION: Status column has semantic class/data attributes or is the fourth column.
    public static final Target CELL_STATUS = Target.the("status cell in overdue table")
            .locatedBy("[data-testid='cell-status'], td.status, td[data-column='status'], table tbody tr td:nth-child(4)");
}