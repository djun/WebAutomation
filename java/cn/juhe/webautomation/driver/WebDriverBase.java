package cn.juhe.webautomation.driver;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;

import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.assertthat.selenium_shutterbug.utils.file.FileUtil;
import com.assertthat.selenium_shutterbug.utils.web.ScrollStrategy;

import cn.juhe.webautomation.common.Util;

/***
 * 
 * @author murphy
 *
 */
public class WebDriverBase extends DriverBase {

	protected WebDriver webDriver = null;

	protected String baseDomain;

	public WebDriver webDriver() {
		if (webDriver == null) {
			webDriver = initWebDriver(true);
		}
		baseDomain = driverMgr.getConfig().baseDomain;
		PageFactory.initElements(webDriver, this);
//		webDriver.manage().window().maximize();
		webDriver.manage().window().setSize(new Dimension(1920, 1080));
		return webDriver;
	}

	public void driverQuit() {
		if (webDriver != null) {
			webDriver.quit();
			webDriver = null;
		}
	}

	/**
	 * 执行javascript
	 * 
	 * @param filePath
	 * @param arg
	 * @return
	 */
	public Object executeJsScript(String filePath, Object... arg) {
		String script = FileUtil.getJsScript(filePath);
		JavascriptExecutor js = (JavascriptExecutor) webDriver;
		return js.executeScript(script, arg);
	}

	/**
	 * 获取当前X轴滑动偏移值
	 * 
	 * @return
	 */
	public int getCurrentScrollX() {
		return ((Long) jsExecutor.executeScript("return window.pageXOffset;")).intValue();
	}

	/**
	 * 获取当前Y轴滑动偏移值
	 * 
	 * @return
	 */
	public int getCurrentScrollY() {
		return ((Long) jsExecutor.executeScript("return window.pageYOffset;")).intValue();
	}

	/**
	 * 截图
	 * 
	 * @param fileName
	 */
	public void takeScreenshot(String fileName, int type) {
		String imageFolderPath;
		if (type == 0) {
			imageFolderPath = System.getProperty("user.dir") + "/files/data/";
		} else {
			imageFolderPath = System.getProperty("user.dir") + "/files/results/" + Util.runTitle + "/";
		}
		String imageName = fileName + ".png";
		File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		String filePath = imageFolderPath + imageName;
		File p = new File(imageFolderPath);
		if (!p.exists()) {
			p.mkdir(); // 创建文件夹
		}
		File destFile = new File(filePath);
		try {
			FileUtils.copyFile(screenshot, destFile);
			// reportLogScreenshot(destFile);
		} catch (Exception e) {
			filePath = filePath + " Take Screentshot Failed:";
			log.logError(filePath + e.getMessage());
			Reporter.log(filePath);
			filePath = "";
		}
	}

	/**
	 * 截图，主要用于图片比较
	 * 
	 * @param fileName
	 *            生成图片名称
	 */
	public void takeScreenshot(String fileName) {
		String imageFolderPath = "files/results/" + Util.runTitle + "/";
		String imageName = fileName + ".png";
		File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		String filePath = imageFolderPath + imageName;
		File p = new File(imageFolderPath);
		if (!p.exists()) {
			p.mkdir(); // 创建文件夹
		}
		File destFile = new File(filePath);
		try {
			FileUtils.copyFile(screenshot, destFile);
			// reportLogScreenshot(destFile);
		} catch (Exception e) {
			filePath = filePath + " Take Screentshot Failed:";
			log.logError(filePath + e.getMessage());
			Reporter.log(filePath);
			filePath = "";
		}
	}

	/**
	 * 获取当前页面的bufferimage
	 * 
	 * @return
	 */
	public BufferedImage getPageImage() {
		BufferedImage img = null;
		File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		try {
			img = ImageIO.read(screenshot);
		} catch (IOException e) {
			log.logException(e);
		}
		return img;
	}

	/**
	 * 截取元素图片
	 * 
	 * @param ele
	 *            某个元素
	 * @param filename
	 *            保存文件，默认png
	 * @param type
	 *            0保存data目录，其他是result目录
	 */
	public void takeElementScreenshot(WebElement ele, String filename, int type) {
		try {
			// 移动到指定元素
			moveToElement(ele);
			Rectangle rect = new Rectangle(ele.getLocation(), ele.getSize());
			// 移动光标到当前元素的左边(保证画面静止正常)
			actions.moveByOffset(-(rect.getWidth() / 2 + 5), 0).perform();
			sleepFor(1000);
			Util.saveImageAsPng(getPageImage().getSubimage(rect.x - getCurrentScrollX(), rect.y - getCurrentScrollY(),
					rect.getWidth(), rect.getHeight()), filename, type);
		} catch (Exception e) {
			log.logException(e);
		}
	}

	/**
	 * 元素截图（第三方工具实现）
	 * 
	 * @param ele
	 * @param fileName
	 * @param type
	 */
	public void takeElementshot(WebElement ele, String fileName, int type) {
		try {
			String imageFolderPath;
			if (type == 0) {
				imageFolderPath = System.getProperty("user.dir") + "/files/data/";
			} else {
				imageFolderPath = System.getProperty("user.dir") + "/files/results/" + Util.runTitle + "/";
			}
			File p = new File(imageFolderPath);
			if (!p.exists()) {
				p.mkdir(); // 创建文件夹
			}
			Shutterbug.shootElement(webDriver, ele).withName(fileName).save(imageFolderPath);
		} catch (Exception e) {
			log.logException(ele.toString() + "--->" + e.getMessage());
		}
	}

	/**
	 * 截取屏幕（for chrome;借助工具，包含不可见部分）
	 * 
	 * @param fileName
	 * @param type
	 */
	public void takeFullScreen(String fileName, int type) {
		String imageFolderPath;
		if (type == 0) {
			imageFolderPath = System.getProperty("user.dir") + "/files/data/";
		} else {
			imageFolderPath = System.getProperty("user.dir") + "/files/results/" + Util.runTitle + "/";
		}
		File p = new File(imageFolderPath);
		if (!p.exists()) {
			p.mkdir(); // 创建文件夹
		}
		Shutterbug.shootPage(webDriver, ScrollStrategy.BOTH_DIRECTIONS).withName(fileName).save(imageFolderPath);
	}

	/**
	 * 判断页面是否加载完成
	 * 
	 * @return
	 */
	public boolean isPageLoadComplete() {
		try {
			return jsExecutor.executeScript("return document.readyState;").equals("complete");
		} catch (Exception ex) {
			log.logException(ex);
			return false;
		}
	}

	/**
	 * 判断指定时间内页面是否加载完成
	 * 
	 * @param timeout
	 * @return
	 */
	public boolean isPageLoadComplete(int timeout) {
		long start = System.currentTimeMillis();
		boolean isLoadComplete = false;
		while (!isLoadComplete && ((System.currentTimeMillis() - start) < timeout * 1000)) {
			try {
				isLoadComplete = jsExecutor.executeScript("return document.readyState;").equals("complete");
			} catch (Exception ex) {
			}
		}
		return isLoadComplete;
	}

	/**
	 * 等待（s）
	 * 
	 * @param second
	 */
	public void sleepFor(int milliseconds) {
		try {
			Thread.sleep(milliseconds);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			log.logException("Sleep Exception" + e.getMessage());
		}
	}

	/**
	 * 查找页面元素通过by
	 * 
	 * @param by
	 * @return
	 */
	public WebElement findElement(By by) {
		WebElement ele = null;
		try {
			ele = webDriver.findElement(by);
		} catch (Exception e) {
			log.logException(e.getMessage() + "\n" + "Find elements failed: " + by);
		}
		return ele;
	}

	/**
	 * 查找页面元素集合通过by
	 * 
	 * @param by
	 * @return
	 */
	public List<WebElement> findElements(By by) {
		List<WebElement> eles = null;
		try {
			eles = webDriver.findElements(by);
		} catch (Exception e) {
			log.logException(e.getMessage() + "\n" + "Find elements failed: " + by);
		}
		return eles;
	}

	/**
	 * 判断元素是否存在
	 * 
	 * @param by
	 * @return
	 */
	public boolean isExistElement(By by) {
		try {
			return findElement(by).isDisplayed();
		} catch (Exception e) {
			log.logException(e);
			return false;
		}
	}

	/**
	 * 判断元素是否存在
	 * 
	 * @param ele
	 * @return
	 */
	public boolean isExistElement(WebElement ele) {
		try {
			return ele.isDisplayed();
		} catch (Exception e) {
			log.logException(e);
			return false;
		}
	}

	/**
	 * 指定时间内元素是否存在
	 * 
	 * @param by
	 * @param seconds
	 *            秒
	 * @return
	 */
	public boolean isExistElement(By by, int timeout) {
		long start = System.currentTimeMillis();
		boolean isExist = false;
		while (!isExist && ((System.currentTimeMillis() - start) < timeout * 1000)) {
			try {
				isExist = findElement(by).isDisplayed();
			} catch (Exception ex) {
			}
		}
		return isExist;
	}

	/**
	 * 指定时间内元素是否存在
	 * 
	 * @param ele
	 * @param timeout
	 * @return
	 */
	public boolean isExistElement(WebElement ele, int timeout) {
		long start = System.currentTimeMillis();
		boolean isExist = false;
		while (!isExist && ((System.currentTimeMillis() - start) < timeout * 1000)) {
			try {
				isExist = ele.isDisplayed();
			} catch (Exception ex) {
			}
		}
		return isExist;
	}

	/*
	 * 元素左击
	 */
	public void leftClick(WebElement ele) {
		actions.click(ele).perform();
	}

	/*
	 * 元素右击
	 */
	public void rightClick(WebElement ele) {
		actions.contextClick(ele).perform();
	}

	/*
	 * 元素双击
	 */
	public void doubleClick(WebElement ele) {
		actions.doubleClick(ele).perform();
	}

	/*
	 * 长按元素
	 */
	public void clickAndHold(WebElement ele) {
		actions.clickAndHold(ele).perform();
	}

	/*
	 * 鼠标移动至指定元素
	 */
	public void moveToElement(WebElement ele) {
		actions.moveToElement(ele).perform();
	}
}
