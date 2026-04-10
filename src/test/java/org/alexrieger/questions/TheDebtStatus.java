package org.alexrieger.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;

import java.util.Locale;

public class TheDebtStatus implements Question<String> {

    @Override
    public String answeredBy(Actor actor) {
        return BrowseTheWeb.as(actor)
                .getDriver()
                .findElement(By.tagName("body"))
                .getText()
                .toLowerCase(Locale.ROOT);
    }

    public static TheDebtStatus displayed() {
        return new TheDebtStatus();
    }
}