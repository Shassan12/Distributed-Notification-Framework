import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {

	public static void main(String[] args) {
		try {
			NotificationSourceInterface source = new NotificationSource(20600);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
