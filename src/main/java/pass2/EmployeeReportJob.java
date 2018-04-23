package pass2;

import java.nio.file.Path;

public class EmployeeReportJob implements Runnable {

    private final EmployeeFileDownloader m_fileDownloader;
    private final EmployeeReportGenerator m_reportGenerator;

    public EmployeeReportJob(final EmployeeFileDownloader fileDownloader, final EmployeeReportGenerator reportGenerator) {
        m_fileDownloader = fileDownloader;
        m_reportGenerator = reportGenerator;
    }

    public void run() {
        try {
            Path tempEmployeeDataFile = m_fileDownloader.downloadEmployeeFile();
            m_reportGenerator.generateReport(tempEmployeeDataFile);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
