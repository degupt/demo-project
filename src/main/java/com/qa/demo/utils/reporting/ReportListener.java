package com.qa.demo.utils.reporting;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.TestListenerAdapter;

import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

/**
 * A custom listener for Extent Reporting
 * 
 * @author Deenesh
 */
/**
 * @author deenesh
 *
 */
public class ReportListener extends TestListenerAdapter {
    protected static Map<String, ExtentTest> extentTestMap = new HashMap<>();
    private static final String OVERALLRESULT = "Overall Test Case Result: ";

    /**
     * default constructor
     */
    public ReportListener() {
        super();
    }

    @Override
    public void onStart(ITestContext testContext) {
        // no-op
    }

    @Override
    public void onTestStart(ITestResult result) {
        ExtentTest test;
        Long threadId = Thread.currentThread().getId();
        test = ExtentReporter.extent.startTest(result.getMethod().getMethodName());
        test.setStartedTime(getTime(result.getStartMillis()));
        extentTestMap.put(result.getMethod().getMethodName() + threadId, test);
        extentTestMap.put(result.getMethod().getMethodName() + threadId, test);
        for (String group : result.getMethod().getGroups()) {
            test.assignCategory(group);
        }
    }

    @Override
    public void onTestSuccess(ITestResult tr) {
        Long threadId = Thread.currentThread().getId();
        ExtentTest test = extentTestMap.get(tr.getMethod().getMethodName() + threadId);
        test.log(LogStatus.PASS, OVERALLRESULT + tr.getMethod().getMethodName(), "Passed");
        test.setEndedTime(getTime(tr.getEndMillis()));
        ExtentReporter.extent.endTest(test);
        if (extentTestMap.containsKey(tr.getMethod().getMethodName() + threadId)) {
            extentTestMap.remove(tr.getMethod().getMethodName() + threadId);
        }
    }

    @Override
    public void onTestFailure(ITestResult tr) {
        Long threadId = Thread.currentThread().getId();
        ExtentTest test = extentTestMap.get(tr.getMethod().getMethodName() + threadId);
        test.log(LogStatus.FAIL, OVERALLRESULT + tr.getMethod().getMethodName(), tr.getThrowable());
        test.setEndedTime(getTime(tr.getEndMillis()));
        ExtentReporter.extent.endTest(test);
        if (extentTestMap.containsKey(tr.getMethod().getMethodName() + threadId)) {
            extentTestMap.remove(tr.getMethod().getMethodName() + threadId);
        }
    }

    /**
     * report for skipped tests
     */
    @Override
    public void onTestSkipped(ITestResult tr) {
        Long threadId = Thread.currentThread().getId();
        ExtentTest test = extentTestMap.get(tr.getMethod().getMethodName() + threadId);
        test.setEndedTime(getTime(tr.getEndMillis()));
        test.log(LogStatus.SKIP, OVERALLRESULT + tr.getMethod().getMethodName(), "Skipped");
        ExtentReporter.extent.endTest(test);
        if (extentTestMap.containsKey(tr.getMethod().getMethodName() + threadId)) {
            extentTestMap.remove(tr.getMethod().getMethodName() + threadId);
        }
    }

    @Override
    public void onFinish(ITestContext testContext) {
        for (String s : Reporter.getOutput()) {
            ExtentReporter.extent.setTestRunnerOutput(s);
        }
    }

    /**
     * This method is used to write to the Extent report. The message string is passed as semi-colon delimited value
     * where first part is the description and second part is more details.
     * 
     * @param test
     * @param isFailed
     * @param message
     */
    public void setAssertStatus(ExtentTest test, Boolean isFailed, String message) {
        String[] messages = message.split(";");
        String stepdesc = messages[0];
        String details = messages[1];
        if (isFailed) {
            test.log(LogStatus.FAIL, stepdesc, details);
        } else
            test.log(LogStatus.PASS, stepdesc, details);
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

}
