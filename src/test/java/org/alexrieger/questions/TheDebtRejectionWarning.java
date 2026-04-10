package org.alexrieger.questions;

import java.util.Locale;

import org.alexrieger.ui.LoanFormSection;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.questions.Visibility;

public class TheDebtRejectionWarning implements Question<Boolean> {

    @Override
    public Boolean answeredBy(Actor actor) {
        try {
            boolean isVisible = Visibility.of(LoanFormSection.MSG_LOAN_ERROR).answeredBy(actor);
            if (!isVisible) {
                return false;
            }

            String message = Text.of(LoanFormSection.MSG_LOAN_ERROR).answeredBy(actor).toLowerCase(Locale.ROOT);
            return message.contains("deuda")
                    || message.contains("multa")
                    || message.contains("pendiente")
                    || message.contains("debt")
                    || message.contains("fine");
        } catch (Exception ignored) {
            return false;
        }
    }

    public static TheDebtRejectionWarning isDisplayed() {
        return new TheDebtRejectionWarning();
    }
}