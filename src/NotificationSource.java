import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class NotificationSource implements NotificationSourceInterface{
	private ArrayList<NotificationSinkInterface> sinkList = 
			new ArrayList<NotificationSinkInterface>();
	
	public NotificationSource() throws RemoteException{
		super();
		UnicastRemoteObject.exportObject(this, 20600);
	}
	
	@Override
	public void registerSink(NotificationSinkInterface sink) throws RemoteException {
		sinkList.add(sink);
		
	}

	@Override
	public void removeSink() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendNotification(Notification notification) throws RemoteException {
		for(NotificationSinkInterface sink : sinkList){
			sink.notifySink(notification);
		}
		
	}
	
	
}
