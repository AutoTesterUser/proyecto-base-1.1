package main.java.utils;

import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeMethod;

import test.java.BaseTest;

public class Events extends BaseTest {

	@BeforeMethod(description = "Busca y encuentra un elemento visible según su XPATH")
	public static WebElement findElement(String xpathElement) {

		String currentEvent = new Throwable().getStackTrace()[0].getMethodName();
		try {
			WebDriverWait wait = new WebDriverWait(driver, 50);
			ElementFetch elementFetch = new ElementFetch();

			WebElement element = elementFetch.getWebElement("XPATH", xpathElement);
			wait.until(ExpectedConditions.visibilityOf(element));
			return element;
		} catch (Exception e) {
			Utils.eventFailed(currentEvent, e.getStackTrace().toString());
			return null;
		}
	}

	@BeforeMethod(description = "Busca y encuentra una lista de elementos visibles según su XPATH")
	public static List<WebElement> findElements(String xpathElement) {

		String currentEvent = new Throwable().getStackTrace()[0].getMethodName();
		try {
			WebDriverWait wait = new WebDriverWait(driver, 50);
			ElementFetch elementFetch = new ElementFetch();

			List<WebElement> elements = elementFetch.getListWebElements("XPATH", xpathElement);
			wait.until(ExpectedConditions.visibilityOfAllElements(elements));
			return elements;

		} catch (Exception e) {
			Utils.eventFailed(currentEvent, e.getStackTrace().toString());
			return null;
		}
	}

	@BeforeMethod(description = "Escribe dentro de un campo de texto")
	public static void sendKeys(String xpathElement, String text) {

		String currentEvent = new Throwable().getStackTrace()[0].getMethodName();
		try {
			WebDriverWait wait = new WebDriverWait(driver, 50);
			ElementFetch elementFetch = new ElementFetch();

			WebElement element = elementFetch.getWebElement("XPATH", xpathElement);
			wait.until(ExpectedConditions.visibilityOf(element));

			if (element.isDisplayed() && element.isEnabled()) {
				String nameInput = Utils.variableName(xpathElement);
				int caracteres = element.getAttribute("value").toCharArray().length;
				for (int i = 0; i < caracteres; i++) {
					element.sendKeys(Keys.BACK_SPACE);
				}
				element.sendKeys(text);
				Utils.outputInfo("Se ha ingresado el texto '" + text + "' en el campo: " + nameInput);
				Validation.trueBooleanCondition(element.getAttribute("value").contains(text),
						"El texto se ha ingresado correctamente", "El texto no se ha ingresado correctamente",
						currentEvent);
			} else {
				String nameInput = Utils.elementName("cambiar");
				Utils.eventFailed(currentEvent, "El campo '" + nameInput + "' no se encuentra habilitado o desplegado");
			}
		} catch (Exception e) {
			Utils.eventFailed(currentEvent, e.getMessage());
		}
	}

	@BeforeMethod(description = "Selecciona un botón")
	public static void clickButton(String xpathElement) {
		String currentEvent = new Throwable().getStackTrace()[0].getMethodName();
		try {
			WebDriverWait wait = new WebDriverWait(driver, 50);
			ElementFetch elementFetch = new ElementFetch();

			WebElement element = elementFetch.getWebElement("XPATH", xpathElement);
			wait.until(ExpectedConditions.visibilityOf(element));

			if (element.isEnabled()) {
				String name = Utils.variableName(xpathElement);
				element.click();
				Utils.outputInfo("Se ha hecho clic en el botón: " + name);
			} else {
				String name = element.getAttribute("text");
				Utils.eventFailed(currentEvent, "El botón '" + name + "' no está desplegado o habilitado");
			}
		} catch (Exception e) {
			Utils.eventFailed(currentEvent, e.getMessage());
		}
	}

	@BeforeMethod(description = "Obtiene el texto de un elemento por su XPATH")
	public static String getText(String xpathElement) {
		String currentEvent = new Throwable().getStackTrace()[0].getMethodName();
		try {
			WebDriverWait wait = new WebDriverWait(driver, 50);
			ElementFetch elementFetch = new ElementFetch();

			WebElement element = elementFetch.getWebElement("XPATH", xpathElement);
			wait.until(ExpectedConditions.visibilityOf(element));
			String text = element.getText();
			return text;
		} catch (Exception e) {
			Utils.eventFailed(currentEvent, e.getMessage());
			return null;
		}
	}

	@BeforeMethod(description = "Obtiene el texto de un elemento dentro de una lista de elementos")
	public static String getText(List<WebElement> elementList, int i) {
		String currentEvent = new Throwable().getStackTrace()[0].getMethodName();
		try {
			String text = elementList.get(i).getText();
			return text;
		} catch (Exception e) {
			Utils.eventFailed(currentEvent, e.getMessage());
			return null;
		}
	}

	@BeforeMethod(description = "Selecciona una opción por el texto visible de una lista desplegable")
	public static String selectByText(String xpathElement, String option) {
		String currentEvent = new Throwable().getStackTrace()[0].getMethodName();
		try {
			WebDriverWait wait = new WebDriverWait(driver, 50);
			ElementFetch elementFetch = new ElementFetch();

			WebElement element = elementFetch.getWebElement("XPATH", xpathElement);
			wait.until(ExpectedConditions.visibilityOf(element));

			String name = Utils.variableName(xpathElement);
			Select select = new Select(element);
			select.selectByVisibleText(option);
			if (select.getFirstSelectedOption().isSelected()) {
				String selectedOption = select.getFirstSelectedOption().getText();
				Utils.outputInfo(
						"Se ha seleccionado la opción '" + selectedOption + "' en la lista desplegable '" + name + "'");
				return selectedOption;
			} else {
				Utils.eventFailed(currentEvent, "La opción requerida no pudo ser seleccionada");
				return null;
			}
		} catch (Exception e) {
			Utils.eventFailed(currentEvent, e.getMessage());
			return null;
		}
	}

	@BeforeMethod(description = "Selecciona un elemento que se encuentra detrás de otro")
	public static void clickJavaScript(String xpathElement, WebDriver driver) {
		String currentEvent = new Throwable().getStackTrace()[0].getMethodName();
		try {
			ElementFetch elementFetch = new ElementFetch();

			WebElement element = elementFetch.getWebElement("XPATH", xpathElement);
			JavascriptExecutor jse = (JavascriptExecutor) driver;
			String name = Utils.variableName(xpathElement);
			jse.executeScript("arguments[0].click()", element);
			Utils.outputInfo("Se hizo clic en el botón: " + name);
		} catch (Exception e) {
			Utils.eventFailed(currentEvent, e.getMessage());
		}
	}

}
