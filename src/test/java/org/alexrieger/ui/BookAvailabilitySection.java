package org.alexrieger.ui;

import net.serenitybdd.screenplay.targets.Target;

public final class BookAvailabilitySection {

    private BookAvailabilitySection() {
    }

    // ASSUMPTION: The search field appears directly after the Nombre del Libro label inside the availability section.
    public static final Target INPUT_BOOK_NAME = Target.the("book name search input")
            .locatedBy("//label[contains(normalize-space(.),'Nombre del Libro')]/following::input[1] | //*[contains(normalize-space(.),'Consultar Disponibilidad de Libro')]/following::input[1]");

    // ASSUMPTION: The availability section exposes a Buscar button next to the book-name field.
    public static final Target BTN_SEARCH = Target.the("book search button")
            .locatedBy("(//button[contains(normalize-space(.),'Buscar')])[1]");

    // ASSUMPTION: The search result area is rendered under the availability heading and contains the status text.
    public static final Target LABEL_BOOK_STATUS = Target.the("book availability status label")
            .locatedBy("//*[contains(normalize-space(.),'Consultar Disponibilidad de Libro')]/following::*[(self::section or self::div or self::article)][1]");

    // ASSUMPTION: Search results are rendered immediately after the availability form.
    public static final Target RESULTS_TABLE = Target.the("book search results table")
            .locatedBy("//*[contains(normalize-space(.),'Consultar Disponibilidad de Libro')]/following::table[1] | //*[contains(normalize-space(.),'Consultar Disponibilidad de Libro')]/following::*[contains(@class,'result')][1]");

    // ASSUMPTION: After searching, the UI renders either the searched title or a status/result message in the availability area.
    public static Target SEARCH_RESULT_FOR(String bookName) {
        return Target.the("availability search result for " + bookName)
                .locatedBy("//*[contains(normalize-space(.),\"" + bookName + "\") or contains(normalize-space(.),'disponible') or contains(normalize-space(.),'prestado') or contains(normalize-space(.),'historial de préstamo') or contains(normalize-space(.),'historial de prestamo') or contains(normalize-space(.),'ON_LOAN')]");
    }

    // ASSUMPTION: Empty results are shown in plain text under the availability section.
    public static final Target MSG_NO_RESULTS = Target.the("message for no book results")
            .locatedBy("//*[contains(normalize-space(.),'No se encontraron resultados') or contains(normalize-space(.),'No se encontró el libro consultado') or contains(normalize-space(.),'No hay resultados')]");
}