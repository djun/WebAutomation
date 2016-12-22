package cn.juhe.webautomation.data;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.juhe.webautomation.common.LogHandle;
import jxl.Cell;
import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 * 从excel中获取数据，第一列是一个特 殊列。 如果是合并的单元格，则next()方法 返回合 并单元格涉及到的这几行的数据，否则返回一行数据。
 * 
 * @author evan.Sun
 * @date 2015-8-19
 */
public class ExcelDataHandle implements Iterator<Object[]> {

	private Workbook book = null;
	private Sheet sheet = null;
	private int rowNum = 0;
	private int beforerowNum = 0;
	private int curPhysicalRowNo = 0;
	private int columnNum = 0; // 列数
	private String[] columnnName;
	private String context;
	private int nullCellNum;

	/**
	 * Excel 数据驱动，用于读取excel中的数据：
	 * 
	 * workbook名字必须为classname，sheet名字必须为methodname
	 * 
	 * @param classname
	 * @param methodname
	 * @author evan.Sun
	 * @date 2015-8-21
	 */
	public ExcelDataHandle(String classname, String methodname) {
		String bookName = classname.replaceAll("\\w+\\.+", "");
		getDataFromExcel(bookName, methodname);
	}

	/**
	 * Excel 数据驱动，用于读取excel中的数据：
	 * 
	 * workbook名字为TestData;，sheet名字必须为methodname
	 * 
	 * @param classname
	 * @author evan.Sun
	 * @date 2015-8-21
	 */
	public ExcelDataHandle(String methodname) {
		String bookName = "TestData";
		getDataFromExcel(bookName, methodname);
	}

	/**
	 * Excel 数据驱动，用于读取excel中的数据：
	 * 
	 * workbook名为TestData，sheet名为TestData
	 * 
	 * @param classname
	 * @author evan.Sun
	 * @date 2015-8-21
	 */
	public ExcelDataHandle() {
		String bookName = "TestData";
		String sheetName = "TestData";
		getDataFromExcel(bookName, sheetName);
	}

	/**
	 * 从excel中获取测试数据
	 * 
	 * @param bookName
	 * @param sheetName
	 * @author evan.Sun
	 * @date 2015-8-21
	 */
	public void getDataFromExcel(String bookName, String sheetName) {
		LogHandle log = LogHandle.getInstence();
		try {
			String excelPath = FileHandle.USER_DIR + "/files/data/";
			// book name 为class name, \\w 过滤包名，
			book = Workbook
					.getWorkbook(new File(excelPath + bookName + ".xls"));
			// sheet name 为方法名
			sheet = book.getSheet(sheetName);
			rowNum = sheet.getRows();
			Cell[] cell = sheet.getRow(0);
			columnNum = cell.length;
			beforerowNum = rowNum;
			for (int i = 1; i < beforerowNum; i++) // 统计行中为空的单元格数
			{
				nullCellNum = 0;
				for (int j = 0; j < columnNum; j++) {
					String val = sheet.getCell(j, i).getContents().toString();
					if (val.isEmpty() || val == null) {
						nullCellNum++;
					}
					if (nullCellNum >= columnNum) { // 如果nullCellNum大于或等于总的列数
						rowNum--; // 行数减一
					}
				}
			}
			columnnName = new String[cell.length];
			for (int colNum = 0; colNum < cell.length; colNum++) {
				columnnName[colNum] = cell[colNum].getContents().toString()
						.replace("\n", "");
			}
			curPhysicalRowNo++;
		} catch (BiffException ex) {
			log.logException(ex);

		} catch (IOException ex) {
			log.logException(ex);
		}
	}

	
	/**
	 * 确认是否有合并的单元行
	 * (non-Javadoc)
	 * hasNext
	 * @return
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		if (rowNum == 0 || curPhysicalRowNo >= rowNum) {
			try {
				book.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		} else
			return true;
	}

	
	/**
	 * 逐行读取数据
	 * (non-Javadoc)
	 * next
	 * @return
	 * @see java.util.Iterator#next()
	 */
	public Object[] next() {
		Map<String, ArrayList<String>> s = new HashMap<String, ArrayList<String>>();
		int rangeRow = 1;
		boolean thisIsRange = false;
		Range[] ranges = sheet.getMergedCells();
		// 首先要确定第一列占了几行，要区分 Range 还是 Row
		for (Range space : ranges) {
			if (space.getTopLeft().getColumn() == 0
					&& space.getBottomRight().getColumn() == 0
					&& space.getTopLeft().getRow() == curPhysicalRowNo) {

				rangeRow = space.getBottomRight().getRow()
						- space.getTopLeft().getRow() + 1;
				thisIsRange = true;
				break;
			}
		}
		for (int i = 0; i < columnNum; i++) {
			ArrayList<String> temp = new ArrayList<String>();
			if (thisIsRange) {
				if (i == 0) {
					context = sheet.getRow(curPhysicalRowNo)[i].getContents()
							.toString();
					temp.add(sheet.getRow(curPhysicalRowNo)[i].getContents()
							.toString());
				} else {
					for (int j = 0; j < rangeRow; j++) {
						context = sheet.getRow(curPhysicalRowNo + j)[i]
								.getContents().toString();
						temp.add(sheet.getRow(curPhysicalRowNo + j)[i]
								.getContents().toString());
					}
				}
			} else {
				context = sheet.getRow(curPhysicalRowNo)[i].getContents()
						.toString();
				temp.add(context);
				// System.out.println(context);
			}
			s.put(columnnName[i], temp);
		}
		Object r[] = new Object[1];
		r[0] = s;
		curPhysicalRowNo = curPhysicalRowNo + rangeRow;
		// System.out.println(rangeRow +"::"+curPhysicalRowNo);
		return r;
	}

	
	/**
	 * Override remove method
	 * (non-Javadoc)
	 * remove
	 * @see java.util.Iterator#remove()
	 */
	public void remove() {
		throw new UnsupportedOperationException("remove unsupported.");
	}
}