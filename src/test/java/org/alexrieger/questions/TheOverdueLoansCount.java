package org.alexrieger.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import org.alexrieger.ui.OverdueLoansSection;

public class TheOverdueLoansCount implements Question<Integer> {

    @Override
    public Integer answeredBy(Actor actor) {
        return OverdueLoansSection.TABLE_OVERDUE_ROWS.resolveAllFor(actor).size();
    }

    public static TheOverdueLoansCount value() {
        return new TheOverdueLoansCount();
    }
}