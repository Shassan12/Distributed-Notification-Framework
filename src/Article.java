
public class Article {
	private String title;
	private String topic;
	private String articleText;
	private String postTime;
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setTopic(String topic) {
		this.topic = topic;
	}
	
	public void setArticleText(String articleText) {
		this.articleText = articleText;
	}
	
	
	public void setPostTime(String postTime) {
		this.postTime = postTime;
	}

	public String getPostTime() {
		return postTime;
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
