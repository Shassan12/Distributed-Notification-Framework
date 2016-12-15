import java.rmi.*;

/*Interface implemented by NotificationSource. extends remote so that sources act 
 * as remote objects*/
public interface NotificationSourceInterface extends Remote{
	
	//register a passed in sink as a sink that is subscribed to this sink
	public void registerSink(NotificationSinkInterface sink) throws RemoteException;
	
	//unsubscribe a sink from this sources list of subscribed sinks
	public void removeSink(NotificationSinkInterface sink) throws RemoteException;
	
	//sends a Notification to all subscribed sinks
	public void sendNotification(Object message) throws RemoteException;
	
	//returns the identifier of this source in the servers RMI registry
	public String getName() throws RemoteException;
	
	//used by sinks to check if this source can still be reached
	public void ping() throws RemoteException;
}
