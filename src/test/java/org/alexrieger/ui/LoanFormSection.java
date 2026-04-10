package org.alexrieger.ui;

import net.serenitybdd.screenplay.targets.Target;

public final class LoanFormSection {

    private LoanFormSection() {
    }

    // ASSUMPTION: The loan form places the ID del Libro input directly after its label.
    public static final Target INPUT_BOOK_ID = Target.the("loan form book id input")
            .locatedBy("//label[contains(normalize-space(.),'ID del Libro')]/following::input[1]");

    // ASSUMPTION: The loan form places the Titulo/Título field directly after its label.
    public static final Target INPUT_BOOK_TITLE = Target.the("loan form book title input")
            .locatedBy("//label[contains(normalize-space(.),'Título del Libro') or contains(normalize-space(.),'Titulo del Libro')]/following::input[1]");

    // ASSUMPTION: The document type control is rendered after the Tipo de Identificacion label.
    public static final Target SELECT_TYPE_ID = Target.the("loan form document type select")
            .locatedBy("//label[contains(normalize-space(.),'Tipo de Identificación') or contains(normalize-space(.),'Tipo de Identificacion')]/following::select[1]");

    // ASSUMPTION: The reader id field is rendered after Numero/Número de Identificacion.
    public static final Target INPUT_READER_ID = Target.the("loan form reader id input")
            .locatedBy("//label[contains(normalize-space(.),'Número de Identificación') or contains(normalize-space(.),'Numero de Identificacion')]/following::input[1]");

    // ASSUMPTION: The reader name field is rendered after Nombre del Lector.
    public static final Target INPUT_READER_NAME = Target.the("loan form reader name input")
            .locatedBy("//label[contains(normalize-space(.),'Nombre del Lector')]/following::input[1]");

    // ASSUMPTION: The loan term may be rendered either as a select or as clickable option cards/buttons.
    public static final Target SELECT_LOAN_DAYS = Target.the("loan form loan days select")
            .locatedBy("//label[contains(normalize-space(.),'Plazo del Préstamo') or contains(normalize-space(.),'Plazo del Prestamo')]/following::select[1]");

    // ASSUMPTION: Loan day options can be selected by their visible text when rendered as cards/radios.
    public static Target LOAN_DAY_OPTION(String loanDays) {
        return Target.the("loan term option " + loanDays)
                .locatedBy("//*[self::label or self::button or self::span or self::div][contains(normalize-space(.),'" + loanDays + " días') or contains(normalize-space(.),'" + loanDays + " dias')]");
    }

    // ASSUMPTION: The submit action is the Registrar Prestamo/Préstamo button in the loan section.
    public static final Target BTN_SUBMIT_LOAN = Target.the("loan form submit button")
            .locatedBy("//button[contains(normalize-space(.),'Registrar Préstamo') or contains(normalize-space(.),'Registrar Prestamo')]");

    // ASSUMPTION: Success feedback may be shown as inline text or toast containing prestamo/préstamo registrado.
    public static final Target MSG_LOAN_SUCCESS = Target.the("loan success message")
            .locatedBy("//*[contains(normalize-space(.),'Préstamo registrado') or contains(normalize-space(.),'Prestamo registrado') or contains(@class,'success')]");

    // ASSUMPTION: Loan rejection/error feedback is shown as visible text near the form.
    public static final Target MSG_LOAN_ERROR = Target.the("loan error message")
            .locatedBy("//*[contains(normalize-space(.),'deuda pendiente') or contains(normalize-space(.),'no está disponible') or contains(normalize-space(.),'no esta disponible') or contains(@class,'error') or contains(@class,'danger')]");

    // ASSUMPTION: The due date is shown near the Fecha Limite/Límite de Devolucion label.
    public static final Target LABEL_DUE_DATE = Target.the("loan due date label")
            .locatedBy("//*[contains(normalize-space(.),'Fecha Límite de Devolución') or contains(normalize-space(.),'Fecha Limite de Devolucion')]");
}