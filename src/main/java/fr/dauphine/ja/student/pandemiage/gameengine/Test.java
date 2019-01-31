package fr.dauphine.ja.student.pandemiage.gameengine;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
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

import fr.dauphine.ja.student.pandemiage.gameengine.Player;

class IHMImages extends JPanel {
	Image lune, terre,fleche,tetedemort,ok,card,pc;
	//boolean draw;
	
	IHMImages() {
		try {
			lune = ImageIO.read(new File("C:/Users/SK/Downloads/pandemicMap (2).jpg"));
			terre = ImageIO.read(new File("C:/Users/SK/Desktop/infection.png"));
			fleche = ImageIO.read(new File("C:/Users/SK/Desktop/location-512.png"));
			ok = ImageIO.read(new File("C:/Users/SK/Desktop/ok.png"));
			card = ImageIO.read(new File("C:/Users/SK/Desktop/playercard.png"));
			pc = ImageIO.read(new File("C:/Users/SK/Desktop/images.jpg"));
			tetedemort=ImageIO.read(new File("C:/Users/SK/Desktop/rcross.jpg"));

			//Rectangle vieuxRectangle=new Rectangle(10,10);
		}
		catch(IOException e) {
			e.printStackTrace();
		}

		setPreferredSize(new Dimension(1126, 796));
		//setBackground(Color.WHITE);
	}

	public void paintComponent(Graphics g) {
		//super.paintComponent(g);
		//if (!draw){
		g.drawImage(lune, 0, 0, getWidth(), getHeight(), this);
		g.drawImage(terre, 712, 150, 38, 38, this); // 2.0
		g.drawImage(terre, 753, 150, 38, 38, this); // 2.1
		g.drawImage(terre, 794, 150, 38, 38, this); // 2.2
		g.drawImage(terre, 835, 150, 38, 38, this); // 3.0
		g.drawImage(terre, 876, 150, 38, 38, this); // 3.1
		g.drawImage(terre, 916, 150, 38, 38, this); // 4.0
		g.drawImage(terre, 956, 150, 38, 38, this); // 4.1
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
		g.drawImage(terre,50, 700, 40, 40, this); //CROSSRED

		/*}



		Timer timer = new Timer(5, new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		        draw = false;
		        repaint();
		    }
		});

*/



		Font font = new Font("Courier", Font.BOLD, 16);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString("Vous etes a San Francisco", 350, 50);
		g.drawString("Nombres de Cartes restants : ", 650, 580);

		g.drawImage(ok, 465, 755, 25, 25, this);
		g.drawImage(ok, 515, 755, 25, 25, this);
		g.drawImage(ok, 410, 755, 25, 25, this);
		g.drawImage(ok, 355, 755, 25, 25, this);
		g.drawImage(card, 663, 585, 140, 181, this);
		g.drawImage(card, 825, 585, 140, 181, this); //PLAYERCARD DEFAUSS
		g.drawImage(pc, 670, 17, 181, 130, this);
		g.drawImage(pc, 865, 17, 181, 130, this); //DEFAUSS PROPAGATION
		
	}
}

class igm extends JPanel {
	public igm(){
		setPreferredSize(new Dimension(400, 400));
	}
	public void paintComponent(Graphics g) {
		g.drawString("- NOMBRE DE CUBES JAUNES :", 50, 50);
		g.drawString("- NOMBRE DE CUBES ROUGES :", 50, 70);
		g.drawString("- NOMBRE DE CUBES VERTS :", 50, 90);
		g.drawString("- NOMBRE DE CUBES NOIRS :", 50, 110);

	}

	public void NewFenetre(){
		JFrame fenetre = new JFrame();
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.setTitle("City Informations");
		fenetre.setVisible(true);
		fenetre.setLocation(500, 500);
	}
}




public class Test { 
	public static void main(String[] arg) {
		JFrame fenetre = new JFrame();
		fenetre.setContentPane(new IHMImages());
		fenetre.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		fenetre.pack();
		fenetre.setTitle("Pandemic Game");
		fenetre.setLocation(500, 100);
		fenetre.setVisible(true);
	}
}