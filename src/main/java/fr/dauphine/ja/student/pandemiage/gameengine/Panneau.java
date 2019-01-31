package fr.dauphine.ja.student.pandemiage.gameengine;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
	Image lune, terre,fleche,tetedemort,ok,card,pc;

	Panneau() {
		try {
			lune = ImageIO.read(new File("pandemicMap (2).jpg"));
			terre = ImageIO.read(new File("infection.png"));
			fleche = ImageIO.read(new File("location-512.png"));
			ok = ImageIO.read(new File("ok.png"));
			card = ImageIO.read(new File("playercard.png"));
			pc = ImageIO.read(new File("images.jpg"));


			//Rectangle vieuxRectangle=new Rectangle(10,10);
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		setPreferredSize(new Dimension(1126, 796));
		//setBackground(Color.WHITE);
	}

	public void paintComponent(Graphics g) {
		/**g.setColor(Color.WHITE);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(Color.BLUE);
		g.fillRect(posX,posY,50,50);
		 **/
		
		try {

			/**Image img = ImageIO.read(new File("imagejeu.jpg"));
			Image img1 = ImageIO.read(new File("pionne.jpg"));
			**/
			lune = ImageIO.read(new File("pandemicMap (2).jpg"));
			terre = ImageIO.read(new File("infection.png"));
			fleche = ImageIO.read(new File("location-512.png"));
			ok = ImageIO.read(new File("ok.png"));
			card = ImageIO.read(new File("playercard.png"));
			pc = ImageIO.read(new File("images.jpg"));
			g.drawImage(lune, 0, 0, getWidth(), getHeight(), this);
			g.drawImage(terre, 712, 150, 38, 38, this); // 2.0
			g.drawImage(terre, 753, 150, 38, 38, this); // 2.1
			g.drawImage(terre, 794, 150, 38, 38, this); // 2.2
			g.drawImage(terre, 835, 150, 38, 38, this); // 3.0
			g.drawImage(terre, 876, 150, 38, 38, this); // 3.1
			g.drawImage(terre, 916, 150, 38, 38, this); // 4.0
			g.drawImage(terre, 956, 150, 38, 38, this); // 4.1
	//		g.drawImage(fleche, GameEngine.getPosx(), GameEngine.getPosY(), 40, 40, this); //testville mexico
			g.drawImage(fleche, 57, 320, 40, 40, this); //L.A
			g.drawImage(fleche, 132, 345, 40, 40, this); //MEXICO
			g.drawImage(fleche, 228, 330, 40, 40, this); //MIAMI
			g.drawImage(fleche, 220, 410, 40, 40, this); //BOGOTA
			g.drawImage(fleche, 203, 587, 40, 40, this); //SANTAGO
			g.drawImage(fleche, 194, 505, 40, 40, this); //LIMA
			g.drawImage(fleche, 290, 577, 40, 40, this); //BUENOS AIRES
			g.drawImage(fleche, 340, 512, 40, 40, this); //SAO PAULO
			g.drawImage(fleche, 550, 455, 40, 40, this); //KINSHASA
			g.drawImage(fleche, 500, 395, 40, 40, this); //LAGOS
			g.drawImage(fleche, 604, 385, 40, 40, this); //KHARTOUM
			g.drawImage(fleche, 599, 538, 40, 40, this); //JOHANNESBURG
			g.drawImage(fleche, 526, 282, 40, 40, this); //ALGIERS
			g.drawImage(fleche, 589, 302, 40, 40, this); //CAIRO
			g.drawImage(fleche, 667, 348, 40, 40, this); //RIYADH
			g.drawImage(fleche, 658, 270, 40, 40, this); //BAGHDAD
			g.drawImage(fleche, 598, 230, 40, 40, this); //ISTANBUL
			g.drawImage(fleche, 665, 190, 40, 40, this); //MOSCOW
			g.drawImage(fleche, 720, 220, 40, 40, this); //TEHERAN
			g.drawImage(fleche, 735, 300, 40, 40, this); //KARASHI
			g.drawImage(fleche, 745, 362, 40, 40, this); //MUMBAI
			g.drawImage(fleche, 810, 407, 40, 40, this); //CHENNAI
			g.drawImage(fleche, 798, 277, 40, 40, this); //DELHI
			g.drawImage(fleche, 855, 300, 40, 40, this); //KALKUTTA

			g.drawImage(fleche, 40, 220, 40, 40, this); //San francisco
			g.drawImage(fleche, 145, 190, 40, 40, this); //Chicago
			g.drawImage(fleche, 230, 190, 40, 40, this); //Montreal
			g.drawImage(fleche, 292, 200, 40, 40, this); //New York
			g.drawImage(fleche, 265, 245, 40, 40, this); //Washington
			g.drawImage(fleche, 176, 250, 40, 40, this); //Atlanta
			g.drawImage(fleche, 445, 150, 40, 40, this); //London
			g.drawImage(fleche, 433, 230, 40, 40, this); //Madrid
			g.drawImage(fleche, 507, 192, 40, 40, this); //Paris
			g.drawImage(fleche, 530, 137, 40, 40, this); //Essen
			g.drawImage(fleche, 561, 175, 40, 40, this); //Milan
			g.drawImage(fleche, 620, 123, 40, 40, this); //ST petersburg
			g.drawImage(fleche, 902, 195, 40, 40, this); //Beijing
			g.drawImage(fleche, 975, 192, 40, 40, this); //Seoul
			g.drawImage(fleche, 907, 255, 40, 40, this); //Shangai
			g.drawImage(fleche, 913, 319, 40, 40, this); //Hong kong
			g.drawImage(fleche, 917, 410, 40, 40, this); //Ho chi minh city
			g.drawImage(fleche, 866, 355, 40, 40, this); //Bankok
			g.drawImage(fleche, 866, 459, 40, 40, this); //JAKARTA
			g.drawImage(fleche, 999, 407, 40, 40, this); //Manila
			g.drawImage(fleche, 982, 310, 40, 40, this); //Taipei
			g.drawImage(fleche, 1040, 285, 40, 40, this); //OSAKA
			g.drawImage(fleche, 1034, 225, 40, 40, this); //Tokyo
			g.drawImage(fleche, 1047, 570, 40, 40, this); //Sydney
					
			
			
			









			Font font = new Font("Courier", Font.BOLD, 16);
			g.setFont(font);
			g.setColor(Color.WHITE);
			g.drawString("Vous êtes à San Francisco", 350, 50);
			g.drawString("Nombres de Cartes restants : ", 650, 580);

			g.drawImage(ok, 465, 755, 25, 25, this);
			g.drawImage(ok, 515, 755, 25, 25, this);
			g.drawImage(ok, 410, 755, 25, 25, this);
			g.drawImage(ok, 355, 755, 25, 25, this);
			g.drawImage(card, 663, 585, 140, 181, this);
			g.drawImage(card, 825, 585, 140, 181, this); //PLAYERCARD DEFAUSS
			g.drawImage(pc, 670, 17, 181, 130, this);
			g.drawImage(pc, 865, 17, 181, 130, this); //DEFAUSS PROPAGATION
			//g.drawImage(img, 0, 0, this.getWidth(),this.getHeight(),this);
			System.out.println("taille de la map "+this.getWidth()+" et "+this.getHeight());
			//img1=img1.getScaledInstance(30, 50, Image.SCALE_DEFAULT);
			posX=Math.round(GameEngine.getPosx());
			posY=Math.round(GameEngine.getPosY());
			if(posX<0)
				posX=-posX;
			if(posY<0)
				posY=-posY;
			
			System.out.println("posX = "+posX+" posY= "+posY);
			//g.drawImage(img1, posY-340, posX-380,this);

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

		this.setTitle("Ma premiere fenetre");
		this.setContentPane(pan);
		//setPreferredSize(new Dimension(1126, 796));
		this.setSize(1147,820);
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

		//go();
	}
	/*
	private void go(){

		//Les coordonn�es de d�part de notre rond

		int x = pan.getPosX(), y = pan.getPosY();

		//Le bool�en pour savoir si l'on recule ou non sur l'axe x

		boolean backX = false;

		//Le bool�en pour savoir si l'on recule ou non sur l'axe y

		boolean backY = false;


		//Dans cet exemple, j'utilise une boucle while

		//Vous verrez qu'elle fonctionne tr�s bien

		//while(true){

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

		//}

	}*/
}