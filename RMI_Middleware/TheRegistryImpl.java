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

class TheRegistryImpl implements TheRegistry {
    ArrayList<SimpleChatServer> regServer;
    ArrayList<String> regServerName;
    ArrayList<SimpleChatClient> regClient;
    ArrayList<String> regClientName;
    
    public TheRegistryImpl() throws RemoteException {
		regServer = new ArrayList<SimpleChatServer>();
		regServerName = new ArrayList<String>();
		regClient = new ArrayList<SimpleChatClient>();
		regClientName = new ArrayList<String>();
    }
    
	public synchronized int register(String name, SimpleChatServer s) throws RemoteException{
		int flag = 1;
		if(regServerName.contains(name)){
			flag = -1;
			return flag;
		}
		regServer.add(s);
		regServerName.add(name);
		return flag;
	}
	public synchronized int register(String name, SimpleChatClient c) throws RemoteException{
		int flag = 1;
		if(regClientName.contains(name)){
			flag = - 1;
			return flag;
		}
		regClient.add(c);
		regClientName.add(name);
		return flag;
	}
	
	public int deregister(String name) throws RemoteException{
		int index = regServerName.indexOf(name);
		if(index == -1){
			return -1;
		}
		if(regServer.get(index).getNumberOfClients() > 0){
			return -2;
		} else{
			regServerName.remove(index);
			regServer.remove(index);
			return 1;
		}
	}
	
	public int deregister(String name, SimpleChatClient c) throws RemoteException{
		int index = regClientName.indexOf(name);
		regClientName.remove(index);
		regClient.remove(index);
		return 1;
	}
	
	public String getInfo(String name) throws RemoteException{
		if(regServerName.size() < 1){
			return "Sorry, there are no servers availiable";
		}
		int index  = regServerName.indexOf(name); 
		if(index == -1){
			return "There is no server with that name";
		}else{
			SimpleChatServer s = regServer.get(index);
			return "Server: " + s.getName() + " | Description: " + s.getDescription();
		}
	}
	
	public boolean serverExist(String name) throws RemoteException{
		for(int i = 0;i < regServerName.size();i++ ){
			if (name.equals(regServerName.get(i))){
				return true;
			}
		}
		return false;
	}
	
	public String getServerInfo() throws RemoteException{
		String servers = "Here are the names of the servers availiable \n";
		if(regServerName.size() < 1){
			return "Sorry, there are no servers availiable";
		}
		for(int i = 0;i < regServerName.size();i++ ){
			servers = servers + "Server Name: " + regServerName.get(i) + " | Description: " + regServer.get(i).getDescription() + " | Number of Clients in Server: " + regServer.get(i).getNumberOfClients() + "\n";
		}
		return servers;
	}
	
	public SimpleChatServer getServer(String name) throws RemoteException{
		int i = regServerName.indexOf(name);
		return regServer.get(i);
	}

    public static void main(String[] args) throws Exception {
		try{
	    	TheRegistryImpl obj = new TheRegistryImpl();
	    	TheRegistry stub = (TheRegistry) UnicastRemoteObject.exportObject(obj, 0);
	    	Registry registry = LocateRegistry.getRegistry();
	    	registry.bind("ChatRegistry", stub);
		}catch(Exception e){
	    	e.printStackTrace();
		}
    }
}
