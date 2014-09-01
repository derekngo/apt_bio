/*Derek Ly (DTL398)
 *Derek Ngo (DN5242)
 *
 *Mini Project #3 - RMI
 *Middleware - Julien
 *
 */

import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.rmi.registry.*;

class SimpleChatServerImpl extends UnicastRemoteObject implements SimpleChatServer {
    List<SimpleChatClient> regClients;
    String description;
    String name;
    
    public SimpleChatServerImpl() throws RemoteException {
		regClients = new ArrayList<SimpleChatClient>();
    }
    
    public void talk(String message, String name) throws RemoteException {
		List<SimpleChatClient> copy = null; 
		synchronized(this) {
	    	copy = new ArrayList<SimpleChatClient>(regClients);
		}
		for(SimpleChatClient c : copy) {
			String serverMessage = "\nServer: " + this.name + " |" + name + ": " + message + "\n" + c.getName() + ": ";
			if(!name.equals(c.getName())){
				c.showMessage(serverMessage);
			} 
		}
    }
    
    public synchronized int getNumberOfClients() throws RemoteException{
    	return regClients.size();
    }
    
    public synchronized void join(SimpleChatClient c) throws RemoteException {
		regClients.add(c);
    }
    
    public synchronized void leave(SimpleChatClient c) throws RemoteException {
    	int i = regClients.indexOf(c);
    	regClients.remove(i);
    }
    
    public String getName() throws RemoteException{
    	return name;
    }
    
    public void setName(String name) throws RemoteException{
    	this.name = name;
    }
    
    public String getDescription() throws RemoteException{
    	return description;
    }
    
    public void setDescription(String description) throws RemoteException{
    	this.description = description;
    }
}
