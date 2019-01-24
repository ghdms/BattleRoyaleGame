package breaktime1.GameUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Statement;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import breaktime1.GameObject.ItemStack;
import breaktime1.GameObject.Player;
import breaktime1.Input.MouseManager;
import breaktime1.Place.Normal_UI;

public class Inventory extends JPanel {

	public static boolean isOpen = false;
	private boolean hasBeenPressed = false;

	private int x, y;
	private int width, height;

	private int numCols = 5;
	private int numRows = 2;

	private BufferedImage image;

	public CopyOnWriteArrayList<ItemSlot> itemSlots;
	private ItemStack currSelectedSlot;
	private Player cur_pl;
	private Normal_UI NU;
	private Statement stmt;
	public void setUIinInventory(Normal_UI NU) {
		this.NU = NU;
	}

	public Inventory(int x, int y, Player p, Statement s) {
		stmt = s;
		cur_pl = p;
		itemSlots = new CopyOnWriteArrayList<ItemSlot>();

		for (int i = 0; i < numCols; i++) {
			for (int j = 0; j < numRows; j++) {
				if (j == (numRows - 1)) {
					y += 5;
				}
				itemSlots.add(
						new ItemSlot(x + (i * (ItemSlot.SLOTSIZE + 10)), y + (j * (ItemSlot.SLOTSIZE + 10)), null));
				if (j == (numRows - 1)) {
					y -= 5;
				}
			}
		}

		width = numCols * (ItemSlot.SLOTSIZE + 10);
		height = numRows * (ItemSlot.SLOTSIZE + 10) + 15;

		try {
			image = ImageIO.read(getClass().getResource("/breakt/UI/bag_pop.png"));
			width = image.getWidth();
			height = image.getHeight();

		} catch (IOException ioe) {
			System.out.println("Could not read in the pic");
			// System.exit(0);
		}
		// 이미지 띄우는 부분 종료

	}

	public void tick(MouseManager mm) {
		if (isOpen) {
			Rectangle temp = new Rectangle(mm.mouseX, mm.mouseY, 1, 1);

			for (ItemSlot is : itemSlots) {
				is.tick();

				Rectangle temp2 = new Rectangle(is.getX(), is.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
				if (mm.isClicked) {
					if (temp2.contains(temp) && !hasBeenPressed) { // 아이템 슬롯 중 하나를 클릭하면

						hasBeenPressed = true;

						if (currSelectedSlot == null) { // 현재 선택된 슬롯이 없다면
							if (is.getItemStack() != null) { // 클릭한 슬롯에 아이템이 있다면
								currSelectedSlot = is.getItemStack(); // 그 아이템을
								// 마우스
								// 커서에
								// 붙인다.
								is.setSelected(true); // 선택한다.

								is.setItem(null); // 클릭한 슬롯을 비운다
							}
						} else { // 현재 선택하고 있는 (드레그하고 있는) 슬롯이 있다면

							// 아이템 사용
							if (is.isSelected()) {

								int id = currSelectedSlot.getItem().getItem_ID();

								JOptionPane.showMessageDialog(null, "아이템 사용");

								switch (currSelectedSlot.getItem().getItem_Type()) {
								case "W":
									cur_pl.setDamage(is.useItem(currSelectedSlot.getItem())); // 공격 아이템 장착으로 인한 플레이어의
									// 공격력 변경
									cur_pl.use_item(id);
									break;

								case "D":
									cur_pl.setDefense(is.useItem(currSelectedSlot.getItem())); // 방어 아이템 장착으로 인한 플레이어의
									// 방어력 변경
									cur_pl.use_item(id);

									break;

								case "H":
									int HP = cur_pl.getHp();
									int newHP = HP + is.useItem(currSelectedSlot.getItem());

									if (HP + currSelectedSlot.getItem().getAbility() >= 100) {
										newHP = 100;
									}

									cur_pl.setHp(newHP); // 회복 아이템 장착으로 인한 플레이어의
									// HP 변경
									// UI에 어떻게 접근?
									NU.HP_change(newHP);
									cur_pl.use_item(id);

									break;

								}
								currSelectedSlot = null; // 아이템 버린다

								// is.setItem(null);
								MouseManager.isClicked = false;
								is.setSelected(false);
							}

							if (currSelectedSlot != null) {
								// 조합 실행
								if (is.addItem(currSelectedSlot.getItem(), currSelectedSlot.getAbility())) {

									cur_pl.use_item(currSelectedSlot.getItem().getItem_ID());
									currSelectedSlot = null;
								} else { // 조합 실패

									for (ItemSlot is2 : itemSlots) {

										if (is2.isSelected()) {
											hasBeenPressed = false;
										}
									}
								}

								MouseManager.isClicked = false;

								for (ItemSlot is2 : itemSlots) {
									is2.setSelected(false);
								}
							}
						}

					}

					else // 아이템 슬롯 바깥을 선택하면
					{
						boolean IsSelected = false;

						for (ItemSlot is2 : itemSlots) {
							Rectangle tempp2 = new Rectangle(is2.getX(), is2.getY(), ItemSlot.SLOTSIZE,
									ItemSlot.SLOTSIZE);
							IsSelected = tempp2.contains(temp);
							if (IsSelected)
								break;
						}

						if (!IsSelected) // 아이템 슬롯 바깥쪽을 클릭하면
						{
							if (currSelectedSlot != null) // 현재 선택한 슬롯이 있다면
							{

								int id = currSelectedSlot.getItem().getItem_ID();
								if (cur_pl.delete_item(id))
									currSelectedSlot = null; // 아이템 버린다

								for (ItemSlot is2 : itemSlots) {
									is2.setSelected(false);
								}

								MouseManager.isClicked = false;
							}
						}

					}

				}
			}

			if (hasBeenPressed && !MouseManager.isClicked) {

				hasBeenPressed = false;
			}
		}
	}

	public void render(Graphics g) {
		if (isOpen) {
			g.drawImage(image, 0, 0, width, height, this);

			for (ItemSlot is : itemSlots) {
				is.render(g);
			}

			if (currSelectedSlot != null) {
				g.drawImage(currSelectedSlot.getItem().getTexture(), MouseManager.mouseX - 20, MouseManager.mouseY - 20,
						null);
				g.drawString(currSelectedSlot.getAbility(), MouseManager.mouseX + 27, MouseManager.mouseY + 33);
			}
		}
	}
}
