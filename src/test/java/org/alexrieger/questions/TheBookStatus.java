package org.alexrieger.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;

public class TheBookStatus implements Question<String> {

    @Override
    public String answeredBy(Actor actor) {
        return BrowseTheWeb.as(actor)
                .getDriver()
                .findElement(By.tagName("body"))
                .getText();
    }

    public static TheBookStatus displayed() {
        return new TheBookStatus();
    }
}