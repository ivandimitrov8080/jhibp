package uk.co.ivandimitrov.jhibp.service;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HibpPasswordService {

    private HibpPasswordService() {
    }

    private static final HibpPasswordService INSTANCE = new HibpPasswordService();

    private static final HttpClient CLIENT = HttpClient.newBuilder()
                                                        .version(HttpClient.Version.HTTP_2)
                                                        .build();
    private static final URI PASSWORD_API_URI = createUri();

    private static URI createUri() {
        try {
            return new URI("https://api.pwnedpasswords.com/range/");
        } catch (final URISyntaxException e) {
            return null;
        }
    }

    public static HibpPasswordService getInstance() {
        return INSTANCE;
    }

    public HttpResponse<String> callHibpPasswordApi(final String firstFiveHashedPasswordChars) throws IOException, InterruptedException {
        return CLIENT.send(buildRequestForHash(firstFiveHashedPasswordChars), HttpResponse.BodyHandlers.ofString());
    }

    private HttpRequest buildRequestForHash(final String firstFiveHashedPasswordChars) {
        return HttpRequest.newBuilder()
                .GET()
                .uri(PASSWORD_API_URI.resolve(firstFiveHashedPasswordChars))
                .build();
    }

}
