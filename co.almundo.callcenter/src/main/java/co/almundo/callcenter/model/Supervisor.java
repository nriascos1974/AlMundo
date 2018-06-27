package co.almundo.callcenter.model;

/**
 * Esta clase define el empleado Supervisor
 * 
 * @author: Nestor J. Riasco Mosquera
 * @version: 26/06/2018
 */
public class Supervisor extends Employee{

	/**
	 * Constructor para el empleado Supervisor.
	 * 
	 * @param name
	 *            El parámetro name define el empleado.
	 */
   public Supervisor(String name) {
        super(name);
        setPriority(Priority.SUPERVISOR);
    }
}
