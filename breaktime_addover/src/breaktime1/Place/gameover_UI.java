package breaktime1.Place;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.imageio.ImageIO;
import javax.swing.*;

import breaktime1.mainFrame;
import breaktime1.GameObject.Player;

public class gameover_UI extends JPanel
{
	Font font = new Font("sserife", Font.BOLD+Font.ITALIC, 50);
	
	Statement stmt;
	JLabel winover;
	JLabel gover;
	JLabel pname;
	JLabel best;
	JLabel lanks;
	JButton exitb;
	Player pl;
	mainFrame fr;
	Place_scene place;
	
	public gameover_UI(mainFrame f,Place_scene p) 
	{	
		setLayout(null);
        setSize(1200, 900);
        this.setOpaque( false );
        
        //
        fr = f;
        place = p;
        pl = p.getCur_pl();
        stmt = f.getStmt();
        //
        pname = pname("NAME");
        best = best(50);
        lanks = lanks("3");
        gover = gover();
        exitb = exitb();
        winover = winover();	
        
        add(pname);
        add(lanks);
        add(best);
        add(exitb);
		add(gover);
		gover.setVisible(false);
		add(winover);
		winover.setVisible(false);
		
		setVisible(true);
		
	}
	
	public void init(boolean win)
	{
		int rmnum = 0;
		try {
			String qr = "select pl_num from Game where id =" +pl.getGame_ID();
			ResultSet rs = stmt.executeQuery(qr);
			while(rs.next())
	               rmnum = rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print(rmnum);
		System.out.print(rmnum);
		String rm = Integer.toString(rmnum);
		pname.setText(pl.getNickname());
		lanks.setText(rm);

		best = best(pl.getShake());
		if(win)
		{
			gover.setVisible(false);
			winover.setVisible(true);
		}
		else
		{
			winover.setVisible(false);
			gover.setVisible(true);
		}
		
		this.revalidate();
		this.repaint();
	}
	public void resetgame()
	{				
		// 플레이어 초기화
		// 게임 초기화 ->각 씬 초기화, DB초기화
		// waiting으로 돌아가기
		// 안해 끈다
		
		if (pl != null) {
			try {
				String qr1 = "delete from player where nickname = '" + pl.getNickname() + "'";
				int r1 = stmt.executeUpdate(qr1);

				String qr2 = "update Game set pl_num = pl_num - 1 where id = " + pl.getGame_ID();
				int r2 = stmt.executeUpdate(qr2);

				String qr3 = "delete from fight where pl_name = '" + pl.getNickname() + "'";
				int r3 = stmt.executeUpdate(qr3);

				System.out.println(r1 + " " + r2 + " " + r3);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.exit(100);
	}
	
	///////////////////////컴포넌트//////////////////
	public JLabel winover()
	{
		BufferedImage image = null;
  		try {
  			image = ImageIO.read(new File("C:\\breakt\\UI\\overwin.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	    int w = image.getWidth();
  	    int h = image.getHeight();
  		JLabel Label = new JLabel(new ImageIcon (image));
		Label.setSize(w, h);
		Label.setBounds(0,-35,w,h);
		
		return Label;
	}
	public JLabel gover()
	{
		BufferedImage image = null;
  		try {
  			image = ImageIO.read(new File("C:\\breakt\\UI\\gameover.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	    int w = image.getWidth();
  	    int h = image.getHeight();
  		JLabel Label = new JLabel(new ImageIcon (image));
		Label.setSize(w, h);
		Label.setBounds(0,-35,w,h);
		
		return Label;
	}
	public JLabel pname(String name)
	{
		JLabel2D nowhp = new JLabel2D (name);

		nowhp.setFont(font);
		nowhp.setBounds(519,271,365,77);
		nowhp.setForeground(Color.WHITE);
		
		return nowhp;
	}
	public JLabel best(int best)
	{
		String bestsc = Integer.toString(best);
		JLabel2D nowhp = new JLabel2D (bestsc);

		nowhp.setFont(font);
		nowhp.setBounds(462,514,186,67);
		nowhp.setForeground(Color.WHITE);
		
		return nowhp;
	}
	public JLabel lanks(String s)
	{
		JLabel2D nowhp = new JLabel2D (s);

		nowhp.setFont(font);
		nowhp.setBounds(496,397,188,59);
		nowhp.setForeground(Color.WHITE);
		
		return nowhp;
	}
	public JButton exitb() 
	{
		BufferedImage image = null;
  		try {
			image = ImageIO.read(new File("C:\\breakt\\UI\\exit.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int w = image.getWidth();
		int h = image.getHeight();
		JButton button = new JButton(new ImageIcon(image));
		button.setSize(w, h);
		button.setBounds(819,668,w,h);
		button.setBorderPainted(false);
		button.setContentAreaFilled(false);
		button.setFocusPainted(false);
		button.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent arg0) 
			{
				resetgame();
				System.out.println("탈주!");
            }
            });
		
		return button;
	}

}