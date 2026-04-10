package org.alexrieger.stepdefinitions;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.questions.Visibility;
import org.alexrieger.questions.TheOverdueLoansCount;
import org.alexrieger.tasks.ConsultOverdueLoans;
import org.alexrieger.ui.OverdueLoansSection;
import org.alexrieger.util.TestData;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;

public class ConsultaVencidosStepDefinitions {

    @Y("que el sistema tiene préstamos vencidos registrados")
    public void queElSistemaTienePrestamosVencidosRegistrados() {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        actor.remember("precondition.overdue.expected", "with-results");
    }

    @Y("que el sistema no tiene préstamos vencidos registrados")
    public void queElSistemaNoTienePrestamosVencidosRegistrados() {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        actor.remember("precondition.overdue.expected", "empty");
    }

    @Cuando("el bibliotecario consulta el listado de préstamos vencidos")
    public void elBibliotecarioConsultaElListadoDePrestamosVencidos() {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        actor.attemptsTo(ConsultOverdueLoans.now());
    }

    @Entonces("el bibliotecario visualiza la tabla de préstamos vencidos")
    public void elBibliotecarioVisualizaLaTablaDePrestamosVencidos() {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        actor.should(seeThat(TheOverdueLoansCount.value(), greaterThan(0)));
    }

    @Y("el bibliotecario ve filas con datos de libro, lector y fecha límite")
    public void elBibliotecarioVeFilasConDatosDeLibroLectorYFechaLimite() {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);

        actor.should(seeThat(
                "columna libro visible",
                currentActor -> Visibility.of(OverdueLoansSection.CELL_BOOK_TITLE).answeredBy(currentActor),
                is(true)
        ));
        actor.should(seeThat(
                "columna lector visible",
                currentActor -> Visibility.of(OverdueLoansSection.CELL_READER_NAME).answeredBy(currentActor),
                is(true)
        ));
        actor.should(seeThat(
                "columna fecha limite visible",
                currentActor -> Visibility.of(OverdueLoansSection.CELL_DUE_DATE).answeredBy(currentActor),
                is(true)
        ));
    }
}
