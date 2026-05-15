import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;

public class PicManager {
    public static BufferedImage changeSize(BufferedImage icon, int width, int height) {
        Image temp = icon.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage newIcon = new BufferedImage(width, height, icon.getType());
        Graphics2D g = newIcon.createGraphics();
        g.drawImage(temp, 0, 0, null);
        g.dispose();
        return newIcon;
    }

    public BufferedImage getUpgradePic(String name, int count) throws IOException {
        if(name.equals("cursor") && count % 100 == 0 && count <= 800){
            int w = 20;
            int h = 19;
            int y = (count / 303) * (h + 1) + 1;
            int x = ((count / 100 - 1) % 3) * (w + 1) + 1;
            BufferedImage upgradePic = ImageIO.read(new File("cursorUpgrades.png")).getSubimage(x, (count > 600 ? y + 2:y), w, (y > 1 ? h + 2:h));
            return changeSize(upgradePic, 100, 100);
        }
        else if(count <= 40 && count % 5 == 0) {
            int w, h, x, y;
            switch (name){
                case "foodCart" -> {
                    w = 28;
                    h = 63;
                    y = (count / 5 - 1) / 4 * h;
                    x = (w * (count - 1)) % 112;
                    if (y + h < 128 && x + w < 128) {
                        return ImageIO.read(new File("foodCartUpgrades.png")).getSubimage(x, y, w, h);
                    }
                }
                case "marketStall" -> {
                    w = 33;
                    h = 33;
                    y = (count / 16) * h + 1;
                    x = ((count / 5 - 1) % 3) * w + 1;
                    BufferedImage upgradePic = ImageIO.read(new File("marketStallUpgrades.png")).getSubimage(x, y, w - 2, h - 2);
                    return changeSize(upgradePic, 100, 100);
                }
                case "farm" -> {
                    w = 16;
                    h = 31;
                    y = (count / 21) * h + 2;
                    x = ((count / 5 - 1) % 4) * w;
                    BufferedImage upgradePic = ImageIO.read(new File("farmUpgrades.png")).getSubimage(x, y, w - 1 + ((count / 5 - 1) % 4 == 3 ? 1:0), h - 2);
                    return changeSize(upgradePic, 50, 100);
                }
                case "factory" -> {
                    w = 20;
                    h = 20;
                    y = (count / 16) * (h + 1);
                    x = ((count / 5 - 1) % 3) * (w + 1);
                    BufferedImage upgradePic = ImageIO.read(new File("factoryUpgrades.png")).getSubimage(x, y, (((count / 5 - 1) % 3) == 2 ? w + 2:w), h );
                    return changeSize(upgradePic, 100, 100);
                }
                case "bank" -> {
                    w = 20;
                    h = 19;
                    y = (count / 16) * (h + 1) + 1;
                    x = ((count / 5 - 1) % 3) * (w + 1) + 1;
                    BufferedImage upgradePic = ImageIO.read(new File("bankUpgrades.png")).getSubimage(x, (count > 30 ? y + 2:y), w, (y > 1 ? h + 2:h));
                    return changeSize(upgradePic, 100, 100);
                }
                case "casino" -> {
                    w = 20;
                    h = 19;
                    y = (count / 16) * (h + 1) + 1;
                    x = ((count / 5 - 1) % 3) * (w + 1) + 1;
                    BufferedImage upgradePic = ImageIO.read(new File("casinoUpgrades.png")).getSubimage(x, (count > 30 ? y + 2:y), w, (y > 1 ? h + 2:h));
                    return changeSize(upgradePic, 100, 100);
                }
            }
        }
        return null;
    }
    public String getPicDescr(String name, int count){
        String descr = "";
        if(name.equals("foodCart")){
            switch (count) {
                case 5 -> descr = "<html>Ketchup: sweet, tomatoey goodness<html>";
                case 10 -> descr = "<html>Mustard: MUSTAAAAAAAAAAARD<html>";
                case 15 -> descr = "<html>Barbecue Sauce: proudly Texan-made<html>";
                case 20 -> descr = "<html>Relish: great for salads!<html>";
                case 25 -> descr = "<html>Mayonnaise: Sweet n' Creamy!<html>";
                case 30 -> descr = "<html>Syrup: Authentic, Canadian Syrup!<html>";
                case 35 -> descr = "<html>Choco-Syrup: ITS NOT POOP I SWEAR<html>";
                case 40 -> descr = "<html>Chili Sauce: \"yaaaahahah das hot\" - Will Smith<html>";
            }
        }
        if(name.equals("marketStall")){
            switch (count) {
                case 5 -> descr = "<html>Pear: a sweet, juicy, freshly washed pear<html>";
                case 10 -> descr = "<html>Potato: it ain't just for the Irish!<html>";
                case 15 -> descr = "<html>Banana: might make a good boomerang ngl<html>";
                case 20 -> descr = "<html>Radish: (*chomps*) oh hey these taste swee- OOOHHHHH SPIIICYYYYYY<html>";
                case 25 -> descr = "<html>Carrot: Radish's sweeter and more lovable brother<html>";
                case 30 -> descr = "<html>Orange: did the color or the fruit come first?<html>";
                case 35 -> descr = "<html>Tomato: to be consumed, NOT for throwing at jesters<html>";
                case 40 -> descr = "<html>Onion: Ogres are like- WAIT DONT SUE ME DREAMWORKS PLEA-<html>";
            }
        }
        if(name.equals("farm")){
            switch (count) {
                case 5 -> descr = "<html>Rake: helps straighten hay to dry them faster<html>";
                case 10 -> descr = "<html>Shovel: a giant spoon used to scoop up dirt! Yummers!<html>";
                case 15 -> descr = "<html>Sickle: (*heavy russian accent*) ROT IN GULAG, STUPID AMERICAN<html>";
                case 20 -> descr = "<html>Hoe: never waste your diamonds on these!<html>";
                case 25 -> descr = "<html>Bucket: \"This is a bucket\" \"dear god\"<html>";
                case 30 -> descr = "<html>Shears: basically just a heavy-duty scissor<html>";
                case 35 -> descr = "<html>Fertilizer: smells like BS... oh wait it literally is<html>";
                case 40 -> descr = "<html>Sprinkler: the key to a good farm is how well you maintain these<html>";
            }
        }
        if(name.equals("factory")){
            switch (count) {
                case 5 -> descr = "<html>Walkie-Talkie: (*KSHHHT*)cleanup on aisle 7(*KSHHHT*)<html>";
                case 10 -> descr = "<html>Cogs: lubrication oil not included<html>";
                case 15 -> descr = "<html>Wrench: makes you look like a auto-repair mechanic<html>";
                case 20 -> descr = "<html>Screw: they're practically everywhere!<html>";
                case 25 -> descr = "<html>Screwdriver: what type of maniac gets screws without these?<html>";
                case 30 -> descr = "<html>Handsaw: I guess the factory's doing woodwork now<html>";
                case 35 -> descr = "<html>Electrical Lever: Electrical > Mechanical<html>";
                case 40 -> descr = "<html>More Electricity: Do NOT show this to Hasanabi<html>";
            }
        }
        if(name.equals("bank")){
            switch (count) {
                case 5 -> descr = "<html>Bronze Coinage: a bit dull but still works as a currency<html>";
                case 10 -> descr = "<html>Silver Coinage: white and shiny, what's not to love?<html>";
                case 15 -> descr = "<html>Gold Coinage: throw into a wishing well for good fortune!<html>";
                case 20 -> descr = "<html>Bronze Ingots: I came looking for copper,<html>";
                case 25 -> descr = "<html>Silver Ingots: It's like that weird middle sibling in a family<html>";
                case 30 -> descr = "<html>Gold Ingots: And found gold instead<html>";
                case 35 -> descr = "<html>Better Investments: profits soar as the bank invests in better companies<html>";
                case 40 -> descr = "<html>Stock Market: essentially legalized gambling<html>";
            }
        }
        if(name.equals("casino")){
            switch (count) {
                case 5 -> descr = "<html>Red Poker Chips: the color of fire: feisty and fierce<html>";
                case 10 -> descr = "<html>Green Poker Chips: the color of nature: chaotic but beautiful<html>";
                case 15 -> descr = "<html>Blue Poker Chips: the color of the seas: smooth and elegant<html>";
                case 20 -> descr = "<html>Ace Of Diamonds: equal sides represent neutrality and unbiased judgement<html>";
                case 25 -> descr = "<html>Ace Of Spades: pointy and represents conviction and determination<html>";
                case 30 -> descr = "<html>Ace Of Hearts: typically used to represent love and affection<html>";
                case 35 -> descr = "<html>Gambler's Dice: totally not rigged (one side is actually heavier)<html>";
                case 40 -> descr = "<html>Fortune's On Your Side: life's treating you hard, have a break<html>";
            }
        }
        if(name.equals("cursor")){
            switch (count) {
                case 100 -> descr = "<html>White Cursor: even the classics must be appreciated!<html>";
                case 200 -> descr = "<html>Golden Cursor: Pure solid gold! (gold detector then proceeds to go silent)<html>";
                case 300 -> descr = "<html>Prismatic Cursor: obtained from looting the powerful queen of light<html>";
                case 400 -> descr = "<html>White Clicker: stop cursing and start clicking!<html>";
                case 500 -> descr = "<html>Golden Clicker: obtain the power of Midas and turn clicks into gold!<html>";
                case 600 -> descr = "<html>Anti-clicker: clicks so powerful, it takes away money but adds it back twice<html>";
                case 700 -> descr = "<html>Pinpoint Accuracy: Your clicks never miss! Click anywhere to get money! <html>";
                case 800 -> descr = "<html>Rapid Fire: \"Reminds me of 'nam\"<html>";
            }
        }
        descr += "<html><br>(Costs $" + getUpgradeCost(name, count) + " and doubles " + name + " revenue)<html>";
        return descr;
    }
    public BigDecimal getUpgradeCost(String name, int count){
        BigDecimal cost = new BigDecimal("1E+" + (count/5 + 1));
        switch (name) {
            case "cursor" -> cost = new BigDecimal("1E+" + (count/100 + 1));
            case "foodCart" -> cost = cost.multiply(new BigDecimal("2"));
            case "marketStall" -> cost = cost.multiply(new BigDecimal("3"));
            case "farm" -> cost = cost.multiply(new BigDecimal("4"));
            case "factory" -> cost = cost.multiply(new BigDecimal("5"));
            case "bank" -> cost = cost.multiply(new BigDecimal("6"));
            case "casino" -> cost = cost.multiply(new BigDecimal("7"));
        }
        return cost;
    }

}
