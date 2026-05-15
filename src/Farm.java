import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Farm extends Assets{
    public Farm() throws IOException {
        super(3, 60, 0, ImageIO.read(new File("farm.png")), "farm");
    }
}
