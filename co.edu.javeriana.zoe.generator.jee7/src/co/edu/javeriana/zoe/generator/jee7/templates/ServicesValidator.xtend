 package co.edu.javeriana.zoe.generator.jee7.templates

import co.edu.javeriana.isml.isml.Service

/**
 * Esta clase contiene todos los templates que son reutilizados por varios otros templates.
 * 
 */
class ServicesValidator {
	
	
	def boolean validateService(Service service){
		if(service.name.equals("FileLoaderInterface")){
			return false
		}
		return true
	}
}