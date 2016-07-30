package com.qa.demo.utils.reporting;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.IExecutionListener;

import com.relevantcodes.extentreports.ExtentReports;

/**
 * A custom listener for Extent Reporting
 * 
 * @author Deenesh
 */
public class ExtentReporter implements IExecutionListener {

    protected static ExtentReports extent;

    @Override
    public void onExecutionStart() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
        Date date = new Date();
        String path = "./test-output/Automated_Report" + "_" + dateFormat.format(date) + ".html";
        if (extent == null) {
            extent = new ExtentReports(path, true);
        }
    }

    @Override
    public void onExecutionFinish() {
        if (extent != null) {
            extent.flush();
            extent.close();
        }
    }

}
