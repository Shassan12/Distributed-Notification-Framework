import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

	public static void main(String[] args) {
		try{
			Registry registry = LocateRegistry.getRegistry("localhost",20600);
			NotificationSinkInterface sink = new NotificationSink();
			NotificationSourceInterface source1 = 
					(NotificationSourceInterface)
					registry.lookup("source1");
			source1.registerSink((NotificationSinkInterface)sink);
			System.out.println("Source found!");
		}catch(Exception e){System.out.println(e.getMessage());}
	}
}
