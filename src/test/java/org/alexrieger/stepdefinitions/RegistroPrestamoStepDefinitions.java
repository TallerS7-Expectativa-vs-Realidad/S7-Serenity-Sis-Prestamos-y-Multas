package org.alexrieger.stepdefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import java.util.Map;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import org.alexrieger.questions.TheDueDate;
import org.alexrieger.tasks.FillLoanForm;
import org.alexrieger.tasks.SubmitLoanForm;
import org.alexrieger.util.TestData;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.blankOrNullString;

public class RegistroPrestamoStepDefinitions {

    @Y("que el sistema tiene el libro {string} identificado con {string} en estado {string}")
    public void queElSistemaTieneElLibroIdentificadoConEnEstado(String title, String idBook, String state) {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        actor.remember("precondition.loan.book.title", title);
        actor.remember("precondition.loan.book.id", idBook);
        actor.remember("precondition.loan.book.state", state);
    }

    @Y("que el sistema tiene al lector {string} con {string} {string} sin deuda pendiente")
    public void queElSistemaTieneAlLectorConSinDeudaPendiente(String readerName, String typeId, String readerId) {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        actor.remember("precondition.reader.name", readerName);
        actor.remember("precondition.reader.typeId", typeId);
        actor.remember("precondition.reader.id", readerId);
    }

    @Cuando("el bibliotecario diligencia el formulario de préstamo con:")
    public void elBibliotecarioDiligenciaElFormularioDePrestamoCon(DataTable dataTable) {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        Map<String, String> data = dataTable.asMap(String.class, String.class);

        String idLibro = data.get("idLibro");
        String tituloLibro = data.get("tituloLibro");
        String tipoDocumento = data.get("tipoDocumento");
        String idLector = data.get("idLector");
        String nombreLector = data.get("nombreLector");
        String diasPrestamo = data.get("diasPrestamo");

        actor.attemptsTo(FillLoanForm.withData(
                idLibro,
                tituloLibro,
                tipoDocumento,
                idLector,
                nombreLector,
                diasPrestamo
        ));
    }

    @Y("el bibliotecario confirma el registro del préstamo")
    public void elBibliotecarioConfirmaElRegistroDelPrestamo() {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        actor.attemptsTo(SubmitLoanForm.now());
    }

    @Entonces("el bibliotecario ve la fecha límite del préstamo")
    public void elBibliotecarioVeLaFechaLimiteDelPrestamo() {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);

        actor.should(seeThat(
                TheDueDate.displayed(),
                not(blankOrNullString())
        ));
    }
}
