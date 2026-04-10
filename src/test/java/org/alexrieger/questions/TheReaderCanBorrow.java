package org.alexrieger.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;

import java.util.Locale;
import org.openqa.selenium.By;

public class TheReaderCanBorrow implements Question<Boolean> {

    @Override
    public Boolean answeredBy(Actor actor) {
        String pageText = BrowseTheWeb.as(actor)
                .getDriver()
                .findElement(By.tagName("body"))
                .getText()
                .toLowerCase(Locale.ROOT);

        return pageText.contains("rehabilitado")
                || pageText.contains("habilitado")
                || pageText.contains("pago registrado exitosamente")
                || pageText.contains("deuda pagada exitosamente");
    }

    public static TheReaderCanBorrow now() {
        return new TheReaderCanBorrow();
    }
}