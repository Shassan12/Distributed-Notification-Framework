import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

	public static void main(String[] args) {
		try{
			Registry registry2 = LocateRegistry.getRegistry("localhost",20600);
			NotificationSink sink = new NotificationSink();
			NotificationSourceInterface source1 = 
					(NotificationSourceInterface)
					registry2.lookup("source1");
			source1.registerSink(sink);
			System.out.println("Source found!");
		}catch(Exception e){System.out.println(e.getMessage());}
	}
}
