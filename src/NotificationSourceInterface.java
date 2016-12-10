import java.rmi.*;

public interface NotificationSourceInterface extends Remote{
	public void registerSink() throws RemoteException;
}
