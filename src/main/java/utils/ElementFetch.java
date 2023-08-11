package main.java.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import test.java.BaseTest;

import java.util.List;

public class ElementFetch {

	public static final String XPATH = "XPATH";
	public static final String ID = "ID";
	public static final String CSS = "CSS";
	public static final String TAGNAME = "TAGNAME";
	public static final String LINKTEXT = "LINK TEXT";

	public WebElement getWebElement(String identifierType, String identifierValue) {
		switch (identifierType) {
		case ID:
			return BaseTest.driver.findElement(By.id(identifierValue));
		case CSS:
			return BaseTest.driver.findElement(By.cssSelector(identifierValue));
		case TAGNAME:
			return BaseTest.driver.findElement(By.tagName(identifierValue));
		case XPATH:
			return BaseTest.driver.findElement(By.xpath(identifierValue));
		case LINKTEXT:
			return BaseTest.driver.findElement(By.linkText(identifierValue));
		default:
			return null;
		}
	}

	public List<WebElement> getListWebElements(String identifierType, String identifierValue) {
		switch (identifierType) {
		case ID:
			return BaseTest.driver.findElements(By.id(identifierValue));
		case CSS:
			return BaseTest.driver.findElements(By.cssSelector(identifierValue));
		case TAGNAME:
			return BaseTest.driver.findElements(By.tagName(identifierValue));
		case XPATH:
			return BaseTest.driver.findElements(By.xpath(identifierValue));
		case LINKTEXT:
			return BaseTest.driver.findElements(By.linkText(identifierValue));
		default:
			return null;
		}
	}
}
