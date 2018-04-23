package pass1;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

public class EmployeeFileDownloader {

    public Path downloadEmployeeFile() throws IOException, InterruptedException {
        return HttpClient.newBuilder()
            .authenticator(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("admin", "superSecretPassword".toCharArray());
                }
            })
            .build()
            .send(HttpRequest.newBuilder(URI.create("http://employees.mycoolcompany.com"))
                .timeout(Duration.ofSeconds(2))
                .GET()
                .build(), HttpResponse.BodyHandler.asFile(Files.createTempFile("employee", "data")))
            .body();
    }
}
