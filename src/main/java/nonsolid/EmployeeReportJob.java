package nonsolid;

import java.io.IOException;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import jdk.incubator.http.HttpClient;
import jdk.incubator.http.HttpRequest;
import jdk.incubator.http.HttpResponse;

public class EmployeeReportJob implements Runnable {

    public void run() {
        try {
            Path tempEmployeeDataFile = downloadEmployeeFile();
            List<String> csvRows = process(tempEmployeeDataFile);
            outputReport(csvRows);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Path downloadEmployeeFile() throws IOException, InterruptedException {
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

    private List<String> process(final Path employeeDataFile) throws IOException {
        return Files.readAllLines(employeeDataFile)
            .stream()
            .map(line -> convertToCsvRow(line))
            .collect(Collectors.toUnmodifiableList());
    }

    private static String convertToCsvRow(final String employeeDataFileLine) {
        var parts = employeeDataFileLine.split(" ");
        return parts[2] + parts[3] + parts[0];
    }

    private void outputReport(final List<String> csvLines) throws IOException {
        final Path reportPath = Paths.get("/reportDirectory/" + Instant.now().toEpochMilli() + ".csv");
        Files.write(reportPath, List.of("FirstName,LastName,EmployeeId"), StandardOpenOption.CREATE);
        Files.write(reportPath, csvLines, StandardOpenOption.APPEND);
    }
}
