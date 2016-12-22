package cn.juhe.webautomation.driver;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import cn.juhe.webautomation.common.LogHandle;

public abstract class DriverBase {

	protected DriverManager driverMgr = null;
	
	protected LogHandle log = null;
	
	protected int defaultTimeoutInSeconds = 20;
	
	protected JavascriptExecutor jsExecutor;
	
	protected Actions actions;
	
	public static WebDriver webDriver;
	
	protected WebDriver initWebDriver(boolean restartFlag){
		if(driverMgr == null){
			driverMgr = new DriverManager();
		}
		log = driverMgr.getConfig().log;
		defaultTimeoutInSeconds = driverMgr.getConfig().defaultTimeOutInSeconds;
		webDriver = driverMgr.getInstance(restartFlag);
		jsExecutor = (JavascriptExecutor)webDriver;
		actions = new Actions(webDriver);
		return webDriver;
	}
	
}
