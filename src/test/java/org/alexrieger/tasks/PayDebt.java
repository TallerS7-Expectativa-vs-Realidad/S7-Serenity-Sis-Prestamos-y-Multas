package org.alexrieger.tasks;

import java.time.Duration;
import java.util.List;

import net.serenitybdd.core.pages.WebElementFacade;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Scroll;
import net.serenitybdd.screenplay.waits.WaitUntil;
import org.alexrieger.ui.DebtsSection;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.support.ui.WebDriverWait;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class PayDebt implements Task {

    public PayDebt() {
    }

    @Override
    @Step("{0} pays the visible debt")
    public <T extends Actor> void performAs(T actor) {
        waitForAnyPaymentAction(actor);

        if (isVisibleFor(actor, DebtsSection.BTN_CONFIRM_PAYMENT)) {
            clickLastVisibleConfirm(actor);

            try {
                new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), Duration.ofSeconds(5))
                        .until(driver -> isVisibleFor(actor, DebtsSection.MSG_PAYMENT_SUCCESS)
                                || visibleConfirmCount(actor) > 1);
            } catch (TimeoutException ignored) {
            }

            if (!isVisibleFor(actor, DebtsSection.MSG_PAYMENT_SUCCESS) && isVisibleFor(actor, DebtsSection.BTN_CONFIRM_PAYMENT)) {
                clickLastVisibleConfirm(actor);
            }

            actor.attemptsTo(WaitUntil.the(DebtsSection.MSG_PAYMENT_SUCCESS, isVisible()).forNoMoreThan(10).seconds());
            return;
        }

        if (isVisibleFor(actor, DebtsSection.PANEL_DEBT_DETAILS)) {
            actor.attemptsTo(Scroll.to(DebtsSection.PANEL_DEBT_DETAILS));
            actor.attemptsTo(WaitUntil.the(DebtsSection.BTN_PAY_DEBT, isVisible()).forNoMoreThan(10).seconds());
        }

        if (isVisibleFor(actor, DebtsSection.BTN_PAY_DEBT)) {
            actor.attemptsTo(Click.on(DebtsSection.BTN_PAY_DEBT));
        }

        if (isVisibleFor(actor, DebtsSection.BTN_CONFIRM_PAYMENT)) {
            actor.attemptsTo(Click.on(DebtsSection.BTN_CONFIRM_PAYMENT));
        }

        actor.attemptsTo(WaitUntil.the(DebtsSection.MSG_PAYMENT_SUCCESS, isVisible()).forNoMoreThan(10).seconds());
    }

    private <T extends Actor> void waitForAnyPaymentAction(T actor) {
        try {
            new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), Duration.ofSeconds(10))
                    .until(driver -> isVisibleFor(actor, DebtsSection.BTN_CONFIRM_PAYMENT)
                            || (isVisibleFor(actor, DebtsSection.PANEL_DEBT_DETAILS)
                            && isVisibleFor(actor, DebtsSection.BTN_PAY_DEBT))
                            || isVisibleFor(actor, DebtsSection.PANEL_DEBT_DETAILS));
        } catch (TimeoutException timeoutException) {
            throw timeoutException;
        }
    }

    private <T extends Actor> boolean isVisibleFor(T actor, net.serenitybdd.screenplay.targets.Target target) {
        return target.resolveAllFor(actor)
                .stream()
                .anyMatch(WebElementFacade::isVisible);
    }

    private <T extends Actor> int visibleConfirmCount(T actor) {
        return (int) DebtsSection.BTN_CONFIRM_PAYMENT.resolveAllFor(actor)
                .stream()
                .filter(WebElementFacade::isVisible)
                .count();
    }

    private <T extends Actor> void clickLastVisibleConfirm(T actor) {
        List<WebElementFacade> visibleConfirmButtons = DebtsSection.BTN_CONFIRM_PAYMENT.resolveAllFor(actor)
                .stream()
                .filter(WebElementFacade::isVisible)
                .toList();

        if (visibleConfirmButtons.isEmpty()) {
            return;
        }

        visibleConfirmButtons.get(visibleConfirmButtons.size() - 1).click();
    }

    public static PayDebt now() {
        return Tasks.instrumented(PayDebt.class);
    }
}
