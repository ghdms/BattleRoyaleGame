package breaktime1.GameObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.*;
import javax.swing.JOptionPane;

import breaktime1.GameUI.ItemSlot;
import breaktime1.Place.Normal_UI;

public class Game implements Comparator<Item> {
	private String game_id;
	private Player cur_pl;
	private bag_p mybag;
	private int fight_id;

	public Player getCur_pl() {
		return cur_pl;
	}

	public bag_p getMybag() {
		return mybag;
	}

	private Statement stmt;

	public Game(Statement s, Player p) {
		cur_pl = p;
		stmt = s;
		game_id = "game" + cur_pl.getGame_ID();
		fight_id = 0;
		
		mybag = new bag_p(780, 70, cur_pl,stmt);
		mybag.setBounds(780, 70, 410, 230);

	}

	public void setZone(int zone) {
		cur_pl.setZone_ID(zone);
	}

	public int search() {
		
		String qr2 = "select * from player where zone = " + cur_pl.getZone_ID() + " and nickname <> '"
	            + cur_pl.getNickname() + "'" + " and fight = 0";
		int myitem = -1;
		int randint = 0;
		int randid = 0;
		try {
			ResultSet rs2 = stmt.executeQuery(qr2);
			ArrayList<String> pl = new ArrayList<String>();
			while (rs2.next()) {
				String temp = new String();
				temp = rs2.getString(1);
				pl.add(temp);
			}

			if (pl.size() != 0) {
				Random r2 = new Random(System.currentTimeMillis());
				int randn = r2.nextInt(pl.size());
				String enemy = new String(pl.get(randn));
				JOptionPane.showMessageDialog(null, enemy);
				
				cur_pl.setEnemy(enemy);
				//fight(enemy);
				return -1;
			} else {
				Random r = new Random(System.currentTimeMillis());
				randint = r.nextInt(10);
				if (randint < 5) {
					ArrayList<Item> item = new ArrayList<Item>();
					String qr = "select * from " + game_id + " where zone_id = " + cur_pl.getZone_ID()
							+ " and rest > 0";

					ResultSet rs = stmt.executeQuery(qr);
					while (rs.next()) {
						Item t = new Item();
						t.setItem_ID(rs.getInt(1));
						t.setItem_Name(rs.getString(2));
						t.setItem_Type(rs.getString(3));
						t.setAbility(rs.getInt(4));
						t.setZone_ID(rs.getInt(5));
						item.add(t);
					}
					if (item.size() == 0)
						return -999;
					else if (item.size() == 1) {
						myitem = 0;
					}
					else if (item.size() == 2) {
						if (randint % 2 == 0) {
							myitem = 0;
						} else {
							myitem = 1;
						}
					}
					else {
						Collections.sort(item, new Comparator<Item>() {
							public int compare(Item t1, Item t2) {
								if (t1.getAbility() > t2.getAbility()) {
									return 1;
								} else if (t1.getAbility() < t2.getAbility()) {
									return -1;
								} else {
									return 0;
								}
							}
						});

						Random r3 = new Random(System.currentTimeMillis());
						int w = r3.nextInt(100);

						randid = 0;
						int idx = (int) ((item.size()) / 2);
						int idx2 = (int) ((idx + 1) / 2);

						if (w < 50) {
							randid = r3.nextInt(idx);
						} else if (w < 80) {
							randid = r3.nextInt(idx2) + idx;
						} else {
							randid = r3.nextInt(item.size() - idx - idx2) + idx + idx2;
						}
						myitem = randid;
					}

					int get = cur_pl.getItemsize();
					
					if (get < 10)
					{
						BufferedImage itemimage;

						try {
							itemimage = ImageIO.read(getClass().getResource("/breakt/item/" + item.get(myitem).getItem_Name() + ".png"));
							item.get(myitem).setTexture(itemimage);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						int i = 0;
						while (i < ItemSlot.MAXITEMSIZE) {
							if (mybag.my_inventory.itemSlots.get(i).IsItemEmpty()) // 아이템 슬롯이 비어 있으면
							{
								mybag.my_inventory.itemSlots.get(i).addItem(item.get(myitem),
										String.valueOf(item.get(myitem).getItem_ID())); // 해당 슬롯에 아이템 추가
								System.out.println("아이템 추가");
								break;
							}
							i++;
						}

						cur_pl.ins_item(item.get(myitem));
						String up = "update " + game_id + " set rest = rest - 1 where id = "
								+ item.get(myitem).getItem_ID();
						stmt.executeUpdate(up);
						
						JOptionPane.showMessageDialog(null, item.get(myitem).getItem_Name() + " "
								+ item.get(myitem).getItem_Type() + " " + item.get(myitem).getAbility());
						
						return 1;
					} 
					else {
						JOptionPane.showMessageDialog(null, "가방이 가득 찼습니다.");

						return 999;
					}
				}
				else {
					return 0;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -100;
		}
	}

	public void fight(String enemy) {
		cur_pl.setFight(1);
		int damage = cur_pl.cal_da();
		int defense = cur_pl.cal_de();

		String f = "update player set fight = 1 where nickname = '" + cur_pl.getNickname() + "'";
		String f2 = "update player set fight = 1 where nickname = '" + enemy + "'";

		String qr = "insert into fight values(" + fight_id + ", '" + cur_pl.getNickname() + "', " + damage + ", "
				+ defense + ", 0)";
		String qr2 = "insert into fight values(" + fight_id + ", '" + enemy + "', 0, 0, 0)";
		try {
			stmt.executeUpdate(f);
			stmt.executeUpdate(f2);
			stmt.executeUpdate(qr);
			stmt.executeUpdate(qr2);
			fight_id++;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void map(int z) {
		cur_pl.setZone_ID(z);
	}

	public int compare(Item t1, Item t2) {
		if (t1.getAbility() > t2.getAbility()) {
			return 1;
		} else if (t1.getAbility() < t2.getAbility()) {
			return -1;
		} else {
			return 0;
		}
	}
	public void setUIinInventory(Normal_UI NU) {
	      mybag.setUIinInventory(NU);
	   }
	public boolean zone_flag(int id)
	{
		try {
			String qr = "select flag from zone" + cur_pl.getGame_ID() + " where id = " + id;
			Statement ss = stmt;
			ResultSet rs = ss.executeQuery(qr);
			int f = 0;
			while(rs.next())
			{
				f = rs.getInt(1);
			}
			if(f == 0)
			{
				return false;
			}
			else
			{
				return true;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}