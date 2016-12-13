import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class NotificationSource extends UnicastRemoteObject implements NotificationSourceInterface{

	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<NotificationSinkInterface> sinkList = 
			new ArrayList<NotificationSinkInterface>();
	private Queue<Notification> Notifications;
	
	public NotificationSource(String name) throws RemoteException{
		super();
		this.name = name;
		this.Notifications = new LinkedList<Notification>();
		//setUpSource();
		Thread pingThread = new Thread(new SinkPinger(sinkList));
		pingThread.start();
	}
	
	/*public void setUpSource(){
		try{
			registry = LocateRegistry.createRegistry(port);
			registry.rebind("source", this);
			System.out.println("Server set up and running.");
			
			NotificationInterface n = new Notification();
		}catch(Exception e){System.out.println(e.getMessage());}
	}*/
	
	@Override
	public String getName(){
		return name;
	}
	
	@Override
	public void registerSink(NotificationSinkInterface sink) throws RemoteException {
		sinkList.add((NotificationSinkInterface)sink);
	}

	@Override
	public void removeSink(NotificationSinkInterface sink) throws RemoteException {
		sinkList.remove(sink);
	}

	@Override
	public void sendNotification(Notification notification) throws RemoteException {
		for(NotificationSinkInterface sink : sinkList){
			try{
				sink.notifySink(notification);
			}catch(Exception e){System.out.println(e.getMessage());}
		}
		//sinkList.get(0).notifySink(notification);
	}
	
	@Override
	public void ping() throws RemoteException {
		System.out.println("recieved ping");
		
	}
	
	class SinkPinger implements Runnable{
		private ArrayList<NotificationSinkInterface> sinkList;
		
		public SinkPinger(ArrayList<NotificationSinkInterface> sinkList){
			this.sinkList = sinkList;
		}
		
		public void run(){
			while(true){
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {}
				
				for(int i=0; i<sinkList.size();i++){
					try{
						sinkList.get(i).ping();
					}catch(RemoteException e){
						System.out.println("Client"+" "+i+" has lost connection");
						sinkList.remove(i);
					}
				}
			}
		}
	}
}
