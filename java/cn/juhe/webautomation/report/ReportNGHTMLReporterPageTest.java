package cn.juhe.webautomation.report;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IResultMap;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.annotations.Test;
import org.uncommons.reportng.HTMLReporter;

import cn.juhe.webautomation.common.LogHandle;
import cn.juhe.webautomation.common.Util;
import cn.juhe.webautomation.driver.DriverBase;

/**
 * @author murphy
 * @date 2015-9-23 上午11:22:23
 * @parameter
 */
public class ReportNGHTMLReporterPageTest extends HTMLReporter implements ITestListener {
	/**
	 * LogHandle
	 */
	private LogHandle log = LogHandle.getInstence();
	/**
	 * 是否需要重新运行
	 */
	public boolean isRetryHandleNeeded;
	/**
	 * 测试失败用例集合
	 */
	public IResultMap failedCases;
	/**
	 * 测试跳过用例集合
	 */
	public IResultMap skippedCases;

	public void onTestStart(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onTestSuccess(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onTestFailure(ITestResult result) {
		// TODO Auto-generated method stub
		reportLogScreenshot(result);
		if (result.getMethod().getRetryAnalyzer() != null) {
			TestNGRetryAnalyzer testRetryAnalyzer = (TestNGRetryAnalyzer) result.getMethod().getRetryAnalyzer();
			if (testRetryAnalyzer.getRetryCount() <= testRetryAnalyzer.getMaxRetryCount()) {
				result.setStatus(ITestResult.SKIP);
				Reporter.setCurrentTestResult(null);
				isRetryHandleNeeded = true;
			} else {
				failedCases = result.getTestContext().getFailedTests();
				failedCases.addResult(result, result.getMethod());
			}
		}
	}

	public void onTestSkipped(ITestResult result) {
		// TODO Auto-generated method stub
		reportLogScreenshot(result);
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
		// TODO Auto-generated method stub

	}

	public void onStart(ITestContext context) {
		// TODO Auto-generated method stub

	}

	public void onFinish(ITestContext context) {
		// TODO Auto-generated method stub
		failedCases = context.getFailedTests();
		if (isRetryHandleNeeded) {
			removeIncorrectlySkippedTests(context, failedCases);
			removeFailedTestsInTestNG(context);
		} else {
			skippedCases = context.getSkippedTests();
		}
	}

	/**
	 * 如果skipped的用例中存在重复的则在fail的用例中剔除掉
	 * 
	 * removeIncorrectlySkippedTests
	 * 
	 * @param test
	 * @param map
	 * @return
	 * @author murphy
	 * @date 2015-8-21
	 */
	protected IResultMap removeIncorrectlySkippedTests(ITestContext test, IResultMap map) {
		List<ITestNGMethod> failsToRemove = new ArrayList<ITestNGMethod>();
		IResultMap returnValue = test.getSkippedTests();
		for (ITestResult result : returnValue.getAllResults()) {
			for (ITestResult resultToCheck : map.getAllResults()) {
				if (resultToCheck.getMethod().equals(result.getMethod())) {
					failsToRemove.add(resultToCheck.getMethod());
					break;
				}
			}
			for (ITestResult resultToCheck : test.getPassedTests().getAllResults()) {
				if (resultToCheck.getMethod().equals(result.getMethod())) {
					failsToRemove.add(resultToCheck.getMethod());
					break;
				}
			}
		}
		for (ITestNGMethod method : failsToRemove) {
			returnValue.removeResult(method);
		}
		skippedCases = returnValue;
		return returnValue;
	}

	/**
	 * 如果fail的用例中存在重复的则在fail的用例中剔除掉
	 * 
	 * @param test
	 * @author murphy
	 * @date 2015-8-21
	 */
	private void removeFailedTestsInTestNG(ITestContext test) {
		IResultMap returnValue = test.getFailedTests();
		for (ITestResult result : returnValue.getAllResults()) {
			boolean isPassed = false;
			for (ITestResult resultToCheck : test.getPassedTests().getAllResults()) {
				if (result.getMethod().equals(resultToCheck.getMethod())) {
					// String testClassName = String.format("%s.%s",
					// result.getMethod()
					// .getRealClass().toString(),
					// result.getMethod().getMethodName());
					// System.out.println("Passed:"
					// + testClassName);
					isPassed = true;
					break;
				}
			}
			if (isPassed) {
				returnValue.removeResult(result.getMethod());
				test.getFailedConfigurations().removeResult(result.getMethod());
			}
		}
	}

	/**
	 * ReportNG log screenshot reportLogScreenshot
	 * 
	 * @param file
	 * @author murphy
	 * @date 2015-9-23
	 */
	protected void reportLogScreenshot(ITestResult tr) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		String filename = tr.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).testName();
		String orgFilePath = System.getProperty("user.dir") + "/files/data/" + filename + ".png";
		String dataFilePath = System.getProperty("user.dir") + "/files/results/" + Util.runTitle + "/" + filename
				+ ".png";
		String diffFilePath = System.getProperty("user.dir") + "/files/results/" + Util.runTitle + "/" + "diff-"
				+ filename + ".png";
		try {
			Reporter.log("<a href=\"" + new File(orgFilePath).getAbsolutePath() + "\">原图片地址</a>");
			Reporter.log("<a href=\"" + new File(dataFilePath).getAbsolutePath() + "\">现图片地址</a>");
			Reporter.log("<a href=\"" + new File(diffFilePath).getAbsolutePath() + "\">对比图片地址</a>");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.logError(new File(diffFilePath).getName() + "文件不存在");
			e.printStackTrace();
		}
	}

	/**
	 * 根据运行结果截图 saveScreenShot
	 * 
	 * @param tr
	 * @author murphy
	 * @date 2015-8-21
	 */
	private void saveScreenShot(ITestResult tr) {
		System.setProperty("org.uncommons.reportng.escape-output", "false");
		WebDriver webDriver = DriverBase.webDriver;
		String imageFolderPath = "files/results/" + Util.runTitle + "/";
		String imageName = tr.getName() + "_" + Util.getCurrentDateTime() + ".png";
		File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		String filePath = imageFolderPath + imageName;
		File p = new File(imageFolderPath);
		if (!p.exists()) {
			p.mkdir(); // 创建文件夹
		}
		File destFile = new File(filePath);
		try {
			FileUtils.copyFile(screenshot, destFile);
			// reportLogScreenshot(destFile);
		} catch (Exception e) {
			e.printStackTrace();
			filePath = filePath + " Take Screentshot Failed:";
			log.logError(filePath + e.getMessage());
			Reporter.log(filePath);
			filePath = "";
		}
		if (!"".equals(filePath)) {
			// 把截图写入到Html报告中方便查看
			try {
				String absolute = destFile.getAbsolutePath();
				// Reporter.log("<a href=\"" + destFile.getAbsolutePath()
				// + "\"><p align=\"left\">Error screenshot at: "
				// + absolute + " " + new Date() + "</p></a>");
				BufferedImage bufimg = ImageIO.read(new File(filePath));
				String imgSrc = "<br/><div style=\"float:left\"><a href=\"" + absolute + "\"><p><img src=\"" + absolute
						+ "\" width=\"" + bufimg.getWidth() * 0.5 + "\" height=\"" + bufimg.getHeight() * 0.5
						+ "\"/></p></a></div>";
				Reporter.log(imgSrc);
			} catch (IOException e) {
				log.logException(e);
			}
		}
	}
}
