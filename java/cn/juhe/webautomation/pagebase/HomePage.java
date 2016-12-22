package cn.juhe.webautomation.pagebase;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.Assert;
import org.testng.Reporter;

public class HomePage extends PageBase {

	@FindBy(linkText = "亲，请登录")
	public static WebElement lkLogin;

	@FindBy(partialLinkText = "聚合数据-基础数据API接口提供商")
	public static WebElement homeLogo;

	@FindBy(linkText = "个人中心")
	public static WebElement lkPersonalCenter;

	@FindBy(linkText = "API")
	public static WebElement lkAPIDoc;

	@FindBy(xpath = "//div[1]")
	public static WebElement imgActivity1;

	@FindBy(xpath = "//div[1]/a[1]")
	public static WebElement imgActivity2;

	/*
	 * 主页最上方，包含个人中心，以及导航栏（首页，API，营销方案，聚合代码）
	 */
	@FindBy(xpath = "//header[@id='new-header']")
	public static WebElement segTopHeader;

	/*
	 * 主页左侧，全部数据分类以及内部所有子类
	 */
	@FindBy(xpath = "//section[@id='section-main']/div/div")
	public static WebElement segLeftItemAll;

	/*
	 * 主页右侧广告栏slider
	 */
	@FindBy(xpath = "//section[@id='section-main']/div/div[@class='ban-content']/div")
	public static WebElement segRightBanContent;

	/*
	 * 主页右侧广告栏下方的小版
	 */
	@FindBy(xpath = "//section[@id='section-main']/div/div[@class='ban-content']/ul")
	public static WebElement segRightXiaoBan;

	/*
	 * 主页中间推荐数据
	 */
	@FindBy(xpath = "//section[@id='section-main']/div[2]")
	public static WebElement segRecommandData;

	/*
	 * 主页中间推荐数据
	 */
	@FindBy(xpath = "//section[@id='section-main']/div[3]")
	public static WebElement segDeveloperServices;

	/*
	 * 主页中间行业动态
	 */
	@FindBy(xpath = "//section[@id='section-main']/div[4]")
	public static WebElement segIndustryNews;

	/*
	 * 主页他们正在使用我们的数据
	 */
	@FindBy(xpath = "//section[@id='section-main']/div[5]")
	public static WebElement segUsingOurData;

	/*
	 * 主页结尾
	 */
	@FindBy(xpath = "//footer/div")
	public static WebElement segPageFooter;

	/*
	 * 主页结尾许可证
	 */
	@FindBy(xpath = "//footer/p")
	public static WebElement setPageFooterLicence;

	/**
	 * 进入主页
	 */
	public void launchHomePage() {
		boolean isHomePage = false;
		int i = 1;
		boolean isLoadComplete = isPageLoadComplete(10);
		if (!isLoadComplete) {
			Assert.assertTrue(false, "主页加载失败超时" + 10);
		}
		while (!isHomePage && i <= 3) {
			isHomePage = isExistElement(homeLogo, 3);
			if (isHomePage) {
				Reporter.log("进入主页成功，尝试次数 -->" + i);
			} else if (i == 3 && !isHomePage) {
				Assert.assertTrue(false, "进入主页失败，尝试次数 -->" + i);
			}
			i++;
		}
	}

	/**
	 * 进入api种类页面
	 */
	public void goToAPIDocPage() {
		boolean isAPIDocPage = false;
		launchHomePage();
		lkAPIDoc.click();
		sleepFor(1);
		String[] handles = new String[webDriver.getWindowHandles().size()];
		webDriver.getWindowHandles().toArray(handles);
		webDriver.switchTo().window(handles[handles.length - 1]);
		int i = 1;
		boolean isLoadComplete = isPageLoadComplete(10);
		if (!isLoadComplete) {
			Assert.assertTrue(false, "主页加载失败超时" + 10);
		}
		while (!isAPIDocPage && i <= 3) {
			isAPIDocPage = isExistElement(By.linkText("生活常用"), 3);
			if (isAPIDocPage) {
				Reporter.log("进入API分类界面成功，尝试次数 -->" + i);
			} else if (i == 3 && !isAPIDocPage) {
				Assert.assertTrue(false, "进入API分类界面失败，尝试次数 -->" + i);
			}
			i++;
		}
	}

	/**
	 * 进入登录页面
	 */
	public void goToLoginPage() {
		boolean isLoginPage = false;
		launchHomePage();
		lkLogin.click();
		boolean isLoadComplete = isPageLoadComplete(10);
		if (!isLoadComplete) {
			Assert.assertTrue(false, "主页加载失败超时" + 10);
		}
		int i = 1;
		while (!isLoginPage && i <= 3) {
			isLoginPage = isExistElement(By.className("loginLogo"), 3);
			if (isLoginPage) {
				Reporter.log("进入登录界面成功，尝试次数 -->" + i);
			} else if (i == 3 && !isLoginPage) {
				Assert.assertTrue(false, "进入登录界面失败，尝试次数 -->" + i);
			}
			i++;
		}
	}

	/**
	 * 有效登录
	 */
	public void validLogin() {
		boolean islogin = false;
		goToLoginPage();
		txtUsername.sendKeys(testUsername);
		txtPassword.sendKeys(testPassword);
		btnLogin.click();
		int i = 1;
		while (!islogin && i <= 3) {
			islogin = isExistElement(By.partialLinkText(testUsername), 3);
			if (islogin) {
				Reporter.log("登录成功，尝试次数 -->" + i);
			} else if (i == 3 && !islogin) {
				Assert.assertTrue(false, "登录失败，尝试次数 -->" + i);
			}
			i++;
		}
	}

	/**
	 * 进入个人中心
	 */
	public void goToPersonalCenter() {
		boolean isLoaded = false;
		validLogin();
		lkPersonalCenter.click();
		sleepFor(1);
		String[] handles = new String[webDriver.getWindowHandles().size()];
		webDriver.getWindowHandles().toArray(handles);
		webDriver.switchTo().window(handles[handles.length - 1]);
		int i = 1;
		boolean isLoadComplete = isPageLoadComplete(10);
		if (!isLoadComplete) {
			Assert.assertTrue(false, "主页加载失败超时" + 10);
		}
		while (!isLoaded && i <= 3) {
			isLoaded = isExistElement(By.partialLinkText("我的聚合"), 3);
			if (isLoaded) {
				Reporter.log("进入个人中心成功，尝试次数 -->" + i);
			} else if (i == 3 && !isLoaded) {
				Assert.assertTrue(false, "进入个人失败，尝试次数 -->" + i);
			}
			i++;
		}
	}

	/**
	 * 关闭上方下拉弹出框
	 * 
	 */
	public void closeActivityIcon() {
		boolean isExist = true;
		int i = 1;
		while (isExist && i <= 3) {
			isExist = isExistElement(imgActivity2, 1);
			if (isExist) {
				imgActivity2.click();
				sleepFor(500);
			}
		}
	}
}
