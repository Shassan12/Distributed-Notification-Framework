import java.rmi.*;

public interface NotificationSinkInterface extends Remote{
	public void notifySink(NotificationInterface Notification) throws RemoteException;
	public void ping() throws RemoteException;

}
