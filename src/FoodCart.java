import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class FoodCart extends Assets {
    public FoodCart() throws IOException {
        super(1, 20, 0, ImageIO.read(new File("foodcart.png")), "foodCart");
    }
}
