package main.java.utils.pageEvents;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeMethod;

import main.java.utils.Events;
import main.java.utils.Utils;
import main.java.utils.Validation;
import main.java.utils.pageObjects.HomePageElements;
import main.java.utils.pageProperties.ProductProperties;

public class HomePageEvents implements HomePageElements {

	@BeforeMethod(description = "Selecciona una categoría desde el menú")
	public static void selectCategory(String categoryName) {

		String currentEvent = new Throwable().getStackTrace()[0].getMethodName();
		Utils.outputInfo("Ha comenzado el evento: " + currentEvent);

		try {
			List<WebElement> categories = Events.findElements(listCategories);
			for (int i = 0; i < categories.size(); i++) {
				String name = Events.getText(categories, i);
				if (name.equalsIgnoreCase(categoryName)) {
					Utils.outputInfo("Se ha encontrado la categoría dentro del menú correctamente: " + name);
					ProductProperties.category = categoryName;
					categories.get(i).click();
					
					String title = Events.getText(labelCategoryTitle);
					Validation.trueBooleanCondition(title.contains(categoryName),
							"Se ha seleccionado la categoría de productos correctamente",
							"No se ha seleccionado la categoría correcta, se esperaba: " + categoryName
									+ ", y se encontró: " + title,
							currentEvent);
					break;
				}
				else if (i == categories.size()){
					Utils.eventFailed(currentEvent, "No se ha encontrado la categoría: " + name);
				}
			}
		} catch (Exception e) {
			Utils.eventFailed(currentEvent, e.getMessage());
		}
	}

	@BeforeMethod(description = "Selecciona una producto desde el catálogo para ver el detalle")
	public static void selectProductDetails(String productName) {

		String currentEvent = new Throwable().getStackTrace()[0].getMethodName();
		Utils.outputInfo("Ha comenzado el evento: " + currentEvent);

		try {
			Events.findElement(aProductDetails.replace("[product-name]", productName));
			Utils.outputInfo("Se ha encontrado el producto dentro del catálogo: " + productName);
			WebElement detailsElement = Events.findElement(aProductDetails.replace("[product-name]", productName)); 
			detailsElement.click();
			String title = Events.getText(labelProductTitle);
			Validation.trueBooleanCondition(title.contains(productName),
					"Se ha seleccionado el producto correctamente",
					"No se ha seleccionado el producto correcto, se esperaba: " + productName
							+ ", y se encontró: " + title,
					currentEvent);
		} catch (Exception e) {
			Utils.eventFailed(currentEvent, e.getMessage());
		}
	}
	
	@BeforeMethod(description = "Ingresa la información del remitente de la Gift Card")
	public static void enterRecipientInfo(String recipientName, String senderName, String message) {

		String currentEvent = new Throwable().getStackTrace()[0].getMethodName();
		Utils.outputInfo("Ha comenzado el evento: " + currentEvent);

		try {
			Events.sendKeys(inputRecipientName, recipientName);
			Events.sendKeys(inputSenderName, senderName);
			Events.sendKeys(inputMessage, message);
		} catch (Exception e) {
			Utils.eventFailed(currentEvent, e.getMessage());
		}
	}


}
