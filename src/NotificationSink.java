import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/*Distributed Object that receives Notifications from NotifcationSources*/
public class NotificationSink extends UnicastRemoteObject
								implements NotificationSinkInterface{
	
	private static final long serialVersionUID = 1L;
	private Registry registry;
	private ArrayList<NotificationSourceInterface> sourceList;
	private Queue<Notification> notificationQueue;
	private int port;
	private String serverAddress;
	
	/*Takes the port and address of the server this is connecting to*/
	public NotificationSink(int port, String serverAddress) throws RemoteException{
		super();
		this.port = port;
		this.serverAddress = serverAddress;
		this.notificationQueue = new LinkedList<Notification>();
		sourceList = new ArrayList<NotificationSourceInterface>();
		this.connectToServer();
	}
	
	//invoked remotely by a source to pass a Notification to this sink
	@Override
	public void notifySink(Notification notification) throws RemoteException {
		notificationQueue.add(notification);
	}
	
	/*empty method used by source to check if source can still 
	 * invoke methods on this sink*/
	@Override
	public void ping(){}
	
	/*unsubscribes this sink from all of the sources it is registered
	 * to*/
	public void unsubscribeFromSources(){
		for(NotificationSourceInterface source : sourceList){
			try {
				source.removeSink(this);
			} catch (RemoteException e) {
				this.reconnect();
			}
		}
		
		sourceList.clear();
	}
	
	/*subscribes this sink to the specified source*/
	public void addSource(String sourceName){
		NotificationSourceInterface source;
		try {
			source = (NotificationSourceInterface)registry.lookup(sourceName);
			source.registerSink((NotificationSinkInterface)this);
			sourceList.add(source);
			System.out.println("registed to "+source.getName());
		} catch (AccessException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			this.reconnect();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	/*returns the identifier of the source at the specified index 
	 * in the list of sources*/
	public NotificationSourceInterface getSource(int option){
		return sourceList.get(option);
	}
	
	//returns the number of sources this sink is subscribed to
	public int getNumOfSources(){
		return sourceList.size();
	}
	
	//connects to the RMI registry running on a server
	public void connectToServer(){
		try {
			registry = LocateRegistry.getRegistry(serverAddress,port);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	//check if this sink has any new Notifications
	public boolean hasNotification(){
		return (!notificationQueue.isEmpty());
	}
	
	//get the next notification that needs to be revieved by an application
	public Object getNotification(){
		Notification notification = notificationQueue.poll();
		return notification.getMessage();
	}
	
	//attempt to resubscribe to sources this sink has lost access to
	public void reconnect(){
		boolean reconnected = false;
		int attempts = 0;
		while(!reconnected){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e1) {}
			try{
				attempts += 1;
				for(NotificationSourceInterface source : sourceList){
					source.ping();
					source.registerSink(this);
				}
				
				reconnected = true;
			}catch(RemoteException e){
				System.out.println("Could not reconnect to server "+attempts);
			}
		}
	}
}
	

