import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

public class NotificationSource extends UnicastRemoteObject implements NotificationSourceInterface{

	private static final long serialVersionUID = 1L;
	private ArrayList<NotificationSinkInterface> sinkList = 
			new ArrayList<NotificationSinkInterface>();
	
	public NotificationSource() throws RemoteException{
		super();
		Thread pingThread = new Thread(new SinkPinger(sinkList));
		pingThread.start();
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
