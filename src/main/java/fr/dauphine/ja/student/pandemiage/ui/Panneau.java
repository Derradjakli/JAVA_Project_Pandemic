package fr.dauphine.ja.student.pandemiage.ui;

import java.awt.Color;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class Panneau extends JPanel {
	private int posX=-50;
	private int posY=-50;

	public void paintComponent(Graphics g) {
		/**g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.BLUE);
		g.fillRect(posX,posY,50,50);
		 **/
		try {

			Image img = ImageIO.read(new File("imagejeu.jpg"));
			Image img1 = ImageIO.read(new File("pionne.jpg"));
			g.drawImage(img, 0, 0, this.getWidth(),this.getHeight(),this);
			img1=img1.getScaledInstance(30, 50, Image.SCALE_DEFAULT);
			g.drawImage(img1, posX, posY,this);

			//Pour une image de fond

			//g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);

		} catch (IOException e) {

			e.printStackTrace();

		}                

	}
	public int getPosX() {
		return posX;
	}


	public void setPosX(int posX) {

		this.posX = posX;

	}


	public int getPosY() {

		return posY;

	}


	public void setPosY(int posY) {

		this.posY = posY;

	}    



	
}
class Fenetre extends JFrame{
	private Panneau pan=new Panneau();
	public Fenetre() {

		this.setTitle("Ma premi�re fenetre");
		this.setContentPane(pan);
		this.setSize(300,300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);

		/*JPanel p= new JPanel();
		p.setLayout(new BoxLayout(p,BoxLayout.LINE_AXIS));
		p.add(new JButton("premier"));

		JPanel p1= new JPanel();
		p1.setLayout(new BoxLayout(p1,BoxLayout.LINE_AXIS));
		p1.add(new JButton("2"));
		p1.add(new JButton("3"));


		JPanel p2= new JPanel();
		p2.setLayout(new BoxLayout(p2,BoxLayout.LINE_AXIS));
		p2.add(new JButton("4"));
		p2.add(new JButton("5"));
		p2.add(new JButton("6"));
		JPanel b4 = new JPanel();

	    //On positionne maintenant ces trois lignes en colonne

	    b4.setLayout(new BoxLayout(b4, BoxLayout.PAGE_AXIS));

	    b4.add(p1);

	    b4.add(p);

	    b4.add(p2);
		this.getContentPane().add(b4);
		 */
		this.setVisible(true);

		/*	GridLayout grd=new GridLayout(4,3);
		grd.setHgap(3);
		grd.setVgap(4);

		this.setLayout(grd);

		this.getContentPane().add(new JButton("Centre"));

		this.getContentPane().add(new JButton("NORTH"));

		this.getContentPane().add(new JButton("SOUTH"));

		this.getContentPane().add(new JButton("WEST"));

		this.getContentPane().add(new JButton("EST"));*/


		//on previent jframe que sa content pan sera notre jpanel
		//this.setContentPane(pan);

		go();
	}
	private void go(){

		//Les coordonn�es de d�part de notre rond

		int x = pan.getPosX(), y = pan.getPosY();

		//Le bool�en pour savoir si l'on recule ou non sur l'axe x

		boolean backX = false;

		//Le bool�en pour savoir si l'on recule ou non sur l'axe y

		boolean backY = false;


		//Dans cet exemple, j'utilise une boucle while

		//Vous verrez qu'elle fonctionne tr�s bien

		while(true){

			//Si la coordonn�e x est inf�rieure � 1, on avance

			if(x < 1)

				backX = false;


			//Si la coordonn�e x est sup�rieure � la taille du Panneau moins la taille du rond, on recule

			if(x > pan.getWidth()-50)

				backX = true;


			//Idem pour l'axe y

			if(y < 1)

				backY = false;

			if(y > pan.getHeight()-50)

				backY = true;


			//Si on avance, on incr�mente la coordonn�e

			//backX est un bool�en, donc !backX revient � �crire

			//if (backX == false)

			if(!backX)

				pan.setPosX(++x);


			//Sinon, on d�cr�mente

			else

				pan.setPosX(--x);


			//Idem pour l'axe Y

			if(!backY)

				pan.setPosY(++y);

			else

				pan.setPosY(--y);


			//On redessine notre Panneau

			pan.repaint();


			//Comme on dit : la pause s'impose ! Ici, trois milli�mes de seconde

			try {

				Thread.sleep(3);

			} catch (InterruptedException e) {

				e.printStackTrace();

			}

		}

	}
}