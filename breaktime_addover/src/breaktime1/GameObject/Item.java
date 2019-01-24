package breaktime1.GameObject;

import java.awt.image.BufferedImage;

public class Item {
	private int item_ID;
	private String item_Name;
	private String item_Type;
	private int Ability;
	private int zone_ID;
	private BufferedImage texture;
	
	public int getItem_ID() {
		return item_ID;
	}
	public void setItem_ID(int item_ID) {
		this.item_ID = item_ID;
	}
	public String getItem_Name() {
		return item_Name;
	}
	public void setItem_Name(String item_Name) {
		this.item_Name = item_Name;
	}
	public String getItem_Type() {
		return item_Type;
	}
	public void setItem_Type(String item_Type) {
		this.item_Type = item_Type;
	}
	public int getAbility() {
		return Ability;
	}
	public void setAbility(int ability) {
		Ability = ability;
	}
	public int getZone_ID() {
		return zone_ID;
	}
	public void setZone_ID(int zone_ID) {
		this.zone_ID = zone_ID;
	}
	public BufferedImage getTexture() {
		return texture;
	}
	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}
	
	
}
