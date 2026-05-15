import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;

public class Factory extends Assets{
    public Factory() throws IOException {
        super(4, 80, 0, ImageIO.read(new File("factory.png")), "factory");
    }
}
