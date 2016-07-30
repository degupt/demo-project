package com.qa.demo.utils.reporting;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Map;
import java.util.Map.Entry;

import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.asserts.Assertion;
import org.testng.asserts.IAssert;
import org.testng.collections.Maps;

import com.relevantcodes.extentreports.ExtentTest;

/**
 * Soft Assert which allows the test to continue even in event of failure
 * 
 * @author Deenesh Gupta
 */
public class SoftAssert extends Assertion {
    private Map<AssertionError, IAssert> failedAsserts = Maps.newHashMap();
    private ReportListener reportListener = new ReportListener();

    @Override
    public void executeAssert(IAssert assertCommand) {
        try {
            assertCommand.doAssert();
            onAssertSuccess(assertCommand);
        } catch (AssertionError ex) {
            onAssertFailure(assertCommand, ex);
            failedAsserts.put(ex, assertCommand);
        }
    }

    @Override
    protected void doAssert(IAssert assertCommand) {
        executeAssert(assertCommand);
    }

    @Override
    public void onAssertSuccess(IAssert assertCommand) {
        printAssertionLog(assertCommand, null, false);

        // Test Method Name is the 6th element in StackTrace Array
        Log.info(Thread.currentThread().getStackTrace()[5].getMethodName() + Thread.currentThread().getId());
        ExtentTest test = ReportListener.extentTestMap
                .get(Thread.currentThread().getStackTrace()[5].getMethodName() + Thread.currentThread().getId());
        reportListener.setAssertStatus(test, false, assertCommand.getMessage());
    }

    public void onAssertFailure(IAssert assertCommand, AssertionError ex) {
        printAssertionLog(assertCommand, ex, true);

        // Test Method Name is the 6th element in StackTrace Array
        ExtentTest test = ReportListener.extentTestMap
                .get(Thread.currentThread().getStackTrace()[5].getMethodName() + Thread.currentThread().getId());
        reportListener.setAssertStatus(test, true, assertCommand.getMessage());
    }

    /**
     * assert all the individuals asserts
     * 
     */
    public void assertAll() {
        if (failedAsserts.isEmpty()) {
            return;
        }
        ITestResult result = Reporter.getCurrentTestResult();
        String msg = String.format("Following soft asserts failed in %s.%s() :", result.getTestClass().getName(),
                result.getMethod().getMethodName());
        Reporter.log(msg, true);

        for (Entry<AssertionError, IAssert> eachEntry : failedAsserts.entrySet()) {
            IAssert eachAssert = eachEntry.getValue();
            AssertionError eachError = eachEntry.getKey();
            StringBuilder sb = new StringBuilder();
            sb.append("The validation");
            if (!eachAssert.getMessage().trim().isEmpty()) {
                sb.append(" \"").append(eachAssert.getMessage()).append("\" ");
            }
            sb.append("failed because the expected value of [").append(eachAssert.getExpected()).append("] ");
            sb.append("was different from the actual value [").append(eachAssert.getActual()).append("]");
            Reporter.log(sb.toString(), true);
            Reporter.log(convertStackTrace(eachError), true);
        }
        result.setStatus(ITestResult.FAILURE);
    }

    private void printAssertionLog(IAssert assertCommand, AssertionError ex, boolean isfailed) {
        String methodName = Reporter.getCurrentTestResult().getMethod().getMethodName();
        StringBuilder sb = new StringBuilder();
        sb.append("Assert ");
        if (assertCommand.getMessage() != null && !assertCommand.getMessage().trim().isEmpty()) {
            sb.append("\"").append(assertCommand.getMessage()).append("\"");
        }
        sb = isfailed ? sb.append(" failed in ") : sb.append(" passed in ");
        sb.append(methodName).append("()");
        if (isfailed) {
            Reporter.getCurrentTestResult().setThrowable(ex);
        }
        Reporter.log(sb.toString(), true);
    }

    private String convertStackTrace(AssertionError error) {
        StringWriter sw = new StringWriter();
        error.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

}
