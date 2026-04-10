package org.alexrieger.stepdefinitions;

import io.cucumber.java.es.Y;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.actors.OnStage;
import org.alexrieger.util.TestData;

public class RechazoPrestamoDeudaStepDefinitions {

    @Y("que el sistema tiene al lector {string} con deuda pendiente de {string}")
    public void queElSistemaTieneAlLectorConDeudaPendienteDe(String readerName, String debtAmount) {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        actor.remember("precondition.reader.withDebt.name", readerName);
        actor.remember("precondition.reader.withDebt.amount", debtAmount);
    }

    @Y("que el sistema tiene al lector {string} con {string} {string} y deuda pendiente")
    public void queElSistemaTieneAlLectorConDocumentoYDeudaPendiente(String readerName, String typeId, String readerId) {
        Actor actor = OnStage.theActorCalled(TestData.ACTOR_BIBLIOTECARIO);
        actor.remember("precondition.reader.withDebt.name", readerName);
        actor.remember("precondition.reader.withDebt.typeId", typeId);
        actor.remember("precondition.reader.withDebt.id", readerId);
    }
}
