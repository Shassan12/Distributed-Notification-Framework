import java.rmi.*;

public interface NotificationSinkInterface extends Remote{
	public void notifySink() throws RemoteException;
}
