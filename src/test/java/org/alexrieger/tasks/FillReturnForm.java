package org.alexrieger.tasks;

import org.alexrieger.ui.ReturnLoanSection;
import org.alexrieger.util.SelectOptionSupport;

import net.serenitybdd.annotations.Step;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.Tasks;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.serenitybdd.screenplay.actions.Enter;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class FillReturnForm implements Task {

    private final String returnDate;
    private final String typeId;
    private final String bookId;
    private final String readerId;
    private final String readerName;

    public FillReturnForm(String returnDate,
                          String typeId,
                          String bookId,
                          String readerId,
                          String readerName) {
        this.returnDate = returnDate;
        this.typeId = typeId;
        this.bookId = bookId;
        this.readerId = readerId;
        this.readerName = readerName;
    }

    @Override
    @Step("{0} fills the return form")
    public <T extends Actor> void performAs(T actor) {
        if (hasText(returnDate)) {
            setDateIfPresent(actor, ReturnLoanSection.INPUT_RETURN_DATE, returnDate);
        }

        if (hasText(typeId)) {
            selectDocumentType(actor, typeId);
        }

        if (hasText(bookId)) {
            enterIfPresent(actor, ReturnLoanSection.INPUT_BOOK_ID_RETURN, bookId);
        }

        if (hasText(readerId)) {
            enterIfPresent(actor, ReturnLoanSection.INPUT_READER_ID_RETURN, readerId);
        }

        if (hasText(readerName)) {
            enterIfPresent(actor, ReturnLoanSection.INPUT_READER_NAME_RETURN, readerName);
        }
    }

    public static FillReturnForm withData(String returnDate,
                                          String typeId,
                                          String bookId,
                                          String readerId,
                                          String readerName) {
        return Tasks.instrumented(FillReturnForm.class, returnDate, typeId, bookId, readerId, readerName);
    }

    private boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private <T extends Actor> void selectDocumentType(T actor, String rawTypeId) {
        String normalizedType = normalizeDocumentType(rawTypeId);
        String optionLabel = expandDocumentTypeLabel(normalizedType);

        SelectOptionSupport.selectMatchingOption(
                actor,
                ReturnLoanSection.SELECT_TYPE_ID_RETURN,
                rawTypeId,
                normalizedType,
                optionLabel
        );
    }

    private <T extends Actor> void enterIfPresent(T actor, net.serenitybdd.screenplay.targets.Target target, String value) {
        if (!target.resolveAllFor(actor).isEmpty()) {
            actor.attemptsTo(Enter.theValue(value).into(target));
        }
    }

    private <T extends Actor> void setDateIfPresent(T actor,
                                                    net.serenitybdd.screenplay.targets.Target target,
                                                    String value) {
        if (target.resolveAllFor(actor).isEmpty()) {
            return;
        }

        WebElement dateInput = target.resolveFor(actor);
        BrowseTheWeb.as(actor).waitFor(dateInput).isVisible();

        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) BrowseTheWeb.as(actor).getDriver();
        javascriptExecutor.executeScript(
            "const valueSetter = Object.getOwnPropertyDescriptor(window.HTMLInputElement.prototype, 'value').set;"
                + "valueSetter.call(arguments[0], arguments[1]);"
                + "arguments[0].setAttribute('value', arguments[1]);"
                + "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));"
                        + "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));"
                        + "arguments[0].dispatchEvent(new Event('blur', { bubbles: true }));",
                dateInput,
                value
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
