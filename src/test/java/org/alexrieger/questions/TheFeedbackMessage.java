package org.alexrieger.questions;

import org.alexrieger.ui.BookAvailabilitySection;
import org.alexrieger.ui.DebtsSection;
import org.alexrieger.ui.LoanFormSection;
import org.alexrieger.ui.OverdueLoansSection;
import org.alexrieger.ui.ReturnLoanSection;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.questions.Text;
import net.serenitybdd.screenplay.targets.Target;

public class TheFeedbackMessage implements Question<String> {

    private final Target messageTarget;

    public TheFeedbackMessage(Target messageTarget) {
        this.messageTarget = messageTarget;
    }

    @Override
    public String answeredBy(Actor actor) {
        return Text.of(messageTarget).answeredBy(actor);
    }

    public static TheFeedbackMessage ofLoanSuccess() {
        return new TheFeedbackMessage(LoanFormSection.MSG_LOAN_SUCCESS);
    }

    public static TheFeedbackMessage ofLoanError() {
        return new TheFeedbackMessage(LoanFormSection.MSG_LOAN_ERROR);
    }

    public static TheFeedbackMessage ofReturnSuccess() {
        return new TheFeedbackMessage(ReturnLoanSection.MSG_RETURN_SUCCESS);
    }

    public static TheFeedbackMessage ofReturnFine() {
        return new TheFeedbackMessage(ReturnLoanSection.MSG_RETURN_FINE);
    }

    public static TheFeedbackMessage ofPaymentSuccess() {
        return new TheFeedbackMessage(DebtsSection.MSG_PAYMENT_SUCCESS);
    }

    public static TheFeedbackMessage ofNoResults() {
        return new TheFeedbackMessage(BookAvailabilitySection.MSG_NO_RESULTS);
    }

    public static TheFeedbackMessage ofNoOverdue() {
        return new TheFeedbackMessage(OverdueLoansSection.MSG_NO_OVERDUE);
    }
}