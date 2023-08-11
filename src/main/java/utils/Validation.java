package main.java.utils;

import org.testng.annotations.BeforeMethod;

public class Validation {

	//Este método reemplaza el "Assert" y/o el "If Else" con la intención de informar el resultado de la validación en el reporte de pruebas (Extent Report).
	
	@BeforeMethod(description = "Valida cualquier condicion booleana enviando un mensaje en caso de éxito y un mensaje en caso de error")
	public static void trueBooleanCondition(boolean condition, String successMessage, String errorMessage) {
		if (condition) {
			Utils.outputInfo(successMessage);
		}
		else {
			Utils.eventFailed(errorMessage + ": " + condition);
		}
	}
}
