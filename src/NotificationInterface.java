import java.io.Serializable;
import java.rmi.RemoteException;

public interface NotificationInterface extends Serializable{
	public void setMessage(String message) throws RemoteException;
	public String getMessage() throws RemoteException;
}
