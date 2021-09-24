package textrpg.items;

public class Item {
    public String name;
    public int quantity;
    public int value;

    public Item(String name,int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

    public void setValue(int i) {
        this.value = i;
    }

}

    
