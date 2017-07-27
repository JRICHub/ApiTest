package com.fkapi.reTry;

import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;
import java.util.Iterator;

/**
 * Created by Administrator on 2017/7/26.
 */
public class TestngListener extends TestListenerAdapter{
    @Override
    public void onFinish(ITestContext testContext) {
        super.onFinish(testContext);
        Iterator listOfFailedTests = testContext.getFailedTests().getAllResults().iterator();
        while (listOfFailedTests.hasNext()) {
            ITestResult failedTest = (ITestResult) listOfFailedTests.next();
            ITestNGMethod method = failedTest.getMethod();
            if (testContext.getFailedTests().getResults(method).size() > 1) {
                listOfFailedTests.remove();
            } else {
                if (testContext.getPassedTests().getResults(method).size() > 0) {
                    listOfFailedTests.remove();
                }
            }
        }
    }
}
