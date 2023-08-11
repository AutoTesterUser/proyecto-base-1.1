package test.java.webshop;

import java.io.IOException;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.opencsv.exceptions.CsvValidationException;

import main.java.utils.OpenCSV;
import main.java.utils.Utils;
import main.java.utils.pageEvents.HomePageEvents;
import test.java.BaseTest;

public class Tests extends BaseTest{
	
	@Test(enabled = true, dataProvider = "webshopdata", priority = 1, groups = "Regresion")
	public void comprarDesdeMenuCategorias(String args[]) {

		Utils.infoTestCase("Comprar 1 producto agregandólo desde el menú de categorías",
				"Validar que el menú de categorías funcione correctamente");
		
		BaseTest.logger.assignCategory("Carrito_Compras");
		
		HomePageEvents.selectCategory(args[0]);
		HomePageEvents.selectProductDetails(args[1]);
		HomePageEvents.enterRecipientInfo(args[2], args[3], args[4]);
	}
	
	@DataProvider(name = "webshopdata")
	public Object[][] datawebshopdata() throws CsvValidationException, InterruptedException, IOException {

		Object[][] data = OpenCSV.getCSVParametersDescription("CSVParametersCarritoCompras.csv", 1, 5);
		return data;
	}
}
