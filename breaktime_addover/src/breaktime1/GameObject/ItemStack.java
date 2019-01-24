package breaktime1.GameObject;

public class ItemStack {
	 
    private int amount;
    private Item item;
    private String ability;
    
    public String getAbility() {
		return ability;
	}

	public void setAbility(String ability) {
		this.ability = ability;
	}

	public ItemStack(Item item, String ability) {
        this.item = item;
        this.ability = ability;
    }
     
    public ItemStack(Item item, int amount) {
        this.item = item;
        this.amount = amount;
    }
     
    public Item getItem() {
        return item;
    }
     
    public int getAmount() {
        return amount;
    }
     
    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    public String getInfo()
    {
    	return (item.getItem_Type() + item.getAbility());
    }
}
