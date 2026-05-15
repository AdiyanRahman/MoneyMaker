import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Bank extends Assets{
    public Bank() throws IOException {
        super(5, 100, 0, ImageIO.read(new File("bank.png")), "bank");
    }
}
