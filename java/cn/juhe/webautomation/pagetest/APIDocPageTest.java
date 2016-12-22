package cn.juhe.webautomation.pagetest;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cn.juhe.webautomation.common.PageConstant;
import cn.juhe.webautomation.common.Util;
import cn.juhe.webautomation.pagebase.APIDocPage;

public class APIDocPageTest extends APIDocPage {

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		webDriver().get(baseDomain);

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		driverQuit();
	}

	@Test(groups = { "APIDocPageInit" }, description = "api类别页面拍照存储")
	public void storeAPIDocPage() {
		Util.getRunTitle();
		goToAPIDocPage();
		closeActivityIcon();
		takeFullScreen(PageConstant.APIDOCPAGE_APICLASSIFY, 0);
	}

	@Test(groups = { "APIDocPageTest", "JuhePageTest" }, description = "api类别页面拍照测试")
	public void screenshotAPIDocPage() {
		Util.getRunTitle();
		goToAPIDocPage();
		closeActivityIcon();
		takeFullScreen(PageConstant.APIDOCPAGE_APICLASSIFY, 1);
	}

	@Test(groups = { "APIDocPageTest",
			"JuhePageTest" }, description = "api分类页面图片对比1", testName = PageConstant.APIDOCPAGE_APICLASSIFY)
	public void testAPIDocPage1() {
		int status = Util.getDifferentImage(PageConstant.APIDOCPAGE_APICLASSIFY, PageConstant.APIDOCPAGE_APICLASSIFY);
		Util.checkImageCompareStatus(status, PageConstant.APIDOCPAGE_APICLASSIFY);
	}

}
