/*Derek Ly (DTL398)
 *Derek Ngo (DN5242)
 *
 *Mini Project #3 - RMI
 *Middleware - Julien
 *
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface SimpleChatServer extends Remote {
	public int getNumberOfClients() throws RemoteException;
    public void talk(String message, String name) throws RemoteException;
    public void join(SimpleChatClient c) throws RemoteException;
    public void leave(SimpleChatClient c) throws RemoteException;
    public String getName() throws RemoteException;
    public void setName(String name) throws RemoteException;
    public String getDescription() throws RemoteException;
    public void setDescription(String description) throws RemoteException;
}
