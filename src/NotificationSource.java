import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/*Distributed object that sends Notifications to registered sinks*/
public class NotificationSource extends UnicastRemoteObject implements NotificationSourceInterface{

	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<NotificationSinkInterface> sinkList = 
			new ArrayList<NotificationSinkInterface>();
	
	public NotificationSource(String name) throws RemoteException{
		super();
		this.name = name;
		Thread pingThread = new Thread(new SinkPinger(sinkList));
		pingThread.start();
	}
	
	//returns the identifier of this source in its parent servers RMI registry
	@Override
	public String getName(){
		return name;
	}
	
	//register a passed in sink as a sink that is subscribed to this sink
	@Override
	public void registerSink(NotificationSinkInterface sink) throws RemoteException {
		if(!sinkList.contains(sink)){
			sinkList.add((NotificationSinkInterface)sink);
		}
	}
	
	//unsubscribe a sink from this sources list of subscribed sinks
	@Override
	public void removeSink(NotificationSinkInterface sink) throws RemoteException {
		sinkList.remove(sink);
	}
	
	//send a Notification to all subscribed sinks
	@Override
	public void sendNotification(Object message) throws RemoteException {
		Notification note = new Notification(message);
		for(NotificationSinkInterface sink : sinkList){
			try{
				sink.notifySink(note);
			}catch(RemoteException e){}
		}
	}
	
	//used by sinks to check if this source can still be reached
	@Override
	public void ping() throws RemoteException {}
	
	/*Constantly pings all registered sinks every 20 seconds and removes sinks that
	 * do not respond.*/
	class SinkPinger implements Runnable{
		private ArrayList<NotificationSinkInterface> sinkList;
		
		public SinkPinger(ArrayList<NotificationSinkInterface> sinkList){
			this.sinkList = sinkList;
		}
		
		public void run(){
			while(true){
				try {
					Thread.sleep(20000);
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
