package co.edu.javeriana.impl;				

import co.edu.javeriana.entities.Dieta;
	
import java.util.*;
import java.io.Serializable;
import javax.ejb.Stateless;
import co.edu.javeriana.interfaces.DietaPersonal;



/**
 * This class represents an EJB named DietaPersonalImpl
 */
@Stateless
public class DietaPersonalImpl  implements DietaPersonal,Serializable{

	
	
	
	/**
	 * Service default constructor
	 */
	public DietaPersonalImpl(){
	}
	
	/**
	 * This method executes the proper actions for test
	 * @param a Parameter from type Integer
	 * @param dieta Parameter from type Dieta
	 */
	@Override
	public void test(Long a,Dieta dieta){
	}
}	

	
