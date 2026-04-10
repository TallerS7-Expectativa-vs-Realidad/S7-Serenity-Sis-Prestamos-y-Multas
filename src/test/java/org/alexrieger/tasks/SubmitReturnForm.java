package org.alexrieger.tasks;

import org.alexrieger.ui.ReturnLoanSection;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;

public class SubmitReturnForm implements Task {

    public SubmitReturnForm() {
    }

    @Override
    @Step("{0} submits the return form")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Click.on(ReturnLoanSection.BTN_SUBMIT_RETURN));
    }

    public static SubmitReturnForm now() {
        return Tasks.instrumented(SubmitReturnForm.class);
    }
}
