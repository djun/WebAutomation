package cn.juhe.webautomation.data;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;
import java.util.regex.Pattern;

import cn.juhe.webautomation.common.LogHandle;

/**
 * 文件 处理器
 * 
 * @author murphy
 * @date 2015-8-20
 */
public class FileHandle {

	public static final String USER_DIR = System.getProperty("user.dir");
	private static Properties properties = null;
	private static Properties parameters_cfg = null;
	private static LogHandle log = LogHandle.getInstence();

	/**
	 * Load the properties from a specified file.
	 * 
	 * @param String
	 *            fileDir - The directory of the property file
	 * @param String
	 *            fileName - The file name of the property file
	 * @author murphy
	 * @date 2015-8-20
	 * @return
	 */
	public static Properties getProperties(String fileDir, String fileName) {
		if (properties == null) {
			File file = new File(fileDir + "/" + fileName);
			properties = getPropertiesInstance(file);
		}
		return properties;
	}

	/**
	 * Load the properties from "parameters.cfg" file.
	 * 
	 * @param null
	 * @return Properties
	 * @author murphy
	 * @date 2015-8-20
	 */
	public static Properties getParametersCfg() {
		if (parameters_cfg == null) {
			String fileName = USER_DIR + "/files/config/properties.xml";
			File file = new File(fileName);
			parameters_cfg = getPropertiesFromXmlFile(file);
		}
		return parameters_cfg;
	}

	/**
	 * Set property-value and save them to the file.
	 * 
	 * @param String
	 *            name - the name of the property.
	 * @param String
	 *            value - the value of the property.
	 * @param File
	 *            file - the file where to store the property and value.
	 * @param Properties
	 *            prop - the properties where to store the property and value.
	 * @author murphy
	 * @date 2015-8-20
	 */
	public static void setProperty(String name, String value, File file,
			Properties prop) {
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			fos = new FileOutputStream(file);
			fis = new FileInputStream(file);
			prop.load(fis);
			prop.put(name, value);
			prop.store(fos, "");
			fis.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
			log.logException(e);
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.logException(e);
				fis = null;
				fos = null;
			}
		}
	}

	/**
	 * Set property-value and save them to the file.
	 * 
	 * @param String
	 *            name - the name of the property.
	 * @param value
	 *            value - the value of the property.
	 * @param file
	 *            file - the file where to store the property and value.
	 * @author murphy
	 * @date 2015-8-20
	 */
	public static void setProperty(String name, String value, File file) {
		FileOutputStream fos = null;
		FileInputStream fis = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			fis = new FileInputStream(file);
			Properties prop = new Properties();
			prop.load(fis);
			prop.put(name, value);
			fos = new FileOutputStream(file);
			prop.store(fos, "");
			fis.close();
			fos.close();
			fis = null;
			fos = null;
		} catch (Exception e) {
			e.printStackTrace();
			log.logException(e);
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (fos != null)
					fos.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.logException(e);
				fis = null;
				fos = null;
			}
		}
	}

	/**
	 * Set property-value and save them to the file.
	 * 
	 * @param name
	 * @param value
	 * @param oFile
	 * @param tFile
	 * @author murphy
	 * @date 2015-8-20
	 */
	public static void setPropertyWithOutEscape(String name, String value,
			File oFile, File tFile) {

		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			if (!tFile.exists()) {
				tFile.createNewFile();
			}
			fis = new FileInputStream(oFile);
			isr = new InputStreamReader(fis);
			br = new BufferedReader(isr);
			String line = null;
			StringBuffer contentBf = new StringBuffer();
			while ((line = br.readLine()) != null) {
				if (!"".equals(line)) {
					if (line.startsWith(name)) {
						line = name + "=" + value;
					}
					contentBf.append(line + "\r\n");
				}
			}
			loadStringToFile(contentBf.toString(), tFile);
		} catch (Exception e) {
			e.printStackTrace();
			log.logException(e);
		} finally {
			try {
				if (fis != null)
					fis.close();
				if (isr != null)
					isr.close();
				if (br != null)
					br.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.logException(e);
			}
			fis = null;
			isr = null;
			br = null;
		}
	}

	/**
	 * Load the properties from a file.
	 * 
	 * @param File
	 *            file - where stores properties
	 * @return Properties
	 * @author murphy
	 * @date 2015-8-20
	 */
	public static Properties getPropertiesInstance(File file) {
		FileInputStream fis = null;
		Properties properties = new Properties();
		try {
			fis = new FileInputStream(file);
			properties.load(fis);
		} catch (IOException e) {
			e.printStackTrace();
			log.logException(e);
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.logException(e);
			}
			fis = null;
		}
		return properties;
	}

	/**
	 * Load the properties from a xml file.
	 * 
	 * getPropertiesFromXmlFile
	 * 
	 * @param file
	 *            file - where stores properties
	 * @return Properties
	 * @throws InvalidPropertiesFormatException
	 * @throws IOException
	 * @author murphy
	 * @date 2015-8-20
	 */
	public static Properties getPropertiesFromXmlFile(File file) {
		FileInputStream fis = null;
		Properties properties = new Properties();
		try {
			fis = new FileInputStream(file);
			properties.loadFromXML(fis);
		} catch (IOException e) {
			e.printStackTrace();
			log.logException(e);
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (IOException e) {
				log.logException(e);
			}
			fis = null;
		}
		return properties;
	}

	/**
	 * Read the end line of the file
	 * 
	 * @param file
	 * @return
	 * @author murphy
	 * @date 2015-8-20
	 */
	public String readFileEndLine(String file) {
		RandomAccessFile rf = null;
		String s = null;
		try {
			rf = new RandomAccessFile(file, "r");
			long len = rf.length();
			if (len <= 0) {
				rf.close();
				return null;
			}
			long start = rf.getFilePointer();
			long end = start + len - 1;
			rf.seek(end);
			int c = rf.read();
			while ((c != '\n' && c != '\r') || s == null || s == ""
					|| s.length() <= 0) {
				end = end - 1;
				rf.seek(end);
				c = rf.read();
				s = rf.readLine();
			}
			rf.close();
		} catch (IOException e) {
			e.printStackTrace();
			log.logException(e);
		}
		return s;
	}

	/**
	 * 删除路径，先删除路径下面所有的文件
	 * 
	 * @param String
	 *            folderPath - 要删除的路径
	 * @author murphy
	 * @date 2015-8-20
	 */
	public static void delFolder(String folderPath) {
		try {
			delAllFile(folderPath);
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete();
		} catch (Exception e) {
			e.printStackTrace();
			log.logException(e);
		}
	}

	/**
	 * 删除路径下面的所有文件
	 * 
	 * @param path
	 * @return
	 * @author murphy
	 * @date 2015-8-20
	 */
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);
				delFolder(path + "/" + tempList[i]);
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 删除指定文件
	 * 
	 * @param fileName
	 * @return
	 * @author murphy
	 * @date 2015-8-20
	 */
	public static boolean deleteFile(String fileName) {
		File file = new File(fileName);
		if (file.isFile() && file.exists()) {
			file.delete();
			System.out.println("Delete file " + fileName + ":successful.");
			return true;
		} else {
			System.out.println("Delete file " + fileName + ":failed.");
			return false;
		}
	}

	/**
	 * read a text file to string
	 * 
	 * @param f
	 * @return
	 * @throws IOException
	 * @author murphy
	 * @date 2015-8-20
	 */
	public static String loadFileToString(File f) {
		BufferedReader br = null;
		String ret = null;
		try {
			br = new BufferedReader(new FileReader(f));
			String line = null;
			StringBuffer sb = new StringBuffer((int) f.length());
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}
			ret = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			log.logException(e);
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (Exception e) {
				e.printStackTrace();
				log.logException(e);
			}
			br = null;

		}
		return ret;
	}

	/**
	 * write string to file
	 * 
	 * @param s
	 * @param f
	 * @throws IOException
	 * @author murphy
	 * @date 2015-8-20
	 */
	public static void loadStringToFile(String s, File f) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		try {
			fw = new FileWriter(f);
			bw = new BufferedWriter(fw);
			bw.write(s);
		} catch (IOException e) {
			e.printStackTrace();
			log.logException(e);
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.logException(e);
			}
			bw = null;
			fw = null;
		}
	}

	/**
	 * This method is designed to copy file
	 * 
	 * @param String
	 *            fileName is - the orignal fileName. exp: "C:/filename.exe"
	 * @param String
	 *            saveDir is - the objective path. exp: "D:/installer"
	 * @author murphy
	 * @date 2015-8-20
	 */
	public boolean copyFile(String fileName, String saveDir) {
		boolean isSuccess = false;
		File file = new File(fileName);
		File dir = new File(saveDir);
		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileChannel fin = null;
		FileChannel fou = null;
		if (!file.isFile()) {
			isSuccess = false;
		} else {
			try {
				File fcopy = new File(dir, file.getName());
				fcopy.createNewFile();
				fis = new FileInputStream(file);
				fos = new FileOutputStream(fcopy);
				fin = fis.getChannel();
				fou = fos.getChannel();
				ByteBuffer byb = ByteBuffer.allocate(1024);
				int i = 1;
				while (i != -1) {
					byb.clear();
					i = fin.read(byb);
					byb.flip();
					fou.write(byb);
				}
				fin.close();
				fou.close();
				fis.close();
				fos.close();
				isSuccess = true;
			} catch (Exception e) {
				e.printStackTrace();
				log.logException(e);
				isSuccess = false;
			} finally {
				try {
					if (fis != null)
						fis.close();
					if (fos != null)
						fos.close();
					if (fin != null)
						fin.close();
					if (fou != null)
						fou.close();
				} catch (IOException e) {
					e.printStackTrace();
					log.logException(e);
				}
				fis = null;
				fos = null;
				fin = null;
				fou = null;
			}
		}
		return isSuccess;
	}

	/**
	 * This method is designed to copy file
	 * 
	 * @param File
	 *            file is - the orignal file. exp: File("C:/filename.exe")
	 * @param File
	 *            dir is - the objective path. exp: File("D:/installer")
	 * @author murphy
	 * @date 2015-8-20
	 */
	public static boolean copyFile(File file, File dir) {
		boolean isSuccess = false;
		FileInputStream fis = null;
		FileOutputStream fos = null;
		FileChannel fin = null;
		FileChannel fou = null;
		if (!file.isFile()) {
			isSuccess = false;
		} else {
			try {
				File fcopy = new File(dir, file.getName());
				fcopy.createNewFile();
				fis = new FileInputStream(file);
				fos = new FileOutputStream(fcopy);
				fin = fis.getChannel();
				fou = fos.getChannel();
				ByteBuffer byb = ByteBuffer.allocate(10240);
				int i = 1;
				while (i != -1) {
					byb.clear();
					i = fin.read(byb);
					byb.flip();
					fou.write(byb);
				}
				isSuccess = true;
			} catch (Exception e) {
				e.printStackTrace();
				isSuccess = false;
			} finally {
				try {
					if (fis != null)
						fis.close();
					if (fos != null)
						fos.close();
					if (fin != null)
						fin.close();
					if (fou != null)
						fou.close();
				} catch (IOException e) {
					e.printStackTrace();
					log.logException(e);
				}
				fis = null;
				fos = null;
				fin = null;
				fou = null;
			}
		}
		return isSuccess;
	}

	/**
	 * download file
	 * 
	 * @param String
	 *            fileUrl - the download resource url
	 * @param String
	 *            fileSaveName - save full name(path+name)
	 * @author murphy
	 * @date 2015-8-20
	 * 
	 */
	public static void downloadFile(String fileUrl, String fileSaveName) {
		FileOutputStream fos = null;
		BufferedInputStream bis = null;
		HttpURLConnection httpUrl = null;
		URL url = null;
		byte[] buf = new byte[1024];
		int size = 0;
		try {
			url = new URL(fileUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			bis = new BufferedInputStream(httpUrl.getInputStream());
			fos = new FileOutputStream(fileSaveName);
			while ((size = bis.read(buf)) != -1)
				fos.write(buf, 0, size);
		} catch (IOException e) {
			e.printStackTrace();
			log.logException(e);
		} finally {
			try {
				if (httpUrl != null)
					httpUrl.disconnect();
				if (fos != null)
					fos.close();
				if (bis != null)
					bis.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.logException(e);
			}
			httpUrl = null;
			fos = null;
			bis = null;
		}
	}

	/**
	 * 文件重命名 changeFileName
	 * 
	 * @param oldFullName
	 * @param newName
	 * @author murphy
	 * @date 2015-8-21
	 */
	public void changeFileName(String oldFullName, String newName) {
		File file = new File(oldFullName);
		file.renameTo(new File(newName));
	}

	/**
	 * 修改文件内容 changeFileWords
	 * 
	 * @param fileName
	 *            ：String of the modify file full name
	 * @param oldWords
	 *            ： The replaced old words
	 * @param newWords
	 *            ： The replaced new words
	 * @author murphy
	 * @date 2015-8-21
	 */
	public void changeFileWords(String fileName, String oldWords,
			String newWords) {
		File file = new File(fileName);
		String str = FileHandle.loadFileToString(file);
		String s = str.replaceAll(oldWords, newWords);
		FileHandle.loadStringToFile(s, file);
	}

	/**
	 * 复制文件 copyFileContent
	 * 
	 * @param fromFilePath
	 * @param toFilePath
	 * @author murphy
	 * @date 2015-8-21
	 */
	public void copyFileContent(String fromFilePath, String toFilePath) {
		File fomFile = new File(fromFilePath);
		File toFile = new File(toFilePath);
		try {
			String str = FileHandle.loadFileToString(fomFile);
			FileHandle.loadStringToFile(str, toFile);
		} catch (Exception e) {
			e.printStackTrace();
			log.logException(e);
		}
	}

	/**
	 * keyword in log to show if success checkInfoInFile
	 * 
	 * @param filename
	 * @param info
	 * @return
	 * @author murphy
	 * @date 2015-8-21
	 */
	public static boolean checkInfoInFile(String filename, String info) {

		if (filename == null || info == null)
			return false;
		boolean isSuccess = false;
		RandomAccessFile rf = null;
		try {
			rf = new RandomAccessFile(filename, "r");
			String s1 = null;
			while ((s1 = rf.readLine()) != null) {
				if (isContains(s1.toLowerCase(), info.toLowerCase())) {
					isSuccess = true;
					break;
				}
			}
			isSuccess = true;
		} catch (IOException e) {
			e.printStackTrace();
			log.logException(e);
			isSuccess = false;
		} finally {
			try {
				if (rf != null)
					rf.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.logException(e);
			}
		}
		return isSuccess;
	}

	/**
	 * 判断是否是数字
	 * 
	 * @param str
	 * @return
	 * @author murphy
	 * @date 2015-8-21
	 */
	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		return pattern.matcher(str).matches();
	}

	/**
	 * 判断给定字符串是否在文件里
	 * 
	 * @param filename
	 * @param info
	 * @param time
	 * @return
	 * @author murphy
	 * @date 2015-8-21
	 */
	public static boolean checkInfoInFile(String filename, String info,
			Date time) {
		if (filename == null || info == null || !new File(filename).exists()) {
			return false;
		}
		boolean isSuccess = false;
		RandomAccessFile rf = null;
		try {
			rf = new RandomAccessFile(filename, "r");
			String s1 = null;
			while ((s1 = rf.readLine()) != null) {
				if (isContains(s1.toLowerCase(), info.toLowerCase())) {
					if (isAfter(s1, time)) {
						isSuccess = true;
						break;
					}
				}
			}
			isSuccess = true;
		} catch (IOException e) {
			e.printStackTrace();
			log.logException(e);
			isSuccess = false;
		} finally {
			try {
				if (rf != null)
					rf.close();
			} catch (IOException e) {
				e.printStackTrace();
				log.logException(e);
			}
		}
		return isSuccess;
	}

	/**
	 * isAfter
	 * 
	 * @param s
	 * @param odate
	 * @return
	 * @author murphy
	 * @date 2015-8-21
	 */
	private static boolean isAfter(String s, Date odate) {
		boolean isAfter = false;
		String ss = s.substring(0, "yyyy-MM-dd HH:mm:ss,SSS".length());
		SimpleDateFormat format = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss,SSS");
		// Date oDate = format.parse("2011-09-30 23:47:47,514");
		try {
			Date date = format.parse(ss);
			isAfter = date.after(odate);
		} catch (ParseException e) {
			e.printStackTrace();
			log.logException(e);
		}
		return isAfter;
	}

	/**
	 * Design to instead of String.contains(String) because this API is since
	 * JDK1.5 not supper 1.4
	 * 
	 * @param str
	 * @param sub
	 * @return
	 * @author murphy
	 * @date 2015-8-21
	 */
	public static boolean isContains(String str, String sub) {
		boolean isContain = false;
		if (str == null || sub == null) {
			isContain = false;
		} else if (str.length() < sub.length()) {
			isContain = false;
		} else {
			int strCount = str.length();
			int subCount = sub.length();
			for (int i = 0; i <= strCount - subCount; i++) {
				if (str.substring(i, i + subCount).equals(sub)) {
					isContain = true;
					break;
				}
			}
		}
		return isContain;
	}
}
