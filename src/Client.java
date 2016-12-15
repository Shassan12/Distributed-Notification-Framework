import java.rmi.RemoteException;

/*Client class that creates a sink and runs the application*/
public class Client {
	private NotificationSink sink;
	private NewsPage newsPage;
	
	/*Constructor. Creates a sink and the GUI then runs the application*/
	public Client(int port, String serverAddress){
		try {
			sink = new NotificationSink(port,serverAddress);
			newsPage = new NewsPage(sink);
			runClient();
		} catch (RemoteException e) {e.printStackTrace();}
	}
	
	/*Constantly queries the sink for notifications. 
	 * If a notification is returned, the article is taken
	 * from the notification and added to the listof articles
	 * in the user interface*/
	public void runClient(){
		while(true){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {}		
			if(sink.hasNotification()){
				Article article;
				article = (Article)sink.getNotification();
				newsPage.addArticle(article);
				System.out.println("Recieved "+article.getTitle());
			}
		}
	
	}
	
	//main method that makes a Client object
	public static void main(String[] args) {
		Client client = new Client(1099, "localhost");
		
	}
}	


