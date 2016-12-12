import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class NewsPage extends JFrame{
	private static final long serialVersionUID = 1L;
	private Client client;
	private JPanel articles;
	
	public NewsPage(Client client){
		super("News Center");
		this.client = client;
		this.init();
	}
	
	public void init(){
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container mainPanel = this.getContentPane();
		mainPanel.setBackground(Color.lightGray);
		
		JPanel articlesPanel = new JPanel();
		articles = new JPanel();
		articles.setLayout(new BoxLayout(articles, BoxLayout.PAGE_AXIS));
		articles.setMinimumSize(new Dimension(300,700));
		JScrollPane articlesScrollPane = new JScrollPane(articles);
		articlesScrollPane.getVerticalScrollBar().setUnitIncrement(25);
		articlesScrollPane.setPreferredSize(new Dimension(350,700));
		articlesPanel.setAutoscrolls(true);
		articlesPanel.add(articlesScrollPane);
		mainPanel.add(articlesPanel);
		
		this.setSize(1200, 700);
		this.setResizable(false);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
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
			this.setPreferredSize(new Dimension(350,80));
			this.setMaximumSize(new Dimension(400,80));
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.weightx = 1.0;
			gbc.weighty = 1.0;
			gbc.anchor = GridBagConstraints.NORTHWEST;

			JLabel articleTitle = new JLabel(article.getTitle());
			articleTitle.setFont(new Font("Serif",Font.BOLD, 16));
			gbc.gridx = 0;
			gbc.gridy = 0;
			gbc.gridwidth = 4;
			this.add(articleTitle, gbc);

			gbc.anchor = GridBagConstraints.WEST;
			gbc.gridwidth = 2;
			JLabel articlePostDate = new JLabel(article.getPostTime());
			articlePostDate.setFont(new Font("Serif",Font.BOLD, 12));
			gbc.gridx = 0;
			gbc.gridy = 1;
			this.add(articlePostDate, gbc);

			JLabel articleTopic = new JLabel(article.getTopic());
			articleTopic.setFont(new Font("Serif",Font.BOLD, 12));
			gbc.gridx = 1;
			gbc.gridy = 1;
			this.add(articleTopic, gbc);

			//this.addMouseListener(new TileListener(this));
		}

		/*Creates the tile with a gradient fill (to differentiate tiles from each other)*/
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			int width = getWidth();
			int height = getHeight();
			GradientPaint gp = new GradientPaint(0, 0, Color.BLUE, 0, height, Color.RED);
			g2d.setPaint(gp);
			g2d.fillRect(0, 0, width, height);
		}
		
		/*Listens for mouse clicks on tiles*/
		private class TileListener extends MouseAdapter{
			/*private AuctionTile auctionTile;

			public TileListener(AuctionTile auctionTile){
				this.auctionTile = auctionTile;
			}

			@Override
			public void mouseClicked(MouseEvent e){
				//create a window to display more information for an item and allows the user
				//to bid on the item
				Item itemToView = auctionTile.getItem();
				new AuctionViewFrame(itemToView,itemToView.getTitle(),user,client);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				//apply a transparent layer on top of the tile on mouse over
				auctionTile.setOpaque(false);
				Graphics g = auctionTile.getGraphics();
				g.setColor(new Color(255,0,0,80));
				g.fillRect(0, 0, auctionTile.getWidth(), auctionTile.getHeight());
			}

			@Override
			public void mouseExited(MouseEvent e) {
				//remove transparent layer when mouse exits the tile
				auctionTile.setOpaque(true);
				auctionTile.repaint();
			}*/		 
		}
	}
}
