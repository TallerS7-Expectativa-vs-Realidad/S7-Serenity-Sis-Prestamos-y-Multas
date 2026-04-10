package org.alexrieger.questions;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Question;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import org.openqa.selenium.By;

import java.util.Locale;

public class TheFineInfo implements Question<Boolean> {

    private static final String[] NEGATIVE_FINE_MARKERS = {
            "no se generó multa",
            "no se genero multa",
            "sin multa"
    };

    private static final String[] POSITIVE_FINE_MARKERS = {
            "devolución registrada con multa",
            "devolucion registrada con multa",
            "deuda pendiente del lector",
            "resumen de multa",
            "multa generada",
            "monto de la multa",
            "multa total",
            "valor de la multa",
            "id de deuda:"
    };

    @Override
    public Boolean answeredBy(Actor actor) {
        try {
            String bodyText = BrowseTheWeb.as(actor)
                    .getDriver()
                    .findElement(By.tagName("body"))
                    .getText()
                    .toLowerCase(Locale.ROOT);

            for (String negativeMarker : NEGATIVE_FINE_MARKERS) {
                if (bodyText.contains(negativeMarker)) {
                    return false;
                }
            }

            for (String positiveMarker : POSITIVE_FINE_MARKERS) {
                if (bodyText.contains(positiveMarker)) {
                    return true;
                }
            }

            return false;
        } catch (Exception ignored) {
            return false;
        }
    }

    public static TheFineInfo isDisplayed() {
        return new TheFineInfo();
    }
}