package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;

public class Main extends Application {

    static final int WIDTH = 800;
    static final int HEIGHT = 600;
    static  final int ANT_COUNT = 100;
    static ArrayList<Ant> ants = new ArrayList<Ant>();
    static Color black = Color.BLACK;
    static Color red = Color.RED;
    static long lastTimeStamp = 0;     //needed for calculating the FPS of the program

    static void createAnts(){
        for (int i = 0; i<ANT_COUNT; i++){
            Random r = new Random();
            Ant a = new Ant(r.nextInt(WIDTH), r.nextInt(HEIGHT));
            ants.add(a);
        }
    }

    static void drawAnts(GraphicsContext context){  //this will draw the points.
        context.clearRect(0,0, WIDTH, HEIGHT);  //clears the scene
        for (Ant ant: ants){
            aggravateAnt();
            context.fillOval(ant.x, ant.y, 5, 5);
        }
    }

    static void aggravateAnt(){
        for(Ant ant : ants ){
            for (Ant ant2: ants){
                int diffX;
                int diffY;
                if (Math.abs(ant.x-ant2.x)<10  || Math.abs(ant.y-ant2.y)<10){
                    ant2.setColor(red);
                } else{
                    ant2.setColor(black);
                }
            }
        }
    }

    static Ant moveAnt(Ant ant){
        ant.x += Math.random() * 2-1;
        ant.y += Math.random() * 2-1;
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ant;
    }

    static void moveAnts(){
        ants = ants.parallelStream()
                .map(Main::moveAnt)
                .collect(Collectors.toCollection(ArrayList<Ant>::new));
    }

    static int fps(long currentTimeStamp){
        double diff = currentTimeStamp - lastTimeStamp;
        double diffSeconds = diff/1000000000;
        return (int) (1/diffSeconds);
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("main.fxml"));
        primaryStage.setTitle("omg ANTS Everywhere!!!");
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();

        Canvas canvas = (Canvas) primaryStage.getScene().lookup("#canvas");
        GraphicsContext context = canvas.getGraphicsContext2D();
        Label fpsLabel = (Label) primaryStage.getScene().lookup("#fps");

        createAnts();

        AnimationTimer timer = new AnimationTimer() {
            @Override                                   //Anonymous class used to extend and instantiate a class at the same time
            public void handle(long now) {
                moveAnts();
                drawAnts(context);

                fpsLabel.setText(fps(now) + "");
                lastTimeStamp = now;
            }
        };
        timer.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
