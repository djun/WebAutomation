package cn.juhe.webautomation.common;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.imageio.ImageIO;

import org.testng.Assert;
import org.testng.Reporter;


/**
 * 运行时参数设置
 * 
 * @author murphy
 * @date 2015-8-19
 */
public class Util {

	/**
	 * 运行当前类
	 */
	public static String runClass = "runClass";
	/**
	 * 运行当前case
	 */
	public static String runCase = "runCase";
	/**
	 * 运行当前title
	 */
	public static String runTitle = "runTitle";
	/**
	 * 运行当前时间
	 */
	public static String runTime = formatDate(new Date(), "yyyyMMdd_HHmmss");

	/**
	 * LogHandle
	 */
	public static LogHandle log = LogHandle.getInstence();

	/**
	 * 根据运行时路径生成截图名 generateScreenShotName
	 * 
	 * @return screenshot name
	 * @author murphy
	 * @date 2015-8-21
	 */
	public static String generateScreenShotName() {
		String path = System.getProperty("user.dir") + "/files/results/" + runTitle;
		File p = new File(path);
		if (!p.exists()) {
			p.mkdirs();
		}
		String fileName = "/" + runClass + "_" + getCurrentDateTime() + ".png";
		return path + fileName;
	}

	/**
	 * 获取当前时间, 格式为 "yyyyMMdd_HHmmss" getCurrentDateTime
	 * 
	 * @return runTime
	 * @author murphy
	 * @date 2015-8-21
	 */
	public static String getCurrentDateTime() {
		runTime = formatDate(new Date(), "yyyyMMdd_HHmmss");
		return runTime;
	}

	/**
	 * 根据运行时的method name 获取Title getRunTitle
	 * 
	 * @param index
	 * @author murphy
	 * @date 2015-8-21
	 */
	public static void getRunTitle(int index) {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[index];
		runClass = ste.getClassName().replaceAll("\\w+\\.+", "");
		runCase = ste.getMethodName();
		runTitle = runClass + "_" + runCase + "_" + getCurrentDateTime();
	}

	public static void getRunTitle(Method m) {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		runClass = ste.getClassName().replaceAll("\\w+\\.+", "");
		runCase = m.getName();
		runTitle = runClass + "_" + runCase + "_" + getCurrentDateTime();
		System.out.println(runTitle);
	}

	/**
	 * 根据运行时的method name 获取Title getRunTitle
	 * 
	 * @author murphy
	 * @date 2015-8-21
	 */
	public static void getRunTitle() {
		StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
		runClass = ste.getClassName().replaceAll("\\w+\\.+", "");
		runCase = ste.getMethodName();
		runTitle = runClass + "_" + runCase + "_" + getCurrentDateTime();
		System.out.println(runTitle);
	}

	/**
	 * 返回一个字符串，该值介于[0，max)区间， 长度为len， 不足在数值前用0补足。 比如随机数值为0，长度为6的话，返回就是000000
	 * getRandomNumber
	 * 
	 * @param max
	 * @param len
	 * @return randomNumber
	 * @author murphy
	 * @date 2015-8-21
	 */
	public static String getRandomNumber(int max, int len) {
		int a = new Random().nextInt(max);
		String s = String.valueOf(a);
		for (int j = s.length(); j < len; j++) {
			s = "0" + s;
		}
		return s;
	}

	/**
	 * 返回一个和expString不一样的字符串，该值介于[0，max)区间， 长度为len， 不足在数值前用0补足。
	 * 比如随机数值为0，长度为6的话，返回就是000000 getRandomNumber
	 * 
	 * @param max
	 * @param len
	 * @param expString
	 * @return randomNumber
	 * @author murphy
	 * @date 2015-8-21
	 */
	public static String getRandomNumber(int max, int len, String expString) {
		String returnStr = "";
		do {
			returnStr = getRandomNumber(max, len);
		} while (expString.equals(returnStr));

		return returnStr;
	}

	/**
	 * 返回指定长度的密码（包含数字和字母）
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomPassword(int length) {
		final int maxNum = 36;
		int i;
		int count = 0;
		char[] str = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
				't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < length) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}

	/**
	 * 转换时间为"yyyyMMddHHmm"格式的字符串 formatDate
	 * 
	 * @param date
	 * @return formatDate
	 * @author murphy
	 * @date 2015-8-21
	 */
	public static String formatDate(Date date) {
		return formatDate(date, "yyyyMMddHHmm");
	}

	/**
	 * 转换时间为指定格式的字符串 formatDate
	 * 
	 * @param date
	 * @param format
	 * @return formatDate
	 * @author murphy
	 * @date 2015-8-21
	 */
	public static String formatDate(Date date, String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	/**
	 * 根据长度产生一个随机的字符串 getRandomString
	 * 
	 * @param length
	 * @return RandomString
	 * @author murphy
	 * @date 2015-8-21
	 */
	public static String getRandomString(int length) {
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int num = random.nextInt(62);
			buf.append(str.charAt(num));
		}
		return buf.toString();
	}

	/**
	 * 获取数据,去除 字符串前后的"[" , "]";
	 * 
	 * getData
	 * 
	 * @param obj
	 * @return 去除"[" , "]"后的字符串
	 * @author murphy
	 * @date 2015-9-21
	 */
	public static String getData(Object obj) {
		return obj.toString().replaceAll("\\[", "").replaceAll("\\]", "").trim();
	}

	/**
	 * 等待指定毫秒时间
	 * 
	 * sleep
	 * 
	 * @param timeoutInMilliseconds
	 * @author murphy
	 * @date 2015-9-21
	 */
	public static void sleep(long timeoutInMilliseconds) {
		try {
			Thread.sleep(timeoutInMilliseconds);
		} catch (InterruptedException e) {
			log.logException(e);
			e.printStackTrace();
		}
	}

	/**
	 * 将图片转为BufferedImage
	 * 
	 * @param type
	 *            0,是从data目录获取; 1,是从result目录获取
	 * @param filename
	 *            文件名称，不要后缀，默认png
	 * @return
	 */
	public static BufferedImage getImageFromFile(String filename, int type) {
		String imageFolderPath;
		BufferedImage img = null;
		if (type == 0) {
			imageFolderPath = System.getProperty("user.dir") + "/files/data/";
		} else {
			imageFolderPath = System.getProperty("user.dir") + "/files/results/" + Util.runTitle + "/";
		}
		try {
			img = ImageIO.read(new File(imageFolderPath + filename + ".png"));
		} catch (IOException e) {
			log.logException(e);
		}
		return img;
	}

	/**
	 * 将图片转为BufferedImage
	 * 
	 * @param file
	 * @return
	 */
	public static BufferedImage getImageFromFile(File file) {
		if (!file.exists()) {
			return null;
		}
		BufferedImage img = null;
		try {
			img = ImageIO.read(file);
		} catch (IOException e) {
			log.logException(e);
		}
		return img;
	}

	/**
	 * 按像素位比较两个图片是否一致
	 * 
	 * @param orgImg
	 *            原始图片
	 * @param otherImg
	 *            比较图片
	 * @param percent
	 *            相似度（0-1） 1是完全相同
	 * @return
	 */
	public static boolean isImageMatch(BufferedImage orgImg, BufferedImage otherImg, double percent) {
		int width = orgImg.getWidth();
		int height = orgImg.getHeight();
		if (otherImg.getWidth() != width || otherImg.getHeight() != height) {
			return false;
		} else {
			int numDiffPixels = 0;
			for (int y = 0; y < height; y++) {
				for (int x = 0; x < width; x++) {
					if (orgImg.getRGB(x, y) != otherImg.getRGB(x, y)) {
						numDiffPixels++;
					}
				}
			}
			double numberPixels = height * width;
			double diffPercent = numDiffPixels / numberPixels;
			return percent <= 1.0D - diffPercent;
		}
	}

	/**
	 * 按像素位比较两个图片是否一致
	 * 
	 * @param orgImg
	 *            原始图片名称
	 * @param otherImg
	 *            比较图片名称
	 * @param percent
	 *            相似度（0-1） 1是完全相同
	 * @return
	 */
	public static boolean isImageMatch(String orgFileName, String otherFileName, double percent) {
		String imageFolderPath = System.getProperty("user.dir") + "/files/results/" + Util.runTitle + "/";
		String imageDataFolderPath = System.getProperty("user.dir") + "/files/data/";
		File file1 = new File(imageDataFolderPath + orgFileName + ".png");
		File file2 = new File(imageFolderPath + otherFileName + ".png");
		if (file1.exists() && file2.exists()) {
			return isImageMatch(getImageFromFile(file1), getImageFromFile(file2), percent);
		} else {
			log.logError(orgFileName + "\t" + otherFileName + " 某个文件不存在啊！");
			return false;
		}
	}

	/**
	 * 对指定图片其中部分截取
	 * 
	 * @param image
	 * @param startX
	 * @param startY
	 * @param width
	 * @param height
	 * @return
	 */
	public static BufferedImage getSubImage(BufferedImage image, int startX, int startY, int width, int height) {
		return image.getSubimage(startX, startY, width, height);
	}

	/**
	 * 将BufferedImage保存成PNG
	 * 
	 * @param bufferedImg
	 * @param fileName
	 *            文件名称，不要后缀，默认png
	 */
	public static void saveImageAsPng(BufferedImage bufferedImg, String fileName, int type) {
		String imageFolderPath;
		String imageName = fileName + ".png";
		if (type == 0) {
			imageFolderPath = System.getProperty("user.dir") + "/files/data/";
		} else {
			imageFolderPath = System.getProperty("user.dir") + "/files/results/" + Util.runTitle + "/";
		}
		File p = new File(imageFolderPath);
		if (!p.exists()) {
			p.mkdir(); // 创建文件夹
		}
		try {
			ImageIO.write(bufferedImg, "PNG", new File(imageFolderPath + imageName));
		} catch (IOException e) {
			log.logException(e);
		}
	}

	/**
	 * hightlight 两个图片不同的地方
	 * 
	 * @param orgFileName
	 * @param otherFileName
	 * @param saveFilename
	 */
	public static int getDifferentImage(String orgFileName, String otherFileName) {
		int width1, width2, height1, height2, highlight;
		int diff = 0;
		String saveFilename = "diff-" + orgFileName;
		BufferedImage orgImage, newImage, outImage;
		String imageDataFolderPath = System.getProperty("user.dir") + "/files/data/";
		String imageFolderPath = System.getProperty("user.dir") + "/files/results/" + Util.runTitle + "/";
		File file1 = new File(imageDataFolderPath + orgFileName + ".png");
		File file2 = new File(imageFolderPath + otherFileName + ".png");
		if (!file1.exists() || !file2.exists()) {
			log.logError(orgFileName + "\t" + otherFileName + " 某个文件不存在啊！");
			return -1;
		}
		try {
			orgImage = getImageFromFile(file1);
			newImage = getImageFromFile(file2);
			width1 = orgImage.getWidth();
			height1 = orgImage.getHeight();
			width2 = newImage.getWidth();
			height2 = newImage.getHeight();
			if (width1 != width2 || height1 != height2) {
				log.logError(orgFileName + "---" + otherFileName + "两个图片长或者宽不一致");
				return 0;
			} else {
				outImage = new BufferedImage(width1, height1, BufferedImage.TYPE_INT_RGB);
				highlight = Color.RED.getRGB();
				int[] p1 = orgImage.getRGB(0, 0, width1, height1, null, 0, width1);
				int[] p2 = newImage.getRGB(0, 0, width2, height2, null, 0, width2);
				for (int i = 0; i < p1.length; i++) {
					if (p1[i] != p2[i]) {
						p2[i] = highlight;
						diff++;
					}
				}
				if (diff != 0) {
					outImage.setRGB(0, 0, width2, height2, p2, 0, width2);
					saveImageAsPng(outImage, saveFilename, 1);
					log.logError(orgFileName + " --- " + otherFileName + "两张图片不成功，两张图片存在差异!");
					return 1;
				} else {
					// log.logInfo(orgFileName + " --- " + otherFileName +
					// "两张图片比较一致");
					return 2;
				}
			}
		} catch (Exception e) {
			log.logException(e);
			return -2;
		}
	}

	/**
	 * 判断图片是否是单一颜色
	 * 
	 * @param fileName
	 *            文件名称
	 * @param type
	 *            文件位置，0为data目录，1为result目录
	 * @param percent
	 *            范围（0-1） 例如0.6，超过60%的相同，就认为是单一图片
	 * @return
	 */
	public static boolean isSingleColorImage(String fileName, int type, double percent) {
		BufferedImage img = getImageFromFile(fileName, type);
		int width = img.getWidth();
		int height = img.getHeight();
		int startPixel = img.getRGB(0, 0);
		int numDiffPixels = 0;
		for (int y = 1; y < height; y++) {
			for (int x = 1; x < width; x++) {
				if (img.getRGB(x, y) != startPixel) {
					numDiffPixels++;
				}
			}
		}
		double numberPixels = height * width;
		double diffPercent = numDiffPixels / numberPixels;
		System.out.println("图片diff百分比：" + diffPercent);
		Reporter.log("图片diff百分比：" + diffPercent);
		return percent <= 1.0D - diffPercent;
	}

	/**
	 * 检查图片对比结果
	 * 
	 * @param status
	 * @param filename
	 */
	public static void checkImageCompareStatus(int status, String filename) {
		switch (status) {
		case -2:
			Assert.assertTrue(false, "对比: " + filename + "图片时产生异常，请查看对应文件!");
			break;
		case -1:
			Assert.assertTrue(false, "对比: " + filename + "图片时发现数据目录和日志目录改文件不存在!");
			break;
		case 0:
			Assert.assertTrue(false, "对比: " + filename + "图片时发现两张图片长宽不一致!");
			break;
		case 1:
			Assert.assertTrue(false, "对比: " + filename + "图片不成功，两张图片存在差异!");
		case 2:
			Assert.assertTrue(true, "对比: " + filename + "图片，两者完全一致!");
		default:
			break;
		}
	}
}