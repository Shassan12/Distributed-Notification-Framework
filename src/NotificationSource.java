import java.rmi.RemoteException;
import java.util.ArrayList;

public class NotificationSource implements NotificationSourceInterface{
	private ArrayList<NotificationSinkInterface> sinkList = 
			new ArrayList<NotificationSinkInterface>();
	
	public NotificationSource(){
		
	}
	
	@Override
	public void registerSink() throws RemoteException {
		// TODO Auto-generated method stub
		
	}
	
	
}
