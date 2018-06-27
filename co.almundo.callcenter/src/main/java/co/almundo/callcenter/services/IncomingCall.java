package co.almundo.callcenter.services;

import co.almundo.callcenter.model.Employee;

import java.util.concurrent.Callable;

/**
 * Esta clase maneja las llamadas entrantes.
 * 
 * @author: Nestor J. Riasco Mosquera.
 * @version: 26/06/2018
 */
public class IncomingCall implements Callable<Employee> {

	// Campos de la clase
	private Integer call;
	private Dispatcher dispatcher;

	/**
	 * Constructor para el manejo de las llamadas.
	 * 
	 * @param dispatcher
	 *            El parámetro dispatcher define el manejo que se le dará a la llamada.
	 * @param call
	 *            El parámetro call define la llamada entrante.
	 */
	public IncomingCall(Dispatcher dispatcher, Integer call) {
		this.dispatcher = dispatcher;
		this.call = call;
	}

	@Override
	public Employee call() throws Exception {
		return dispatcher.dispatchCall(call);
	}
}
