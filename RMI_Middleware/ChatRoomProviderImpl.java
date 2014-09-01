/*Derek Ly (DTL398)
 *Derek Ngo (DN5242)
 *
 *Mini Project #3 - RMI
 *Middleware - Julien
 *
 */

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class ChatRoomProviderImpl extends UnicastRemoteObject implements ChatRoomProvider {
	
	public ChatRoomProviderImpl() throws RemoteException{
		
	}
	
	public void createServer(TheRegistry tr) throws RemoteException{
		Scanner in = new Scanner(System.in);
		
		System.out.println("What would you like to name your server?");
		String name = in.next();
		SimpleChatServer s = new SimpleChatServerImpl();
		s.setName(name);
		System.out.println("Give a description of your server");
		String description = in.next();
		s.setDescription(description);
		int response = tr.register(name, s);
		if(response ==  1){
			System.out.println("The server " + name + " has been created");
		} 
		else if (response == -1){
			System.out.println("There is already a server named " + name);
		}
	}
	
	public void act(TheRegistry tr) throws RemoteException{
		while(true){
			System.out.println("What would you like to do?\n[1]: Create a Server\n[2]: Delete a Server");
			Scanner in = new Scanner(System.in);
			while (!in.hasNextInt()) {				
				   System.out.println("Not a valid response, please enter a number from ranging from 0 to 3");
				   in.nextLine();
			}
			int input = in.nextInt();
			if (input == 1){
				createServer(tr);
			} 
			else if (input == 2){
				String message = tr.getServerInfo();
				System.out.println(message);
				if (!message.equals("Sorry, there are no servers availiable")){
					System.out.println("What server would you like to delete. \n*Note* You can not delete a server that still has clients connected to it");
					String serverName = in.next();
					int flag = tr.deregister(serverName);
					if(flag == -1){
						System.out.println("There is no server named " + serverName);
					}
					else if (flag == -2){
						System.out.println(serverName + " cannot be deleted because it still has clients connected to it!");
					}
					else if (flag == 1){
						System.out.println(serverName + " successfully deleted");
					}
				}
			} 
			else{
				System.out.println("Not a valid response, please enter a number from ranging from 0 to 2");
			}
			
		}
	}
	
	public static void main(String args[]) throws Exception {
		String host = args[0];
		try{
		    Registry registry = LocateRegistry.getRegistry(host);
		    TheRegistry stub = (TheRegistry) registry.lookup("ChatRegistry");
		    new ChatRoomProviderImpl().act(stub);
		}
		catch(Exception e){
		    e.printStackTrace();
		}
	}
}
