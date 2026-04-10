package org.alexrieger.stepdefinitions;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import org.alexrieger.questions.TheBookStatus;
import org.alexrieger.tasks.SearchBookByName;
import org.alexrieger.util.TestData;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsStringIgnoringCase;

public class ConsultaDisponibilidadStepDefinitions {

    @Y("que el sistema tiene el libro {string} en estado {string}")
    public void queElSistemaTieneElLibroEnEstado(String bookName, String state) {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        actor.remember("precondition.book.name", bookName);
        actor.remember("precondition.book.state", state);
    }

    @Y("que el sistema no tiene registrado el libro {string}")
    public void queElSistemaNoTieneRegistradoElLibro(String bookName) {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        actor.remember("precondition.book.notRegistered", bookName);
    }

    @Cuando("el bibliotecario busca el libro {string}")
    public void elBibliotecarioBuscaElLibro(String bookName) {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        actor.attemptsTo(SearchBookByName.withName(bookName));
    }

    @Entonces("el bibliotecario ve que el libro está {string}")
    public void elBibliotecarioVeQueElLibroEsta(String expectedStatus) {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);

        if ("prestado".equalsIgnoreCase(expectedStatus)) {
            actor.should(seeThat(
                TheBookStatus.displayed(),
                anyOf(
                    containsStringIgnoringCase("prestado"),
                    containsStringIgnoringCase("préstamo activo"),
                    containsStringIgnoringCase("on_loan"),
                    containsStringIgnoringCase("on loan")
                )
            ));
            return;
        }

        if ("disponible".equalsIgnoreCase(expectedStatus)) {
            actor.should(seeThat(
                TheBookStatus.displayed(),
                anyOf(
                    containsStringIgnoringCase("disponible"),
                    containsStringIgnoringCase("available"),
                    containsStringIgnoringCase("se considera disponible"),
                    containsStringIgnoringCase("returned")
                )
            ));
            return;
        }

        if ("sin resultados".equalsIgnoreCase(expectedStatus)) {
            actor.should(seeThat(
                TheBookStatus.displayed(),
                anyOf(
                    containsStringIgnoringCase("sin resultados"),
                    containsStringIgnoringCase("no se encontraron libros con ese nombre")
                )
            ));
            return;
        }

        actor.should(seeThat(
                TheBookStatus.displayed(),
                containsStringIgnoringCase(expectedStatus)
        ));
    }
}
