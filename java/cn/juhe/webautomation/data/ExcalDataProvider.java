package cn.juhe.webautomation.data;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.testng.annotations.DataProvider;

/**
 * 根据运行时的class and method 从excel里面获取相对应的数据
 * 
 * @author evan.Sun
 * @date 2015-8-19
 */
public class ExcalDataProvider {

	/**
	 * 从Excel中获取类名， book name 为class name， sheet name 为 method name
	 * 
	 * getDataFromClass
	 * 
	 * @param method
	 * @return 获取类名
	 * @author evan.Sun
	 * @date 2015-8-21
	 */
	@DataProvider(name = "dpClass")
	public static Iterator<Object[]> getDataFromClass(Method method) {
		return new ExcelDataHandle(method.getDeclaringClass().getName(),
				method.getName());
	}

	/**
	 * 从Excel中获取方法名， book name 为TestData， sheet name 为 method name
	 * 
	 * getDataFromMethod
	 * 
	 * @param method
	 * @return 从Excel中获取方法名
	 * @author evan.Sun
	 * @date 2015-8-21
	 */
	@DataProvider(name = "dpMethod")
	public static Iterator<Object[]> getDataFromMethod(Method method) {
		return new ExcelDataHandle(method.getName());
	}

	/**
	 * 从Excel中获取数据， book name 为TestData,sheet name 为 TestData
	 * 
	 * getDataFromExcel
	 * 
	 * @return 数据集合
	 * @author evan.Sun
	 * @date 2015-8-21
	 */
	@DataProvider(name = "dpExcel")
	public static Iterator<Object[]> getDataFromExcel() {
		return new ExcelDataHandle();
	}

	/**
	 * 数据驱动，用于运行时从外部获取数据到指定的method中 dataFortestMethod
	 * 
	 * @param method
	 * @return
	 * @author evan.Sun
	 * @date 2015-8-21
	 */
	@DataProvider(name = "dp")
	public Iterator<Object[]> dataFortestMethod(Method method) {
		return new ExcelDataHandle(this.getClass().getName(), method.getName());
	}

	@DataProvider
	public Object[][] getData() {
		return new Object[][] { { "DataWithNoName1" }, { "DataWithNoName2" },
				{ "DataWithNoName3" } };
	}
}
