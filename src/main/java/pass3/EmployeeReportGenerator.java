package pass3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class EmployeeReportGenerator {

    private final Properties m_reportGeneratorProperties;

    public EmployeeReportGenerator(final Properties reportGeneratorProperties) {
        m_reportGeneratorProperties = reportGeneratorProperties;
    }

    public void generateReport(final List<Employee> employees) {
        try {
            final Path reportPath = Paths.get(m_reportGeneratorProperties.getProperty("reportDirectory") + Instant.now().toEpochMilli() + ".csv");
            Files.write(reportPath, List.of("FirstName,LastName,EmployeeId"), StandardOpenOption.CREATE);
            Files.write(reportPath, employees.stream().map(EmployeeReportGenerator::convertToCsvRow).collect(Collectors.toList()), StandardOpenOption.APPEND);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String convertToCsvRow(final Employee employee) {
        return List.of(employee.getFirstName(), employee.getLastName(), employee.getId()).stream().collect(Collectors.joining(","));
    }
}
