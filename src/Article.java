import java.io.Serializable;

/*Represents a basic news article with a title, topic and 
 * the contents of the news article*/
public class Article implements Serializable{
	private static final long serialVersionUID = 1L;
	private String title;
	private String topic;
	private String articleText;
	
	//set the title of this article
	public void setTitle(String title) {
		this.title = title;
	}
	
	//set the topic of this article
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	//set the contents of this article
	public void setArticleText(String articleText) {
		this.articleText = articleText;
	}

	//return the title of this article
	public String getTitle() {
		return title;
	}
	
	//return the topic of this article
	public String getTopic() {
		return topic;
	}
	
	//return the contents of this article
	public String getArticleText() {
		return articleText;
	}
	
	
}
