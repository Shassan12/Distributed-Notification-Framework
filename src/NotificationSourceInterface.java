import java.rmi.*;

public interface NotificationSourceInterface extends Remote{
	public void registerSink(NotificationSinkInterface sink) throws RemoteException;
	public void removeSink(NotificationSinkInterface sink) throws RemoteException;
	public void sendNotification(Notification notification) throws RemoteException;
	public String getName() throws RemoteException;
	public void ping() throws RemoteException;
}
