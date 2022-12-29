package nyc.pipegame;

import java.util.ArrayList;

public class PipeNode {
    ArrayList<PipeNode> nodeList = new ArrayList<>();
    GameImage pipe;
    int x, y;
    int c; // -1 leaf, 2 root, 1 default
    public PipeNode(int x, int y, GameImage pipe, int c){
        this.x = x;
        this.y = y;
        this.pipe = pipe;
        this.c = c;
    }

    public void connectNode(PipeNode pn){
        if (!nodeList.contains(pn))
            nodeList.add(pn);
    }

    public void color(){
        pipe.color();
    }
    public void discolor(){
        pipe.discolor();
    }


}
