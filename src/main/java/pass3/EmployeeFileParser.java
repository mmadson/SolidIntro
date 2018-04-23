package pass3;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class EmployeeFileParser {

    public List<Employee> parse(final Path employeeDataFile) {
        try {
            return Files.readAllLines(employeeDataFile)
                .stream()
                .map(this::toEmployee)
                .collect(Collectors.toUnmodifiableList());
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Employee toEmployee(final String employeeDataFileLine) {
        var parts = employeeDataFileLine.split(" ");
        return new Employee(parts[0], parts[2], parts[3]);
    }
}
