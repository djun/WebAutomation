package cn.juhe.webautomation.driver;

import cn.juhe.webautomation.common.LogHandle;
import cn.juhe.webautomation.data.FileHandle;

public class DriverConfig {

	/**
	 * LogHandle实例对象
	 */
	protected LogHandle log = null;

	/**
	 * 启动webdriver的类型，local/remote
	 */
	protected boolean isRemoteMode = false;

	/**
	 * remote driver 地址
	 */
	protected String remoteAddress = "127.0.0.1";

	/**
	 * 测试平台名称,如：firefox，ie，chrome，默认是firefox
	 */
	protected String browserName = "firefox";

	/**
	 * 搜索页面元素超时时间，默认10秒
	 */
	protected int implicitlyWaitInSeconds = 10;

	/**
	 * 执行java script 脚本超时时间，默认20秒
	 */
	protected int scriptTimeoutInSeconds = 20;

	/**
	 * 等待页面加载超时时间，默认是20秒
	 */
	protected int pageLoadTimeoutInSeconds = 20;

	/**
	 * 自定义超时时间，默认20秒
	 */
	protected int defaultTimeOutInSeconds = 20;

	/**
	 * 默认启动Web URL
	 */
	protected String baseDomain = "http://www.baidu.com";

	/**
	 * CHROME 浏览器
	 */
	private final String CHROME = "chrome";

	/**
	 * FIRE FOX 浏览器
	 */
	private final String FIRE_FOX = "firefox";

	/**
	 * IE 浏览器
	 */
	private final String INTERNET_EXPLORER = "ie";

	/**
	 * 判断待测的是否是chrome
	 * 
	 * @return
	 */
	protected boolean isChrome() {
		return CHROME.equalsIgnoreCase(browserName);
	}

	/**
	 * 判断待测的是否是IE
	 * 
	 * @return
	 */
	protected boolean isInternetExplore() {
		return INTERNET_EXPLORER.equalsIgnoreCase(browserName);
	}

	/**
	 * 判断待测的是否是firefox
	 * 
	 * @return
	 */
	protected boolean isFirefox() {
		return FIRE_FOX.equalsIgnoreCase(browserName);
	}

	public DriverConfig() {
		log = LogHandle.getInstence();
		isRemoteMode = FileHandle.getParametersCfg().getProperty("DriverMode").toLowerCase().equals("remote");
		remoteAddress = "http://" + FileHandle.getParametersCfg().getProperty("RemoteAddress") + ":4444/wd/hub";
		browserName = FileHandle.getParametersCfg().getProperty("BrowserName");
		baseDomain = FileHandle.getParametersCfg().getProperty("BaseDomain");
		implicitlyWaitInSeconds = Integer
				.parseInt(FileHandle.getParametersCfg().getProperty("ImplicitlyWaitInSeconds"));
		scriptTimeoutInSeconds = Integer.parseInt(FileHandle.getParametersCfg().getProperty("ScriptTimeoutInSeconds"));
		pageLoadTimeoutInSeconds = Integer
				.parseInt(FileHandle.getParametersCfg().getProperty("PageLoadTimeoutInSeconds"));
		defaultTimeOutInSeconds = Integer
				.parseInt(FileHandle.getParametersCfg().getProperty("DefaultTimeOutInSeconds"));
	}
}
