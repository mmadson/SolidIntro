package pass3;

import java.util.Properties;

public class Main {

    public static void main(String[] args) {
        new ReportGeneratorJob<>(new HttpFileDownloader(loadPropertiesFile("/employeeFileHttpDownloader.properties"))::downloadFile,
            new EmployeeFileParser()::parse,
            new EmployeeReportGenerator(loadPropertiesFile("/employeeReportGenerator.properties"))::generateReport).run();
    }

    private static Properties loadPropertiesFile(final String propertiesFileResourcePath) {
        try {
            Properties result = new Properties();
            result.load(ReportGeneratorJob.class.getResourceAsStream(propertiesFileResourcePath));
            return result;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
