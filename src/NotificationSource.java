import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class NotificationSource extends UnicastRemoteObject implements NotificationSourceInterface{
	private ArrayList<NotificationSink> sinkList = 
			new ArrayList<NotificationSink>();
	
	public NotificationSource() throws RemoteException{
		super();
		//UnicastRemoteObject.exportObject(this, 20600);
	}
	
	@Override
	public void registerSink(NotificationSink sink) throws RemoteException {
		sinkList.add(sink);
		System.out.println("lololololololololoololoololol");
	}

	@Override
	public void removeSink() throws RemoteException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendNotification(Notification notification) throws RemoteException {
		//System.out.println(sinkList.size());
		for(NotificationSink sink : sinkList){
			//System.out.println("im sending it ok!");
			sink.notifySink(notification);
		}
		
	}
	
	
}
