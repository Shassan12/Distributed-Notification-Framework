import java.io.Serializable;

public class Article implements Serializable{
	private static final long serialVersionUID = 1L;
	private String title;
	private String topic;
	private String articleText;
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public void setArticleText(String articleText) {
		this.articleText = articleText;
	}

	public String getTitle() {
		return title;
	}
	
	public String getTopic() {
		return topic;
	}
	
	public String getArticleText() {
		return articleText;
	}
	
	
}
