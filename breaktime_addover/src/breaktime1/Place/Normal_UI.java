package breaktime1.Place;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.*;
//import javax.swing.border.*;

import breaktime1.mainFrame;
import breaktime1.DB.DataUpdateRunnable;
import breaktime1.GameObject.Fight;
import breaktime1.GameObject.Game;
import breaktime1.GameObject.Player;
import breaktime1.GameObject.bag_p;
import breaktime1.GameUI.Inventory;
import breaktime1.GameUI.MapSearch_UI;
import breaktime1.GameUI.ThreadClock;

public class Normal_UI extends JPanel
// 기본 ui 만들 때 쓰는 클래스
/*
 * 들어가야 할 요소 : 버튼 : 지도 가방 탐색 일시정지 유저정보 : 아이콘 이름 hp st / 현재인원 / 킬수 notice와 가방은 다른
 * 클래스에 구현
 */
{
	Font font = new Font("sserife", Font.BOLD, 17);
	Font hudfont = new Font("sserife", Font.BOLD, 22);
	Font namefont = new Font("sserife", Font.BOLD, 27);

	JButton mapb = new JButton(new ImageIcon("/breakt/UI/map.png"));
	JButton bagb = new JButton(new ImageIcon("/breakt/UI/bag.png"));
	JButton searchb = new JButton(new ImageIcon("/breakt/UI/search.png"));
	JButton pauseb = new JButton(new ImageIcon("/breakt/UI/stop.png"));

	JLabel usericon = new JLabel();
	JLabel info = new JLabel();
	JLabel username = new JLabel();

	JLabel kill = new JLabel();
	JLabel remain = new JLabel();

	JPanel HPbar = new JPanel();
	JLabel HP_label = new JLabel();

	JPanel STbar = new JPanel();
	JLabel ST_label = new JLabel();
	
	private int myd = -1;
	
	// 버튼 구현부
	int w, h;

	private Game game;
	private Thread t1;
	private Thread t2;
	private Thread t3;
	private Thread t4;
	private Thread hudup;
	private mainFrame f;
	private Player cur_pl;
	private MapSearch_UI map_search;
	private Fight ff;
	private Thread fight;
	private Thread t5;
	private DataUpdateRunnable DR;
	private Statement stmt;
	private JLabel l;
	private ImageIcon joro;
	private JPanel myHP;
	private Place_scene place;
	private boolean fighted = false;
	private int destroy = -1;
	private JLabel deszone;
	public Normal_UI(mainFrame f, Player player, Place_scene place) {
		add(new ThreadClock());

		this.place = place;
		stmt = f.getStmt();
		setLayout(null);
		setSize(1200, 900);

		this.f = f;
		cur_pl = player;
		DR = f.getDR();
		game = place.getGame();

		joro = new ImageIcon("/breakt/joro.png"); // 조로 그림 가져오기 -> 저한테 이거 없어서 잠깐 딴거 썼어요!
		l = new JLabel(joro);
		l.setBounds(200, 150, 500, 400);
		add(l);
		l.setVisible(false);

		this.setOpaque(false);

		this.setComponentZOrder(HP_label, 0);
		this.setComponentZOrder(HPbar, 1);

		// 라벨 초기화
		kill = Kill(0);
		remain = Remain("3");
		STbar = ST(100);
		username = Username("초깃값");
		HPbar = HP(100);
		
		deszone = new JLabel();
		deszone.setBounds(10, 700, 420, 70);
		deszone.setForeground(Color.BLUE);
		deszone.setFont(getFont().deriveFont(25.0f));
		deszone.setVisible(true);

		add(kill);
		add(remain);
		add(HPbar);
		add(HP_label);
		add(username);
		add(STbar);
		add(mapbt(f, place));
		add(bagb());
		add(searchb());
		add(pauseb());
		add(usericon());
		add(info());
		add(deszone);
		


		ff = new Fight(f.getStmt(), cur_pl);
		fight = new Thread(ff);
		fight.start();

		setVisible(true);

	}

	public void setflag(boolean tf) {
		ff.setFlag(tf);
	}

	public void start() {
		// 유저이름, 체력, 스태미너 (변하는함수 미구현), 킬수
		username.setText(cur_pl.getNickname());
		HP_change(cur_pl.getHp());
		
		map_search = new MapSearch_UI(place, game);
		map_search.setBounds(200, 150, 600, 450);		

	      t3 = new Thread(new zone_destroy());
	      t3.setPriority(9);
	      t3.start();
	      
	      destroy = -1;
	      t5 = new Thread(new my_destroy());
	      t5.setPriority(8);
	      t5.start();
	      
	      t4 = new Thread(new next_check());
	      t4.setPriority(7);
	      t4.start();
	      
	      t1 = new Thread(new fight_check());
	      t1.setPriority(6);
	      t1.start();

	      t2 = new Thread(new last_check());
	      t2.setPriority(2);
	      t2.start();
	      
	      hudup = new Thread(new hudupdate());
	      hudup.setPriority(1);
	      hudup.start();
		// 스태랑 킬수 넣어야함
	}
	public void init() 
	{
		System.out.print("노말 초기화아악");
		username.setText(cur_pl.getNickname());
		HP_change(cur_pl.getHp());
		DR.after_fight(cur_pl);
		setflag(true);
	}

	public class hudupdate implements Runnable
	{
		public void run() 
		{
			while (true) {
				int rmnum = 0;
				try {
					String qr = "select pl_num from Game where id =" + cur_pl.getGame_ID();
					ResultSet rs = stmt.executeQuery(qr);
					while (rs.next())
						rmnum = rs.getInt(1);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				try {
					Thread.sleep(2530);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				String rm = Integer.toString(rmnum);
				remain.setText(rm);
				pan().setComponentZOrder(remain, 3);
				pan().revalidate();
				pan().repaint();
			}
		}
	}
	
	public Normal_UI pan()
	{
		return this;
	}
	// 별개로 스레드 하나 돌려야겠네요 HUD(남은사람수/알림창<-되면) 업뎃할거

	/////////////////////////////// 버튼들 ///////////////////////////////////
	public JButton mapbt(mainFrame f, Place_scene p) {
		mapb.setBorderPainted(false);
		mapb.setContentAreaFilled(false);
		mapb.setFocusPainted(false);
		mapb.setBounds(910, 560, 188, 128);

		mapb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				add(map_search);

				if (MapSearch_UI.isOpen == false) {
					map_search.setVisible(true);
					MapSearch_UI.isOpen = true;
				} else {
					map_search.setVisible(false);
					MapSearch_UI.isOpen = false;
				}

				// game.setZone(map_search.getzone());

				f.revalidate();
				f.repaint();

				// map_search.isOpen = false;

			}
		});

		return mapb;
	}

	public JButton bagb() {
		bagb.setBorderPainted(false);
		bagb.setContentAreaFilled(false);
		bagb.setFocusPainted(false);
		bagb.setBounds(810, 690, 221, 126);
		bagb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				bag_p Mybag = game.getMybag();
				add(Mybag);

				if (Inventory.isOpen == false) {
					Mybag.setVisible(true);
					Inventory.isOpen = true;
				} else {
					Mybag.setVisible(false);
					Inventory.isOpen = false;
				}
				System.out.println("백백백");

				f.revalidate();
				f.repaint();

			}
		});

		return bagb;
	}

	public JButton searchb() {
		searchb.setBorderPainted(false);
		searchb.setContentAreaFilled(false);
		searchb.setFocusPainted(false);
		searchb.setBounds(983, 660, 199, 162);
		searchb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int result = game.search();
				if (result == 0) {
					l.setVisible(true);
					Timer m_timer = new Timer();
					TimerTask m_task = new TimerTask() {
						public void run() {
							l.setVisible(false);
						}
					};
					m_timer.schedule(m_task, 1000);
				} else if (result == -1) {
					place.ene_find_init(cur_pl.getEnemy());
					place.uichange("ene_find");
				} else if (result == 999) {
					// 가득 차 있는 상태
				} else if (result == -999) {
					// 그 지역에 아이템 아무것도 없음.
				}
			}

		});
		return searchb;
	}

	public JButton pauseb() {
		pauseb.setBorderPainted(false);
		pauseb.setContentAreaFilled(false);
		pauseb.setFocusPainted(false);
		pauseb.setBounds(1059, 0, 93, 46);
		pauseb.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("일시정지");

				// f.GotoPage("Two"); // 일단은 뒤로 가기

			}
		});

		return pauseb;

	}

	///////////////////////////////// 그냥 레이블/////////////////////////////////
	// 사용자정보
	public JLabel usericon() {
		BufferedImage us_image = null;
		try {
			us_image = ImageIO.read(getClass().getResource("/breakt/UI/user.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		w = us_image.getWidth();
		h = us_image.getHeight();
		JLabel usericon = new JLabel(new ImageIcon(us_image));
		usericon.setSize(w, h);
		usericon.setBounds(0, 0, w, h);

		return usericon;
	}

	public void UpdateHP() {

	}

	///////////////////////////////////////////////// HUD////////////////////////////////////////////////////////

	public JLabel setHP_label(int hp_point) {
		String hp_string = Integer.toString(hp_point);
		JLabel2D nowhp = new JLabel2D(hp_string);

		nowhp.setFont(font);
		nowhp.setBounds(106 + 355, 59 - 2, 60, 20);
		nowhp.setOutlineColor(Color.white);
		nowhp.setStroke(new BasicStroke(3f));

		return nowhp;
	}

	public void HP_change(int hp_change) {
		System.out.print("체력바꿔달라고" +hp_change);
		this.remove(HPbar);
		this.remove(HP_label);

		HPbar = HP(hp_change);
		HP_label = setHP_label(hp_change);
		this.add(HPbar);
		this.add(HP_label);

		this.setComponentZOrder(HP_label, 0);
		this.setComponentZOrder(HPbar, 1);
		this.revalidate();
		this.repaint();

	}

	public JPanel HP(int hp_point) {
		int pos_w = 226;
		int pos_h = 59;

		// 패널 설정
		JPanel hp_panel = new JPanel();
		hp_panel.setLayout(null);
		hp_panel.setSize(1200, 900);
		hp_panel.setOpaque(false);

		// 라벨
		HP_label = setHP_label(hp_point);

		// 베이스 설정
		BufferedImage hpbase_image = null;
		try {
			hpbase_image = ImageIO.read(getClass().getResource("/breakt/UI/hp_base.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		w = hpbase_image.getWidth();
		h = hpbase_image.getHeight();
		JLabel HP_base = new JLabel(new ImageIcon(hpbase_image));
		HP_base.setSize(w, h);
		HP_base.setBounds(pos_w, pos_h, w, h);
		hp_panel.add(HP_base);

		// 바 설정

		BufferedImage hpbar_image = null;
		try {
			hpbar_image = ImageIO.read(getClass().getResource("/breakt/UI/hp.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int bar_w = hpbar_image.getWidth();
		int bar_h = hpbar_image.getHeight();
		JLabel HP_bar = new JLabel(new ImageIcon(hpbar_image));
		HP_bar.setSize(bar_w, bar_h);

		int length = (int) (bar_w * (hp_point / 100.));

		HP_bar.setBounds(pos_w + 8, pos_h, length, h);
		hp_panel.add(HP_bar);

		// 탑 설정
		BufferedImage hptop_image = null;
		try {
			hptop_image = ImageIO.read(getClass().getResource("/breakt/UI/hp_top.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int top_w = hptop_image.getWidth();
		int top_h = hptop_image.getHeight();
		JLabel HP_top = new JLabel(new ImageIcon(hptop_image));
		HP_top.setSize(top_w, top_h);
		HP_top.setBounds(pos_w + length + 8, pos_h, top_w, top_h);
		hp_panel.add(HP_top);

		hp_panel.setVisible(true);
		return hp_panel;
	}

	public JPanel ST(int st_point) {
		int pos_w = 99;
		int pos_h = 79;
		// 패널 설정
		JPanel st_panel = new JPanel();
		st_panel.setLayout(null);
		st_panel.setSize(1200, 900);
		st_panel.setOpaque(false);

		// 라벨 설정
		String st_string = Integer.toString(st_point);
		JLabel2D nowst = new JLabel2D(st_string);

		nowst.setFont(font);
		nowst.setBounds(pos_w + 348, pos_h - 2, 60, 20);
		nowst.setOutlineColor(Color.white);
		nowst.setStroke(new BasicStroke(3f));

		add(nowst);

		// 베이스 설정
		BufferedImage stbase_image = null;
		try {
			stbase_image = ImageIO.read(getClass().getResource("/breakt/UI/st_base.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		w = stbase_image.getWidth();
		h = stbase_image.getHeight();
		JLabel ST_base = new JLabel(new ImageIcon(stbase_image));
		ST_base.setSize(w, h);
		ST_base.setBounds(pos_w, pos_h, w, h);
		st_panel.add(ST_base);

		// 바 설정

		BufferedImage stbar_image = null;
		try {
			stbar_image = ImageIO.read(getClass().getResource("/breakt/UI/st.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int bar_w = stbar_image.getWidth();
		int bar_h = stbar_image.getHeight();
		JLabel ST_bar = new JLabel(new ImageIcon(stbar_image));
		ST_base.setSize(bar_w, bar_h);

		int length = (int) (bar_w * (st_point / 100.));
		System.out.println("원래길이 : " + bar_w);
		System.out.println("현재ST길이 : " + length);
		ST_bar.setBounds(pos_w + 125, pos_h, length, h);
		st_panel.add(ST_bar);

		// 탑 설정
		BufferedImage sttop_image = null;
		try {
			sttop_image = ImageIO.read(getClass().getResource("/breakt/UI/st_top.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int top_w = sttop_image.getWidth();
		int top_h = sttop_image.getHeight();
		JLabel ST_top = new JLabel(new ImageIcon(sttop_image));
		ST_top.setSize(top_w, top_h);
		ST_top.setBounds(pos_w + length + 125, pos_h, top_w, top_h);
		st_panel.add(ST_top);

		st_panel.setVisible(true);
		return st_panel;
	}

	public JLabel2D Username(String Username) {
		JLabel2D UsernameHud = new JLabel2D(Username);

		UsernameHud.setFont(namefont);
		UsernameHud.setBounds(280, 13, 280, 30);
		UsernameHud.setOutlineColor(Color.white);
		UsernameHud.setStroke(new BasicStroke(5f));

		return UsernameHud;
	}

	public JLabel2D Remain(String rm) {
		JLabel2D RemainHud = new JLabel2D(rm);

		RemainHud.setFont(hudfont);
		RemainHud.setBounds(860, 4, 280, 30);
		RemainHud.setOutlineColor(Color.white);
		RemainHud.setStroke(new BasicStroke(3f));

		return RemainHud;
	}

	public JLabel info() {
		BufferedImage info_image = null;
		try {
			info_image = ImageIO.read(getClass().getResource("/breakt/UI/info.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		w = info_image.getWidth();
		h = info_image.getHeight();
		JLabel info = new JLabel(new ImageIcon(info_image));
		info.setSize(w, h);
		info.setBounds(790, 0, w, h);

		return info;
	}

	public JLabel2D Kill(int killP) {
		String Killstr = Integer.toString(killP);
		JLabel2D KillHud = new JLabel2D(Killstr);

		KillHud.setFont(hudfont);
		KillHud.setBounds(1000, 4, 280, 30);
		KillHud.setOutlineColor(Color.white);
		KillHud.setStroke(new BasicStroke(3f));

		return KillHud;
	}
	public class fight_check implements Runnable {
	      public void run() {
	         try {
	            while (true) {
	               if (!ff.isFlag()) {
	                  System.out.print("노말의 그것");
	                  place.initui("fight");
	                  place.uichange("fight");
	               }
	               while (!ff.isFlag()) {
	                  Thread.sleep(2500);
	                  if (ff.isFlag())
	                     break;
	               }
	               Thread.sleep(1000);
	            }
	         } catch (Exception ex) {
	            ex.printStackTrace();
	         }
	      }
	   }

	   public class last_check implements Runnable {
	      public void run() {
	         try {
	            Class.forName("com.mysql.jdbc.Driver");
	            Connection con = DriverManager.getConnection("jdbc:mysql://1.201.141.109:3306/breaktime?useServerPrepStmts=false&rewriteBatchedStatements=true","root", "chamel186");
	            Statement s1 = con.createStatement();
	            while (true) {
	               Thread.sleep(17371);
	               String qr = "select pl_num from Game where id = " + cur_pl.getGame_ID();
	               ResultSet rs = s1.executeQuery(qr);
	               int id = 0;
	               while (rs.next()) {
	                  id = rs.getInt(1);
	               }
	               if (id == 1) {
	                  // 마무리 화면
	                  place.initui("win");
	                  place.uichange("gameover");
	                  break;
	               }
	            }
	         } catch (Exception ex) {
	            ex.printStackTrace();
	         }
	      }
	   }

	   public class my_destroy implements Runnable {
	      public void run() {
	         try {
	            while (true) {
	               Thread.sleep(1001);
	               if (cur_pl.getZone_ID() == myd) {
	                  JOptionPane.showMessageDialog(null, "지역과 함께 사라지셨습니다.");

	                  place.initui("gameover");
	                  place.uichange("gameover");

	               }
	            }
	         } catch (Exception e) {
	            e.printStackTrace();
	         }
	      }
	   }
	   
	   public class zone_destroy implements Runnable {
	      public void run() {
	         try {
	            Class.forName("com.mysql.jdbc.Driver");
	            Connection con = DriverManager.getConnection("jdbc:mysql://1.201.141.109:3306/breaktime?useServerPrepStmts=false&rewriteBatchedStatements=true","root", "chamel186");
	            Statement s0 = con.createStatement();
	            Statement s2 = con.createStatement();
	            Statement s11 = con.createStatement();
	            Statement ss = con.createStatement();
	            Statement s3 = con.createStatement();
	            while (true) {
	               Thread.sleep(30997);
	               boolean check = false;
	               String me = "select nickname from player where time = (select max(time) from player where room = "
	                     + cur_pl.getGame_ID() + ")";
	               ResultSet rs2 = s0.executeQuery(me);
	               String tid = new String();
	               while (rs2.next()) {
	                  tid = rs2.getString(1);
	               }
	               if (cur_pl.getNickname().equals(tid)) {
	                  check = true;
	               }
	               if (check) {
	                  ArrayList<Integer> id = new ArrayList<Integer>();
	                  ArrayList<String> name = new ArrayList<String>();
	                  String qr = "select id, name from zone" + cur_pl.getGame_ID() + " where flag = 1 and id <> 9";
	                  ResultSet rs = s2.executeQuery(qr);
	                  while (rs.next()) {
	                     id.add(rs.getInt(1));
	                     name.add(rs.getString(2));
	                  }
	                  
	                  Thread.sleep(29001);
	                  
	                  Random r = new Random(System.currentTimeMillis());
	                  destroy = r.nextInt(id.size());
	                  String up = "update zone" + cur_pl.getGame_ID() + " set next = 1 where id = " + id.get(destroy);
	                  int rs10 = s11.executeUpdate(up);
	                  
	                  Thread.sleep(28952);
	                  
	                  String qr13 = "update zone" + cur_pl.getGame_ID() + " set next = 0";
	                  int rs101 = ss.executeUpdate(qr13);
	                  
	                  String qr2 = "update zone" + cur_pl.getGame_ID() + " set flag = 0 where id = "
	                        + id.get(destroy);
	                  int rs100 = s3.executeUpdate(qr2);
	                  
	                  myd = id.get(destroy);
	               }
	            }
	         } catch (Exception ex) {
	            ex.printStackTrace();
	         }
	      }
	   }

	   public class next_check implements Runnable {
	      public void run() {
	         try {
	            Class.forName("com.mysql.jdbc.Driver");
	            Connection con = DriverManager.getConnection("jdbc:mysql://1.201.141.109:3306/breaktime?useServerPrepStmts=false&rewriteBatchedStatements=true","root", "chamel186");
	            Statement s12 = con.createStatement();
	            while (true) {
	               Thread.sleep(1333);
	               String down = "select id, name from zone" + cur_pl.getGame_ID() + " where next = 1";
	               ResultSet rs11 = s12.executeQuery(down);
	               int did = 0;
	               String dname = new String();
	               while (rs11.next()) {
	                  did = rs11.getInt(1);
	                  dname = rs11.getString(2);   
	               }
	               did++;
	               deszone.setText("곧 " + did + ". " + dname + " 지역이 파괴됩니다.");
	               ffff().setComponentZOrder(deszone, 0);
	               
	               deszone.revalidate();
	               deszone.repaint();
	            }
	         } catch (Exception ex) {
	            ex.printStackTrace();
	         }
	      }
	   }
	public Normal_UI ffff()
	{
		return this;
	}
}