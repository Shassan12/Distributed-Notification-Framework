import java.rmi.*;

/*Interface implemented by NotificationSink. extends remote so that sinks act 
 * as remote objects*/
public interface NotificationSinkInterface extends Remote{
	
	//notifies the sink with a Notification object
	public void notifySink(Notification Notification) throws RemoteException;
	
	//pings the sink to check if a connection is still active
	public void ping() throws RemoteException;
}
