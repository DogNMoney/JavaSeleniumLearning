import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class TestReportManager {
    private static final TestReportManager INSTANCE = new TestReportManager();
    public final AtomicInteger testsPassedCount = new AtomicInteger(0);
    public final AtomicInteger testsFailedCount = new AtomicInteger(0);
    public final List<FailedTest> failedTests = new CopyOnWriteArrayList<>();

    //Prevent creating another instance of Singleton
    private TestReportManager() {}

    public static TestReportManager getInstance() {
        return INSTANCE;
    }

    static class FailedTest{
        public String testName;
        public String screenShotPath;

        public FailedTest(String testName, String filePath) {
            this.testName = testName;
            this.screenShotPath = filePath;
        }
    }

    static class ReportSummary {
        public int passedCount;
        public int failedCount;
        public List<FailedTest> failedTests;

        public ReportSummary(int passedCount, int failedCount, List<FailedTest> failedTests) {
            this.passedCount = passedCount;
            this.failedCount = failedCount;
            this.failedTests = failedTests;
        }
    }
}
