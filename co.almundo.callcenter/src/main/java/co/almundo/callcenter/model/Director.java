package co.almundo.callcenter.model;

/**
 * Esta clase define el empleado Director
 * 
 * @author: Nestor J. Riasco Mosquera
 * @version: 26/06/2018
 */
public class Director extends Employee {

	/**
	 * Constructor para el empleado Director.
	 * 
	 * @param name
	 *            El parámetro name define el empleado.
	 */
	public Director(String name) {
		super(name);
		setPriority(Priority.DIRECTOR);
	}
}
