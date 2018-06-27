package co.almundo.callcenter;

import co.almundo.callcenter.model.Director;
import co.almundo.callcenter.model.Employee;
import co.almundo.callcenter.model.Operator;
import co.almundo.callcenter.model.Supervisor;
import co.almundo.callcenter.services.Dispatcher;
import co.almundo.callcenter.services.IncomingCall;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class CallcenterTest {

	PriorityBlockingQueue<Employee> attendees;
	ExecutorService incomingCallExecutor;
	Dispatcher dispatcher;

	@Test
	public void testConcurrentCalls() throws InterruptedException, ExecutionException {
		setup(7, 2, 1, 10);

		List<Callable<Employee>> callableList = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			callableList.add(new IncomingCall(dispatcher, i));
		}

		List<Future<Employee>> futureList = incomingCallExecutor.invokeAll(callableList);

		int counCalls = 0;
		for (Future<Employee> future : futureList) {
			assert future.get() != null;
			counCalls++;
		}

		assert counCalls == 10;
	}

	@Test
	/**
	 * Se agregan 7 operadores, 2 supervisores y 1 director y se envian 7 llamadas,
	 * todos los empleados que atiendan las llamadas deben ser Operadores
	 */
	public void testPriorityCallAntedee() throws InterruptedException, ExecutionException {
		setup(7, 2, 1, 10);

		List<Callable<Employee>> callableList = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
			callableList.add(new IncomingCall(dispatcher, i));
		}

		List<Future<Employee>> futureList = incomingCallExecutor.invokeAll(callableList);

		for (Future<Employee> future : futureList) {
			assert future.get() instanceof Operator;
		}
	}

	@Test
	/**
	 * Se agregan 7 operadores, 2 supervisores y 1 director y se envian 8 llamadas,
	 * todas los empleados que atienden las llamadas deben ser los Operadores y un
	 * Supervisor
	 */
	public void testPriorityCallWithSupervisor() throws InterruptedException, ExecutionException {
		setup(7, 2, 1, 10);

		List<Callable<Employee>> callableList = new ArrayList<>();
		for (int i = 0; i < 8; i++) {
			callableList.add(new IncomingCall(dispatcher, i));
		}

		List<Future<Employee>> futureList = incomingCallExecutor.invokeAll(callableList);

		for (Future<Employee> future : futureList) {
			Employee e = future.get();
			assert e != null;
			assert e instanceof Operator || e instanceof Supervisor;
		}

	}

	@Test
	/**
	 * Se agregan 7 operadores, 2 supervisores y 1 director y se envian 12 llamadas,
	 * todas los empleados que atienden las llamadas deben ser los Operadores,
	 * Supervisores y Director, se espera a que se desocupe alguno para asignarle la nueva llamada
	 */
	public void testPriorityCallMoreTen() throws InterruptedException, ExecutionException {
		setup(7, 2, 1, 10);

		List<Callable<Employee>> callableList = new ArrayList<>();
		for (int i = 0; i < 12; i++) {
			callableList.add(new IncomingCall(dispatcher, i));
		}

		List<Future<Employee>> futureList = incomingCallExecutor.invokeAll(callableList);

		for (Future<Employee> future : futureList) {
			Employee e = future.get();
			assert e != null;
			assert e instanceof Operator || e instanceof Supervisor;
		}

	}

	private void setup(Integer operators, Integer supervisors, Integer directors, Integer callPoolSize) {
		attendees = new PriorityBlockingQueue<>();
		for (int i = 0; i < operators; i++) {
			attendees.add(new Operator("Operator #" + i));
		}

		for (int i = 0; i < supervisors; i++) {
			attendees.add(new Supervisor("Supervisor #" + i));
		}

		for (int i = 0; i < directors; i++) {
			attendees.add(new Director("Director #" + i));
		}

		incomingCallExecutor = Executors.newFixedThreadPool(callPoolSize);
		dispatcher = new Dispatcher(attendees);
	}

	@After
	public void tearDown() {
		attendees = null;
		dispatcher = null;
		incomingCallExecutor.shutdown();
		if (incomingCallExecutor.isShutdown()) {
			incomingCallExecutor = null;
		}
	}
}
