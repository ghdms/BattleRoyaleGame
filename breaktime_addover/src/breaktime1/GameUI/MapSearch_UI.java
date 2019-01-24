package breaktime1.GameUI;

import breaktime1.Gameflow.place;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;

import breaktime1.GameObject.Game;
import breaktime1.Place.Place_scene;

public class MapSearch_UI extends JPanel implements ActionListener {

   public static boolean isOpen = false;
   JButton b1, b2, b3, b4, b5, b6, b7, b8, b9, b10, b11, b12;
   String filename = null;
   private int w,h;
   private Place_scene pp;
   private int zone;
   private Game game;
   ImageIcon i;
   public int getzone()
   {
      return zone;
   }
   public MapSearch_UI(Place_scene p,Game game) {
      zone = game.getCur_pl().getZone_ID();
      setLayout(new GridLayout(3,3));   
      
      i = new ImageIcon(getClass().getResource("/breakt/X.png"));
      
      b1 = new JButton(new ImageIcon (getClass().getResource("/breakt/mapbutton/bg_classroom.png")));
      b1.addActionListener(this);
      
      b2 = new JButton(new ImageIcon (getClass().getResource("/breakt/mapbutton/bg_aisle.png")));
      b2.addActionListener(this);
      
      b3 = new JButton(new ImageIcon (getClass().getResource("/breakt/mapbutton/bg_restaurant.png")));
      b3.addActionListener(this);
      
      b4 = new JButton(new ImageIcon (getClass().getResource("/breakt/mapbutton/bg_art.png")));
      b4.addActionListener(this);
      
      b5 = new JButton(new ImageIcon (getClass().getResource("/breakt/mapbutton/bg_gym.png")));
      b5.addActionListener(this);
      
      b6 = new JButton(new ImageIcon (getClass().getResource("/breakt/mapbutton/bg_kitchen.png")));
      b6.addActionListener(this);
      
      b7 = new JButton(new ImageIcon (getClass().getResource("/breakt/mapbutton/bg_music.png")));
      b7.addActionListener(this);
      
      b8 = new JButton(new ImageIcon (getClass().getResource("/breakt/mapbutton/bg_science.png")));
      b8.addActionListener(this);
      
      b9 = new JButton(new ImageIcon (getClass().getResource("/breakt/mapbutton/bg_nurse.png")));
      b9.addActionListener(this);
      
      pp=p;
      this.game = game;
      
      add(b1);
      add(b2);
      add(b3);
      add(b4);
      add(b5);
      add(b6);
      add(b7);
      add(b8);
      add(b9);
      setSize(600,450);
      
      Thread t = new Thread(new next_check());
      t.setPriority(8);
      t.start();
   }
   public void actionPerformed(ActionEvent e) {
      Object obj = e.getSource();
      if ((JButton) obj == b1 && game.zone_flag(0)) {
         setVisible(false);
         pp.imagechange(place.classroom);
         zone = 0;
         
      }
      else if ((JButton) obj == b2 && game.zone_flag(1)) {
         setVisible(false);
         pp.imagechange(place.aisle);
         zone = 1;
         
      }
      else if ((JButton) obj == b3 && game.zone_flag(2)) {
         setVisible(false);
         pp.imagechange(place.restaurant);
         zone = 2;
         
      }
      else if ((JButton) obj == b4 && game.zone_flag(3)) {
         setVisible(false);
         pp.imagechange(place.art);
         zone = 3;
         
      }
      else if ((JButton) obj == b5 && game.zone_flag(4)) {
         setVisible(false);
         pp.imagechange(place.gym);
         zone = 4;
         
      }
      else if ((JButton) obj == b6 && game.zone_flag(5)) {
         setVisible(false);
         pp.imagechange(place.kitchen);
         zone = 5;
         
      }
      else if ((JButton) obj == b7 && game.zone_flag(6)) {
         setVisible(false);
         pp.imagechange(place.music);
         zone = 6;
         
      }
      else if ((JButton) obj == b8 && game.zone_flag(7)) {
         setVisible(false);
         pp.imagechange(place.science);
         zone = 7;
         
      }
      else if ((JButton) obj == b9 && game.zone_flag(8)) {
         setVisible(false);
         pp.imagechange(place.nurse);
         zone = 8;
         
      }
      MapSearch_UI.isOpen = false;
      this.game.setZone(zone);
   }
   
   public class next_check implements Runnable {
      public void run() {
         try {
            while (true) {
               Thread.sleep(4000);
               if(!game.zone_flag(0))
               {
                  b1.setIcon(i);
               }
               Thread.sleep(100);
               if(!game.zone_flag(1))
               {
                  b2.setIcon(i);
               }
               Thread.sleep(100);
               if(!game.zone_flag(2))
               {
                  b3.setIcon(i);
               }
               Thread.sleep(100);
               if(!game.zone_flag(3))
               {
                  b4.setIcon(i);
               }
               Thread.sleep(100);
               if(!game.zone_flag(4))
               {
                  b5.setIcon(i);
               }
               Thread.sleep(100);
               if(!game.zone_flag(5))
               {
                  b6.setIcon(i);
               }
               Thread.sleep(100);
               if(!game.zone_flag(6))
               {
                  b7.setIcon(i);
               }
               Thread.sleep(100);
               if(!game.zone_flag(7))
               {
                  b8.setIcon(i);
               }
               Thread.sleep(100);
               if(!game.zone_flag(8))
               {
                  b9.setIcon(i);
               }
               Thread.sleep(100);
            }
         } catch (Exception ex) {
            ex.printStackTrace();
         }
      }
   }
}