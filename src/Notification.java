import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/*Notification class that carries a message*/
public class Notification implements NotificationInterface{
	
	private static final long serialVersionUID = 1L;
	private Object message;
	
	public Notification(Object message) throws RemoteException{
		super();
		this.message = message;
	}
	
	//returns the object carried in this notification
	public Object getMessage(){
		return this.message;
	}
}
