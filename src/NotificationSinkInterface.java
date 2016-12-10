import java.rmi.*;

public interface NotificationSinkInterface extends Remote{
	public void notifySink(NotificationInterface Notification) throws RemoteException;
	//public void notifySink(Notification notification) throws RemoteException;

}
