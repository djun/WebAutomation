package cn.juhe.webautomation.pagebase;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import cn.juhe.webautomation.data.FileHandle;
import cn.juhe.webautomation.driver.WebDriverBase;

public class PageBase extends WebDriverBase {

	/**
	 * 测试数据 ，登录用户名
	 */
	public static final String testUsername = FileHandle.getParametersCfg().getProperty("Username");

	/**
	 * 测试数据，登录密码
	 */
	public static String testPassword = FileHandle.getParametersCfg().getProperty("Password");

	/**
	 * 测试数据，电话号码
	 */
	public static final String testPhoneNo = FileHandle.getParametersCfg().getProperty("PhoneNo");

	/**
	 * 测试数据，邮箱地址
	 */
	public static final String testEmail = FileHandle.getParametersCfg().getProperty("Email");

	@FindBy(id = "username")
	public static WebElement txtUsername;

	@FindBy(id = "password")
	public static WebElement txtPassword;

	@FindBy(id = "loginBtn")
	public static WebElement btnLogin;

	@FindBy(partialLinkText = "忘记密码")
	public static WebElement lkForget;

	@FindBy(partialLinkText = "立即注册")
	public static WebElement lkRegister;
}
