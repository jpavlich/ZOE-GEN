package co.edu.javeriana.interfaces;		

import co.edu.javeriana.entities.Dieta;

import java.util.*;
import javax.ejb.Local;

/**
 * This interface represents the local instance for the DietaPersonalBean EJB 
 */
@Local
public interface DietaPersonal {
	
	/**
	 * This method executes the proper actions for test
	 * @param a Parameter from type Integer
	 * @param dieta Parameter from type Dieta
	 */
	public void test(Long a,Dieta dieta);
}	

	
