package cn.juhe.webautomation.testcase;

import java.lang.reflect.Method;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import cn.juhe.webautomation.common.Util;
import cn.juhe.webautomation.pagebase.HomePage;

public class HomePageTestCase extends HomePage {

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
	public void testValidLogin() {
		validLogin();
	}

	@Test
	public void testPersonalCenter() {
		goToPersonalCenter();
	}

}
