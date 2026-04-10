package org.alexrieger.stepdefinitions.hooks;

import io.cucumber.java.Before;
import net.serenitybdd.screenplay.actors.OnStage;
import net.serenitybdd.screenplay.actors.OnlineCast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Hook {

    private static final Path RESET_SCRIPT_PATH = Paths.get("sql", "reset_serenity_repeatable_state.sql");
    private static final String CONTAINER_NAME = "s7-sistema-de-prestamos-y-multas-db-1";

    @Before
    public void startStage() {
        OnStage.setTheStage(new OnlineCast());
    }

    @Before(value = "@consulta or @vencidos or @devolucion or @deuda or @prestamo", order = 0)
    public void resetRepeatableDataset() {
        if (!Files.exists(RESET_SCRIPT_PATH)) {
            throw new IllegalStateException("Reset script not found: " + RESET_SCRIPT_PATH.toAbsolutePath());
        }

        ProcessBuilder processBuilder = new ProcessBuilder(buildResetCommand());
        processBuilder.redirectErrorStream(true);

        try {
            Process process = processBuilder.start();

            try (OutputStream processInput = process.getOutputStream()) {
                Files.copy(RESET_SCRIPT_PATH, processInput);
            }

            boolean completed = process.waitFor(Duration.ofSeconds(60).toMillis(), java.util.concurrent.TimeUnit.MILLISECONDS);
            String output = readFully(process.getInputStream());

            if (!completed) {
                process.destroyForcibly();
                throw new IllegalStateException("Timed out while resetting the repeatable Serenity dataset.");
            }

            if (process.exitValue() != 0) {
                throw new IllegalStateException("Failed to reset the repeatable Serenity dataset. Output: " + output);
            }
        } catch (InterruptedException exception) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Could not reset the repeatable Serenity dataset.", exception);
        } catch (IOException exception) {
            throw new IllegalStateException("Could not reset the repeatable Serenity dataset.", exception);
        }
    }

    private List<String> buildResetCommand() {
        List<String> command = new ArrayList<>();

        if (isWindows()) {
            command.add("wsl");
        }

        command.add("docker");
        command.add("exec");
        command.add("-i");
        command.add(CONTAINER_NAME);
        command.add("psql");
        command.add("-U");
        command.add("postgres");
        command.add("-d");
        command.add("postgres");

        return command;
    }

    private boolean isWindows() {
        return System.getProperty("os.name", "").toLowerCase().contains("win");
    }

    private String readFully(InputStream inputStream) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        inputStream.transferTo(outputStream);
        return outputStream.toString();
    }
}
