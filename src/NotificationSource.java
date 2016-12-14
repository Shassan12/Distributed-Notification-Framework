import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

public class NotificationSource extends UnicastRemoteObject implements NotificationSourceInterface{

	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<NotificationSinkInterface> sinkList = 
			new ArrayList<NotificationSinkInterface>();
	private ArrayList<Notification> notifications;
	
	public NotificationSource(String name) throws RemoteException{
		super();
		this.name = name;
		this.notifications = new ArrayList<Notification>();
		Thread pingThread = new Thread(new SinkPinger(sinkList));
		pingThread.start();
	}
	
	@Override
	public String getName(){
		return name;
	}
	
	@Override
	public void registerSink(NotificationSinkInterface sink) throws RemoteException {
		sinkList.add((NotificationSinkInterface)sink);
		syncSinkNotifications(sink);
	}

	@Override
	public void removeSink(NotificationSinkInterface sink) throws RemoteException {
		sinkList.remove(sink);
	}

	@Override
	public void sendNotification(Notification notification) throws RemoteException {
		notifications.add(notification);
		for(NotificationSinkInterface sink : sinkList){
			try{
				sink.notifySink(notification);
			}catch(Exception e){System.out.println(e.getMessage());}
		}
		//sinkList.get(0).notifySink(notification);
	}
	
	public void syncSinkNotifications(NotificationSinkInterface sink){
		for(Notification note : notifications){
			try {
				sink.notifySink(note);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void ping() throws RemoteException {}
	
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
