package breaktime1.GameUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import breaktime1.GameObject.Item;
import breaktime1.GameObject.ItemStack;

public class ItemSlot {

	public static final int SLOTSIZE = 50;
	public static final int MAXITEMSIZE = 10;

	public static Font myFont = new Font("sserife", Font.BOLD, 15);

	private int x, y;
	private ItemStack itemStack;
	private boolean selected = false;

	public ItemSlot(int x, int y, ItemStack itemStack) {
		this.x = x;
		this.y = y;
		this.itemStack = itemStack;
	}

	public void tick() {
	}

	public void render(Graphics g) {
		g.setColor(new Color(255,0,0,0));
		g.fillRect(x, y, SLOTSIZE, SLOTSIZE);

		g.setColor(new Color(255,0,0,0));
		g.drawRect(x, y, SLOTSIZE, SLOTSIZE);

		g.setFont(myFont);

		if (itemStack != null) {
			g.drawImage(itemStack.getItem().getTexture(), x, y, SLOTSIZE, SLOTSIZE, null);
			g.drawString(itemStack.getInfo(), x + SLOTSIZE - 20, y + SLOTSIZE - 10);
		}
	}

	public ItemStack getItemStack() {
		return itemStack;
	}

	public void setItem(ItemStack item) {
		this.itemStack = item;
	}

	public boolean IsItemEmpty() {
		return (itemStack == null);
	}

	public boolean addItem(Item item, String ability) { // 아이템 추가 & 조합
		if (itemStack != null) { // 조합

			BufferedImage itemimage;

			if (ability.equals(this.itemStack.getAbility())) { // 같은 아이템 조합
				JOptionPane.showMessageDialog(null, "같은 아이템을 조합할 수 없습니다.", "아이템 조합 실패", JOptionPane.ERROR_MESSAGE);
				return false;
			} else { // 다른 아이템 조합

				if ((this.itemStack.getAbility().equals("40") && ability.equals("39"))
						|| (this.itemStack.getAbility().equals("39") && ability.equals("40"))) {
					try {
						this.itemStack.setAbility(String.valueOf(
								(int) ((Integer.parseInt(this.itemStack.getAbility()) + Integer.parseInt(ability))
										* 1.4)));

						itemimage = ImageIO.read(getClass().getResource("/breakt/item/superdanso.png"));
						this.itemStack.getItem().setTexture(itemimage);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					JOptionPane.showMessageDialog(null, "조합성공 : 악보 + 단소 = 숙련된 단소 연주가");
					return true;

				} else if ((this.itemStack.getAbility().equals("39") && ability.equals("36"))
						|| (this.itemStack.getAbility().equals("36") && ability.equals("39"))) {
					try {
						this.itemStack.setAbility(String.valueOf(
								(int) ((Integer.parseInt(this.itemStack.getAbility()) + Integer.parseInt(ability))
										* 1.4)));

						itemimage = ImageIO.read(getClass().getResource("/breakt/item/electguitar.png"));
						this.itemStack.getItem().setTexture(itemimage);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					JOptionPane.showMessageDialog(null, "조합성공 : 악보 + 기타 = 일렉기타");
					return true;

				} else if ((this.itemStack.getAbility().equals("51") && ability.equals("9"))
						|| (this.itemStack.getAbility().equals("9") && ability.equals("51"))) {
					try {
						this.itemStack.setAbility(String.valueOf(
								(int) ((Integer.parseInt(this.itemStack.getAbility()) + Integer.parseInt(ability))
										* 1.4)));

						itemimage = ImageIO.read(getClass().getResource("/breakt/item/superacid.png"));
						this.itemStack.getItem().setTexture(itemimage);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					JOptionPane.showMessageDialog(null, "조합성공 : 염산 + 비커 = 슈퍼염산");
					return true;

				}

				else if ((this.itemStack.getAbility().equals("51") && ability.equals("49"))
						|| (this.itemStack.getAbility().equals("49") && ability.equals("51"))) {
					try {
						this.itemStack.setAbility(String.valueOf(
								(int) ((Integer.parseInt(this.itemStack.getAbility()) + Integer.parseInt(ability))
										* 1.4)));

						itemimage = ImageIO.read(getClass().getResource("/breakt/item/supersulfur.png"));
						this.itemStack.getItem().setTexture(itemimage);

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					JOptionPane.showMessageDialog(null, "조합성공 : 황산 + 비커 = 슈퍼황산");
					return true;

				}

				JOptionPane.showMessageDialog(null, "아이템을 조합할 수 없습니다.", "아이템 조합 실패", JOptionPane.ERROR_MESSAGE);
				return false;
			}

		} else { // 아이템 추가
			this.itemStack = new ItemStack(item, ability);
			return true;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}

	public int useItem(Item item) {
		if (item.getItem_Type().equals("H")) {
			JOptionPane.showMessageDialog(null,
					"꿀꺽꿀꺽\n" + item.getItem_Name() + "사용으로 HP " + item.getAbility() + " 회복!");
			
		}

		return item.getAbility();
	}
}
