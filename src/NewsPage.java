import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class NewsPage extends JFrame{
	private static final long serialVersionUID = 1L;
	private NotificationSink sink;
	private JPanel articles;
	private JButton setTopics;
	private JTextArea contents;
	private JLabel titleLabel;
	private JCheckBox politics;
	private JCheckBox finance;
	private JCheckBox technology;
	private JCheckBox satire;
	
	public NewsPage(NotificationSink sink){
		super("News Center");
		this.sink = sink;
		this.init();
	}
	
	public void init(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container mainPanel = this.getContentPane();
		mainPanel.setBackground(Color.lightGray);
		mainPanel.setLayout(new FlowLayout());
		
		JPanel articlesPanel = new JPanel();
		articlesPanel.setPreferredSize(new Dimension(350,650));
		articles = new JPanel();
		articles.setLayout(new BoxLayout(articles, BoxLayout.PAGE_AXIS));
		articles.setMinimumSize(new Dimension(400,700));
		JScrollPane articlesScrollPane = new JScrollPane(articles);
		articlesScrollPane.getVerticalScrollBar().setUnitIncrement(25);
		articlesScrollPane.setPreferredSize(new Dimension(350,700));
		articlesPanel.setAutoscrolls(true);
		articlesPanel.add(articlesScrollPane);
		mainPanel.add(articlesPanel);
		
		JPanel optionPanel = new JPanel();
		optionPanel.setMinimumSize(new Dimension(350,650));
		optionPanel.setPreferredSize(new Dimension(350,650));
		
		ArrayList<JCheckBox> buttonList = new ArrayList<JCheckBox>();
		politics = new JCheckBox("Politics");
		finance = new JCheckBox("Finance");
		technology = new JCheckBox("Technology");
		satire = new JCheckBox("satire");
		buttonList.add(politics);
		buttonList.add(finance);
		buttonList.add(technology);
		buttonList.add(satire);

		for(JCheckBox button : buttonList){
			optionPanel.add(button);
		}
		
		setTopics = new JButton("set Topics");
		
		setTopics.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				sink.unsubscribeFromSinks();
				clearArticles();
				if(politics.isSelected()){
					sink.addSource("Politics");
				}
				
				if(finance.isSelected()){
					sink.addSource("Finance");
				}
				
				if(technology.isSelected()){
					sink.addSource("Technology");
				}
				
				if(satire.isSelected()){
					sink.addSource("Satire");
				}
				
			};
		
		});
		
		optionPanel.add(setTopics);
		mainPanel.add(optionPanel);
		
		JPanel articleContentsPanel = new JPanel();
		articleContentsPanel.setLayout(new BoxLayout(articleContentsPanel, BoxLayout.PAGE_AXIS));
		articleContentsPanel.setMinimumSize(new Dimension(350,650));
		articleContentsPanel.setPreferredSize(new Dimension(350,650));
		titleLabel = new JLabel(" ");
		contents = new JTextArea();
		contents.setMaximumSize(new Dimension(500, 550));
		contents.setPreferredSize(new Dimension(500, 550));
		titleLabel.setMinimumSize(new Dimension(350,80));
		
		articleContentsPanel.add(titleLabel);
		articleContentsPanel.add(contents);
		mainPanel.add(articleContentsPanel);
		
		this.setSize(1200, 700);
		this.setResizable(false);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
	}
	
	public void addArticle(Article article){
		NewsTile tile = new NewsTile(article);
		articles.add(tile);
		this.revalidate();
		this.repaint();
	}
	
	public void clearArticles(){
		articles.removeAll();
		this.revalidate();
		this.repaint();
	}
	
	private class NewsTile extends JPanel{
		private static final long serialVersionUID = 1L;
		private Article article;

		public NewsTile(Article article){
			this.article = article;
			this.init();
		}

		public Article getArticle(){
			return this.article;
		}

		/*Creates the tile, with fields containing information about the item, including title, sellerID, start time,
		 * close time, highest bid and the item's status*/
		public void init(){
			this.setLayout(new GridBagLayout());
			this.setPreferredSize(new Dimension(350,45));
			this.setMaximumSize(new Dimension(400,45));
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			gbc.anchor = GridBagConstraints.NORTHWEST;

			JLabel articleTitle = new JLabel("  "+article.getTitle());
			articleTitle.setFont(new Font("Serif",Font.BOLD, 16));
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridwidth = 4;
			this.add(articleTitle, gbc);

			JLabel articleTopic = new JLabel(article.getTopic());
			articleTopic.setFont(new Font("Serif",Font.BOLD, 12));
			gbc.gridx = 2;
			gbc.gridy = 1;
			this.add(articleTopic, gbc);

			this.addMouseListener(new TileListener(this));
		}

		/*Creates the tile with a gradient fill (to differentiate tiles from each other)*/
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			int width = getWidth();
			int height = getHeight();
			
			if(article.getTopic().equals("Politics")){
				g.setColor(new Color(255,0,0));
				g.fillRect(0, 0, width, height);
			}
			
			if(article.getTopic().equals("Finance")){
				g.setColor(new Color(155,0,155));
				g.fillRect(0, 0, width, height);
			}
			
			if(article.getTopic().equals("Technology")){
				g.setColor(new Color(0,0,255));
				g.fillRect(0, 0, width, height);
			}
			
			if(article.getTopic().equals("Satire")){
				g.setColor(new Color(155,155,0));
				g.fillRect(0, 0, width, height);
			}
		}
		
		/*Listens for mouse clicks on tiles*/
		private class TileListener extends MouseAdapter{
			private NewsTile newsTile;

			public TileListener(NewsTile newsTile){
				this.newsTile = newsTile;
			}

			@Override
			public void mouseClicked(MouseEvent e){
				Article article = newsTile.getArticle();
				titleLabel.setText(article.getTitle());
				contents.setText(article.getArticleText());
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				//apply a transparent layer on top of the tile on mouse over
				newsTile.setOpaque(false);
				Graphics g = newsTile.getGraphics();
				g.setColor(new Color(0,255,0,80));
				g.fillRect(0, 0, newsTile.getWidth(), newsTile.getHeight());
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				//remove transparent layer when mouse exits the tile
				newsTile.setOpaque(true);
				newsTile.repaint();
			}
		}
	}
}
