package co.almundo.callcenter.model;

/**
 * Esta clase define el empleado Operador
 * 
 * @author: Nestor J. Riasco Mosquera
 * @version: 26/06/2018
 */

public class Operator extends Employee {

	/**
	 * Constructor para el empleado Operador.
	 * 
	 * @param name
	 *            El parámetro name define el empleado.
	 */
	public Operator(String name) {
		super(name);
		setPriority(Priority.OPERATOR);
	}

}
