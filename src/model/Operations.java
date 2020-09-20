package model;
public class Operations{
	private static final int ROUGH_CONSTRUCTION = 1300000; //workforce price for rough construction
	private static final int FINISHED_CONSTRUCTION = 2600000; //workforce price for finished construction
	private static final int PAINTING = 980000; //workforce price for painting
	private static final String[] UTILIZATIONS = {"obra negra","obra blanca","pintura"};
	
	/**
	*	searches for an input on a given array. <br>
	*	<b>pre: The parameters are initialized.</b> 
	*	<b>post: True if input is on array. False if input is not on array.</b>
	*	@param search Entry to search. search!="" and search!=null.
	*	@param array Place to look. array!=null.
	*	@param length length of the array. length>0 and length!=null.
	*/
	public static boolean searchInput (String search, String[] array, int length){
		boolean result = false;
		for (int i = 0; i<length; i++){
			if (search.equals(array[i])){
				result = true;
			}
		}
		return result;
	}
	
	/**
	*	Calculates the total purchase valur <br>
	*	<b>pre: Parameters are initialized</b>
	*	<b>post: returns a positive Real number that represents the purchase value</b>
	*	@param array Prices of the materials. array>0 and array!=null.
	*	@param quantity Quantity of material requested. quantity>0 and quantity!=null.
	*	@param quantityMaterials Quantity of input materials. quantityMaterials>0 and quantityMaterials!=null.
	*/
	public static double calculateTotal (double[] array, int[] quantity, int quantityMaterials){
		double result = 0; 
		for (int i = 0; i<quantityMaterials; i++){
			result += (array[i]*quantity[i]);
		}
		return result;
	}
	
	/**
	*	Calculates the total Workforce. <br>
	*	<b>pre: Returns a positive integer that represents the total workforce</b>
	*	<b>post: </b>
	*	@param typeUtilization Utilization of the material. typeUtilization!="" and typeUtilization!=null
	*	@param quantityMaterials Quantity of input materials. quantityMaterials>0 and quantityMaterials!=null
	*/
	public static int calculateWorkForce(String[] typeUtilization, int quantityMaterials){
		int result = 0; 
		boolean stop = false;
		for (int i = 0; i<quantityMaterials && !stop; i++){
			if (typeUtilization[i].equals(UTILIZATIONS[0])){
				result += ROUGH_CONSTRUCTION;
				stop = true;
			}	
		}
		stop = false;
		for (int i = 0; i<quantityMaterials && !stop; i++){
			if (typeUtilization[i].equals(UTILIZATIONS[1])){
				result += FINISHED_CONSTRUCTION;
				stop = true;
			}	
		}
		stop = false;
		for (int i = 0; i<quantityMaterials && !stop; i++){
			if (typeUtilization[i].equals(UTILIZATIONS[2])){
				result+= PAINTING;
				stop = true;
			}	
		}
		return result;
	}
	
	/**
	*	Finds the lowest prices in each postition by comparing 3 arrays. <br>
	*	<b>pre: parameters are initialized</b>
	*	<b>post: Returns an array with the lowest Prices in each store</b>
	*	@param pricesHC Prices in HomeCenter. pricesHC>0 and pricesHC!=null.
	*	@param pricesCentro Prices in ferreteria del Centro. pricesCentro>0 and pricesCentro!=null.
	*	@param pricesBarrio Prices in ferreteria del barrio. pricesBarrio>0 and pricesBarrio!=null.
	*	@param quantityMaterials. Quantity of input materials. quantityMaterials>0 and quantityMaterials!=null.
	*/
	public static double[] bestPrices (double[] pricesHC, double[] pricesCentro, double[] pricesBarrio, int quantityMaterials){
		double[] minPrices = new double[quantityMaterials]; 
		for (int i = 0; i<quantityMaterials; i++){
			if (pricesHC[i]<pricesCentro[i] && pricesHC[i]<pricesBarrio[i])
				minPrices[i] = pricesHC[i];
			else if (pricesCentro[i]<pricesHC[i] && pricesCentro[i]<pricesBarrio[i])
				minPrices[i] = pricesCentro[i];
			else
				minPrices[i] = pricesBarrio[i];
		}
		return minPrices;
	}
	
	/**
	*	Finds the stores that have the lowest prices. <br>
	*	<b>pre:The parameters are initialized.</b>
	*	<b>post: Returns the stores that have the lowest prices</b>
	*	@param pricesHC Prices in HomeCenter. pricesHC>0 and pricesHC!=null.
	*	@param pricesCentro Prices in ferreteria del Centro. pricesCentro>0 and pricesCentro!=null.
	*	@param pricesBarrio Prices in ferreteria del barrio. pricesBarrio>0 and pricesBarrio!=null
	*	@param quantityMaterials quantity of input materials. quantityMaterials>0 and quantityMaterials!=null
	*/
	public static String[] bestStores (double[] pricesHC, double[] pricesCentro, double[] pricesBarrio, int quantityMaterials){
		String[] minStores = new String[quantityMaterials]; 
		for (int i = 0; i<quantityMaterials; i++){
			if (pricesHC[i]<pricesCentro[i] && pricesHC[i]<pricesBarrio[i])
				minStores[i] = "HomeCenter";
			else if (pricesCentro[i]<pricesHC[i] && pricesCentro[i]<pricesBarrio[i])
				minStores[i] = "Ferreteria del Centro";
			else
				minStores[i] = "Ferreteria del Barrio";
		}
		return minStores;
	}
	
	/**
	*	Calculates the amount to pay for tansportation. <br>
	*	<b>pre: Parameters are initialized</b>
	*	<b>post: Returns a positive integer that represents the amount to pay for transportation</b>
	*	@param totalCost The price to pay for the work. totalCost!=null.
	*	@param location	The location of the property.
	*/
	public static int calculateTransportation (double totalCost, String location){
		int transportation = 0;
		switch (location){
			case "norte":
				if (totalCost<80000)
					transportation = 120000;
				else if (totalCost<300000)
					transportation = 28000;
				break;
			case "centro":
				if (totalCost<80000)
					transportation += 50000;
				break;
			case "sur":
				if (totalCost<80000)
					transportation += 120000;
				else if (totalCost<300000)
					transportation += 55000;
		}
		return transportation;
	}
}