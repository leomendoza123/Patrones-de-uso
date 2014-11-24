/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Iterador;

/**
 *
 * @author Leonardo
 */
public class NewMain {

    /**
     * @param args the command line arguments
     */

   public static void main(String argv[]) {
    	Vector<Integer> vector = new Vector(5);
 
    	//Creación del iterador
    	IteradorVector<Integer> iterador = vector.iterador();
        
        vector._datos.add(2); 
        vector._datos.add(6); 
        vector._datos.add(23); 
 
    	//Recorrido con el iterador
    	while (iterador.hasNext())
            System.out.println(iterador.next());    	    	
    }

    
}
