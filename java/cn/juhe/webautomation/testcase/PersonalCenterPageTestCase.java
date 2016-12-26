package cn.juhe.webautomation.testcase;

import java.lang.reflect.Method;

import org.testng.Reporter;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cn.juhe.webautomation.common.Util;
import cn.juhe.webautomation.pagebase.PersonalCenterPage;

public class PersonalCenterPageTestCase extends PersonalCenterPage {

	@AfterMethod
	public void afterTest() {
		driverQuit();
	}

	@BeforeMethod
	public void beforeTest(Method m) {
		webDriver().get(baseDomain);
		Util.getRunTitle(m);
	}

	@Test
	public void testModityPwd() {
		// 修改密码
		goToPersonalCenter();
		lkPwdModify.click();
		String orgPwd = testPassword;
		String newPwd = Util.getRandomPassword(8);
		log.logInfo("Original Password -->" + orgPwd + "\n" + "New Password -->" + newPwd);
		Reporter.log("Original Password -->" + orgPwd + "\n" + "New Password -->" + newPwd);
		txtOldPwd.sendKeys(testPassword);
		txtNewPwd.sendKeys(newPwd);
		txtConfirmPwd.sendKeys(newPwd);
		btnConfirm.click();
		sleepFor(1);
		dialogConfirm.click();
		launchHomePage();
		testPassword = newPwd;
		// 恢复到原始密码
		goToPersonalCenter();
		lkPwdModify.click();
		txtOldPwd.sendKeys(newPwd);
		txtNewPwd.sendKeys(orgPwd);
		txtConfirmPwd.sendKeys(orgPwd);
		btnConfirm.click();
		sleepFor(1);
		dialogConfirm.click();
		testPassword = orgPwd;
	}
}
