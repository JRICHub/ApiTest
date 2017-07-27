package com.fkapi.reTry;

import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

/**
 * Created by Administrator on 2017/7/26.
 */
public class TestngRetry implements IRetryAnalyzer {
    private static Logger logger = Logger.getLogger(TestngRetry.class);
    private int retryCount = 1;
    private static int maxRetryCount=2;

    @Override
    public boolean retry(ITestResult result) {
        if (retryCount <= maxRetryCount) {
            String message = "running retry for " + result.getName() + " on class " +this.getClass().getName() + " Retrying " + retryCount + " times";
            logger.info(message);
            Reporter.log(message);
            Reporter.setCurrentTestResult(result);
            Reporter.log("RunCount=" + (retryCount + 1));
            retryCount++;
            return true;
        }
        return false;
    }

}
