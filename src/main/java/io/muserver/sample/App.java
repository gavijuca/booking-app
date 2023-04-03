package io.muserver.sample;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.google.gson.Gson;
import io.muserver.*;
import io.muserver.handlers.ResourceHandlerBuilder;
import io.muserver.rest.BasicAuthSecurityFilter;
import io.muserver.rest.RestHandlerBuilder;
import io.muserver.sample.conf.MyAuthorizer;
import io.muserver.sample.conf.MyUserPassAuthenticator;
import io.muserver.sample.dto.BookingDTO;
import io.muserver.sample.resource.BookingResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonMap;

public class App {
    private static final Logger log = LoggerFactory.getLogger(App.class);

    private static final Map<String, Map<String, List<String>>> usersToPasswordToRoles = new HashMap<>();
    public static void main(String[] args) {
        log.info("Starting mu-server-sample app");

        usersToPasswordToRoles.put("admin", singletonMap("admin", asList("User", "Admin")));
        usersToPasswordToRoles.put("user", singletonMap("user", asList("User")));

        MyUserPassAuthenticator authenticator = new MyUserPassAuthenticator(usersToPasswordToRoles);
        MyAuthorizer authorizer = new MyAuthorizer();


        MuServer server = MuServerBuilder.httpServer()
            .withHttpPort(18080)
            .withHttpsPort(18443)
            .withHttpsConfig(SSLContextBuilder.sslContext()
                    .withKeystoreFromClasspath("/keystore.jks")
                    .withKeystoreType("JKS")
                    .withKeystorePassword("Very5ecure")
                    .withKeyPassword("ActuallyNotSecure")
                    .build()
            )
            .addHandler(new RequestLoggingHandler())
            /*.addHandler(Method.GET, "/current-time", (request, response, pathParams) -> {
                response.status(200);
                response.contentType(ContentTypes.TEXT_PLAIN);
                response.write(Instant.now().toString());
            })*/
            .addHandler(RestHandlerBuilder.restHandler(new BookingResource())
                .addRequestFilter(new BasicAuthSecurityFilter("my-bank", authenticator, authorizer))
                .addCustomWriter(new JacksonJaxbJsonProvider())
                .addCustomReader(new JacksonJaxbJsonProvider())
            )
            //.addHandler(Method.GET, "/api/booking", App::getBooking)
            //.addHandler(Method.POST, "/api/booking", App::postBooking)
            .addHandler(ResourceHandlerBuilder.fileOrClasspath("src/main/resources/web", "/web")
                .withPathToServeFrom("/")
                .withDefaultFile("index.html")
                .build())
            .start();

        log.info("Server started at " + server.httpUri() + " and " + server.httpsUri());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("Shutting down...");
            server.stop();
            log.info("Shut down complete.");
        }));

    }

    private static void getBooking(MuRequest request, MuResponse response, Map<String, String> pathParams) {
        response.status(200);
        response.contentType(ContentTypes.APPLICATION_JSON);
        response.write(Instant.now().toString());
    }

    private static void postBooking(MuRequest request, MuResponse response, Map<String, String> pathParams) {
        response.status(201);
        response.contentType(ContentTypes.APPLICATION_JSON);
        try {
            BookingDTO bookingDto = new Gson().fromJson(new InputStreamReader(request.inputStream().get(), "UTF-8"), BookingDTO.class);
            response.write(Instant.now().toString());
        } catch (UnsupportedEncodingException e) {
            response.status(400);
        }


    }

    private static class RequestLoggingHandler implements MuHandler {
        public boolean handle(MuRequest request, MuResponse response) {
            log.info(request.method() + " " + request.uri());
            return false;
        }
    }
}
