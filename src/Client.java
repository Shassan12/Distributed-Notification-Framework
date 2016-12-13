import java.rmi.RemoteException;

public class Client {
	private NotificationSink sink;
	private NewsPage newsPage;
	
	public Client(int port, String serverAddress){
		try {
			sink = new NotificationSink(port,serverAddress);
			newsPage = new NewsPage(sink);
			runClient();
		} catch (RemoteException e) {e.printStackTrace();}
		Runtime.getRuntime().addShutdownHook(new Thread() {
		    @Override
		    public void run() {
		        sink.unsubscribeFromSinks();
		        System.out.println("k");
		    }
		});
	}
	
	public void runClient(){
		while(true){
			try {
				Thread.sleep(50);
			} catch (InterruptedException e1) {}		
			if(sink.hasNotification()){
				Article article;
				try {
					article = (Article)sink.getNotification();
					newsPage.addArticle(article);
					System.out.println(article.getTitle());
				} catch (RemoteException e) {
					e.printStackTrace();
				}
			}
		}
	
	}
	
	public static void main(String[] args) {
		Client client = new Client(1099, "localhost");
		
	}
}	


