import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

	public static void main(String[] args) {
		try {
			NotificationSourceInterface source = new NotificationSource(20600);
			NotificationSourceInterface source2 = new NotificationSource(20601);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
