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
		double totalCost;
		String type;
		
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
		
		System.out.println("El total de compra en HomeCenter es: ");
		System.out.println(Operations.calculateTotal(pricesHC, typeUtilization, quantity, quantityMaterials));
		
		System.out.println("El total de compra en Ferreteria del Centro es: ");
		System.out.println(Operations.calculateTotal(pricesCentro, typeUtilization, quantity, quantityMaterials));
		
		System.out.println("El total de compra en Ferreteria del Barrio es: ");
		System.out.println(Operations.calculateTotal(pricesBarrio, typeUtilization, quantity, quantityMaterials));
		System.out.println("");
		
		minPrices = Operations.bestPrices(pricesHC, pricesCentro, pricesBarrio, quantityMaterials);
		minStores = Operations.bestStores(pricesHC, pricesCentro, pricesBarrio, quantityMaterials);
		
		System.out.println("MEJORES PRECIOS Y TIENDAS");
		for (int i = 0; i<quantityMaterials; i++){
			System.out.println("Material: "+materials[i]);
			System.out.println("Mejor precio: "+minPrices[i]);
			System.out.println("Se encuentra en la tienda: "+minStores[i]);
			System.out.println("\n----------");
		}
		
		totalCost = Operations.calculateTotal(minPrices, typeUtilization,quantity, quantityMaterials);
		totalCost += Operations.calculateTransportation(totalCost, location);
		
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
		Operations.showMaterials(materials, typeUtilization, quantityMaterials, type);
	}
	
	public static void main(String[] args){
		Scanner sc = new Scanner(System.in);
		requestData(sc);
	}
}