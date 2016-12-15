import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/*GUI that runs on the server side of the application to allow uploading of
 * an article*/
public class UploadArticleInterface extends JFrame{
	private static final long serialVersionUID = 1L;
	private Server server;
	private JTextField title;
	private JTextArea contents;
	private ButtonGroup radioGroup;
	private JButton uploadArticleButton;
	private ArrayList<JRadioButton> buttonList;
	
	public UploadArticleInterface(Server server){
		super("Upload articles");
		this.server = server;
		this.init();
	}
	
	/*Creates the GUI*/
	public void init(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container mainPanel = this.getContentPane();
		mainPanel.setBackground(Color.lightGray);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
		
		mainPanel.add(Box.createHorizontalGlue());
		JLabel titleLabel = new JLabel("Title");
		titleLabel.setPreferredSize(new Dimension(350, 50));
		titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		title = new JTextField("Enter article title");
		title.setMaximumSize(new Dimension(350, 20));
		title.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(titleLabel);
		mainPanel.add(title);
		
		JLabel contentsLabel = new JLabel("Article contents");
		contents = new JTextArea();
		contents.setMaximumSize(new Dimension(500, 350));
		contents.setPreferredSize(new Dimension(500, 350));
		contents.setAlignmentX(Component.CENTER_ALIGNMENT);
		contents.setWrapStyleWord(true);
		contentsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		mainPanel.add(contentsLabel);
		mainPanel.add(contents);
		
		JLabel articleTopicLabel = new JLabel("article Topic");
		articleTopicLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(articleTopicLabel);
		
		JPanel articleTopicsPanel = new JPanel();
		articleTopicsPanel.setMaximumSize(new Dimension(600,50));
		radioGroup = new ButtonGroup();
		ArrayList<JRadioButton> buttonList = new ArrayList<JRadioButton>();
		buttonList.add(new JRadioButton("Politics"));
		buttonList.add(new JRadioButton("Finance"));
		buttonList.add(new JRadioButton("Technology"));
		buttonList.add(new JRadioButton("Satire"));
		buttonList.get(0).setSelected(true);
		
		for(JRadioButton button : buttonList){
			radioGroup.add(button);
			articleTopicsPanel.add(button);
		}
		
		mainPanel.add(articleTopicsPanel);
		
		uploadArticleButton = new JButton("Upload Article");
		uploadArticleButton.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		/*Adds listener to button that will create the article of the selected topic
		 * and contents*/
		uploadArticleButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				for(JRadioButton button : buttonList){
					if(button.isSelected()){
						Article article = new Article();
						article.setTitle(title.getText());
						article.setArticleText(contents.getText());
						article.setTopic(button.getText());
						clearBoxes();
						server.sendNotification(article, button.getText());
						JOptionPane.showMessageDialog(null, button.getText()+" article uploaded", null, JOptionPane.INFORMATION_MESSAGE);
					}
				}
				
			}
		
		});
		
		mainPanel.add(uploadArticleButton);
		
		this.setSize(800, 600);
		this.setResizable(false);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	//clears all textboxes in GUI
	public void clearBoxes(){
		title.setText("Enter Article Title");
		contents.setText("");
	}
}
