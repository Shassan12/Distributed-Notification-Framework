import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Notification implements NotificationInterface{
	
	private static final long serialVersionUID = 1L;
	private Object message;
	
	public Notification(Object message) throws RemoteException{
		super();
		this.message = message;
	}
	
	public void setMessage(String message) throws RemoteException{
		this.message = message;
	}
	
	public Object getMessage()throws RemoteException{
		return this.message;
	}
}
