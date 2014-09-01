/*Derek Ly (DTL398)
 *Derek Ngo (DN5242)
 *
 *Mini Project #3 - RMI
 *Middleware - Julien
 *
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface TheRegistry extends Remote{
	public int register(String name, SimpleChatServer s) throws RemoteException;
	public int register(String name, SimpleChatClient c) throws RemoteException;
	public int deregister(String name) throws RemoteException;
	public int deregister(String name, SimpleChatClient c) throws RemoteException;
	public String getInfo(String name) throws RemoteException;
	public boolean serverExist(String name) throws RemoteException;
	public String getServerInfo() throws RemoteException;
	public SimpleChatServer getServer(String name) throws RemoteException;
}

