import java.rmi.Remote;
import java.rmi.RemoteException;

public interface NotificationInterface extends Remote{
	public void setMessage(String message) throws RemoteException;
	public String getMessage() throws RemoteException;
}
