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
			NotificationSourceInterface politicsSource = new NotificationSource("Politics");
			NotificationSourceInterface financeSource = new NotificationSource("Finance");
			NotificationSourceInterface technologySource = new NotificationSource("Technology");
			NotificationSourceInterface satireSource = new NotificationSource("Satire");
			server.addSource(politicsSource);
			server.addSource(financeSource);
			server.addSource(technologySource);
			server.addSource(satireSource);
			Article test = new Article();
			test.setTitle("Man eats a potato");
			test.setTopic("Politics");
			test.setPostTime("21:47 12/12/2016");
			test.setArticleText("fssssasffffffffffffffffffffffffffffffffffffffffffffffffffff");
			Notification note = new Notification(test);
			while(true){
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {}	
				server.sendNotification(note, "Politics");
				server.sendNotification(note, "Satire");
				System.out.println("sent");
			}
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}


