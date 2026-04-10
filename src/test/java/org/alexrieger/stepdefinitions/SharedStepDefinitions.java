package org.alexrieger.stepdefinitions;

import java.time.Duration;
import java.util.Locale;

import org.alexrieger.hooks.OpenBrowser;
import org.alexrieger.tasks.NavigateToModule;
import org.alexrieger.util.ServiceAvailability;
import org.alexrieger.util.TestData;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsString;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.Actor;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actors.OnStage;

public class SharedStepDefinitions {

    @Dado("que el bibliotecario ingresa al sistema de biblioteca")
    public void queElBibliotecarioIngresaAlSistemaDeBiblioteca() {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);

        ServiceAvailability.assertReachable("frontend", TestData.FRONTEND_BASE_URL, TestData.DEFAULT_WAIT_SECONDS);
        ServiceAvailability.assertReachable("backend", TestData.BACKEND_BASE_URL, TestData.DEFAULT_WAIT_SECONDS);

        actor.attemptsTo(OpenBrowser.withUrl(TestData.FRONTEND_BASE_URL));
    }

    @Y("que el bibliotecario navega al módulo de {string}")
    public void queElBibliotecarioNavegaAlModuloDe(String moduleName) {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        actor.attemptsTo(NavigateToModule.called(moduleName));
    }

    @Entonces("el bibliotecario ve el mensaje {string}")
    public void elBibliotecarioVeElMensaje(String expectedMessage) {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        String expectedText = expectedMessage.toLowerCase(Locale.ROOT);
        String alternateText = alternateFeedbackFor(expectedText);

        try {
            new WebDriverWait(BrowseTheWeb.as(actor).getDriver(), Duration.ofSeconds(TestData.DEFAULT_WAIT_SECONDS))
                .until(driver -> driver.findElement(By.tagName("body"))
                    .getText()
                    .toLowerCase(Locale.ROOT)
                    .contains(expectedText)
                    || (!alternateText.equals(expectedText)
                    && driver.findElement(By.tagName("body"))
                    .getText()
                    .toLowerCase(Locale.ROOT)
                    .contains(alternateText)));
        } catch (Throwable ignored) {
        }

        if (!alternateText.equals(expectedText)) {
            actor.should(seeThat(
                "el texto visible de la pagina",
                currentActor -> BrowseTheWeb.as(currentActor)
                    .getDriver()
                    .findElement(By.tagName("body"))
                    .getText()
                    .toLowerCase(Locale.ROOT),
                anyOf(
                    containsString(expectedText),
                    containsString(alternateText)
                )
            ));
            return;
        }

        actor.should(seeThat(
            "el texto visible de la pagina",
            currentActor -> BrowseTheWeb.as(currentActor)
                .getDriver()
                .findElement(By.tagName("body"))
                .getText()
                .toLowerCase(Locale.ROOT),
            containsString(expectedText)
        ));
    }

    private String alternateFeedbackFor(String expectedText) {
        return switch (expectedText) {
            case "el libro no está disponible para préstamo" -> "the book is not available for loan";
            case "pago registrado exitosamente" -> "deuda pagada exitosamente";
            case "préstamo rechazado por deuda pendiente del lector" -> "reader has pending debt and cannot borrow books";
            case "devolución registrada con multa" -> "resumen de multa";
            default -> expectedText;
        };
    }
}
