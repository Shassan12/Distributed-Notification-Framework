import java.awt.Color;
import java.awt.Container;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class UploadArticleInterface extends JFrame{
	private Server server;
	private JTextField title;
	private JTextArea contents;
	public UploadArticleInterface(Server server){
		super("Upload articles");
		this.server = server;
		this.init();
	}
	
	public void init(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container mainPanel = this.getContentPane();
		mainPanel.setBackground(Color.lightGray);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		
		
		
		this.setSize(800, 600);
		this.setResizable(false);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
}
