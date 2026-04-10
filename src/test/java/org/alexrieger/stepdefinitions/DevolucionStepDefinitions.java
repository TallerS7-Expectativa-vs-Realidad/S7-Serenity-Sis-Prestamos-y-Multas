package org.alexrieger.stepdefinitions;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import org.alexrieger.questions.TheFineInfo;
import org.alexrieger.tasks.FillReturnForm;
import org.alexrieger.tasks.SubmitReturnForm;
import org.alexrieger.util.TestData;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.is;

public class DevolucionStepDefinitions {

    private String returnDate = TestData.RETURN_DATE_ON_TIME;
    private String typeId = TestData.READER_TYPE_ID_CLEAN;
    private String bookId = "";
    private String readerId = "";
    private String readerName = "";

    @Y("que el sistema tiene un préstamo activo del libro {string} para el lector {string} con fecha límite vigente")
    public void queElSistemaTieneUnPrestamoActivoDelLibroParaElLectorConFechaLimiteVigente(String bookTitle, String currentReaderName) {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        returnDate = TestData.RETURN_DATE_ON_TIME;
        bookId = resolveBookId(bookTitle, currentReaderName);
        readerId = resolveReaderId(bookTitle, currentReaderName);
        typeId = resolveTypeId(bookTitle, currentReaderName);
        readerName = currentReaderName;

        actor.remember("precondition.return.active.bookTitle", bookTitle);
        actor.remember("precondition.return.active.readerName", currentReaderName);
    }

    @Y("que el sistema tiene un préstamo vencido hace {string} días del libro {string} para el lector {string}")
    public void queElSistemaTieneUnPrestamoVencidoHaceDiasDelLibroParaElLector(String overdueDays, String bookTitle, String currentReaderName) {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        returnDate = TestData.RETURN_DATE_LATE_8_DAYS;
        bookId = resolveBookId(bookTitle, currentReaderName);
        readerId = resolveReaderId(bookTitle, currentReaderName);
        typeId = resolveTypeId(bookTitle, currentReaderName);
        readerName = currentReaderName;

        actor.remember("precondition.return.overdue.days", overdueDays);
        actor.remember("precondition.return.overdue.bookTitle", bookTitle);
        actor.remember("precondition.return.overdue.readerName", currentReaderName);
    }

    @Cuando("el bibliotecario registra la devolución del libro {string}")
    public void elBibliotecarioRegistraLaDevolucionDelLibro(String bookTitle) {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        actor.remember("precondition.return.requestedBookTitle", bookTitle);

        actor.attemptsTo(
                FillReturnForm.withData(returnDate, typeId, bookId, readerId, readerName),
                SubmitReturnForm.now()
        );
    }

    @Y("el bibliotecario ve que no se generó multa")
    public void elBibliotecarioVeQueNoSeGeneroMulta() {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        actor.should(seeThat(TheFineInfo.isDisplayed(), is(false)));
    }

    @Y("el bibliotecario visualiza el valor de la multa generada")
    public void elBibliotecarioVisualizaElValorDeLaMultaGenerada() {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        actor.should(seeThat(TheFineInfo.isDisplayed(), is(true)));
    }

    @Y("el bibliotecario ve la deuda pendiente del lector")
    public void elBibliotecarioVeLaDeudaPendienteDelLector() {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        actor.should(seeThat(TheFineInfo.isDisplayed(), is(true)));
    }

    private String resolveBookId(String bookTitle, String readerName) {
        if (TestData.LIVE_RETURN_ON_TIME_BOOK_TITLE.equalsIgnoreCase(bookTitle)
                && TestData.LIVE_RETURN_ON_TIME_READER_NAME.equalsIgnoreCase(readerName)) {
            return TestData.LIVE_RETURN_ON_TIME_BOOK_ID;
        }

        if (TestData.LIVE_RETURN_LATE_BOOK_TITLE.equalsIgnoreCase(bookTitle)
                && TestData.LIVE_RETURN_LATE_READER_NAME.equalsIgnoreCase(readerName)) {
            return TestData.LIVE_RETURN_LATE_BOOK_ID;
        }

        return "";
    }

    private String resolveReaderId(String bookTitle, String readerName) {
        if (TestData.LIVE_RETURN_ON_TIME_BOOK_TITLE.equalsIgnoreCase(bookTitle)
                && TestData.LIVE_RETURN_ON_TIME_READER_NAME.equalsIgnoreCase(readerName)) {
            return TestData.LIVE_RETURN_ON_TIME_READER_ID;
        }

        if (TestData.LIVE_RETURN_LATE_BOOK_TITLE.equalsIgnoreCase(bookTitle)
                && TestData.LIVE_RETURN_LATE_READER_NAME.equalsIgnoreCase(readerName)) {
            return TestData.LIVE_RETURN_LATE_READER_ID;
        }

        return "";
    }

    private String resolveTypeId(String bookTitle, String readerName) {
        if (TestData.LIVE_RETURN_ON_TIME_BOOK_TITLE.equalsIgnoreCase(bookTitle)
                && TestData.LIVE_RETURN_ON_TIME_READER_NAME.equalsIgnoreCase(readerName)) {
            return TestData.LIVE_RETURN_ON_TIME_READER_TYPE;
        }

        if (TestData.LIVE_RETURN_LATE_BOOK_TITLE.equalsIgnoreCase(bookTitle)
                && TestData.LIVE_RETURN_LATE_READER_NAME.equalsIgnoreCase(readerName)) {
            return TestData.LIVE_RETURN_LATE_READER_TYPE;
        }

        return TestData.READER_TYPE_ID_CLEAN;
    }
}
