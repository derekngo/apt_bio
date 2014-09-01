/*Derek Ly (DTL398)
 *Derek Ngo (DN5242)
 *
 *Mini Project #3 - RMI
 *Middleware - Julien
 *
 */


import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SimpleChatClientImpl extends UnicastRemoteObject implements SimpleChatClient {
	private static ArrayList<SimpleChatServer> serverList = new ArrayList<SimpleChatServer>();
	private String name;
	
	private static String[] menuItems = 
		{ 	"[0]: Quit",
			"[1]: Connect to a chatroom server",
			"[2]: Chat in a chatroom",
			"[3]: Disconnect from a chatroom server",
			"[4]: Look up chatroom server Info"
		};
	
    public SimpleChatClientImpl() throws RemoteException {}
    
    public void showMessage(String message) throws RemoteException {
		System.out.print(message);
    }
    public void act(TheRegistry tr) throws RemoteException {
		System.out.println("What is your name?");
    	while(true){
			Scanner in = new Scanner(System.in);
			String input = in.next();  
			int success = tr.register(input,this);
			if (success == 1){
				this.name = input;
				break;
			} else{
				System.out.println("Name already taken, please input another name");
			}
		}
		
		while(true){
			displayPrompt();
			
			Scanner in = new Scanner(System.in);
			while (!in.hasNextInt()) {
				displayPrompt();
				in.nextLine();
			}
			
			int input = in.nextInt(); //make sure to handle exceptions here. ^ check if the above method works ^
			if(input == 0){
				leaveAllServers();
				System.out.println("Goodbye " + this.name);
				tr.deregister(this.name, this);
				System.exit(1);
			} 
			else if (input == 1){
				String message = tr.getServerInfo();
				System.out.println(message);
				if(!message.equals("Sorry, there are no servers availiable")){
					System.out.println("Enter the name of the Server you would like to connect to");
					String serverName = in.next(); //make sure to handle exception here if user inputs a server name that does not exist.
					if(isConnected(serverName) == -1){
						if(tr.serverExist(serverName)){
							SimpleChatServer server = tr.getServer(serverName);
							server.join(this);
							serverList.add(server);
							System.out.println("Connected to " + serverName);
						}
						else {
							System.out.println(serverName + " is not a listed server");
						}
					}
					else{
						System.out.println("You are already connected to " + serverName);
					}
				}
			} 
			else if (input == 2){
				boolean flag = false;
				String response = getMyServers();
				System.out.println(response);
				if(!response.equals("You are not connected to any server!")){
					String serverName = in.next();
					for(int i = 0; i < serverList.size(); i++){
						if(serverName.equals(serverList.get(i).getName())){
							flag = true;
							SimpleChatServer stub = serverList.get(i);
							System.out.println("You are now talking in the " + serverName + "chatroom\n Please enter \"mainmenu\" to go back to the main menu");
							chatting(stub);
						}
					}
					if (flag == false){
						System.out.println("You are not connected to any server named " + serverName);
					}
				}
				
			}
			else if (input == 3){
				String response = getMyServers();
				System.out.println(response);
				if(!response.equals("You are not connected to any server!")){
					System.out.println("Which server would you like to disconnect from?");
					String serverName = in.next(); 	
					if(isConnected(serverName) >= 0){
						SimpleChatServer stub = serverList.get(isConnected(serverName));
						stub.leave(this);
						removeServer(serverName);	
					}
					else{
						System.out.println("You are not connected to any server named " + serverName);
					}
					
				}
			}
			else if (input == 4){
				System.out.println("What is the name of the server you would like to look up?");
				String serverName = in.next();
				System.out.println(tr.getInfo(serverName));
			}
			else {
				System.out.println("Not a valid response, please enter a number from ranging from 0 to 4");
			}
		}
		
	}
    
    public void displayPrompt(){
    	System.out.println("testing the display prompt method");
    	System.out.println("What would you like to do?");
    	for(String a: menuItems){
    		System.out.println(a);
    	}
    	System.out.print(name + ": ");
    }
    
    public void chatting(SimpleChatServer s) throws RemoteException{
		while(true){
			System.out.print(name + ": ");
			String message = System.console().readLine();
			if (message.equals("mainmenu")){
				return;
			}
			else{
				s.talk(message, name);
			}
		}
    }
    
    public void removeServer(String serverName) throws RemoteException{			//should only be called if there are Servers that the Client is connected to
    	for(int i = 0; i < serverList.size();i++){
    		if(serverName.equals(serverList.get(i).getName())){
    			serverList.remove(i);
    			return;
    		}
    	}
    }
    
    public int isConnected(String serverName) throws RemoteException{
    	for(int i = 0; i < serverList.size();i++){
    		if(serverName.equals(serverList.get(i).getName())){
    			return i;
    		}
    	}
    	return -1;
    }
    
    public String getMyServers() throws RemoteException{
    	String response = "Here are the servers you are connected to\n";
    	if(serverList.size() < 1){
    		return "You are not connected to any server!";
    	}
    	for (int i = 0; i < serverList.size(); i++){
			response = response + serverList.get(i).getName() + "\n";
		}
    	return response;
    }

    public void leaveAllServers() throws RemoteException{
    	for(int i = 0; i < serverList.size();i++){
    		SimpleChatServer stub = serverList.get(i); 
    		stub.leave(this);	
    	}
    	serverList.clear();
    }
    
    public String getName() throws RemoteException{
    	return name;
    }
    
    public static void main(String args[]) throws Exception {
		String host = args[0];
		try{
			Registry registry = LocateRegistry.getRegistry(host);
			TheRegistry stub = (TheRegistry) registry.lookup("ChatRegistry");
			new SimpleChatClientImpl().act(stub);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		
    }
    
   
}
