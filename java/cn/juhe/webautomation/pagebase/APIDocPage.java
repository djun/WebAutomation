package cn.juhe.webautomation.pagebase;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class APIDocPage extends HomePage {
	
	
	/*
	 * 接口分类栏
	 */
	@FindBy(xpath = "//section[1]/div[@class='api-assort']")
	public static WebElement segApiClassify;

	/*
	 * 接口分类详细
	 */
	@FindBy(xpath = "//section[2]/ul")
	public static WebElement segApiClassifydetail;

	/*
	 * 接口分类page设置num
	 */
	@FindBy(xpath = "//section[2]/div")
	public static WebElement segApiClassifyPageNum;
}
