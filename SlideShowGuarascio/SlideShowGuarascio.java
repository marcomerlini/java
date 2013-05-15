import java.applet.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

    
//definiamo la classe
public class SlideShow extends Applet implements Runnable,MouseListener, ActionListener
{  boolean asd=true;
   AudioClip ac ;
   Image[] Gif = new Image[4];
    java.awt.Button b1,b2;
  //valore iniziale del frame
  int clip = 0;
        
  //un thread è un flusso di controllo in un programma
  //si crea istanziando un oggetto della classe Thread 

    volatile Thread td;

    @Override
  public void init()
            //getParameter("img1")
  { Gif[0] = getImage(getDocumentBase(),getParameter("img1"));
    Gif[1] = getImage(getDocumentBase(), getParameter("img2"));
    Gif[2] = getImage(getDocumentBase(), getParameter("img3"));
    Gif[3] = getImage(getDocumentBase(), getParameter("img4"));
    this.addMouseListener(this);
    b1=new Button("Indietro");
    b1.setVisible(false);
    add(b1);
    b1.addActionListener(this);
    b1.addMouseListener(this);
    b2=new Button("Avanti");
    b2.addActionListener(this);
    b2.addMouseListener(this);
    b2.setVisible(false);
    add(b2);
    //utilizzo audioclip
    //ac=getAudioClip(getDocumentBase(),getParameter("audio"));
    //ac.loop();
   }
        
  //definiamo il comportamento del thread nel metodo
    @Override
  public void start()
  {
    	int i;
    	char k;
    	char [] v= new char [10];
    	String s="X31";
    	v=s.toCharArray()	;
    	for (i=0;i<v.length ;i++)
    		k=v[i];
        (td = new Thread(this)).start();
    }
    @Override
  public void stop()
  {td = null;}
        
  //"disegnamo" le immagini nell'Applet
    @Override
  public void paint(Graphics g)
   { super.paintComponents(g);
     g.drawImage(Gif[clip], 0, 0, this);
    }

    public void run()
  {//stabiliamo la durata della visualizzazione
    //di ciascuna immagine 1000 è pari ad 1 secondo 
    int tempo = 2000;
    while(true) {
    if (asd==true) {
    try
    {//ciclo di visualizzazione
     //while (td == Thread.currentThread()){
       clip = (clip+1)%Gif.length;
        repaint();
        Thread.sleep(tempo);
//      }
    //notifica delle eccezioni
    }
    catch (Exception e) {}
  }
    else
    {try
    { Thread.sleep(100);}
    //notifica delle eccezioni
    catch (Exception e) {}
        }
      }
    }

    public void mouseClicked(MouseEvent e) { }

    public void mousePressed(MouseEvent e) { }

    public void mouseReleased(MouseEvent e) { }

    public void mouseEntered(MouseEvent e) {
     b1.setVisible(true);
     b2.setVisible(true);
     asd=false;
     //condizione=false;
      }
    public void mouseExited(MouseEvent e) {
    asd=true;
     }

    public void actionPerformed(ActionEvent e) {
      if (e.getSource()==b1)
          if (clip==0)
              {b1.setEnabled(false);
      b2.setEnabled(true);
      }
          else {

                b1.setEnabled(true);
                b2.setEnabled(true);
                clip--;
                repaint();
                }
      else
          if (clip==3)
             { b2.setEnabled(false);

            b1.setEnabled(true);}
          else {
                b2.setEnabled(true);
                b1.setEnabled(true);
                clip++;
                repaint();
                }
    }

	@Override
}
