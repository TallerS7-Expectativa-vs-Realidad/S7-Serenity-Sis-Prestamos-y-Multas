package org.alexrieger.ui;

import net.serenitybdd.screenplay.targets.Target;

public final class DebtsSection {

    private DebtsSection() {
    }

    // ASSUMPTION: The payment page captures reader id after the Numero/Número de Identificacion label.
    public static final Target INPUT_READER_ID_DEBT = Target.the("reader id input for debt search")
            .locatedBy("//label[contains(normalize-space(.),'Número de Identificación') or contains(normalize-space(.),'Numero de Identificacion')]/following::input[1]");

    // ASSUMPTION: The payment page captures reader name after the Nombre del Lector label.
    public static final Target INPUT_READER_NAME_DEBT = Target.the("reader name input for debt search")
            .locatedBy("//label[contains(normalize-space(.),'Nombre del Lector')]/following::input[1]");

    // ASSUMPTION: The payment page renders document type as a select after its label.
    public static final Target SELECT_TYPE_ID_DEBT = Target.the("document type select for debt search")
            .locatedBy("//label[contains(normalize-space(.),'Tipo de Identificación') or contains(normalize-space(.),'Tipo de Identificacion')]/following::select[1]");

    // ASSUMPTION: The payment page exposes Buscar/Consultar and Pagar buttons.
    public static final Target BTN_SEARCH_DEBT = Target.the("search debt button")
            .locatedBy("//button[normalize-space(.)='Buscar Deuda' or normalize-space(.)='Buscar' or normalize-space(.)='Consultar']");

    // ASSUMPTION: Debt details are rendered with a dedicated heading/summary before payment is available.
    public static final Target PANEL_DEBT_DETAILS = Target.the("debt details panel")
            .locatedBy("//*[contains(normalize-space(.),'Detalles de la Deuda') or contains(normalize-space(.),'Resumen de multa')]");

    // ASSUMPTION: Debt rows appear in the first result table on the payment page.
    public static final Target TABLE_DEBTS_ROWS = Target.the("rows of debts table")
            .locatedBy("(//table)[1]//tbody/tr");

    // ASSUMPTION: Debt id cell is tagged with a semantic data attribute/class or first table column.
    public static final Target CELL_DEBT_ID = Target.the("debt id cell")
            .locatedBy("[data-testid='cell-debt-id'], td.debt-id, td[data-column='debtId'], table tbody tr td:nth-child(1)");

    // ASSUMPTION: Debt amount cell is tagged with semantic attributes/class or second table column.
    public static final Target CELL_DEBT_AMOUNT = Target.the("debt amount cell")
            .locatedBy("[data-testid='cell-debt-amount'], td.debt-amount, td[data-column='amount'], table tbody tr td:nth-child(2)");

    // ASSUMPTION: Debt status cell is tagged with semantic attributes/class or third table column.
    public static final Target CELL_DEBT_STATUS = Target.the("debt status cell")
            .locatedBy("[data-testid='cell-debt-status'], td.debt-status, td[data-column='status'], table tbody tr td:nth-child(3)");

    // ASSUMPTION: The pay action uses the Pagar/Pagar Multa text.
    public static final Target BTN_PAY_DEBT = Target.the("pay debt button")
            .locatedBy("((//*[contains(normalize-space(.),'Detalles de la Deuda') or contains(normalize-space(.),'Resumen de multa')])[1]/following::button[not(contains(normalize-space(.),'Buscar')) and not(contains(normalize-space(.),'Consultar')) and not(contains(normalize-space(.),'Limpiar'))][1]) | //button[normalize-space(.)='Registrar Pago de Multa']");

    // ASSUMPTION: The payment flow confirms the action in a modal/dialog.
    public static final Target BTN_CONFIRM_PAYMENT = Target.the("confirm payment button")
            .locatedBy("//button[contains(normalize-space(.),'Confirmar Pago') or contains(normalize-space(.),'Confirmar')]");

    // ASSUMPTION: Payment success feedback appears in toast/alert with success semantics.
    public static final Target MSG_PAYMENT_SUCCESS = Target.the("debt payment success message")
            .locatedBy("//*[@data-testid='payment-success-message' or contains(normalize-space(.),'Pago registrado exitosamente') or contains(normalize-space(.),'Deuda pagada exitosamente') or contains(normalize-space(.),'Pago exitoso') or contains(normalize-space(.),'rehabilitado') or contains(@class,'success')]");

    // ASSUMPTION: No-debts message is displayed in empty state text or info alert.
    public static final Target MSG_NO_DEBTS = Target.the("no debts message")
            .locatedBy("//*[@data-testid='no-debts-message' or contains(normalize-space(.),'No tiene deudas') or contains(normalize-space(.),'No se encontraron deudas') or contains(normalize-space(.),'Lector no tiene deuda pendiente') or contains(normalize-space(.),'Deuda no encontrada') or contains(normalize-space(.),'No debts found')]");
}