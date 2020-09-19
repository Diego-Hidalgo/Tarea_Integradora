package model;
public class Operations{
	private static final int ROUGH_CONSTRUCTION = 1300000;
	private static final int FINISHED_CONSTRUCTION = 2600000;
	private static final int PAINTING = 980000;
	private static final String[] LOCATIONS = {"norte","centro","sur"};
	private static final String[] UTILIZATIONS = {"obra negra","obra blanca","pintura"};
	
	public static boolean searchEntry (String search, String[] array, int lenght){
		boolean result = false;
		for (int i = 0; i<lenght; i++){
			if (search.equals(array[i])){
				result = true;
			}
		}
		return result;
	}
	
	public static double calculateTotal (double[] array, String[] typeUtilization, int[] quantity, int quantityMaterials){
		double result = 0; 
		boolean stop = false;
		for (int i = 0; i<quantityMaterials; i++){
			result += array[i]*quantity[i];
		}
		for (int i = 0; i<quantityMaterials && !stop; i++){
			if (typeUtilization[i] == UTILIZATIONS[0]){
				result += ROUGH_CONSTRUCTION;
				stop = true;
			}	
		}
		stop = false;
		for (int i = 0; i<quantityMaterials && !stop; i++){
			if (typeUtilization[i] == UTILIZATIONS[1]){
				result += FINISHED_CONSTRUCTION;
				stop = true;
			}	
		}
		stop = false;
		for (int i = 0; i<quantityMaterials && !stop; i++){
			if (typeUtilization[i] == UTILIZATIONS[2]){
				result+= PAINTING;
				stop = true;
			}	
		}
		return result;
	}
	
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
	
	public static void showMaterials (String[] materials, String[] typeUtilization, int quantityMaterials, String type){
		int c = 0;
		for (int i = 0; i<quantityMaterials; i++){
			if (typeUtilization[i].equals(type)){
				System.out.println(materials[i]);
				c++;
			}
		}
		if (c==0){
			System.out.println("No existen entradas para la utilizacion solicitada");
		}
	}
}