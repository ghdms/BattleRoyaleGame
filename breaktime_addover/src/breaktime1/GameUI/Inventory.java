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
		// �̹��� ���� �κ� ����

	}

	public void tick(MouseManager mm) {
		if (isOpen) {
			Rectangle temp = new Rectangle(mm.mouseX, mm.mouseY, 1, 1);

			for (ItemSlot is : itemSlots) {
				is.tick();

				Rectangle temp2 = new Rectangle(is.getX(), is.getY(), ItemSlot.SLOTSIZE, ItemSlot.SLOTSIZE);
				if (mm.isClicked) {
					if (temp2.contains(temp) && !hasBeenPressed) { // ������ ���� �� �ϳ��� Ŭ���ϸ�

						hasBeenPressed = true;

						if (currSelectedSlot == null) { // ���� ���õ� ������ ���ٸ�
							if (is.getItemStack() != null) { // Ŭ���� ���Կ� �������� �ִٸ�
								currSelectedSlot = is.getItemStack(); // �� ��������
								// ���콺
								// Ŀ����
								// ���δ�.
								is.setSelected(true); // �����Ѵ�.

								is.setItem(null); // Ŭ���� ������ ����
							}
						} else { // ���� �����ϰ� �ִ� (�巹���ϰ� �ִ�) ������ �ִٸ�

							// ������ ���
							if (is.isSelected()) {

								int id = currSelectedSlot.getItem().getItem_ID();

								JOptionPane.showMessageDialog(null, "������ ���");

								switch (currSelectedSlot.getItem().getItem_Type()) {
								case "W":
									cur_pl.setDamage(is.useItem(currSelectedSlot.getItem())); // ���� ������ �������� ���� �÷��̾���
									// ���ݷ� ����
									cur_pl.use_item(id);
									break;

								case "D":
									cur_pl.setDefense(is.useItem(currSelectedSlot.getItem())); // ��� ������ �������� ���� �÷��̾���
									// ���� ����
									cur_pl.use_item(id);

									break;

								case "H":
									int HP = cur_pl.getHp();
									int newHP = HP + is.useItem(currSelectedSlot.getItem());

									if (HP + currSelectedSlot.getItem().getAbility() >= 100) {
										newHP = 100;
									}

									cur_pl.setHp(newHP); // ȸ�� ������ �������� ���� �÷��̾���
									// HP ����
									// UI�� ��� ����?
									NU.HP_change(newHP);
									cur_pl.use_item(id);

									break;

								}
								currSelectedSlot = null; // ������ ������

								// is.setItem(null);
								MouseManager.isClicked = false;
								is.setSelected(false);
							}

							if (currSelectedSlot != null) {
								// ���� ����
								if (is.addItem(currSelectedSlot.getItem(), currSelectedSlot.getAbility())) {

									cur_pl.use_item(currSelectedSlot.getItem().getItem_ID());
									currSelectedSlot = null;
								} else { // ���� ����

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

					else // ������ ���� �ٱ��� �����ϸ�
					{
						boolean IsSelected = false;

						for (ItemSlot is2 : itemSlots) {
							Rectangle tempp2 = new Rectangle(is2.getX(), is2.getY(), ItemSlot.SLOTSIZE,
									ItemSlot.SLOTSIZE);
							IsSelected = tempp2.contains(temp);
							if (IsSelected)
								break;
						}

						if (!IsSelected) // ������ ���� �ٱ����� Ŭ���ϸ�
						{
							if (currSelectedSlot != null) // ���� ������ ������ �ִٸ�
							{

								int id = currSelectedSlot.getItem().getItem_ID();
								if (cur_pl.delete_item(id))
									currSelectedSlot = null; // ������ ������

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
