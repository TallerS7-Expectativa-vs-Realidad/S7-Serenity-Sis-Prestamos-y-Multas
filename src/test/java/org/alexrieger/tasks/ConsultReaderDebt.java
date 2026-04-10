package org.alexrieger.tasks;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;
import org.alexrieger.ui.DebtsSection;
import org.alexrieger.util.SelectOptionSupport;

public class ConsultReaderDebt implements Task {

    private final String readerId;
    private final String typeId;
    private final String readerName;

    public ConsultReaderDebt(String readerId, String typeId, String readerName) {
        this.readerId = readerId;
        this.typeId = typeId;
        this.readerName = readerName;
    }

    @Override
    @Step("{0} consults debts for reader #readerId")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue(readerId).into(DebtsSection.INPUT_READER_ID_DEBT)
        );

        enterIfPresent(actor, DebtsSection.INPUT_READER_NAME_DEBT, readerName);

        selectDocumentType(actor, typeId);

        actor.attemptsTo(Click.on(DebtsSection.BTN_SEARCH_DEBT));
    }

    public static ConsultReaderDebt forReader(String readerId, String typeId, String readerName) {
        return Tasks.instrumented(ConsultReaderDebt.class, readerId, typeId, readerName);
    }

    private <T extends Actor> void enterIfPresent(T actor, net.serenitybdd.screenplay.targets.Target target, String value) {
        if (value != null && !value.trim().isEmpty() && !target.resolveAllFor(actor).isEmpty()) {
            actor.attemptsTo(Enter.theValue(value).into(target));
        }
    }

    private <T extends Actor> void selectDocumentType(T actor, String rawTypeId) {
        String normalizedType = normalizeDocumentType(rawTypeId);
        String optionLabel = expandDocumentTypeLabel(normalizedType);

        SelectOptionSupport.selectMatchingOption(
                actor,
                DebtsSection.SELECT_TYPE_ID_DEBT,
                rawTypeId,
                normalizedType,
                optionLabel
        );
    }

    private String normalizeDocumentType(String rawTypeId) {
        if (rawTypeId == null) {
            return "CI";
        }

        String normalized = rawTypeId.trim().toLowerCase();
        if (normalized.contains("dni")) {
            return "DNI";
        }
        if (normalized.contains("ci") || normalized.contains("identidad") || normalized.contains("ciudadan")) {
            return "CI";
        }
        if (normalized.contains("extranjer")) {
            return "DNI";
        }
        return rawTypeId.trim();
    }

    private String expandDocumentTypeLabel(String normalizedType) {
        if ("DNI".equalsIgnoreCase(normalizedType)) {
            return "Documento Nacional de Identificación (DNI)";
        }
        if ("CI".equalsIgnoreCase(normalizedType)) {
            return "Cédula de Identidad (CI)";
        }
        return normalizedType;
    }
}
