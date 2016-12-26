package cn.juhe.webautomation.driver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import cn.juhe.webautomation.data.FileHandle;

public class DriverManager {

	/**
	 * DriverConfig 实例
	 */
	private DriverConfig config = null;

	/**
	 * capabilities
	 */
	private DesiredCapabilities capabilities = null;

	/**
	 * Web Driver 实例
	 */
	private WebDriver webDriver;

	/**
	 * 浏览器driver路径
	 */
	protected String driverDirectory;

	public DriverConfig getConfig() {
		if (config == null) {
			config = new DriverConfig();
		}
		return config;
	}

	public DriverManager() {
		if (config == null) {
			config = getConfig();
		}
	}

	public WebDriver getInstance(boolean restartFlag) {
		if (restartFlag) {
			webDriver = initWebDriver();
		} else {
			if (webDriver == null)
				webDriver = initWebDriver();
		}
		return webDriver;
	}

	public WebDriver initWebDriver() {
		if (webDriver != null) {
			webDriver.quit();
		}
		if (!config.isRemoteMode) {
			if (config.isInternetExplore()) {
				driverDirectory = FileHandle.getParametersCfg().getProperty("IEDriverDirectory");
				System.setProperty("webdriver.ie.driver", driverDirectory);
				capabilities = DesiredCapabilities.internetExplorer();
				capabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
						"true");
				webDriver = new InternetExplorerDriver(capabilities);
			} else if (config.isChrome()) {
				driverDirectory = FileHandle.getParametersCfg().getProperty("ChromeDriverDirectory");
				System.setProperty("webdriver.chrome.driver", driverDirectory);
				capabilities = DesiredCapabilities.chrome();
				webDriver = new ChromeDriver(capabilities);
			} else if (config.isFirefox()) {
				driverDirectory = FileHandle.getParametersCfg().getProperty("FirefoxDriverDirectory");
				System.setProperty("webdriver.gecko.driver", driverDirectory);
				capabilities = DesiredCapabilities.firefox();
				webDriver = new FirefoxDriver(capabilities);
			}
		} else {
			if (config.isChrome()) {
				driverDirectory = FileHandle.getParametersCfg().getProperty("ChromeDriverDirectory");
				System.setProperty("webdriver.chrome.driver", driverDirectory);
				capabilities = DesiredCapabilities.chrome();
			} else if (config.isFirefox()) {
				driverDirectory = FileHandle.getParametersCfg().getProperty("FirefoxDriverDirectory");
				System.setProperty("webdriver.gecko.driver", driverDirectory);
				capabilities = DesiredCapabilities.firefox();
			} else if (config.isInternetExplore()) {
				driverDirectory = FileHandle.getParametersCfg().getProperty("IEDriverDirectory");
				System.setProperty("webdriver.ie.driver", driverDirectory);
				capabilities = DesiredCapabilities.internetExplorer();
			}
			try {
				webDriver = new RemoteWebDriver(new URL(config.remoteAddress), capabilities);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		}
		webDriver.manage().timeouts().pageLoadTimeout(config.pageLoadTimeoutInSeconds, TimeUnit.SECONDS);
		webDriver.manage().timeouts().implicitlyWait(config.implicitlyWaitInSeconds, TimeUnit.SECONDS);
		webDriver.manage().timeouts().setScriptTimeout(config.scriptTimeoutInSeconds, TimeUnit.SECONDS);
		return webDriver;
	}
}
