import java.rmi.*;

public interface NotificationSinkInterface extends Remote{
	public void notifySink(Notification Notification) throws RemoteException;
	public void ping() throws RemoteException;
	public Object getNotification() throws RemoteException;
}
