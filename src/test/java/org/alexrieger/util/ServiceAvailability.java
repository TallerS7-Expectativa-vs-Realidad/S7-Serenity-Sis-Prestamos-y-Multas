package org.alexrieger.util;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;

public final class ServiceAvailability {

    private ServiceAvailability() {
    }

    public static void assertReachable(String serviceName, String url, int timeoutSeconds) {
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) URI.create(url).toURL().openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(timeoutSeconds * 1000);
            connection.setReadTimeout(timeoutSeconds * 1000);
            connection.setInstanceFollowRedirects(true);
            connection.connect();
            connection.getResponseCode();
        } catch (IOException exception) {
            throw new IllegalStateException(
                    "El servicio '" + serviceName + "' no esta disponible en " + url
                            + ". Levanta el SUT antes de ejecutar Serenity.",
                    exception
            );
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}