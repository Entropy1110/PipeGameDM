package nyc.pipegame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GameImageManager {

    public static MainGUI mainGUI;

    public static GameImage getGameImage(int idx){
        GameImage image = new GameImage(idx, mainGUI);
        return image;
    }

}
