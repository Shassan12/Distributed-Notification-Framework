import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class NotificationSink extends UnicastRemoteObject
								implements NotificationSinkInterface{
	
	private static final long serialVersionUID = 1L;

	public NotificationSink() throws RemoteException{
		super();
		//UnicastRemoteObject.exportObject(this, 20600);
	}
	
	@Override
	public void notifySink(Notification notification) throws RemoteException {
		System.out.println("I got the note m9: "+notification.getMessage());
		
	}

}
