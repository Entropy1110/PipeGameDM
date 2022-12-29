package nyc.pipegame;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class GameImage {
    private static List<String> imageIconList = new ArrayList<>();


    static {
        imageIconList.add("img/1.png");
        imageIconList.add("img/2.png");
        imageIconList.add("img/3.png");
        imageIconList.add("img/4.png");

        imageIconList.add("img/1-1.png");
        imageIconList.add("img/2-1.png");
        imageIconList.add("img/3-1.png");
        imageIconList.add("img/4-1.png");

        imageIconList.add("img/red-1.png");
        imageIconList.add("img/red-2.png");
        imageIconList.add("img/red-3.png");
    }
    private final String path;
    private int idx;
    private ImageIcon imageIcon;
    private Image image;
    private int degree; //degree * 90
    private int[][] pipeArr;
    public boolean colored = false;

    private MainGUI mainGUI;
    public GameImage(int idx, MainGUI mainGUI){
        this.idx = idx;
        this.path = imageIconList.get(idx);
        this.imageIcon = new ImageIcon(path);
        this.image = imageIcon.getImage();
        degree = 0;
        this.mainGUI = mainGUI;
        setPipeArr();
    }

    public int getIdx() {
        return idx;
    }



    public ImageIcon getImageIcon(){
        return imageIcon;
    }


    public void color(){
        if (colored || idx >= 8) return;
        this.imageIcon = new ImageIcon(imageIconList.get(this.idx + 4));
        this.image = this.imageIcon.getImage();
        int k = degree % 4;
        for (int i = 0; i < k; i++) {
            rotate();
            degree--;
        }
        colored = true;
    }

    public void discolor(){
        if (!colored || idx >= 8) return;
        this.imageIcon = new ImageIcon(imageIconList.get(this.idx));
        this.image = this.imageIcon.getImage();
        int k = degree % 4;
        for (int i = 0; i < k; i++) {
            rotate();
            degree--;
        }
        colored = false;
    }

    public void rotate(){
        BufferedImage bufferedImage = getRotateImage(imageToBufferedImage(image), 90);
        this.image = bufferedImage;
        this.imageIcon = new ImageIcon(this.image);
        degree++;

    }

    public void rotateArr() {
        int arr[][] = this.pipeArr.clone();
        for (int i = 0; i < 3; i++){
            this.pipeArr[i] = new int[]{arr[2][i], arr[1][i], arr[0][i]};
        }
    }

    //-1 : end, 2: start

    private void setPipeArr() {
        int idx = imageIconList.indexOf(this.path);
        switch (idx){
            case 0:
            case 4:
                this.pipeArr = new int[][]{
                        {0, 1, 0},
                        {0, -1, 0},
                        {0, 0, 0}
                };
                break;
            case 1:
            case 5:
                this.pipeArr = new int[][]{
                        {0, 1, 0},
                        {0, 1, 0},
                        {0, 1, 0}
                };
                break;
            case 2:
            case 6:
                this.pipeArr = new int[][]{
                        {0, 1, 0},
                        {1, 1, 1},
                        {0, 0, 0}
                };
                break;
            case 3:
            case 7:
                this.pipeArr = new int[][]{
                        {0, 0, 0},
                        {0, 1, 1},
                        {0, 1, 0}
                };
                break;

            case 8:
                this.pipeArr = new int[][]{
                        {0, 1, 0},
                        {0, 2, 0},
                        {0, 1, 0}
                };
                break;
            case 9:
                this.pipeArr = new int[][]{
                    {0, 1, 0},
                    {1, 2, 1},
                    {0, 0, 0}
            };
            break;
            case 10:
                this.pipeArr = new int[][]{
                        {0, 0, 0},
                        {0, 2, 1},
                        {0, 1, 0}
                };
                break;

        }
    }


    public int[][] getPipeArr(){
        return pipeArr;
    }

    private BufferedImage getRotateImage(BufferedImage image, double angle){//angle : degree

        double _angle = Math.toRadians(angle);
        double sin = Math.abs(Math.sin(_angle));
        double cos = Math.abs(Math.cos(_angle));
        double w = image.getWidth();
        double h = image.getHeight();
        int newW = (int)Math.floor(w*cos + h*sin);
        int newH = (int)Math.floor(w*sin + h*cos);

        GraphicsConfiguration gc = mainGUI.getGraphicsConfiguration();
        BufferedImage result = gc.createCompatibleImage(newW, newH, Transparency.TRANSLUCENT);
        Graphics2D g = result.createGraphics();

        g.translate((newW-w)/2, (newH-h)/2);
        g.rotate(_angle, w/2, h/2);
        g.drawRenderedImage(image, null);
        g.dispose();

        return result;
    } //이미지 회전 함수 출처 : http://egloos.zum.com/icegeo/v/300628

    private BufferedImage imageToBufferedImage(Image im) {
        BufferedImage bi = new BufferedImage
                (im.getWidth(null),im.getHeight(null),BufferedImage.TYPE_INT_RGB);
        Graphics bg = bi.getGraphics();
        bg.drawImage(im, 0, 0, null);
        bg.dispose();
        return bi;
    }

    //angle = 40 -> java.lang.OutOfMemoryError: heap space
    //https://docs.oofbird.me/java/article/java-memory-management.html#%E1%84%86%E1%85%A6%E1%84%86%E1%85%A9%E1%84%85%E1%85%B5-%E1%84%91%E1%85%AE%E1%86%AF
}
