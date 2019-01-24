package breaktime1.Place;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.*;

import breaktime1.mainFrame;
import breaktime1.GameObject.Game;
import breaktime1.GameObject.Player;
public class ene_findUI extends JPanel
{
	Font font = new Font("sserife", Font.BOLD, 17);
	Font hudfont = new Font("sserife", Font.BOLD, 22);
	Font namefont = new Font("sserife", Font.BOLD, 27);
	
	JLabel usericon = new JLabel();
	JLabel info = new JLabel();
	JLabel enestand = new JLabel();
	JLabel enefind = new JLabel();
	JLabel sayst = new JLabel();
	
	JLabel2D username = new JLabel2D();
	JLabel kill = new JLabel();
	JLabel remain = new JLabel();
	JLabel say = new JLabel();
	JLabel2D enehp = new JLabel2D();
	JLabel2D wpname = new JLabel2D();
	
	JButton pauseb = new JButton();
	JButton fbagb = new JButton();
	JButton fightb = new JButton();
	JButton runb = new JButton();
	JButton okey = new JButton();
	
	private String enemy;
	private Game game;
	
	private mainFrame F;
	private Place_scene pp;
	private Statement stmt;
	private Player cur_pl;
	public ene_findUI(mainFrame f, Place_scene p) 
	{
		cur_pl = p.getCur_pl();
		stmt = f.getStmt();
		F = f;
		pp = p;
        setSize(1200, 900);
        setLayout(null);
        setOpaque( false );
        
        say = say();
        wpname = wp("무기이름");
        enehp = eneHP(100);
        
        enestand = enestand();
        runb = runb();
        usericon = usericon();
        fbagb = fbagb();
        fightb = fightb();
        info = info();
        enefind = enefind();
        sayst = sayst();
        pauseb = pauseb();
        kill = Kill(0); //킬수
        remain = Remain(5); //남은인원수

        
        //system에서 정보 받아와 표시
        username = Username("dbwjdldsdsdd");
        
        add(enehp);
        add(wpname);
        add(say);
        add(username);
        add(kill);
        add(remain);
        add(pauseb);
        add(sayst);
        add(enefind);
        add(enestand);
        add(runb);
        add(fightb);
        add(info);
        add(usericon);
        add(fbagb);
        
        setVisible(true);
	}

	public JLabel usericon()
	{
		BufferedImage us_image = null;
  		try {
			us_image = ImageIO.read(getClass().getResource("/breakt/UI/ene_prof.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	    int w = us_image.getWidth();
  	    int h = us_image.getHeight();
  		JLabel usericon = new JLabel(new ImageIcon (us_image));
		usericon.setSize(w, h);
		usericon.setBounds(0,0,w,h);
		
		return usericon;
	}
	public JLabel2D Username(String Username)
	{
		JLabel2D  UsernameHud = new JLabel2D ("\n"+Username);
		UsernameHud.setText(Username);
		UsernameHud.setFont(namefont);
		UsernameHud.setBounds(355,16,281,30);
		UsernameHud.setOutlineColor(Color.white);
		UsernameHud.setStroke(new BasicStroke(5f));

		return UsernameHud;
	}
	
	public JButton pauseb() 
	{
		BufferedImage image = null;
  		try {
			image = ImageIO.read(getClass().getResource("/breakt/UI/stop.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = image.getWidth();
		int h = image.getHeight();
		JButton button = new JButton(new ImageIcon(image));
		button.setSize(w, h);
		button.setBounds(1059,0,w,h);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
            	System.out.println("일시정지");
            }
            });
		
		return button;
	}
	public JButton runb()
	{
		BufferedImage image = null;
  		try {
			image = ImageIO.read(getClass().getResource("/breakt/UI/run_b.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = image.getWidth();
		int h = image.getHeight();
		JButton button = new JButton(new ImageIcon(image));
		button.setSize(w, h);
		button.setBounds(913, 590, w, h);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
            	System.out.println("튀어욧");
            	String qr = "update player set fight = 0 where nickname = '" + cur_pl.getNickname() + "'";
        		try {
        			int r = stmt.executeUpdate(qr);
        		} catch (SQLException e) {
        			// TODO Auto-generated catch block
        			e.printStackTrace();
        		}
        		cur_pl.setFight(0);
        		pp.initui("normal");
        		pp.uichange("normal");
            }
        });
		
		return button;
	}
	public JButton fightb()
	{
		BufferedImage image = null;
  		try {
			image = ImageIO.read(getClass().getResource("/breakt/UI/fight_b.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = image.getWidth();
		int h = image.getHeight();
		JButton button = new JButton(new ImageIcon(image));
		button.setSize(w, h);
		button.setBounds(1005, 694, w, h);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				game = pp.getGame();
				game.fight(enemy);
				pp.fight_flag(false);
				pp.initui("fight");
				pp.uichange("fight");
            	System.out.println("전투시작");
            }
            });
		
		return button;
	}
	public JButton fbagb()
	{
		BufferedImage image = null;
  		try {
			image = ImageIO.read(getClass().getResource("/breakt/UI/fbag_b.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	    int w = image.getWidth();
  	    int h = image.getHeight();
  	    JButton fbag = new JButton(new ImageIcon(image));
		fbag.setSize(w, h);
		fbag.setBounds(818,684,w,h);
		fbag.setBorderPainted(false);
		fbag.setContentAreaFilled(false);
		fbag.setFocusPainted(false);
		fbag.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) 
            {
            	System.out.println("전투시 가방");
            }
            });
		
		return fbag;
	}
	public JLabel info()
	{
		BufferedImage image = null;
  		try {
  			image = ImageIO.read(getClass().getResource("/breakt/UI/info.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	    int w = image.getWidth();
  	    int h = image.getHeight();
  		JLabel label = new JLabel(new ImageIcon (image));
  		label.setSize(w, h);
		label.setBounds(790,0,w,h);
		
		return label;
	}
	public JLabel enestand()
	{
		BufferedImage image = null;
  		try {
  			image = ImageIO.read(getClass().getResource("/breakt/UI/ene_stand.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	    int w = image.getWidth();
  	    int h = image.getHeight();
  		JLabel label = new JLabel(new ImageIcon (image));
  		label.setSize(w, h);
		label.setBounds(335,234,w,h);
		
		return label;
	}
	public JLabel enefind()
	{
		BufferedImage image = null;
  		try {
  			image = ImageIO.read(getClass().getResource("/breakt/UI/enefind.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	    int w = image.getWidth();
  	    int h = image.getHeight();
  		JLabel label = new JLabel(new ImageIcon (image));
  		label.setSize(w, h);
		label.setBounds(284,405,w,h);
		
		return label;
	}
	public JLabel sayst()
	{
		BufferedImage image = null;
  		try {
  			image = ImageIO.read(getClass().getResource("/breakt/UI/saysmt.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	    int w = image.getWidth();
  	    int h = image.getHeight();
  		JLabel label = new JLabel(new ImageIcon (image));
  		label.setSize(w, h);
		label.setBounds(748,139,w,h);
		
		return label;
	}
	public JLabel2D Kill(int killP)
	{
		String Killstr = Integer.toString(killP);
		JLabel2D  KillHud = new JLabel2D (Killstr);
	
		KillHud.setFont(hudfont);
		KillHud.setBounds(1000,4,280,30);
		KillHud.setOutlineColor(Color.white);
		KillHud.setStroke(new BasicStroke(3f));
	
		return KillHud;
	}
	public JLabel2D Remain(int RemainP)	
	{
		String Remainstr = Integer.toString(RemainP);
		JLabel2D  RemainHud = new JLabel2D (Remainstr);
	
		RemainHud.setFont(hudfont);
		RemainHud.setBounds(860,4,280,30);
		RemainHud.setOutlineColor(Color.white);
		RemainHud.setStroke(new BasicStroke(3f));
	
		return RemainHud;
	}
	public JLabel2D say()
	{
		JLabel2D  Say = new JLabel2D ("xs");
		Say.setText("\uBB54\uAC00\uB9D0\uD558\uB294\uACF3");
	
		
		Say.setFont(hudfont);
		Say.setBounds(790,161,280,30);
		Say.setOutlineColor(Color.white);
		Say.setStroke(new BasicStroke(3f));
	
		return Say;
	}
	public JLabel2D eneHP(int enehp)
	{
		String hp = Integer.toString(enehp);
		JLabel2D  Say = new JLabel2D ("HP : "+hp);
		
		Say.setFont(hudfont);
		Say.setBounds(311,135,111,30);
		Say.setOutlineColor(Color.white);
		Say.setStroke(new BasicStroke(3f));
	
		return Say;
	}
	public JLabel2D wp(String wpname)
	{
        JLabel2D label2D = new JLabel2D();
        label2D.setText("\uCC29\uC6A9\uBB34\uAE30 : "+wpname);
        label2D.setOutlineColor(Color.WHITE);
        label2D.setFont(new Font("Dialog", Font.BOLD, 22));
        label2D.setBounds(321, 63, 223, 46);
        
        return label2D;
	}

	public void init(String ene)
	{
		cur_pl = pp.getCur_pl();
		enemy = ene;
		username.setText(ene);
		String qr = "update player set fight = -1 where nickname = '" + cur_pl.getNickname() + "'";
		try {
			int r = stmt.executeUpdate(qr);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		cur_pl.setFight(-1);
	}
}