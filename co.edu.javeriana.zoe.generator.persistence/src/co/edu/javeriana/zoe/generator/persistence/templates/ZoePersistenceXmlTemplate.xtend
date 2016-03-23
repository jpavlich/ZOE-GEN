package co.edu.javeriana.zoe.generator.persistence.templates

import co.edu.javeriana.isml.generator.common.SimpleTemplate
import co.edu.javeriana.isml.isml.Entity
import co.edu.javeriana.isml.scoping.IsmlModelNavigation
import com.google.inject.Inject
import java.util.List
import org.eclipse.xtext.naming.IQualifiedNameProvider

class ZoePersistenceXmlTemplate extends SimpleTemplate<List<Entity>> {

	/*Inyección de las clases auxiliares con metodos utilitarios*/
	@Inject extension IsmlModelNavigation
	@Inject extension IQualifiedNameProvider

	override def CharSequence template(List<Entity> totalPages) '''
		<?xml version="1.0" encoding="UTF-8"?>
		<persistence version="2.1" xmlns="http://xmlns.jcp.org/xml/ns/persistence" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://xmlns.jcp.org/xml/ns/persistence http://xmlns.jcp.org/xml/ns/persistence/persistence_2_1.xsd">
		  <persistence-unit name="co.edu.javeriana.javemovil_javemovil-web_war_1.0-SNAPSHOTPU" transaction-type="JTA">
		    <jta-data-source>jdbc/prenat</jta-data-source>
		    <provider>org.eclipse.persistence.jpa.PersistenceProvider</provider>
				<class>co.edu.javeriana.entities.Dieta</class>
			    <class>co.edu.javeriana.entities.Lugares</class>
			    <class>co.edu.javeriana.entities.Preferencias</class>
		    <exclude-unlisted-classes>true</exclude-unlisted-classes>
		    <properties>
		      <property name="javax.persistence.schema-generation.database.action" value="create"/>
		    </properties>
		  </persistence-unit>
		</persistence>
	'''

}
