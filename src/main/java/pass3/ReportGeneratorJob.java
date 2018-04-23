package pass3;

import java.nio.file.Path;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class ReportGeneratorJob<T> implements Runnable {

    private final Supplier<Path> m_employeeFileSupplier;
    private final Function<Path, List<T>> m_employeeFileProcessor;
    private final Consumer<List<T>> m_reportGenerator;

    public ReportGeneratorJob(final Supplier<Path> employeeFileSupplier, final Function<Path, List<T>> employeeFileProcessor, final Consumer<List<T>> reportGenerator) {
        m_employeeFileSupplier = employeeFileSupplier;
        m_employeeFileProcessor = employeeFileProcessor;
        m_reportGenerator = reportGenerator;
    }

    public void run() {
        m_reportGenerator.accept(m_employeeFileProcessor.apply(m_employeeFileSupplier.get()));
    }

}
