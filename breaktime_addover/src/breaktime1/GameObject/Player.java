package breaktime1.GameObject;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class Player {
	private String Nickname;
	private int game_ID;
	private int zone_ID;
	private int Damage = 0;
	private int Defense = 0;
	private int Hp = 100;
	private int Shake = 0;
	private int fight = 0;
	private int kill = 0;
	private boolean myready = false;
	private String enemy;
	
	public int getKill() {
		return kill;
	}

	public void setKill(int kill) {
		this.kill = kill;
	}

	public String getEnemy() {
		return enemy;
	}

	public void setEnemy(String enemy) {
		this.enemy = enemy;
	}

	public boolean isMyready() {
		return myready;
	}

	public void setMyready(boolean myready) {
		this.myready = myready;
	}

	public int getFight() {
		return fight;
	}

	public void setFight(int fight) {
		this.fight = fight;
	}

	private ArrayList<Item> da_list;
	private ArrayList<Item> de_list;
	private ArrayList<Item> h_list;

	private ArrayList<Item> item_list;

	public Player() {
		Nickname = new String();
		da_list = new ArrayList<Item>();
		de_list = new ArrayList<Item>();
		h_list = new ArrayList<Item>();
		item_list = new ArrayList<Item>();
	}

	public void set(String n, int g, int z, int d, int dd, int h, int s, int f) {
		Nickname = n;
		game_ID = g;
		zone_ID = z;
		Damage = d;
		Defense = dd;
		Hp = h;
		Shake = s;
		fight = f;
	}

	public String getNickname() {
		return Nickname;
	}

	public void setNickname(String nickname) {
		Nickname = nickname;
	}

	public int getGame_ID() {
		return game_ID;
	}

	public void setGame_ID(int game_ID) {
		this.game_ID = game_ID;
	}

	public int getZone_ID() {
		return zone_ID;
	}

	public void setZone_ID(int zone_ID) {
		this.zone_ID = zone_ID;
	}

	public int getDamage() {
		return Damage;
	}

	public void setDamage(int damage) {
		Damage = damage;
	}

	public int getDefense() {
		return Defense;
	}

	public void setDefense(int defense) {
		Defense = defense;
	}

	public int getHp() {
		return Hp;
	}

	public void setHp(int hp) {
		Hp = hp;
	}

	public int getShake() {
		return Shake;
	}

	public void setShake(int shake) {
		Shake = shake;
	}

	public int getsize() {
		int size = 0;
		size = da_list.size() + de_list.size() + h_list.size();
		// W , D, H
		return size;
	}

	public int cal_da() {
		int result = 1;
		return result * Shake;
	}

	public int cal_de() {
		return 1;
	}

	public int cal_h() {
		return 1;
	}
	/*
	 * public void ins_item(Item item) { if(item.getItem_Type().equals("W")) {
	 * da_list.add(item); } else if(item.getItem_Type().equals("D")) {
	 * de_list.add(item); } else { h_list.add(item); } }
	 */

	public void ins_item(Item item) {
		item_list.add(item);
		if (item.getItem_Type().equals("W")) {
			da_list.add(item);
		} else if (item.getItem_Type().equals("D")) {
			de_list.add(item);
		} else {
			h_list.add(item);
		}
	}

	public boolean delete_item(int id) {
		int result = JOptionPane.showConfirmDialog(null, "버리기 선택", "confirm", JOptionPane.OK_CANCEL_OPTION);
		int size = item_list.size();
		if(result == JOptionPane.OK_OPTION)
		{
			for (int i = 0; i < size; i++)
			{
				if (id == item_list.get(i).getItem_ID())
				{
					if(item_list.get(i).getItem_Type() == "W")
					{
						int s = da_list.size();
						for(int j=0;j<s;j++)
						{
							if (id == da_list.get(j).getItem_ID())
							{
								da_list.remove(j);
								break;
							}
						}
					}
					else if(item_list.get(i).getItem_Type() == "D")
					{
						int s = de_list.size();
						for(int j=0;j<s;j++)
						{
							if (id == de_list.get(j).getItem_ID())
							{
								de_list.remove(j);
								break;
							}
						}
					}
					else
					{
						int s = h_list.size();
						for(int j=0;j<s;j++)
						{
							if (id == h_list.get(j).getItem_ID())
							{
								h_list.remove(j);
								break;
							}
						}
					}
				}
				item_list.remove(i);
				break;
			}
			return true;
		}
		else if(result == JOptionPane.CANCEL_OPTION)
		{
			return false;
		}
		return false;
	}

	public Item geti() {
		return da_list.get(0);
	}
	
	public int getItemsize() {
		return item_list.size();
	}
	public void use_item(int id) {
	      int size = item_list.size();

	      for (int i = 0; i < size; i++) {
	         if (id == item_list.get(i).getItem_ID()) {
	            if (item_list.get(i).getItem_Type() == "W") {
	               int s = da_list.size();
	               for (int j = 0; j < s; j++) {
	                  if (id == da_list.get(j).getItem_ID()) {
	                     int ge = item_list.get(i).getAbility();
	                     Damage = Damage + ge;
	                     da_list.remove(j);
	                     break;
	                  }
	               }
	            }

	            else if (item_list.get(i).getItem_Type() == "D") {
	               int s = de_list.size();
	               for (int j = 0; j < s; j++) {
	                  if (id == de_list.get(j).getItem_ID()) {
	                     int de = item_list.get(i).getAbility();
	                     Defense = Defense + de;
	                     de_list.remove(j);
	                     break;
	                  }
	               }
	            } else {
	               int s = h_list.size();
	               for (int j = 0; j < s; j++) {
	                  if (id == h_list.get(j).getItem_ID()) {
	                     h_list.remove(j);
	                     break;
	                  }
	               }

	            }
	            item_list.remove(i);
	            break;
	         }
	      }

	}
}
