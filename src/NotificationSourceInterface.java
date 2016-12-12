import java.rmi.*;

public interface NotificationSourceInterface extends Remote{
	public void registerSink(NotificationSinkInterface sink) throws RemoteException;
	public void removeSink() throws RemoteException;
	public void sendNotification(NotificationInterface notification) throws RemoteException;
	public void ping() throws RemoteException;
}
