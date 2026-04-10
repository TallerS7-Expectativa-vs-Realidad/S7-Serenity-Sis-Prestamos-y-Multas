package org.alexrieger.util;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.targets.Target;

public final class SelectOptionSupport {

    private SelectOptionSupport() {
    }

    public static void selectMatchingOption(Actor actor, Target selectTarget, String... candidates) {
        List<String> normalizedCandidates = Arrays.stream(candidates)
                .filter(Objects::nonNull)
                .map(String::trim)
                .filter(value -> !value.isEmpty())
                .map(SelectOptionSupport::normalize)
                .distinct()
                .toList();

        Select select = new Select(selectTarget.resolveFor(actor));

        for (WebElement option : select.getOptions()) {
            String optionText = normalize(option.getText());
            String optionValue = normalize(option.getAttribute("value"));
            boolean hasOptionValue = !optionValue.isEmpty();

            boolean matches = normalizedCandidates.stream().anyMatch(candidate ->
                    optionText.equals(candidate)
                    || (hasOptionValue && optionValue.equals(candidate))
                            || optionText.contains(candidate)
                    || (hasOptionValue && optionValue.contains(candidate))
                    || (hasOptionValue && candidate.contains(optionValue))
            );

            if (matches) {
                select.selectByVisibleText(option.getText());
                return;
            }
        }

        throw new NoSuchElementException(
                "Cannot locate option for candidates " + normalizedCandidates
                        + ". Available options: " + select.getOptions().stream()
                        .map(option -> option.getText() + " [value=" + option.getAttribute("value") + "]")
                        .collect(Collectors.joining(", "))
        );
    }

    private static String normalize(String value) {
        if (value == null) {
            return "";
        }

        String sanitized = Normalizer.normalize(value, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .trim();

        return sanitized.replaceAll("\\s+", " ");
    }
}