package org.alexrieger.stepdefinitions;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import org.alexrieger.questions.TheDebtStatus;
import org.alexrieger.questions.TheReaderCanBorrow;
import org.alexrieger.tasks.ConsultReaderDebt;
import org.alexrieger.tasks.PayDebt;
import org.alexrieger.util.TestData;

import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.containsStringIgnoringCase;
import static org.hamcrest.Matchers.is;

public class PagoMultaRehabilitacionStepDefinitions {

    @Y("que el sistema tiene al lector {string} con deuda pendiente por multa")
    public void queElSistemaTieneAlLectorConDeudaPendientePorMulta(String readerName) {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        actor.remember("precondition.debt.reader.pending", readerName);
    }

    @Y("que el sistema tiene al lector {string} con deuda previamente pagada")
    public void queElSistemaTieneAlLectorConDeudaPreviamentePagada(String readerName) {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        actor.remember("precondition.debt.reader.paid", readerName);
    }

    @Cuando("el bibliotecario busca la deuda del lector {string}")
    public void elBibliotecarioBuscaLaDeudaDelLector(String readerName) {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        actor.attemptsTo(ConsultReaderDebt.forReader(resolveReaderId(readerName), resolveTypeId(readerName), readerName));
    }

    @Y("el bibliotecario registra el pago total de la deuda")
    public void elBibliotecarioRegistraElPagoTotalDeLaDeuda() {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        actor.attemptsTo(PayDebt.now());
    }

    @Y("el bibliotecario ve que el estado de la deuda es {string}")
    public void elBibliotecarioVeQueElEstadoDeLaDeudaEs(String expectedDebtState) {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);

        if ("pagada".equalsIgnoreCase(expectedDebtState)) {
            actor.should(seeThat(
                    TheDebtStatus.displayed(),
                    anyOf(
                            containsStringIgnoringCase(expectedDebtState),
                            containsStringIgnoringCase("pago registrado exitosamente"),
                            containsStringIgnoringCase("deuda pagada exitosamente"),
                            containsStringIgnoringCase("rehabilitado")
                    )
            ));
            return;
        }

        actor.should(seeThat(TheDebtStatus.displayed(), containsStringIgnoringCase(expectedDebtState)));
    }

    @Y("el bibliotecario ve que el lector quedó {string}")
    public void elBibliotecarioVeQueElLectorQuedo(String expectedReaderStatus) {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);

        if ("habilitado".equalsIgnoreCase(expectedReaderStatus)) {
            actor.should(seeThat(TheReaderCanBorrow.now(), is(true)));
            return;
        }

        actor.should(seeThat(TheReaderCanBorrow.now(), is(false)));
    }

    private String resolveReaderId(String readerName) {
        if (readerName == null) {
            return TestData.READER_ID_CLEAN;
        }

        if (readerName.equalsIgnoreCase(TestData.LIVE_READER_NAME_PENDING_DEBT)) {
            return TestData.LIVE_READER_ID_PENDING_DEBT;
        }

        if (readerName.equalsIgnoreCase(TestData.LIVE_READER_NAME_REHABILITATED)) {
            return TestData.LIVE_READER_ID_REHABILITATED;
        }

        if (readerName.equalsIgnoreCase(TestData.READER_NAME_WITH_DEBT)) {
            return TestData.READER_ID_WITH_DEBT;
        }

        return TestData.READER_ID_CLEAN;
    }

    private String resolveTypeId(String readerName) {
        if (readerName == null) {
            return TestData.READER_TYPE_ID_CLEAN;
        }

        if (readerName.equalsIgnoreCase(TestData.LIVE_READER_NAME_PENDING_DEBT)) {
            return TestData.LIVE_READER_TYPE_PENDING_DEBT;
        }

        if (readerName.equalsIgnoreCase(TestData.LIVE_READER_NAME_REHABILITATED)) {
            return TestData.LIVE_READER_TYPE_REHABILITATED;
        }

        if (readerName.equalsIgnoreCase(TestData.READER_NAME_WITH_DEBT)) {
            return TestData.READER_TYPE_ID_DEBT;
        }

        return TestData.READER_TYPE_ID_CLEAN;
    }
}
