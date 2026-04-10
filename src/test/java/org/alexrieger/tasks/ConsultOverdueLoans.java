package org.alexrieger.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.alexrieger.ui.OverdueLoansSection;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class ConsultOverdueLoans implements Task {

    public ConsultOverdueLoans() {
    }

    @Override
    @Step("{0} consults overdue loans data")
    public <T extends Actor> void performAs(T actor) {
        try {
            actor.attemptsTo(
                    WaitUntil.the(OverdueLoansSection.TABLE_OVERDUE_ROWS, isVisible()).forNoMoreThan(10).seconds()
            );
        } catch (Throwable ignored) {
            actor.attemptsTo(
                    WaitUntil.the(OverdueLoansSection.MSG_NO_OVERDUE, isVisible()).forNoMoreThan(10).seconds()
            );
        }
    }

    public static ConsultOverdueLoans now() {
        return Tasks.instrumented(ConsultOverdueLoans.class);
    }
}
