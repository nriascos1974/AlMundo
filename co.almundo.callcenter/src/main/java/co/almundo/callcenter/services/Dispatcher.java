package co.almundo.callcenter.services;

import co.almundo.callcenter.model.Employee;
import org.apache.log4j.Logger;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Esta clase es la encargada de manejar las llamadas.
 * 
 * @author: Nestor J. Riasco Mosquera
 * @version: 26/06/2018
 */

public class Dispatcher {

	// Campos de la clase
	private PriorityBlockingQueue<Employee> attendees;
	private static final Integer MAX_CALL_DURATION = 10000;
	private static final Integer MIN_CALL_DURATION = 5000;

	private static final Logger log = Logger.getLogger(Dispatcher.class);

	/**
	 * Constructor para el manejo de las llamadas.
	 * 
	 * @param attendees El parámetro attendees define el empleado que atendera la llamada.
	 */
	public Dispatcher(PriorityBlockingQueue<Employee> attendees) {
		this.attendees = attendees;
	}

	/**
	 * Toma un attendee (empleado) disponible y realiza la llamada, luego vuelve a ponerlo en la cola.
     * Si no hay attendee disponible, entonces el método take() bloquea todas las nuevas llamadas entrantes 
     * hasta que un atendee esté disponible nuevamente.
	 * 
	 * @param call El parámetro call define el número de llama.
	 * @return El attendee a quien se le asignó la llamada entrante.
	 */
	public Employee dispatchCall(Integer call) {
		Employee attendee = null;
		try {
			attendee = attendees.take();
			log.info("Llamada entrante No. " + call + " Asignada a: " + attendee);
			doCall(call, attendee);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
		return attendee;
	}

	/**
	 * Simula la ejecución de la llamada y cuando esta finaliza, el attendee regresa
	 * a la cola.
	 * 
	 * @param call El parámentro call define el número de la llamada.
	 * @param attendee El parámetro attendee define el attendee (empleado) quien atendió la llamada.
	 */
	private void doCall(Integer call, Employee attendee) {
		try {
			Integer duration = new Random().nextInt((MAX_CALL_DURATION - MIN_CALL_DURATION) + 1) + MIN_CALL_DURATION;
			Thread.sleep(duration);
			log.info("Finalizada llamada No. " + call + " Asignada a: " + attendee + " Duración: " + duration);
			attendees.add(attendee);
		} catch (InterruptedException e) {
			log.error(e.getMessage(), e);
		}
	}
}
