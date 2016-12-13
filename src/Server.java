import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
	private int port;
	private Registry registry;
	
	public Server(int port){
		this.port = port;
		setUpRegistry();
	}
	
	public void setUpRegistry(){
		try {
			registry = LocateRegistry.createRegistry(port);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void addSource(NotificationSourceInterface source){
		try {
			registry.rebind(source.getName(), source);
			System.out.println("sourceName source added");
		} catch (AccessException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void sendNotification(Notification note, String sourceName){
		try {
			NotificationSourceInterface source = 
					(NotificationSourceInterface)registry.lookup(sourceName);
			source.sendNotification(note);
		} catch (RemoteException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		try {
			Server server = new Server(1099);
			NotificationSourceInterface source = new NotificationSource("source1");
			server.addSource(source);
			Article test = new Article();
			test.setTitle("Man eats a potato");
			test.setTopic("nonsense");
			test.setPostTime("21:47 12/12/2016");
			Notification note = new Notification(test);
			while(true){
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {}	
				server.sendNotification(note, "source1");
				System.out.println("sent");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}


