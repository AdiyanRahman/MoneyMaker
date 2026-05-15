import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class MarketStall extends Assets{
    public MarketStall() throws IOException {
        super(2, 40, 0, ImageIO.read(new File("marketStall.png")), "marketStall");
    }
}
