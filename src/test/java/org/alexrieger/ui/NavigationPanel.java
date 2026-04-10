package org.alexrieger.ui;

import net.serenitybdd.screenplay.targets.Target;

public final class NavigationPanel {

    private NavigationPanel() {
    }

    // ASSUMPTION: The availability section is rendered on the landing page, so this target anchors to its visible heading.
    public static final Target MENU_CONSULTA = Target.the("main menu option for consulta/disponibilidad")
            .locatedBy("//*[self::h1 or self::h2 or self::h3][contains(normalize-space(.),'Consultar Disponibilidad de Libro')]");

    // ASSUMPTION: The loan module is reached from a SPA link labelled Registrar Prestamo/Préstamo.
    public static final Target MENU_PRESTAMO = Target.the("main menu option for prestamo")
            .locatedBy("//a[@href='/loan' or contains(normalize-space(.),'Registrar Préstamo') or contains(normalize-space(.),'Registrar Prestamo')]");

    // ASSUMPTION: The return module is reached from a SPA link labelled Registrar Devolucion/Devolución.
    public static final Target MENU_DEVOLUCION = Target.the("main menu option for devolucion")
            .locatedBy("//a[@href='/return' or contains(normalize-space(.),'Registrar Devolución') or contains(normalize-space(.),'Registrar Devolucion')]");

    // ASSUMPTION: The overdue page is reached from a SPA link labelled Prestamos/Préstamos Vencidos.
    public static final Target MENU_VENCIDOS = Target.the("main menu option for prestamos vencidos")
            .locatedBy("//a[@href='/loans/overdue' or contains(normalize-space(.),'Préstamos Vencidos') or contains(normalize-space(.),'Prestamos Vencidos')]");

    // ASSUMPTION: The payment module is reached from a SPA link labelled Pagar Multa.
    public static final Target MENU_DEUDAS = Target.the("main menu option for deudas/pagos")
            .locatedBy("//a[@href='/payment' or contains(normalize-space(.),'Pagar Multa') or contains(normalize-space(.),'Pago')]");
}