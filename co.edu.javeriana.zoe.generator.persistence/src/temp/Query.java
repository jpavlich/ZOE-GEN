package temp;

public class Query {
	Query next;

	
	
	class Select extends Query {
		String[] columnas;

		public Select(Query previous, String[] columnas) {
			super(previous);
			this.columnas = columnas;
		}

	}

	class From extends Query {
		String tabla;

		public From(Query previous, String tabla) {
			super(previous);
			this.tabla = tabla;
		}

	}

	class Where extends Query {
		public Where(Query previous, String criterio) {
			super(previous);
			this.criterio = criterio;
		}

		String criterio;
	}

	
	
	
	public Query(Query previous) {
		super();			
			next = this;
		
	}
	
	
	/////////////

	public Query select(String... columnas) {
		return new Select(this, columnas);
	}

	public Query from(String tabla) {
		return new From(this, tabla);
	}

	public Query where(String criterio) {
		return new Where(this, criterio);
	}
	
	public String toSQL() {
		return "";
	}
	
	////////////////

	public static void main(String[] args) {
		System.out.println();
		System.out.println(new Query(null).select("almuerzo").from("dieta").where("..."));
		
		

	}
}
