import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class NotificationSource extends UnicastRemoteObject implements NotificationSourceInterface{
	private ArrayList<NotificationSinkInterface> sinkList = 
			new ArrayList<NotificationSinkInterface>();
	
	public NotificationSource() throws RemoteException{
		super();
		//UnicastRemoteObject.exportObject(this, 20600);
	}
	
	@Override
	public void registerSink(NotificationSinkInterface sink) throws RemoteException {
		sinkList.add((NotificationSinkInterface)sink);
	}

	@Override
	public void removeSink() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendNotification(Notification notification) throws RemoteException {
		//System.out.println(sinkList.size());
		/*for(NotificationSinkInterface sink : sinkList){
			//System.out.println("im sending it ok!");
			try{
				sink.notifySink(notification);
			}catch(Exception e){System.out.println(e.getMessage());}
		}*/
		sinkList.get(0).notifySink(notification);
	}
	
	
}
