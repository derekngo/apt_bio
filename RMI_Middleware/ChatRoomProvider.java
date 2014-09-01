/*Derek Ly (DTL398)
 *Derek Ngo (DN5242)
 *
 *Mini Project #3 - RMI
 *Middleware - Julien
 *
 */

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ChatRoomProvider {
	public void createServer(TheRegistry tr) throws RemoteException;
}
