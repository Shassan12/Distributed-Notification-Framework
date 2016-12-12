import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public class Client {
	private Registry registry;
	private NotificationSinkInterface sink;
	private NotificationSourceInterface source1;
	
	public Client(){
		try {
			sink = new NotificationSink();
		} catch (RemoteException e) {e.printStackTrace();}
		this.connectToServer();
	}
	
	public NotificationSourceInterface getSource(){
		return source1;
	}
	
	public boolean connectToServer(){
		try {
			registry = LocateRegistry.getRegistry("localhost",20600);
			source1 =(NotificationSourceInterface)
						registry.lookup("source");
			source1.registerSink((NotificationSinkInterface)sink);
			System.out.println("Source found!");
			return true;
		} catch (RemoteException | NotBoundException e) {
			System.out.println("not yet");
			return false;
		}
	}
	
	public static void main(String[] args) {
		/*try{
			Registry registry = LocateRegistry.getRegistry("localhost",20600);
			NotificationSinkInterface sink = new NotificationSink();
			NotificationSourceInterface source1 = 
					(NotificationSourceInterface)
					registry.lookup("source1");
			source1.registerSink((NotificationSinkInterface)sink);
			System.out.println("Source found!");
		}catch(Exception e){System.out.println(e.getMessage());}*/
		Client client = new Client();
		NewsPage newsPage = new NewsPage(client);
		Thread sourcePinger = new Thread(new SourcePinger(client));
		sourcePinger.start();
	}
}	
class SourcePinger implements Runnable{
		private NotificationSourceInterface source;
		private Client client;
		
		public SourcePinger(Client client){
			this.client = client;
			this.source = client.getSource();
		}
		
		public void run(){
			while(true){
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {}
				
				try{
					source.ping();
				}catch(RemoteException e){
					System.out.println("lost connection to server. Attempting reconnection");
					boolean reConnected = false;
					int attempts = 0;
					while(!reConnected&&attempts<10){
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e1) {}
						reConnected = client.connectToServer();
						attempts += 1;
					}
					
					if(attempts > 9){
						System.out.println("Connetion timed out");
					}else{
						this.source = client.getSource();
					}
				}
			}
		}
	}

