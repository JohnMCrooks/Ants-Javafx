package sample;


import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Created by johncrooks on 7/14/16.
 */
public class Ant {
    double x;
    double y;
    Color color;

    public Ant(double x, double y, Color color) {
        this.x = x;
        this.y = y;
        this.color = Color.BLACK;
    }

    public Ant(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setColor(Color color) {
        this.color = color;
    }

}
