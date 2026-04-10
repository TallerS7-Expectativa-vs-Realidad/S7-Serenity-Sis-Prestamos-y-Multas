package org.alexrieger.tasks;

import java.util.Locale;

import org.alexrieger.ui.BookAvailabilitySection;
import org.alexrieger.ui.LoanFormSection;
import org.alexrieger.ui.NavigationPanel;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;
import net.serenitybdd.screenplay.targets.Target;
import net.serenitybdd.screenplay.waits.WaitUntil;
import net.serenitybdd.core.pages.WebElementFacade;

public class NavigateToModule implements Task {

    private final String moduleName;

    public NavigateToModule(String moduleName) {
        this.moduleName = moduleName;
    }

    @Override
    @Step("{0} navigates to module #moduleName")
    public <T extends Actor> void performAs(T actor) {
        String normalized = moduleName == null ? "" : moduleName.trim().toLowerCase(Locale.ROOT);

        if (normalized.equals("consulta de disponibilidad")) {
            actor.attemptsTo(
                    WaitUntil.the(BookAvailabilitySection.INPUT_BOOK_NAME, isVisible()).forNoMoreThan(10).seconds()
            );
            return;
        }

        if (normalized.equals("registro de préstamo") || normalized.equals("registro de prestamo")) {
            if (isVisibleFor(actor, LoanFormSection.INPUT_BOOK_ID)) {
                return;
            }
        }

        Target moduleTarget = targetFor(moduleName);

        actor.attemptsTo(
                WaitUntil.the(moduleTarget, isVisible()).forNoMoreThan(10).seconds(),
                Click.on(moduleTarget)
        );

        if (normalized.equals("registro de préstamo") || normalized.equals("registro de prestamo")) {
            actor.attemptsTo(
                WaitUntil.the(LoanFormSection.INPUT_BOOK_ID, isVisible()).forNoMoreThan(10).seconds()
            );
        }
    }

    public static NavigateToModule called(String moduleName) {
        return Tasks.instrumented(NavigateToModule.class, moduleName);
    }

    private Target targetFor(String requestedModule) {
        String normalized = requestedModule == null ? "" : requestedModule.trim().toLowerCase(Locale.ROOT);

        return switch (normalized) {
            case "consulta de disponibilidad" -> NavigationPanel.MENU_CONSULTA;
            case "registro de préstamo", "registro de prestamo" -> NavigationPanel.MENU_PRESTAMO;
            case "devoluciones" -> NavigationPanel.MENU_DEVOLUCION;
            case "préstamos vencidos", "prestamos vencidos" -> NavigationPanel.MENU_VENCIDOS;
            case "deudas y pagos" -> NavigationPanel.MENU_DEUDAS;
            default -> throw new IllegalArgumentException("Unsupported module name: " + requestedModule);
        };
    }

    private <T extends Actor> boolean isVisibleFor(T actor, Target target) {
        return target.resolveAllFor(actor)
                .stream()
                .anyMatch(WebElementFacade::isVisible);
    }
}
