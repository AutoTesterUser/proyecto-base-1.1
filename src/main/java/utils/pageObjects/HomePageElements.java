package main.java.utils.pageObjects;

public interface HomePageElements {
	
	//list
	public static String listCategories = "//div[contains(@class,'category')]//a";

	//label
	public static String labelProductTitle = "//h1[contains(@itemprop,'name')]";
	public static String labelCategoryTitle = "//div[contains(@class,'category-page')]//h1";
	
	//input
	public static String inputRecipientName = "//*[@id=\"giftcard_3_RecipientName\"]";
	public static String inputSenderName = "//*[@id=\"giftcard_3_SenderName\"]";
	public static String inputMessage = "//*[@id=\"giftcard_3_Message\"]";
	public static String inputBusqueda = "//input[contains(@class,'search-box-text')]";
	
	//button
	public static String buttonAddToCart = "//input[contains(@value,'Add to cart')]";
	
	//a
	public static String aProductDetails = "//a[contains(@title,'Show details for [product-name]')]";
	
}
