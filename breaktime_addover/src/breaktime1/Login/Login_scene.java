package breaktime1.Login;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;


import breaktime1.mainFrame;
import breaktime1.GameObject.Clock;
import breaktime1.GameObject.Player;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Statement;


public class Login_scene extends JPanel implements Runnable {
	
	private BufferedImage image;
	private int w, h;
	private JTextField Id;
	private JPasswordField Pass;
	private Statement stmt;
	
	private mainFrame f;
	private JButton login;
	private JButton signup;
	private Clock myClock;

	private Login Log;
	private Player player;
	
	//폰트 설정
	Font font = new Font("arian", Font.BOLD, 20);
	
	public Login_scene(mainFrame F,Player plyr) {
		setSize(1200, 900);
		setLayout(null);
		
		f = F;
		stmt = f.getStmt();
		Log = new Login(stmt);
		myClock = new Clock();
		player = plyr;
	
		//
		// 백그라운드 이미지 띄우는 부분
		try {
			image  =  ImageIO.read(getClass().getResource("/breakt/login_bg.png"));
			w = image.getWidth();
			h = image.getHeight();

		} catch (IOException ioe) {
			System.out.println("Could not read in the pic");
			// System.exit(0);
		}
		// 이미지 띄우는 부분 종료

		Id = new JTextField(20) { // id/pass 20자 제한
			public void setBorder(Border border) // 경계 없애기
			{
			}
		};
		Id.setBounds(920, 37, 200, 30);
		// Id.setBorder(new LineBorder(Color.BLACK, 1));
		Id.setFont(font);
		this.add(Id);

		Pass = new JPasswordField(20) {
			public void setBorder(Border border) // 경계 없애기
			{
			}
		};
		Pass.setEchoChar('*');
		Pass.setBounds(920, 103, 200, 30);
		// Pass.setBackground(Color.YELLOW);
		Pass.setFont(font);
		this.add(Pass);
		//////////////////////////////////////////////

		Pass.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				isLoginCheck();
			}
		});
		//////////////////////////////////////////////

		login = new JButton(new ImageIcon(getClass().getResource("/breakt/login.png")));
		login.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isLoginCheck();
			}
		});
		login.setBounds(920, 150, 95, 48);
		add(login);

		signup = new JButton(new ImageIcon(getClass().getResource("/breakt/signup.png")));
		signup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Log.signup();
			}
		});
		signup.setBounds(1025, 150, 95, 48);
		add(signup);

		Thread th = new Thread(this);
		th.start();

		setVisible(true);
	}
	
    @Override
    public void run() {
        // TODO Auto-generated method stub
        while (true) {
            try {
                Thread.sleep(1000); // 1초마다
                repaint();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    

	public Dimension getPreferredSize() {
		return new Dimension(w, h);
	}

	public void paintComponent(Graphics g) {
		//System.out.println("이미지 불러오긴 함");
		
		g.drawImage(image, 0, 0, w, h, this);
		setOpaque(false);
		super.paintComponent(g);
		myClock.paintClock(g);	// 시계
 	
	}
    
	public void isLoginCheck() // 이후 서버단이랑 이어서 체킹~~~~~~~~~~~~~~~~
	{
		String id = Id.getText();
		char pa[] = Pass.getPassword();
		String password = new String(pa);
		
		
		//Log에서 성공 체킹하는 함수 부르기
		boolean islogin = Log.islogin(id, password);
				
		// 로그인 성공 시
        if(islogin)
        {
        	JOptionPane.showMessageDialog(null, "Success!");
        	player.setNickname(id); // player id  업데이트
        	System.out.print(player.getNickname());
        	f.initcard("waiting", player);        	
        	f.GotoPage("waiting");
        }                  
        else
        {
        //로그인 실패 시
        	JOptionPane.showMessageDialog(null, "Failed");
        }
	
	}
		 
	
}

