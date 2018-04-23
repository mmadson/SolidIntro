package pass2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeReportGenerator {

    private final EmployeeReportGeneratorConfig m_reportGeneratorConfig;

    public EmployeeReportGenerator() {
        this(new EmployeeReportGeneratorConfig(EmployeeReportGenerator.class.getResource("employeeReportGenerator.properties")));
    }

    public EmployeeReportGenerator(final EmployeeReportGeneratorConfig reportGeneratorConfig) {
        m_reportGeneratorConfig = reportGeneratorConfig;
    }

    public void generateReport(final Path employeeDataFile) throws IOException {
        outputReport(process(employeeDataFile));
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
        final Path reportPath = Paths.get(m_reportGeneratorConfig.getReportDirectory() + Instant.now().toEpochMilli() + ".csv");
        Files.write(reportPath, List.of("FirstName,LastName,EmployeeId"), StandardOpenOption.CREATE);
        Files.write(reportPath, csvLines, StandardOpenOption.APPEND);
    }
}
