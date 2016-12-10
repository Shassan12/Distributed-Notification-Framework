import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

	public static void main(String[] args) {
		try{
			Registry registry = LocateRegistry.createRegistry(20600);
			NotificationSourceInterface source1 = new NotificationSource();
			registry.rebind("source1", source1);
			System.out.println("Server set up and running.");
			
			Notification n = new Notification();
			n.setMessage("lololol");
			while(true){
				try{
				source1.sendNotification(n);
				}catch(Exception e){System.out.println(e.getMessage());}
			}
		}catch(Exception e){System.out.println(e.getMessage());}

	}

}
