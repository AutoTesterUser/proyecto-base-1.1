package main.java.utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import main.java.utils.pageObjects.LoginElements;
import test.java.BaseTest;

public class Utils {
	
	@BeforeMethod(description = "Obtiene la fecha formateada del momento en formato día-mes-año hora:minutos:segundos")
	public static String getCurrentDate() {
		
		try {
			LocalDateTime currentDateTime = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
	        String formattedDateTime = currentDateTime.format(formatter).replace(":", ".").replace(" ", "_");
	        return formattedDateTime;
		} catch (Exception e) {
			return null;
		}        
	}

	@BeforeMethod(description = "Registra el escenario de prueba y el objetivo de la prueba en el reporte de pruebas")
	public static void infoTestCase(String funcionality, String description) {
		String logText = "Funcionalidad: " + funcionality;
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.TEAL);
		BaseTest.logger.log(Status.INFO, m);
		logText = "Descripción: " + description;
		m = MarkupHelper.createLabel(logText, ExtentColor.TEAL);
		BaseTest.logger.log(Status.INFO, m);
	}

	@BeforeMethod(description = "Se ejecuta si algún método cae en el catch")
	public static void eventFailed(String currentEvent, String errorMessage) {
		BaseTest.logger.warning("No se pudo terminar el evento '" + currentEvent + "' a causa de: " + errorMessage);
		System.out.println("No se pudo terminar el evento '" + currentEvent + "' a causa de: " + errorMessage);

		String logText = "FAILED: " + currentEvent;
		Markup m = MarkupHelper.createLabel(logText, ExtentColor.RED);
		BaseTest.logger.log(Status.FAIL, m);
		String path = takeScreenshot(currentEvent);
		try {
			BaseTest.logger.addScreenCaptureFromPath(path, currentEvent);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Assert.fail();
	}
	
	@BeforeMethod(description = "Captura la pantalla en caso de fallo y guarda la imagen")
	public static String takeScreenshot(String methodName) {
		
		String fileName = System.getProperty("user.dir") + File.separator + "screenshots" + File.separator + "failed"
				+ File.separator + methodName + BaseTest.extentDate + ".png";
		File f = ((TakesScreenshot) BaseTest.driver).getScreenshotAs(OutputType.FILE);
		try {
			File newFile = new File(fileName);
			FileUtils.copyFile(f, newFile);
			return newFile.getAbsolutePath();
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	}

	@BeforeMethod(description = "Envía información por la consola y en el reporte de pruebas")
	public static void outputInfo(String output) {
		BaseTest.logger.info(output);
		System.out.println(output);
	}

	public static void testCasePassed(String currentTestCase, String message) {
		outputInfo("PASSED: " + currentTestCase + "\n" + message + "\n");
	}

	public static void testCaseFailed(String currentTestCase, String caseTestDesc, Exception e) {
		outputInfo("No se ha terminado el caso de prueba '" + currentTestCase + "' por la siguiente excepción: " + "\n"
				+ e.getMessage());
		outputInfo("FAILED: " + currentTestCase + " - " + caseTestDesc + "\n");
		takeScreenshot(currentTestCase);
		Assert.fail();
	}

	@BeforeMethod(description = "Obtiene el nombre del elemento")
	public static String elementName(String fieldName) {
		try {
			Class<LoginElements> clazz = LoginElements.class;
	        Field field = clazz.getField(fieldName);

	        if (field.isAnnotationPresent(Element.class)) {
	            Element elementAnnotation = field.getAnnotation(Element.class);
	            String attributeName = elementAnnotation.name();
	            return attributeName;
	        } else {
	            System.out.println("La anotación @Element no está presente en el campo.");
	        }
		} catch (Exception e) {
			return null;
		}
		return fieldName;
	}
	
	@BeforeMethod(description = "Obtiene el nombre de la variable")
	public static String variableName(String variable) {
		try {
			StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
			String className = stackTrace[3].getClassName();
			Class<?> classClass = Class.forName(className);
			
			String interfaceName = classClass.getInterfaces()[0].getName();
			Class<?> interfaceClass = Class.forName(interfaceName);

		    Field[] fields = interfaceClass.getDeclaredFields();

		    String targetValue = variable;
		    
		    for (Field field : fields) {
		        if (!java.lang.reflect.Modifier.isStatic(field.getModifiers())) {
		            continue;
		        }
		        
		        field.setAccessible(true);
		        Object value = field.get(null);
		        
		        if (targetValue.equals(value)) {
		            String variableName = field.getName();
		            return variableName;
		        }
		    }
		} catch (Exception e) {
			return null;
		}
		return "";
	}
}
