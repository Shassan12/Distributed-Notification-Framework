import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Notification implements NotificationInterface{
	
	private static final long serialVersionUID = 1L;
	private String message;
	
	public Notification() throws RemoteException{
		super();
	}
	
	public void setMessage(String message) throws RemoteException{
		this.message = message;
	}
	
	public String getMessage()throws RemoteException{
		return this.message;
	}
}
