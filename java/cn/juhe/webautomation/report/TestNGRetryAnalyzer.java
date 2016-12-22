package cn.juhe.webautomation.report;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.Reporter;

import cn.juhe.webautomation.common.LogHandle;
import cn.juhe.webautomation.data.FileHandle;

/**
 * 尝试重新运行失败的测试用例
 * 
 * @author evan.Sun
 * @date 2015-8-21
 */
public class TestNGRetryAnalyzer implements IRetryAnalyzer {
	/**
	 * 参数次数计数器
	 */
	private int retryCount = 1;
	/**
	 * 最大参数次数
	 */
	private static int maxRetryCount = 3;
	/**
	 * LogHandle
	 */
	private LogHandle log = LogHandle.getInstence();

//	/**
//	 * 构造函数
//	 */
//	public TestNGRetryAnalyzer() {
//		maxRetryCount = Integer.parseInt(FileHandle.getParametersCfg()
//				.getProperty("FailedRetrycount"));
//	}
	static
	{
		maxRetryCount = Integer.parseInt(FileHandle.getParametersCfg()
				.getProperty("FailedRetrycount"));
	}

	/**
	 * 
	 * 获取当前参数次数
	 * 
	 * @return
	 * @author evan.Sun
	 * @date 2015-8-21
	 */
	public int getRetryCount() {
		return retryCount;
	}

	/**
	 * 获取最大尝试次数 getMaxCount
	 * 
	 * @return
	 * @author evan.Sun
	 * @date 2015-8-21
	 */
	public int getMaxRetryCount() {
		return maxRetryCount;
	}

	
	/**
	 * 重写retry方法
	 * (non-Javadoc)
	 * retry
	 * @param result
	 * @return
	 * @see org.testng.IRetryAnalyzer#retry(org.testng.ITestResult)
	 */
	public boolean retry(ITestResult result) {
//		String testClassName = String.format("%s.%s", result.getMethod()
//				.getRealClass().toString(), result.getMethod().getMethodName());
//		if (retryCount <= maxRetryCount) {
//			//AppiumDriverBase.restartFlag = true;
//			result.setAttribute("RETRY", retryCount);
//			logger.warn("[RETRYING] " + testClassName + " FAILED, "
//					+ "Retrying " + retryCount + " times");
//			retryCount += 1;
//			return true;
//		}
//		return false;
		if (retryCount <= maxRetryCount) {
			String message = "Retry testcase [" + result.getName() + "] on class [" + result.getTestClass().getName() + "] Retry "
					+ retryCount + " times";
			log.logInfo(message);
			Reporter.setCurrentTestResult(result);
			//Reporter.log("RunCount=" + retryCount);
			Reporter.log(message);
			retryCount++;
			return true;
		}
		return false;
	}
}
