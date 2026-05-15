import java.awt.image.BufferedImage;
import java.math.BigDecimal;

public abstract class Assets{
    public BigDecimal revenue;
    public BufferedImage icon;
    public BigDecimal cost;
    public String name;
    public int count;

    public Assets(int revenue, double cost, int count, BufferedImage icon, String name){
        this.revenue = new BigDecimal("" + revenue);
        this.icon = icon;
        this.cost = new BigDecimal("" + cost);
        this.count = count;
        this.name = name;
    }
    public BigDecimal getRevenue(){
        return revenue;
    }

    public void increaseCount(){
        count += 1;
        cost = cost.multiply(new BigDecimal("1.15"));
    }
    public void decreaseCount(){
        if(count > 0) {
            count -= 1;
            cost = cost.multiply(new BigDecimal("" + 1.0/1.15));
        }
    }

}