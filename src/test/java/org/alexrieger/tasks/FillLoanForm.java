package org.alexrieger.tasks;

import org.alexrieger.ui.LoanFormSection;
import org.alexrieger.util.SelectOptionSupport;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.actions.Click;
import net.serenitybdd.screenplay.actions.Enter;

public class FillLoanForm implements Task {

    private final String idBook;
    private final String title;
    private final String typeId;
    private final String readerId;
    private final String readerName;
    private final String loanDays;

    public FillLoanForm(String idBook,
                        String title,
                        String typeId,
                        String readerId,
                        String readerName,
                        String loanDays) {
        this.idBook = idBook;
        this.title = title;
        this.typeId = typeId;
        this.readerId = readerId;
        this.readerName = readerName;
        this.loanDays = loanDays;
    }

    @Override
    @Step("{0} fills the loan form for book #title and reader #readerName")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(
                Enter.theValue(idBook).into(LoanFormSection.INPUT_BOOK_ID),
                Enter.theValue(title).into(LoanFormSection.INPUT_BOOK_TITLE),
                Enter.theValue(readerId).into(LoanFormSection.INPUT_READER_ID),
                Enter.theValue(readerName).into(LoanFormSection.INPUT_READER_NAME)
        );

        selectDocumentType(actor, typeId);
        selectLoanDays(actor, loanDays);
    }

    public static FillLoanForm withData(String idBook,
                                        String title,
                                        String typeId,
                                        String readerId,
                                        String readerName,
                                        String loanDays) {
        return Tasks.instrumented(FillLoanForm.class, idBook, title, typeId, readerId, readerName, loanDays);
    }

    private <T extends Actor> void selectDocumentType(T actor, String rawTypeId) {
        String normalizedType = normalizeDocumentType(rawTypeId);
        String optionLabel = expandDocumentTypeLabel(normalizedType);

        SelectOptionSupport.selectMatchingOption(
                actor,
                LoanFormSection.SELECT_TYPE_ID,
                rawTypeId,
                normalizedType,
                optionLabel
        );
    }

    private <T extends Actor> void selectLoanDays(T actor, String rawLoanDays) {
        try {
            SelectOptionSupport.selectMatchingOption(actor, LoanFormSection.SELECT_LOAN_DAYS, rawLoanDays, rawLoanDays + " días");
            return;
        } catch (Throwable ignored) {
        }

        actor.attemptsTo(Click.on(LoanFormSection.LOAN_DAY_OPTION(rawLoanDays)));
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
