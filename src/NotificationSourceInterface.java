import java.rmi.*;

public interface NotificationSourceInterface extends Remote{
	public void registerSink(NotificationSinkInterface sink) throws RemoteException;
	public void removeSink() throws RemoteException;
	public void sendNotification(Notification notification) throws RemoteException;
}
