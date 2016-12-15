import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/*Server class that has an RMI registry containing one or more sources*/
public class Server {
	private int port;
	private Registry registry;
	
	public Server(int port){
		this.port = port;
		setUpRegistry();
	}
	
	/*Create the RMI registry*/
	public void setUpRegistry(){
		try {
			registry = LocateRegistry.createRegistry(port);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	//Adds a source to the RMI registry
	public void addSource(NotificationSourceInterface source){
		try {
			registry.rebind(source.getName(), source);
			System.out.println("source "+source.getName()+" added");
		} catch (AccessException e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	/*Passes an article to a source that will then send the article to all 
	 *subscribed sinks*/
	public void sendNotification(Article article, String sourceName){
		try {
			NotificationSourceInterface source = 
					(NotificationSourceInterface)registry.lookup(sourceName);
			source.sendNotification(article);
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	/*Main methods that creates sinks and adds them to the server. Creates server GUI*/
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
			UploadArticleInterface uai = new UploadArticleInterface(server);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}


