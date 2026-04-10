package org.alexrieger.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TheDueDate implements Question<String> {

    private static final Pattern DATE_PATTERN = Pattern.compile("(\\b\\d{2}/\\d{2}/\\d{4}\\b|\\b\\d{4}-\\d{2}-\\d{2}\\b)");

    @Override
    public String answeredBy(Actor actor) {
        String bodyText = BrowseTheWeb.as(actor)
                .getDriver()
                .findElement(By.tagName("body"))
                .getText();

        Matcher matcher = DATE_PATTERN.matcher(bodyText);
        if (matcher.find()) {
            return matcher.group(1);
        }

        return "";
    }

    public static TheDueDate displayed() {
        return new TheDueDate();
    }
}