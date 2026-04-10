package org.alexrieger.tasks;

import org.alexrieger.ui.LoanFormSection;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

public class SubmitLoanForm implements Task {

    public SubmitLoanForm() {
    }

    @Override
    @Step("{0} submits the loan form")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Click.on(LoanFormSection.BTN_SUBMIT_LOAN));
    }

    public static SubmitLoanForm now() {
        return Tasks.instrumented(SubmitLoanForm.class);
    }
}
