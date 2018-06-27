package co.almundo.callcenter;

import co.almundo.callcenter.model.Director;
import co.almundo.callcenter.model.Employee;
import co.almundo.callcenter.model.Operator;
import co.almundo.callcenter.model.Supervisor;
import co.almundo.callcenter.services.Dispatcher;
import co.almundo.callcenter.services.IncomingCall;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 
 * @author: Nestor J. Riasco Mosquera.
 * @version: 26/06/2018
 */
public class App {
   
    public static void main(String[] args) throws Exception {
        new App().process();    
    }
    
    public void process() throws ExecutionException, InterruptedException {
        PriorityBlockingQueue<Employee> attendees = new PriorityBlockingQueue<>();
        
        for(int i = 0; i < 3; i++) {
            attendees.add(new Operator("Operator #" + i));
        }
        
        for(int i = 0; i < 2; i++) {
            attendees.add(new Supervisor("Supervisor #" + i));
        }
        
        for(int i = 0; i < 2; i++) {
            attendees.add(new Director("Director #" + i));
        }

        Dispatcher d = new Dispatcher(attendees);

        ExecutorService threadPool = Executors.newFixedThreadPool(10); 
        
        List<Callable<Employee>> callablesList = new ArrayList<>();
        for(int i = 0; i < 50; i++) {
            callablesList.add(new IncomingCall(d, i));
        }
        
        threadPool.shutdown();
    }
}
