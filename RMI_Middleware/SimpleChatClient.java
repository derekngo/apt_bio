/*Derek Ly (DTL398)
 *Derek Ngo (DN5242)
 *
 *Mini Project #3 - RMI
 *Middleware - Julien
 *
 */

import java.rmi.*;

public interface SimpleChatClient extends Remote {
    public void showMessage(String message) throws RemoteException ;
    public String getName() throws RemoteException;
}
