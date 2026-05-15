import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.LinkedHashMap;

public class MONEYMakerRunner extends JFrame {
    public boolean isActive = false;
    public static final String FLIP_FORWARD = "flipForward";
    public static final String FLIP_BACKWARD = "flipBackward";
    public static final JPanel CENTER = new JPanel();
    public static final JPanel UPGRADES = new JPanel();
    public static final JPanel PURCHASABLES = new JPanel();
    public static final JLabel MONEY = new JLabel();
    public static final int WIDTH = 2400;
    public static final int HEIGHT = 1300;
    public BigDecimal money = new BigDecimal("0");
    public BigDecimal mps = new BigDecimal("0");
    public int page = 0;
    public int clicks = 0;
    public static final double PULSESPEED = 50;
    public int clickPower = 1;
    public static final Color MONEYGREEN = new Color(62, 155, 10);
    public static final Color MARMALADE = new Color(250, 110, 6);
    public static final Color CREAM = new Color(220, 220, 187);
    public static final Color JEANS = new Color(66, 94, 106);
    public static final LinkedHashMap<String, Assets> CONNECTIONS = new LinkedHashMap<>();
    public static final ArrayList<String> upgradeTips = new ArrayList<>();
    PicManager picManager = new PicManager();


    public MONEYMakerRunner() throws IOException {
        setTitle("MONEY Maker");
        setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUpConnections();
        addPanels();
        pack();
        setVisible(true);
    }

    private void setUpConnections() throws IOException {
        CONNECTIONS.put("foodCart", new FoodCart());
        CONNECTIONS.put("marketStall", new MarketStall());
        CONNECTIONS.put("farm", new Farm());
        CONNECTIONS.put("factory", new Factory());
        CONNECTIONS.put("bank", new Bank());
        CONNECTIONS.put("casino", new Casino());
    }

    private void addPanels() throws IOException {
        setUpCenter(CENTER);
        add(CENTER, BorderLayout.CENTER);

        JPanel right = new JPanel();
        right.setLayout(new BorderLayout());
        right.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT));

        setUpUpgrades();
        right.add(UPGRADES, BorderLayout.NORTH);

        setUpPurchasables();
        right.add(PURCHASABLES);
        add(right, BorderLayout.EAST);

        DrawingPanel iconSpace = new DrawingPanel();
        iconSpace.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT));
        getContentPane().addMouseListener(iconSpace);
        add(iconSpace, BorderLayout.WEST);
    }
    private void setUpUpgrades(){
        UPGRADES.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT / 4));
        UPGRADES.setBackground(CREAM);
        UPGRADES.setLayout(new FlowLayout());
    }
    private int getUpgradeIndex(String name){
        for (int i = 0; i < UPGRADES.getComponents().length; i++) {
            if(UPGRADES.getComponents()[i].getName().equals(name)){
                return i;
            }
        }
        return -1;
    }
    private void updateUpgrades(String picName, int cnt, Assets a) throws IOException {
        int count;
        String name;
        if(a == null){
            count = cnt;
            name = "cursor";
        }
        else{
            count = CONNECTIONS.get(a.name).count;
            name = a.name;
        }
        if(count >= 0){
            System.out.println("processed count increase");
            BufferedImage pic = picManager.getUpgradePic(picName, cnt);
            String toolTip = picManager.getPicDescr(picName, cnt);
            if(pic != null && toolTip != null && !upgradeTips.contains(toolTip)) {
                upgradeTips.add(toolTip);
                JButton upgrade = new JButton(new ImageIcon(pic));
                upgrade.setPreferredSize(new Dimension(100, 100));
                upgrade.setToolTipText(toolTip);
                upgrade.setName(name + count);
                System.out.println(name + count);
                UPGRADES.add(upgrade);
                System.out.println("added upgrade");
                upgrade.addActionListener(e -> {
                    BigDecimal cost = picManager.getUpgradeCost(picName, cnt);
                    if(a == null){
                        if(cost.compareTo(money) <= 0) {
                            clickPower = (int) Math.pow(2, (count / 50.0));
                            money = money.subtract(cost);
                            UPGRADES.remove(getUpgradeIndex(upgrade.getName()));
                            UPGRADES.repaint();
                            updatePurchasables();
                            updateCenter();
                        }
                    }
                    else if(cost.compareTo(money) <= 0) {
                        BigDecimal rev = CONNECTIONS.get(a.name).revenue;
                        money = money.subtract(cost);
                        mps = mps.add((new BigDecimal(CONNECTIONS.get(a.name).count)).multiply(rev));
                        CONNECTIONS.get(a.name).revenue = rev.add(rev);
                        UPGRADES.remove(getUpgradeIndex(upgrade.getName()));
                        UPGRADES.repaint();
                        updatePurchasables();
                        updateCenter();
                    }
                });
            }
            SwingUtilities.updateComponentTreeUI(UPGRADES);
        }
    }
    private void setUpPurchasables() throws IOException {
        PURCHASABLES.setPreferredSize(new Dimension(WIDTH / 3, HEIGHT * 3 / 4));
        PURCHASABLES.setBackground(MARMALADE);
        PURCHASABLES.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(0, 0, 0, 0);
        Object[] objAry = CONNECTIONS.keySet().toArray();
        for (int i = 5 * page; i < 5 * (page + 1) && i < objAry.length; i++) {
            setUpShop(i % 5, (String) objAry[i], constraints);
        }
        constraints.gridy = 5;
        constraints.gridx = 1;
        JLabel pageNum = new JLabel("Page: " + (page + 1) + "/2");
        pageNum.setBackground(MARMALADE);
        PURCHASABLES.add(pageNum, constraints);
        constraints.gridx = 0;
        JButton leftArrow = new JButton(new ImageIcon(PicManager.changeSize(ImageIO.read(new File("left_arrow.png")), 50, 50)));
        leftArrow.setBackground(MARMALADE);
        leftArrow.setBorder(null);
        leftArrow.setFocusPainted(false);
        PURCHASABLES.add(leftArrow, constraints);
        leftArrow.addActionListener(e -> flipPage(FLIP_BACKWARD, pageNum));
        constraints.gridx = 2;
        JButton rightArrow = new JButton(new ImageIcon(PicManager.changeSize(ImageIO.read(new File("right_arrow.png")), 50, 50)));
        rightArrow.setBackground(MARMALADE);
        rightArrow.setBorder(null);
        rightArrow.setFocusPainted(false);
        rightArrow.addActionListener(e -> flipPage(FLIP_FORWARD, pageNum));
        PURCHASABLES.add(rightArrow, constraints);
    }

    private void flipPage(String flip, JLabel pageNum) {
        page = flip.equals("flipForward") ? Math.min(page + 1, CONNECTIONS.keySet().size() / 5) : Math.max(page - 1, 0);
        pageNum.setText("Page: " + (page + 1) + "/2");
        updatePurchasables();
    }

    private void updatePurchasables() {
        PURCHASABLES.setVisible(false);
        Component[] components = PURCHASABLES.getComponents();
        for (Component c : components) {
            if (!c.getBackground().equals(MARMALADE)) {
                PURCHASABLES.remove(c);
            }
        }
        Object[] objAry = CONNECTIONS.keySet().toArray();
        for (int i = 5 * page; i < 5 * (page + 1) && i < objAry.length; i++) {
            setUpShop(i % 5, (String) objAry[i], new GridBagConstraints());
        }
        PURCHASABLES.setVisible(true);
    }

    public void setUpCenter(JPanel center) {
        center.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        center.setBackground(JEANS);
        constraints.insets = new Insets(0, 0, 20, 0);
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.ipadx = WIDTH / 4;
        constraints.ipady = (HEIGHT * 13) / 100;
        MONEY.setText("<html>MONEY: <br>$" + money + "<br>MPS: $" + mps + "<html>");
        MONEY.setFont(new Font(Font.MONOSPACED, Font.BOLD, 35));
        MONEY.setPreferredSize(new Dimension(400, 200));
        JPanel MONEYDisplay = new JPanel();
        MONEYDisplay.setBackground(JEANS);
        MONEYDisplay.setLayout(new FlowLayout());
        MONEYDisplay.add(MONEY);
        constraints.ipadx = WIDTH / 8;
        constraints.ipady = WIDTH / 17;
        center.add(MONEYDisplay, constraints);
    }

    private void setUpShop(int y, String fileName, GridBagConstraints c) {
        c.gridx = 0;
        c.gridy = y;
        c.ipadx = 100;
        c.ipady = 25;
        Assets asset = CONNECTIONS.get(fileName);
        BufferedImage shopIcon = PicManager.changeSize(asset.icon, 100, 100);
        JLabel icon = new JLabel(new ImageIcon(shopIcon), SwingConstants.LEFT);
        MONEYMakerRunner.PURCHASABLES.add(icon, c);
        c.gridx = 1;
        c.gridy = y;
        JLabel text = new JLabel("<html>Cost: $" + asset.cost.round(new MathContext("precision=3 roundingMode=HALF_UP")) + "<br>Refund: $"
                + asset.cost.multiply(new BigDecimal("0.5")).round(new MathContext("precision=3 roundingMode=HALF_UP"))
                + "<br>Revenue: $" + asset.getRevenue()
                + "<br>Owned: " + asset.count + "<html>");
        text.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        text.setForeground(MONEYGREEN);
        text.setBackground(new Color(0, 0, 0, 0));
        MONEYMakerRunner.PURCHASABLES.add(text, c);
        c.gridx = 2;
        c.gridy = y;
        c.ipady = 0;
        addBuySell(c, asset, fileName);
    }


    private void addBuySell(GridBagConstraints constraints, Assets a, String fileName) {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        JButton sell = new JButton("sell");
        sell.addActionListener(e -> {
            if (a.count > 0) {
                money = money.add(a.cost.multiply(new BigDecimal("0.5"))).round(new MathContext("precision=3 roundingMode=HALF_UP"));
                BigDecimal subtracted = mps.subtract(a.revenue);
                if (subtracted.compareTo(new BigDecimal("0")) >= 0) {
                    mps = subtracted;
                }
                a.decreaseCount();
                updatePurchasables();
                updateCenter();
            }
        });
        JButton buy = new JButton("buy");
        buy.addActionListener(e -> {
            if(money.compareTo(a.cost) >= 0) {
                mps = mps.add(a.revenue);
                money = money.subtract(a.cost);
                a.increaseCount();
                updatePurchasables();
                updateCenter();
                try {
                    updateUpgrades(fileName, a.count, a);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        buttonPanel.add(sell, BorderLayout.SOUTH);
        buttonPanel.add(buy, BorderLayout.NORTH);
        MONEYMakerRunner.PURCHASABLES.add(buttonPanel, constraints);
    }

    private void run() {
        while (true) {
            if (!mps.equals(new BigDecimal("0")) && mps.compareTo(new BigDecimal("1000")) < 0) {
                money = money.add(new BigDecimal("1"));
            }
            else if(mps.compareTo(new BigDecimal("1000")) >= 0){
                money = money.add(mps);
            }
            MONEY.setText("<html>MONEY: $" + money.round(new MathContext("precision=3 roundingMode=HALF_UP")) + "<br>MPS: $" + mps + "<html>");
            if(isActive) {
                try {
                    int millis = 1000;
                    int nanos = 0;
                    if ((mps.compareTo(new BigDecimal("0")) > 0) && (mps.compareTo(new BigDecimal("1000")) < 0)) {
                        nanos = (int) ((millis / ((double) mps.intValue())) - 1);
                        millis /= mps.intValue();
                    }
                    Thread.sleep(millis, nanos);
                }
                catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
    private void updateCenter() {
        MONEY.setText("<html>MONEY: $" + money.round(new MathContext("precision=3 roundingMode=HALF_UP")) + "<br>MPS: $" + mps + "<html>");
        if (!isActive) {
            Thread t = new Thread(this::run);
            t.start();
            isActive = true;
        }
    }
    private class DrawingPanel extends JPanel implements MouseListener {
        private int iconSize = 500;
        private int iconX = 0;
        private int iconY = 0;
        public boolean iconPulsing = false;
        
        public void paintComponent(Graphics g) {
            getContentPane().setBackground(MONEYGREEN);
            try {
                BufferedImage icon = PicManager.changeSize(ImageIO.read(new File("icon.png")), iconSize, iconSize);
                iconX = getContentPane().getWidth() / 6 - icon.getWidth() / 2;
                iconY = getContentPane().getHeight() / 2 - icon.getHeight() / 2;
                g.drawImage(icon, iconX, iconY, null);
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        public void iconPulse() throws InterruptedException, IOException {
            clicks += 1;
            updateUpgrades("cursor", clicks, null);
            System.out.println(clicks);
            int origSize = 500;
            for(double i = 0 ; i <= 2 * Math.PI ; i += (Math.PI / PULSESPEED)) {
                iconSize = (int) (origSize * ((Math.cos(i) + 2) / 3));
                getContentPane().repaint();
                Thread.sleep(1);
            }
            iconPulsing = false;
        }
        public void mouseClicked(MouseEvent e) {
            if(e.getX() > iconX && e.getX() < iconX + iconSize && e.getY() > iconY && e.getY() < iconY + iconSize){
                if (!iconPulsing) {
                    Thread t = new Thread(() ->
                    {
                        try {
                            iconPulsing = true;
                            iconPulse();
                        }
                        catch (InterruptedException | IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                    );
                    t.start();
                }
                if(!iconPulsing) {
                    money = money.add(new BigDecimal(clickPower));
                }
                MONEY.setText("<html>MONEY: $" + money.round(new MathContext("precision=3 roundingMode=HALF_UP")) + "<br>MPS: $" + mps + "<html>");
                getContentPane().repaint();
            }
        }
        public void mousePressed(MouseEvent e) {}
        public void mouseReleased(MouseEvent e) {}
        public void mouseEntered(MouseEvent e) {}
        public void mouseExited(MouseEvent e) {}
    }
    public static void main(String[] args) throws IOException {new MONEYMakerRunner();}
}