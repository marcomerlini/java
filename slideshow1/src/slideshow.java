import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.JApplet;
import javax.swing.JButton;

//applet che visualizza i valori dei suoi parametri 
public class slideshow extends JApplet implements MouseListener, Runnable
{
	public String s;
	public String t;
	public static int w=0;
	public JButton button = new JButton();
	public JButton button1 = new JButton();
	public static String[] imgs = new String[2];
	public static boolean attivo=true;
	
	public Thread mythread = new Thread(this);
	@Override
	public void start() 
	{
		mythread.start();
		super.start();
	}
	
	@Override
	public void run() 
	{
		//stabiliamo la durata della visualizzazione
	    //di ciascuna immagine 1000 è pari ad 1 secondo 
	    int tempo = 2000;
	    while(true) 
	    {
	    	if (attivo==true) 
	    	{
	    	    //ciclo di visualizzazione
	    	     //while (td == Thread.currentThread()){
	    	    	w++;
	    			if(w>1)
	    			{
	    				w=0;
	    			}
	    	        repaint();
	    	        try
		    	    {
	    	        mythread.sleep(tempo);
		    	    }
	    	    catch (Exception e) {}
	    	  } 
	    }
	}
	
	//ci mette tutto quello che devi fare all'inizio
	public void init()
	{
		this.addMouseListener(this);
		this.setLayout(null);
		imgs[0]="homer.jpg";
		imgs[1]="pistola.jpg";
		button.setVisible(true);
		button.setText("<");
		button.setSize(50,50);
		button.setLocation(20,200);
		this.add(button);
		button.addMouseListener(this);
		button.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ev)
			{
				w++;
				if(w>1)
				w=0;
				repaint();
			}
		});
		button1.setVisible(true);
		button1.setText(">");
		button1.setSize(50,50);
		button1.setLocation(620,200);
		this.add(button1);
		button1.addMouseListener(this);
		button1.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent ev)
			{
				w--;
				if(w<0)
				w=1;
				repaint();
			}
		});
	}
	public void paint(Graphics g)
	{
		BufferedImage img=null;
		try
		{
				URL url = new URL(getCodeBase(),imgs[w]);
				img = ImageIO.read(url);
		}
		catch(IOException e){}
		g.drawImage(img,1,1,this);
	}
	@Override
	public void mouseClicked(MouseEvent arg0){}
	@Override
	public void mouseEntered(MouseEvent arg0) 
	{
		attivo=false;
		button.setVisible(true);
		button1.setVisible(true);
	}
	@Override
	public void mouseExited(MouseEvent e) 
	{
		try
		{
			JApplet a = (JApplet) e.getSource();
			attivo=true;
			button.setVisible(false);
			button1.setVisible(false);
		}catch(Exception e1){}
	}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	@Override
	public void mouseReleased(MouseEvent arg0) {}

}
