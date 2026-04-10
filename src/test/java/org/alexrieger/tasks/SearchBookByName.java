package org.alexrieger.tasks;

import org.alexrieger.ui.BookAvailabilitySection;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import net.serenitybdd.screenplay.waits.WaitUntil;

import static net.serenitybdd.screenplay.matchers.WebElementStateMatchers.isVisible;

public class SearchBookByName implements Task {

    private final String bookName;

    public SearchBookByName(String bookName) {
        this.bookName = bookName;
    }

    @Override
    @Step("{0} searches for the book named #bookName")
    public <T extends Actor> void performAs(T actor) {
        actor.remember("last.searched.book", bookName);

        actor.attemptsTo(
                Enter.theValue(bookName).into(BookAvailabilitySection.INPUT_BOOK_NAME),
            Click.on(BookAvailabilitySection.BTN_SEARCH)
        );

        try {
            actor.attemptsTo(
                WaitUntil.the(BookAvailabilitySection.SEARCH_RESULT_FOR(bookName), isVisible()).forNoMoreThan(10).seconds()
            );
        } catch (Throwable ignored) {
        }
    }

    public static SearchBookByName withName(String bookName) {
        return Tasks.instrumented(SearchBookByName.class, bookName);
    }
}
