package main.java.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import test.java.BaseTest;

import java.util.List;

public class ElementFetch {

	public static final String xpath = "XPATH";
	public static final String id = "ID";
	public static final String css = "CSS";
	public static final String tagname = "TAGNAME";
	public static final String linktext = "LINK TEXT";

	public WebElement getWebElement(String identifierType, String identifierValue) {
		switch (identifierType) {
		case id:
			return BaseTest.driver.findElement(By.id(identifierValue));
		case css:
			return BaseTest.driver.findElement(By.cssSelector(identifierValue));
		case tagname:
			return BaseTest.driver.findElement(By.tagName(identifierValue));
		case xpath:
			return BaseTest.driver.findElement(By.xpath(identifierValue));
		case linktext:
			return BaseTest.driver.findElement(By.linkText(identifierValue));
		default:
			return null;
		}
	}

	public List<WebElement> getListWebElements(String identifierType, String identifierValue) {
		switch (identifierType) {
		case id:
			return BaseTest.driver.findElements(By.id(identifierValue));
		case css:
			return BaseTest.driver.findElements(By.cssSelector(identifierValue));
		case tagname:
			return BaseTest.driver.findElements(By.tagName(identifierValue));
		case xpath:
			return BaseTest.driver.findElements(By.xpath(identifierValue));
		case linktext:
			return BaseTest.driver.findElements(By.linkText(identifierValue));
		default:
			return null;
		}
	}
}
