package pass2;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.nio.file.Path;
import java.time.Duration;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

public class EmployeeFileDownloader {

    private final DownloaderConfig m_downloaderConfig;

    public EmployeeFileDownloader() {
        this(new DownloaderConfig(EmployeeFileDownloader.class.getResource("/employeeFileDownloader.properties")));
    }

    public EmployeeFileDownloader(final DownloaderConfig downloaderConfig) {
        m_downloaderConfig = downloaderConfig;
    }

    public Path downloadEmployeeFile() throws IOException, InterruptedException {
        return HttpClient.newBuilder()
            .authenticator(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(m_downloaderConfig.getUsername(), m_downloaderConfig.getPassword());
                }
            })
            .build()
            .send(HttpRequest.newBuilder(URI.create(m_downloaderConfig.getServiceUri()))
                .timeout(Duration.ofSeconds(2))
                .GET()
                .build(), HttpResponse.BodyHandler.asFile(m_downloaderConfig.getOutputFileLocation()))
            .body();
    }
}
