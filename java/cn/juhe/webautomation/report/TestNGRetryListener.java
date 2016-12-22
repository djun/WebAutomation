package cn.juhe.webautomation.report;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;


/**
 * 测试失败用例重新运行监听器
 * 
 * modify from git
 * @author evan.Sun
 * @date 2015-8-21
 */
public class TestNGRetryListener implements IAnnotationTransformer {

	/**
	 * 重写transform, 设置 TestRetryAnalyzer
	 * (non-Javadoc)
	 * transform
	 * @param annotation
	 * @param testClass
	 * @param testConstructor
	 * @param testMethod
	 * @see org.testng.IAnnotationTransformer#transform(org.testng.annotations.ITestAnnotation, java.lang.Class, java.lang.reflect.Constructor, java.lang.reflect.Method)
	 */
	@SuppressWarnings("rawtypes")
	public void transform(ITestAnnotation annotation, Class testClass,
			Constructor testConstructor, Method testMethod) {
		IRetryAnalyzer retry = annotation.getRetryAnalyzer();
        if (retry == null) {
            annotation.setRetryAnalyzer(TestNGRetryAnalyzer.class);
        }
	}

}
