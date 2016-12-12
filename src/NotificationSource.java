import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class NotificationSource extends UnicastRemoteObject implements NotificationSourceInterface{

	private static final long serialVersionUID = 1L;
	private ArrayList<NotificationSinkInterface> sinkList = 
			new ArrayList<NotificationSinkInterface>();
	private int port;
	private Registry registry;
	
	public NotificationSource(int port) throws RemoteException{
		super();
		this.port = port;
		setUpSource();
		Thread pingThread = new Thread(new SinkPinger(sinkList));
		pingThread.start();
	}
	
	public void setUpSource(){
		try{
			registry = LocateRegistry.createRegistry(port);
			registry.rebind("source", this);
			System.out.println("Server set up and running.");
			
			NotificationInterface n = new Notification();
		}catch(Exception e){System.out.println(e.getMessage());}
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
	public void sendNotification(NotificationInterface notification) throws RemoteException {
		//System.out.println(sinkList.size());
		/*for(NotificationSinkInterface sink : sinkList){
			//System.out.println("im sending it ok!");
			try{
				sink.notifySink(notification);
			}catch(Exception e){System.out.println(e.getMessage());}
		}*/
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
