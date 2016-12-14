import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class NotificationSink extends UnicastRemoteObject
								implements NotificationSinkInterface{
	
	private static final long serialVersionUID = 1L;
	private Registry registry;
	private ArrayList<NotificationSourceInterface> sourceList;
	private Queue<Notification> notificationQueue;
	private int port;
	private String serverAddress;
	
	public NotificationSink(int port, String serverAddress) throws RemoteException{
		super();
		this.port = port;
		this.serverAddress = serverAddress;
		this.notificationQueue = new LinkedList<Notification>();
		sourceList = new ArrayList<NotificationSourceInterface>();
		this.connectToServer();
		//Thread sourcePinger = new Thread(new SourcePinger(this));
		//sourcePinger.start();
	}
	
	@Override
	public void notifySink(Notification notification) throws RemoteException {
		notificationQueue.add(notification);
		
	}
	
	@Override
	public void ping(){}
	
	public void unsubscribeFromSinks(){
		for(NotificationSourceInterface source : sourceList){
			try {
				source.removeSink(this);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		
		sourceList.clear();
	}
	
	public void addSource(String sourceName){
		NotificationSourceInterface source;
		try {
			source = (NotificationSourceInterface)registry.lookup(sourceName);
			source.registerSink((NotificationSinkInterface)this);
			sourceList.add(source);
			System.out.println("Source added!");
		} catch (AccessException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	public NotificationSourceInterface getSource(int option){
		return sourceList.get(option);
	}
	
	public int getNumOfSources(){
		return sourceList.size();
	}
	
	public void connectToServer(){
		try {
			registry = LocateRegistry.getRegistry(serverAddress,port);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	public boolean hasNotification(){
		return (!notificationQueue.isEmpty());
	}
	@Override
	public Object getNotification() throws RemoteException {
		Notification notification = notificationQueue.poll();
		return notification.getMessage();
	}
	
	/*public boolean reConnectToServer(){
		try {
			registry = LocateRegistry.getRegistry(serverAddress,port);
			ArrayList<NotificationSourceInterface> sourceList2 = 
					new ArrayList<NotificationSourceInterface>();
			
			for(int i=0; i<sourceList.size();i++){
				sourceList2.add(sourceList.get(i));
			}
			sourceList.clear();
			
			for(int j=0; j<sourceList2.size();j++){
				NotificationSourceInterface source;
				source = (NotificationSourceInterface)registry.lookup(sourceList.get(j).getName());
				source.registerSink((NotificationSinkInterface)this);
				sourceList.add(source);
			}

			
			//sourceList.add((NotificationSourceInterface)
					//	registry.lookup("source"));
			//source1.registerSink((NotificationSinkInterface)sink);
			
			System.out.println("Source found!");
			return true;
		} catch (RemoteException | NotBoundException e) {
			System.out.println("not yet");
			return false;
		}
	}*/
	
	
	
	/*class SourcePinger implements Runnable{
		private NotificationSink sink;
		
		public SourcePinger(NotificationSink sink){
			this.sink = sink;
		}
		
		public void run(){
			NotificationSourceInterface source;
			while(true){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {}
				
				try{
					for(int i=0; i<sink.getNumOfSources(); i++){
						source = sink.getSource(i);
						source.ping();
					}
				}catch(RemoteException e){
					System.out.println("lost connection to server. Attempting reconnection");
					boolean reConnected = false;
					while(!reConnected){
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e1) {}
						reConnected = sink.reConnectToServer();
						if(reConnected){
							for(int i=0; i<sink.getNumOfSources();i++){
								try {
									System.out.println("wut");
									sink.getSource(i).registerSink((NotificationSinkInterface)sink);
								} catch (RemoteException e1) {
									//e1.printStackTrace();
								}
							}
						}
					}
				}
			}
		}*/
	}

