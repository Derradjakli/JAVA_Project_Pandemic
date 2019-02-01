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

import fr.dauphine.ja.pandemiage.common.Disease;
public class Panneau extends JPanel {
	//	private int posX=-50;
	//private int posY=-50;
	Image lune, terre,fleche,tetedemort,ok,card,pc,red,blue,black,yellow;

	Panneau() {
		try {
			lune = ImageIO.read(new File("pandemicMap (2).jpg"));
			terre = ImageIO.read(new File("infection.png"));
			fleche = ImageIO.read(new File("location-512.png"));
			ok = ImageIO.read(new File("ok.png"));
			card = ImageIO.read(new File("playercard.png"));
			pc = ImageIO.read(new File("images.jpg"));
			red= ImageIO.read(new File("red.png"));
			blue= ImageIO.read(new File("blue.png"));
			black= ImageIO.read(new File("black.png"));
			yellow= ImageIO.read(new File("yellow.png"));

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
			Player p=GameEngine.getPlayer();
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
			if(GameEngine.getCptprop()==0)
				g.drawImage(terre, 712, 142, 38, 38, this); // 2.0
			if(GameEngine.getCptprop()==1)
				g.drawImage(terre, 753, 142, 38, 38, this);// 2.1
			if(GameEngine.getCptprop()==2)
				g.drawImage(terre, 794, 142, 38, 38, this);
			if(GameEngine.getCptprop()==3)
				g.drawImage(terre, 835, 142, 38, 38, this);
			if(GameEngine.getCptprop()==4)
				g.drawImage(terre, 876, 142, 38, 38, this);

			if(GameEngine.getCptprop()==5)
				g.drawImage(terre, 916, 142, 38, 38, this);
			if(GameEngine.getCptprop()==6)
				g.drawImage(terre, 956, 142, 38, 38, this);
			/**switch(Fenetre.getRefresh()) {
				case 0: g.drawImage(terre, 712, 150, 38, 38, this); Fenetre.setRefresh(1); // 2.0

				case 1:g.drawImage(terre, 753, 150, 38, 38, this);Fenetre.setRefresh(1); // 2.1
				case 2:g.drawImage(terre, 794, 150, 38, 38, this);Fenetre.setRefresh(1); // 2.2
				}
			}
			if(GameEngine.getVit_prop()==3) {
				switch(Fenetre.getRefresh()) {
				case 3:g.drawImage(terre, 835, 150, 38, 38, this); Fenetre.setRefresh(1);// 3.0
				case 4: g.drawImage(terre, 876, 150, 38, 38, this);Fenetre.setRefresh(1); // 3.1
				}
			}
			if(GameEngine.getVit_prop()==4) {
				switch(Fenetre.getRefresh()) {

				case 5: g.drawImage(terre, 916, 150, 38, 38, this);Fenetre.setRefresh(1); // 4.0
				case 6 :g.drawImage(terre, 956, 150, 38, 38, this);Fenetre.setRefresh(1); // 4.1
				}
			}**/
			g.drawImage(fleche, GameEngine.getPosX(), GameEngine.getPosY(), 40, 40, this); //testville mexico


			for(City c:GameEngine.getListCity()) {
				//for(Disease d :Disease.values()) {
				//for(int j=0;j<c.getNbCubes(d);j++) {
				if(c.getNbCubes(Disease.RED)>0){
					g.drawImage(red, c.getPosX(), c.getPosY(), 10, 10, this); 
				}
				if(c.getNbCubes(Disease.BLUE)>0){
					g.drawImage(blue, c.getPosX()+2, c.getPosY(), 5, 5, this); 
				}
				if(c.getNbCubes(Disease.YELLOW)>0){
					if(c.getDisease().equals(Disease.RED)){
						g.drawImage(red, c.getPosX()+30, c.getPosY()+5, 5, 5, this); 
					}
					if(c.getDisease().equals(Disease.YELLOW)){
					
						g.drawImage(yellow, c.getPosX(), c.getPosY()+2, 5, 5, this); 
					
				}
				if(c.getNbCubes(Disease.BLACK)>0){
					g.drawImage(black, c.getPosX(), c.getPosY()-2, 5, 5, this); 
				}
			}
			}




			/**g.drawImage(fleche, 57, 320, 40, 40, this); //L.A
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


			 **/



			//for(Disease d:Disease.values()){
			if(GameEngine.getRemedes(Disease.RED)){
				//g.drawImage(ok, 465, 755, 25, 25, this);
				g.drawImage(ok, 410, 719, 25, 25, this);
				//g.drawImage(ok, 515, 755, 25, 25, this);
			}
			if(GameEngine.getRemedes(Disease.YELLOW)){
				g.drawImage(ok, 355, 719, 25, 25, this);
			}
			if(GameEngine.getRemedes(Disease.BLACK)){
				g.drawImage(ok, 515, 719, 25, 25, this);
			}
			if(GameEngine.getRemedes(Disease.BLUE)){
				g.drawImage(ok, 465, 719, 25, 25, this);
			}
			//g.drawImage(ok, 410, 755, 25, 25, this);
			//g.drawImage(ok, 355, 755, 25, 25, this);









			Font font = new Font("Courier", Font.BOLD, 16);
			g.setFont(font);
			g.setColor(Color.RED);

			g.drawString("Vous êtes à "+p.getCurrentCity().getName(), 350, 50);
			//g.drawString("Nombres de Cartes restants : ", 400, 650);
			g.drawString("Nombres de Cartes en main: "+p.playerHand().size(), 650, 540);


			//g.drawImage(ok, 465, 755, 25, 25, this);
			//g.drawImage(ok, 515, 755, 25, 25, this);
			//g.drawImage(ok, 410, 755, 25, 25, this);
			//g.drawImage(ok, 355, 755, 25, 25, this);
			g.drawImage(card, 663, 565, 140, 181, this);
			g.drawImage(card, 825, 565, 140, 181, this); //PLAYERCARD DEFAUSS
			g.drawImage(pc, 670, 14, 181, 130, this);
			g.drawImage(pc, 865, 14, 181, 130, this); //DEFAUSS PROPAGATION
			//g.drawImage(img, 0, 0, this.getWidth(),this.getHeight(),this);
			System.out.println("taille de la map "+this.getWidth()+" et "+this.getHeight());
			//img1=img1.getScaledInstance(30, 50, Image.SCALE_DEFAULT);
			/*posX=Math.round(GameEngine.getPosx());
			posY=Math.round(GameEngine.getPosY());
			if(posX<0)
				posX=-posX;
			if(posY<0)
				posY=-posY;

			System.out.println("posX = "+posX+" posY= "+posY);
			 */
			//g.drawImage(img1, posY-340, posX-380,this);

			//Pour une image de fond

			//g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);

		} catch (IOException e) {

			e.printStackTrace();

		}      



	}




}
class Fenetre extends JFrame{
	private Panneau pan=new Panneau();
	private static int refresh=0;// Number of refrech of the window
	public Fenetre() {

		this.setTitle("Pandemic Game");
		this.setContentPane(pan);
		//setPreferredSize(new Dimension(1126, 796));
		this.setSize(1147,820);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);


		this.setVisible(true);


	}
	public static int getRefresh(){
		return refresh;
	}
	public static void setRefresh(int i) {
		refresh+=i;
	}

}