import java.rmi.*;

public interface NotificationSinkInterface extends Remote{
	public void notifySink(Notification notification) throws RemoteException;
}
