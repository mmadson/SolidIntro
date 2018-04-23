package pass3;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Properties;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

public class HttpFileDownloader {

    private final Properties m_downloadProperties;

    public HttpFileDownloader(final Properties downloadProperties) {
        m_downloadProperties = downloadProperties;
    }

    public Path downloadFile() {
        try {
            return HttpClient.newBuilder()
                .authenticator(new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(m_downloadProperties.getProperty("username"), m_downloadProperties.getProperty("password").toCharArray());
                    }
                })
                .build()
                .send(HttpRequest.newBuilder(URI.create(m_downloadProperties.getProperty("serviceUri")))
                    .timeout(Duration.ofSeconds(2))
                    .GET()
                    .build(), HttpResponse.BodyHandler.asFile(Paths.get(m_downloadProperties.getProperty("downloadPath"))))
                .body();
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
