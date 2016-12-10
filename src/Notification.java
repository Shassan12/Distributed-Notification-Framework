import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Notification extends UnicastRemoteObject 
							implements NotificationInterface{
	
	private static final long serialVersionUID = 1L;

	public Notification() throws RemoteException{
		super();
	}
}
