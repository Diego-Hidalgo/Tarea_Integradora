package ui;
import model.*;
import java.util.Scanner;
public class Budget{
	
	private static final String[] LOCATIONS = {"norte","centro","sur"};
	private static final String[] UTILIZATIONS = {"obra negra","obra blanca","pintura"};
	
	/**
	*	Request the inputs needed to solve the problem. <br>
	*	<b>pre: sc is initialized</b>
	*	<b>post: </b>
	*	@param sc Scanner.
	*/
	public static void requestData (Scanner sc){
		int quantityMaterials; //quantity of materials to enter
		String aux; // it is used an auxiliar
		
		System.out.print("Cantidad de materiales a ingresar: ");
		quantityMaterials = sc.nextInt();
		while (quantityMaterials <= 0){
			System.out.println("LA CANTIDAD DEBE SER POSITIVA");
			System.out.print("Cantidad de materiales a ingresar: ");
			quantityMaterials = sc.nextInt();
		}
		
		String[] materials = new String[quantityMaterials];	//stores input materials
		int[] quantity = new int[quantityMaterials];	//stores the quantity of each material
		String[] typeUtilization = new String[quantityMaterials];	//stores the utilization of each material
		double[] pricesHC = new double[quantityMaterials];	//Stores the price for HomeCenter
		double[] pricesCentro = new double[quantityMaterials]; //Stores the price for Ferreteria del Centro
		double[] pricesBarrio = new double[quantityMaterials];	//Stores the price for Ferreteria del Barrio
		double[] minPrices = new double[quantityMaterials];		//Stores the lowest prices
		String[] minStores = new String[quantityMaterials];		//Stores the Hardaware stores with the lowest prices
		int workForce;	//Stores the total workforce
		int transportation; //Stores the price of the transportation
		double totalCost; //Stores the total Cost of the work
		
		sc.nextLine();
		
		System.out.println("Ingrese la ubicacion del inmueble: ");
		aux = sc.nextLine().toLowerCase();
		while (!Operations.searchInput(aux, LOCATIONS, 3)){
			System.out.println("LA UBICACION DEBE SER NORTE, CENTRO O SUR");
			System.out.println("Ingrese la ubicacion del inmueble: ");
			aux = sc.nextLine().toLowerCase();
		}
		String location = aux;	//Stores the location of the property
		
		for (int i = 0; i<quantityMaterials; i++){
			System.out.println("\n----------");
			System.out.print("Nombre del material #"+(i+1)+" :");
			materials[i] = sc.nextLine().toLowerCase();
			
			System.out.print("cantidad de unidades necesaria del material #"+(i+1)+" :");
			quantity[i] = sc.nextInt();
			while (quantity[i]<=0){
				System.out.println("LA CANTIDAD DEBE SER POSITIVA");
				System.out.print("Cantidad de unidades necesaria del material: ");
				quantity[i] = sc.nextInt();
			}
			
			sc.nextLine();
			
			System.out.print("Utilizacion del material #"+(i+1)+" :");
			aux = sc.nextLine().toLowerCase();
			while (!Operations.searchInput(aux, UTILIZATIONS, 3)){
				System.out.println("NO ES UNA UTILIZACION VALIDA");
				System.out.println("Los usos validos son: obra negra, obra blanca y pintura: ");
				aux = sc.nextLine().toLowerCase();
			}
			typeUtilization[i] = aux;
			
			System.out.print("Precio del material #"+(i+1)+" en HomeCenter: ");
			pricesHC[i] = sc.nextDouble();
			while (pricesHC[i]<=0){
				System.out.println("EL PRECIO DEBE SER POSITIVO");
				System.out.print("Precio del material #"+(i+1)+" en HomeCenter: ");
				pricesHC[i] = sc.nextDouble();
			}
			
			System.out.print("Precio del material #"+(i+1)+" en Ferreteria del Centro: ");
			pricesCentro[i] = sc.nextDouble();
			while (pricesCentro[i]<=0){
				System.out.println("EL PRECIO DEBE SER POSITIVO");
				System.out.print("Precio del material #"+(i+1)+" en Ferreteria del Centro: ");
				pricesCentro[i] = sc.nextDouble();
			}
			
			System.out.print("Precio del material #"+(i+1)+" en Ferreteria del Barrio: ");
			pricesBarrio[i] = sc.nextDouble();
			while (pricesBarrio[i]<=0){
				System.out.println("EL PRECIO DEBE SER POSITIVO");
				System.out.print("Precio del material #"+(i+1)+" en Ferreteria del Barrio: ");
				pricesBarrio[i] = sc.nextDouble();
			}
			sc.nextLine();
		}
		workForce = Operations.calculateWorkForce(typeUtilization, quantityMaterials);
		
		double totalHC = Operations.calculateTotal(pricesHC, quantity, quantityMaterials);
		totalHC += workForce;
		double totalCentro = Operations.calculateTotal(pricesCentro, quantity, quantityMaterials);
		totalCentro += workForce;
		double totalBarrio = Operations.calculateTotal(pricesBarrio, quantity, quantityMaterials);
		totalBarrio += workForce;
		
		minPrices = Operations.bestPrices(pricesHC, pricesCentro, pricesBarrio, quantityMaterials);
		minStores = Operations.bestStores(pricesHC, pricesCentro, pricesBarrio, quantityMaterials);
		
		totalCost = Operations.calculateTotal(minPrices, quantity, quantityMaterials);
		transportation = Operations.calculateTransportation(totalCost, location);
		totalCost += workForce + transportation;
		
		showData(sc, totalHC, totalCentro, totalBarrio, quantityMaterials, materials, minPrices, minStores, totalCost, typeUtilization);
	}  
	
	/**
	*	Shows the data. <br>
	*	<b>pre: parameters are initialized</b>
	*	<b>post: </b>
	*	@param sc Scanner.
	*	@param totalHC Total purchase value HomeCenter
	*	@param totalCentro Total purchase value Ferreteria del Centro.
	*	@param totalBarrio Total purchase value Ferreteria del Barrio.
	*	@param quantityMaterials quantity of input materials. quantityMaterials>0 and quantityMaterials!=null.
	*	@param materials Name of the products to buy. materials!="" and materials!=null.
	*	@param minPrices lowest prices for each material.
	*	@param minStores Stores with the lowest prices.
	*	@param totalCost Total purchase cost
	*  	@param typeUtilization Utilization of the material.
	*/
	public static void showData(Scanner sc,double totalHC,double totalCentro,double totalBarrio,int quantityMaterials,String[] materials,double[] minPrices,String[] minStores,double totalCost,String[] typeUtilization){
		String type;	//Stores the type of material the user requests
		
		System.out.println("El total de compra en HomeCenter es: "+totalHC);
		System.out.println("El total de compra en Ferreteria del Centro es: "+totalCentro);
		System.out.println("El total de compra en Ferreteria del Barrio es: "+totalBarrio);
		System.out.println("");
		
		System.out.println("MEJORES PRECIOS Y TIENDAS");
		for (int i = 0; i<quantityMaterials; i++){
			System.out.println("Material: "+materials[i]);
			System.out.println("Mejor precio: "+minPrices[i]);
			System.out.println("Se encuentra en la tienda: "+minStores[i]);
			System.out.println("\n----------");
		}
		
		System.out.println("El precio total de compra en las mejores tiendas (incluido transporte y mano de obra) es: "+totalCost);
		System.out.println("\n----------");
		
		System.out.println("Tipo de utilizacion de productos que desea ver: ");
		type = sc.nextLine().toLowerCase();
		while (!Operations.searchInput(type, UTILIZATIONS, 3)){
			System.out.println("NO ES UNA UTILIZACION VALIDA");
			System.out.println("Los usos validos son: obra negra, obra blanca y pintura");
			System.out.println("Tipo de utilizacion de productos que desea ver: ");
			type = sc.nextLine().toLowerCase();
		}
		showMaterials(materials, typeUtilization, quantityMaterials, type);
	}
	
	/**
	*	Shows the type of material requested. <br>
	*	<b> pre: The parameters are initialized</b>
	*	<b>	post:</b>
	*	@param materials Name of the products.
	*	@param typeUtilization Utilization of the material.
	*	@param quantityMaterials quantity of input materials. quantityMaterials>0 and quantityMaterials!=null.
	*	@param type Type of material requested. type!="" and type!=null
	*/
	public static void showMaterials (String[] materials, String[] typeUtilization, int quantityMaterials, String type){
		int c = 0; //counter
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
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		requestData(sc);
	}
}