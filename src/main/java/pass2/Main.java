package pass2;

public class Main {

    public static void main(String[] args) {
        new EmployeeReportJob(new EmployeeFileDownloader(), new EmployeeReportGenerator()).run();
    }
}
