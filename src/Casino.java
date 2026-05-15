import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Casino extends Assets{
    public Casino() throws IOException {
        super(6, 120, 0, ImageIO.read(new File("casino.png")), "casino");
    }
}
