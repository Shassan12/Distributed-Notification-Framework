import java.io.Serializable;
import java.rmi.RemoteException;

/*Serializable interface inherited by Notification*/
public interface NotificationInterface extends Serializable{
	//returns the object carried by the notification
	public Object getMessage();
}
