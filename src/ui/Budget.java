package ui;
import model.*;
import java.util.Scanner;
public class Budget{
	
	private static final String[] LOCATIONS = {"norte","centro","sur"};
	private static final String[] UTILIZATIONS = {"obra negra","obra blanca","pintura"};
	
	public static void requestData (Scanner sc){
		int quantityMaterials;
		String aux;
		
		System.out.print("Cantidad de materiales a ingresar: ");
		quantityMaterials = sc.nextInt();
		while (quantityMaterials <= 0){
			System.out.println("LA CANTIDAD DEBE SER POSITIVA");
			System.out.print("Cantidad de materiales a ingresar: ");
			quantityMaterials = sc.nextInt();
		}
		
		String[] materials = new String[quantityMaterials];
		int[] quantity = new int[quantityMaterials];
		String[] typeUtilization = new String[quantityMaterials];
		double[] pricesHC = new double[quantityMaterials];
		double[] pricesCentro = new double[quantityMaterials];
		double[] pricesBarrio = new double[quantityMaterials];
		double[] minPrices = new double[quantityMaterials];
		String[] minStores = new String[quantityMaterials];
		int workForce;
		int transportation;
		double totalCost;
		
		sc.nextLine();
		
		System.out.println("Ingrese la ubicacion del inmueble: ");
		aux = sc.nextLine().toLowerCase();
		while (!Operations.searchEntry(aux, LOCATIONS, 3)){
			System.out.println("LA UBICACION DEBE SER NORTE, CENTRO O SUR");
			System.out.println("Ingrese la ubicacion del inmueble: ");
			aux = sc.nextLine().toLowerCase();
		}
		String location = aux;
		
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
			while (!Operations.searchEntry(aux, UTILIZATIONS, 3)){
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
	
	public static void showData(Scanner sc,double totalHC, double totalCentro, double totalBarrio, int quantityMaterials, String[] materials, double[] minPrices, String[] minStores, double totalCost, String[] typeUtilization){
		String type;
		
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
		while (!Operations.searchEntry(type, UTILIZATIONS, 3)){
			System.out.println("NO ES UNA UTILIZACION VALIDA");
			System.out.println("Los usos validos son: obra negra, obra blanca y pintura");
			System.out.println("Tipo de utilizacion de productos que desea ver: ");
			type = sc.nextLine().toLowerCase();
		}
		showMaterials(materials, typeUtilization, quantityMaterials, type);
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
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		requestData(sc);
	}
}