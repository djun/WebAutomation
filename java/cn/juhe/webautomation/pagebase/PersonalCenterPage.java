package cn.juhe.webautomation.pagebase;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PersonalCenterPage extends HomePage {

	@FindBy(partialLinkText = "我的聚合")
	public static WebElement lkMyJuhe;

	@FindBy(partialLinkText = "我的数据")
	public static WebElement lkMyData;

	@FindBy(partialLinkText = "我的活动")
	public static WebElement lkMyActivity;

	@FindBy(partialLinkText = "我的收藏")
	public static WebElement lkMyFavorite;

	@FindBy(partialLinkText = "IP白名单")
	public static WebElement lkIpWhiteList;

	@FindBy(partialLinkText = "我的余额")
	public static WebElement lkMyBalance;

	@FindBy(partialLinkText = "我的充值记录")
	public static WebElement lkMyChargeLog;

	@FindBy(partialLinkText = "我的消费记录")
	public static WebElement lkMyConsumeLog;

	@FindBy(partialLinkText = "我的工单")
	public static WebElement lkMyWorkList;

	@FindBy(partialLinkText = "用户中心")
	public static WebElement lkUserCenter;

	@FindBy(partialLinkText = "用户信息")
	public static WebElement lkAccountInfo;

	@FindBy(partialLinkText = "密码修改")
	public static WebElement lkPwdModify;

	@FindBy(partialLinkText = "实名认证")
	public static WebElement lkCertification;

	@FindBy(partialLinkText = "聚合币")
	public static WebElement lkJuheIcon;

	@FindBy(partialLinkText = "优惠券")
	public static WebElement lkDiscountCoupon;

	@FindBy(partialLinkText = "发票管理")
	public static WebElement lkInvoiceManage;

	@FindBy(name = "oldPassword")
	public static WebElement txtOldPwd;

	@FindBy(name = "newPassword")
	public static WebElement txtNewPwd;

	@FindBy(name = "newPassword2")
	public static WebElement txtConfirmPwd;

	@FindBy(css = "button.centerBtn")
	public static WebElement btnConfirm;
	
	@FindBy(xpath = "//div[@type='dialog']/div/a")
	public static WebElement dialogConfirm;

}
