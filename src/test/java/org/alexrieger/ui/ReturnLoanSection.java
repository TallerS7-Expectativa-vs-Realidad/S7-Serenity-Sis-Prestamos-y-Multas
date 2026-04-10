package org.alexrieger.ui;

import net.serenitybdd.screenplay.targets.Target;

public final class ReturnLoanSection {

    private ReturnLoanSection() {
    }

    // ASSUMPTION: The return date field appears after a Fecha de Devolucion/Devolución label.
    public static final Target INPUT_RETURN_DATE = Target.the("return date input")
            .locatedBy("//label[contains(normalize-space(.),'Fecha de Devolución') or contains(normalize-space(.),'Fecha de Devolucion')]/following::input[1] | //input[@type='date'][1]");

    // ASSUMPTION: The return document type control is rendered after the type label.
    public static final Target SELECT_TYPE_ID_RETURN = Target.the("return form document type select")
            .locatedBy("//label[contains(normalize-space(.),'Tipo de Identificación') or contains(normalize-space(.),'Tipo de Identificacion')]/following::select[1]");

    // ASSUMPTION: The return book id field is rendered after the ID del Libro label.
    public static final Target INPUT_BOOK_ID_RETURN = Target.the("return form book id input")
            .locatedBy("//label[contains(normalize-space(.),'ID del Libro')]/following::input[1]");

    // ASSUMPTION: The return reader id field is rendered after Numero/Número de Identificacion.
    public static final Target INPUT_READER_ID_RETURN = Target.the("return form reader id input")
            .locatedBy("//label[contains(normalize-space(.),'Número de Identificación') or contains(normalize-space(.),'Numero de Identificacion')]/following::input[1]");

    // ASSUMPTION: The return reader name field is rendered after Nombre del Lector.
    public static final Target INPUT_READER_NAME_RETURN = Target.the("return form reader name input")
            .locatedBy("//label[contains(normalize-space(.),'Nombre del Lector')]/following::input[1]");

    // ASSUMPTION: The return submit action uses the Registrar Devolucion/Devolución text.
    public static final Target BTN_SUBMIT_RETURN = Target.the("return form submit button")
            .locatedBy("//button[contains(normalize-space(.),'Registrar Devolución') or contains(normalize-space(.),'Registrar Devolucion') or contains(normalize-space(.),'Confirmar Devolución') or contains(normalize-space(.),'Confirmar Devolucion')]");

    // ASSUMPTION: Return success feedback is rendered as inline text or toast.
    public static final Target MSG_RETURN_SUCCESS = Target.the("return success message")
            .locatedBy("//*[contains(normalize-space(.),'Devolución registrada') or contains(normalize-space(.),'Devolucion registrada') or contains(@class,'success')]");

    // ASSUMPTION: Fine-related feedback appears as visible text including multa/deuda terms.
    public static final Target MSG_RETURN_FINE = Target.the("return fine message")
            .locatedBy("//*[contains(normalize-space(.),'multa') or contains(normalize-space(.),'deuda') or contains(@class,'warning') or contains(@class,'danger')]");

    // ASSUMPTION: Late-days calculation is shown in a dedicated summary field with consistent naming.
    public static final Target LABEL_DAYS_LATE = Target.the("days late label")
            .locatedBy("[data-testid='days-late'], #daysLate, .days-late, [name='daysLate']");
}