package cn.juhe.webautomation.pagetest;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import cn.juhe.webautomation.common.PageConstant;
import cn.juhe.webautomation.common.Util;
import cn.juhe.webautomation.pagebase.HomePage;

public class HomePageTest extends HomePage {

	@BeforeClass(alwaysRun = true)
	public void beforeClass() {
		webDriver().get(baseDomain);

	}

	@AfterClass(alwaysRun = true)
	public void afterClass() {
		driverQuit();
	}

	@Test(groups = { "HomePageInit" }, description = "主页拍照存储")
	public void storeHomePage() {
		Util.getRunTitle();
		launchHomePage();
		closeActivityIcon();
		takeElementScreenshot(segTopHeader, PageConstant.HOMEPAGE_HEADER, 0);
		takeElementScreenshot(segLeftItemAll, PageConstant.HOMEPAGE_LEFTITEM, 0);
		takeElementScreenshot(segRightBanContent, PageConstant.HOMEPAGE_RIGHTSLIDERCONTENT, 0);
		takeElementScreenshot(segRightXiaoBan, PageConstant.HOMEPAGE_RIGHTBANCONTENT, 0);
		takeElementScreenshot(segRecommandData, PageConstant.HOMEPAGE_RECOMMANDDATA, 0);
		takeElementScreenshot(segDeveloperServices, PageConstant.HOMEPAGE_DEVELOPERSERVICES, 0);
		takeElementScreenshot(segIndustryNews, PageConstant.HOMEPAGE_INDUSTRYNEWS, 0);
		takeElementScreenshot(segUsingOurData, PageConstant.HOMEPAGE_USINGOURDATA, 0);
		takeElementScreenshot(segPageFooter, PageConstant.HOMEPAGE_FOOTER, 0);
		takeElementScreenshot(setPageFooterLicence, PageConstant.HOMEPAGE_FOOTERLICENCE, 0);
	}

	@Test(groups = { "HomePageTest", "JuhePageTest" }, description = "主页拍照测试")
	public void screenshotHomePage() {
		Util.getRunTitle();
		launchHomePage();
		closeActivityIcon();
		takeElementScreenshot(segTopHeader, PageConstant.HOMEPAGE_HEADER, 1);
		takeElementScreenshot(segLeftItemAll, PageConstant.HOMEPAGE_LEFTITEM, 1);
		takeElementScreenshot(segRightBanContent, PageConstant.HOMEPAGE_RIGHTSLIDERCONTENT, 1);
		takeElementScreenshot(segRightXiaoBan, PageConstant.HOMEPAGE_RIGHTBANCONTENT, 1);
		takeElementScreenshot(segRecommandData, PageConstant.HOMEPAGE_RECOMMANDDATA, 1);
		takeElementScreenshot(segDeveloperServices, PageConstant.HOMEPAGE_DEVELOPERSERVICES, 1);
		takeElementScreenshot(segIndustryNews, PageConstant.HOMEPAGE_INDUSTRYNEWS, 1);
		takeElementScreenshot(segUsingOurData, PageConstant.HOMEPAGE_USINGOURDATA, 1);
		takeElementScreenshot(segPageFooter, PageConstant.HOMEPAGE_FOOTER, 1);
		takeElementScreenshot(setPageFooterLicence, PageConstant.HOMEPAGE_FOOTERLICENCE, 1);
	}

	@Test(groups = { "HomePageTest", "JuhePageTest" }, description = "主页图片对比1", testName = PageConstant.HOMEPAGE_HEADER)
	public void testHomePage1() {
		int status = Util.getDifferentImage(PageConstant.HOMEPAGE_HEADER, PageConstant.HOMEPAGE_HEADER);
		Util.checkImageCompareStatus(status, PageConstant.HOMEPAGE_HEADER);
	}

	@Test(groups = { "HomePageTest",
			"JuhePageTest" }, description = "主页图片对比2", testName = PageConstant.HOMEPAGE_LEFTITEM)
	public void testHomePage2() {
		int status = Util.getDifferentImage(PageConstant.HOMEPAGE_LEFTITEM, PageConstant.HOMEPAGE_LEFTITEM);
		Util.checkImageCompareStatus(status, PageConstant.HOMEPAGE_LEFTITEM);
	}

	@Test(groups = { "HomePageTest",
			"JuhePageTest" }, description = "主页图片对比3", testName = PageConstant.HOMEPAGE_RIGHTSLIDERCONTENT)
	public void testHomePage3() {
//		int status = Util.getDifferentImage(PageConstant.HOMEPAGE_RIGHTSLIDERCONTENT,
//				PageConstant.HOMEPAGE_RIGHTSLIDERCONTENT);
//		Util.checkImageCompareStatus(status, PageConstant.HOMEPAGE_RIGHTSLIDERCONTENT);
		boolean isSingle = Util.isSingleColorImage(PageConstant.HOMEPAGE_RIGHTSLIDERCONTENT, 1, 0.9);
		Assert.assertFalse(isSingle, "该图片超过90%都为单一颜色，请检查");
	}

	@Test(groups = { "HomePageTest",
			"JuhePageTest" }, description = "主页图片对比4", testName = PageConstant.HOMEPAGE_RIGHTBANCONTENT)
	public void testHomePage4() {
		int status = Util.getDifferentImage(PageConstant.HOMEPAGE_RIGHTBANCONTENT,
				PageConstant.HOMEPAGE_RIGHTBANCONTENT);
		Util.checkImageCompareStatus(status, PageConstant.HOMEPAGE_RIGHTBANCONTENT);
	}

	@Test(groups = { "HomePageTest",
			"JuhePageTest" }, description = "主页图片对比5", testName = PageConstant.HOMEPAGE_RECOMMANDDATA)
	public void testHomePage5() {
		int status = Util.getDifferentImage(PageConstant.HOMEPAGE_RECOMMANDDATA, PageConstant.HOMEPAGE_RECOMMANDDATA);
		Util.checkImageCompareStatus(status, PageConstant.HOMEPAGE_RECOMMANDDATA);
	}

	@Test(groups = { "HomePageTest",
			"JuhePageTest" }, description = "主页图片对比6", testName = PageConstant.HOMEPAGE_DEVELOPERSERVICES)
	public void testHomePage6() {
		int status = Util.getDifferentImage(PageConstant.HOMEPAGE_DEVELOPERSERVICES,
				PageConstant.HOMEPAGE_DEVELOPERSERVICES);
		Util.checkImageCompareStatus(status, PageConstant.HOMEPAGE_DEVELOPERSERVICES);
	}

	@Test(groups = { "HomePageTest",
			"JuhePageTest" }, description = "主页图片对比7", testName = PageConstant.HOMEPAGE_INDUSTRYNEWS)
	public void testHomePage7() {
//		int status = Util.getDifferentImage(PageConstant.HOMEPAGE_INDUSTRYNEWS, PageConstant.HOMEPAGE_INDUSTRYNEWS);
//		Util.checkImageCompareStatus(status, PageConstant.HOMEPAGE_INDUSTRYNEWS);
		boolean isSingle = Util.isSingleColorImage(PageConstant.HOMEPAGE_INDUSTRYNEWS, 1, 0.9);
		Assert.assertFalse(isSingle, "该图片超过90%都为单一颜色，请检查");
	}

	@Test(groups = { "HomePageTest",
			"JuhePageTest" }, description = "主页图片对比8", testName = PageConstant.HOMEPAGE_USINGOURDATA)
	public void testHomePage8() {
		int status = Util.getDifferentImage(PageConstant.HOMEPAGE_USINGOURDATA, PageConstant.HOMEPAGE_USINGOURDATA);
		Util.checkImageCompareStatus(status, PageConstant.HOMEPAGE_USINGOURDATA);
	}

	@Test(groups = { "HomePageTest", "JuhePageTest" }, description = "主页图片对比9", testName = PageConstant.HOMEPAGE_FOOTER)
	public void testHomePage9() {
		int status = Util.getDifferentImage(PageConstant.HOMEPAGE_FOOTER, PageConstant.HOMEPAGE_FOOTER);
		Util.checkImageCompareStatus(status, PageConstant.HOMEPAGE_FOOTER);
	}

	@Test(groups = { "HomePageTest",
			"JuhePageTest" }, description = "主页图片对比10", testName = PageConstant.HOMEPAGE_FOOTERLICENCE)
	public void testHomePage10() {
		int status = Util.getDifferentImage(PageConstant.HOMEPAGE_FOOTERLICENCE, PageConstant.HOMEPAGE_FOOTERLICENCE);
		Util.checkImageCompareStatus(status, PageConstant.HOMEPAGE_FOOTERLICENCE);
	}
}
