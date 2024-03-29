package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;


import javafx.scene.control.Label;
import java.util.Timer;
import java.util.TimerTask;

public class Main extends Application {
    // Map
    public static Rectangle map = new Rectangle(600, 350);
    // P1
    public static Rectangle p1 = new Rectangle(50, 50);
    // P2
    public static Rectangle p2 = new Rectangle(50, 50);
    // Pane
    public static Pane root = new Pane();

    // Bullet TranslateTransition
    public static TranslateTransition p1BulletTransition = new TranslateTransition();
    public static TranslateTransition p2BulletTransition = new TranslateTransition();
    // Recharge Line TranslateTransition
    public static TranslateTransition p1RechargeTransition = new TranslateTransition();
    public static TranslateTransition p2RechargeTransition = new TranslateTransition();

    // Animation Timer
    public static AnimationTimer gameLoop;

    // Bullet Desired X
    public static double p1DesiredX = 700;
    public static double p2DesiredX = -700;

    // Player Movement Coordinates
    public static double p1NewY;
    public static double p2NewY;

    // Bullet Arrays
    public static Rectangle[] p1Bullet = new Rectangle[999];
    public static Rectangle[] p2Bullet = new Rectangle[999];
    // Bullet Array Counters
    public static int p1BulletCounter = 0;
    public static int p2BulletCounter = 0;

    // Lives
    public static int p1Lives = 3;
    public static int p2Lives = 3;

    // Recharge Bars
    public static Rectangle p1Recharge = new Rectangle(100, 30);
    public static Rectangle p2Recharge = new Rectangle(100, 30);

    // Recharge Booleans
    public static boolean p1Ammo = true;
    public static boolean p2Ammo = true;

    // Recharge Fillings
    public static Rectangle[] p1AmmoFill = new Rectangle[999];
    public static Rectangle[] p2AmmoFill = new Rectangle[999];
    // Recharge Fillings Counters
    public static int p1AmmoFillCounter = 0;
    public static int p2AmmoFillCounter = 0;

    // Recharge Filling Line
    public static Line[] p1AmmoLine = new Line[999];
    public static Line[] p2AmmoLine = new Line[999];
    // Recharge Filling Line Counter
    public static int p1AmmoLineCounter = 0;
    public static int p2AmmoLineCounter = 0;

    // Pause Boolaen
    public static boolean pause = false;

    // Pause Bars
    public static Rectangle pauseBar1 = new Rectangle(50, 200, Color.GREY);
    public static Rectangle pauseBar2 = new Rectangle(50, 200, Color.GREY);


    // Timer loop
    public class clock extends Pane{
        Timeline animation;
        int secondsPass = 0;
        String S = " ";
        String tempSecs = "";
        String tempMins = "";
        int mins = 0;


        Label time = new Label("00:00");

        private clock(){

            time.setFont(Font.font(100));
            time.setTranslateX(305);
            time.setTranslateY(400);

            getChildren().add(time);
            animation = new Timeline(new KeyFrame(Duration.seconds(1), e-> timelabel()));
            animation.setCycleCount(Timeline.INDEFINITE);
            animation.play();
        }
        private void timelabel(){
            if (secondsPass >= 0){
                secondsPass++;
                if(mins <10){
                    tempMins = "0" + mins;
                    S = tempMins + ":" + secondsPass ;
                }
                if(secondsPass < 10){
                    tempSecs = "0" + secondsPass;
                    S = tempMins +":" + tempSecs;
                }
                if(secondsPass % 60 == 0){
                    secondsPass = 0;
                    mins++;
                    tempMins = mins + "";
                }
                time.setText(S);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        // P1 Bullet Initialization
        for (int i = 0; i < p1Bullet.length; i++) {
            p1Bullet[i] = new Rectangle(30, 30);
        }
        // P2 Bullet Initialization
        for (int j = 0; j < p1Bullet.length; j++) {
            p2Bullet[j] = new Rectangle(30, 30);
        }

        // P1 Bullet Ammo Fill Initialization
        for (int i = 0; i < p1AmmoFill.length; i++) {
            p1AmmoFill[i] = new Rectangle(1, 30);
        }

        // P2 Bullet Ammo Fill Initialization
        for (int i = 0; i < p1AmmoFill.length; i++) {
            p2AmmoFill[i] = new Rectangle(1, 30);
        }

        // P1 Ammo Fill Line Initialization
        for (int i = 0; i < p1AmmoLine.length; i++) {
            p1AmmoLine[i] = new Line(150, 465, 150, 495);
        }

        // P2 Ammo Fill Line Initialization
        for (int i = 0; i < p1AmmoLine.length; i++) {
            p2AmmoLine[i] = new Line(750, 465, 750, 495);
        }

        // Scene
        Scene scene = new Scene(root, 900, 600);

        // Application Title
        primaryStage.setScene(scene);
        primaryStage.setTitle("HIYA!");
        primaryStage.show();

        // Key Numbers
        int mapCenter = 250;
        int screenCenter = (int) scene.getWidth() / 2;
        int p1Center = 100;
        int p2Center = 800;

        // Map Location
        map.setX(screenCenter - map.getWidth() / 2);
        map.setY(mapCenter - map.getHeight() / 2);
        root.getChildren().add(map);
        map.setFill(Color.TRANSPARENT);
        map.setStroke(Color.BLACK);
        map.setStrokeWidth(2);

        // Player 1 Start Position
        p1.setX(p1Center - p1.getWidth() / 2);
        p1.setY(mapCenter - p1.getHeight() / 2);
        root.getChildren().add(p1);
        p1.setFill(Color.RED);

        // Player 2 Start Position
        p2.setX(p2Center - p2.getWidth() / 2);
        p2.setY(mapCenter - p2.getHeight() / 2);
        root.getChildren().add(p2);
        p2.setFill(Color.BLUE);

        // lives locations
        Rectangle p1Lives1 = new Rectangle(30, 30);
        p1Lives1.setX(map.getX());
        p1Lives1.setY(map.getY() + map.getHeight() + 5);
        root.getChildren().add(p1Lives1);
        p1Lives1.setFill(Color.GREY);

        Rectangle p1Lives2 = new Rectangle(30, 30);
        p1Lives2.setX(p1Lives1.getX() + p1Lives2.getWidth() + 5);
        p1Lives2.setY(map.getY() + map.getHeight() + 5);
        root.getChildren().add(p1Lives2);
        p1Lives2.setFill(Color.GREY);

        Rectangle p1Lives3 = new Rectangle(30, 30);
        p1Lives3.setX(p1Lives2.getX() + p1Lives3.getWidth() + 5);
        p1Lives3.setY(map.getY() + map.getHeight() + 5);
        root.getChildren().add(p1Lives3);
        p1Lives3.setFill(Color.GREY);

        Rectangle p2Lives1 = new Rectangle(30, 30);
        p2Lives1.setX(map.getX() + map.getWidth() - p2Lives1.getWidth());
        p2Lives1.setY(map.getY() + map.getHeight() + 5);
        root.getChildren().add(p2Lives1);
        p2Lives1.setFill(Color.GREY);

        Rectangle p2Lives2 = new Rectangle(30, 30);
        p2Lives2.setX(p2Lives1.getX() - p2Lives2.getWidth() - 5);
        p2Lives2.setY(map.getY() + map.getHeight() + 5);
        root.getChildren().add(p2Lives2);
        p2Lives2.setFill(Color.GREY);

        Rectangle p2Lives3 = new Rectangle(30, 30);
        p2Lives3.setX(p2Lives2.getX() - p2Lives3.getWidth() - 5);
        p2Lives3.setY(map.getY() + map.getHeight() + 5);
        root.getChildren().add(p2Lives3);
        p2Lives3.setFill(Color.GREY);

        // P1 Recharge Bar
        p1Recharge.setX(map.getX());
        p1Recharge.setY(p1Lives1.getY() + p1Lives1.getHeight() + 5);
        p1Recharge.setFill(Color.TRANSPARENT);
        p1Recharge.setStroke(Color.BLACK);
        p1Recharge.setStrokeWidth(1);
        root.getChildren().add(p1Recharge);

        // P2 Recharge Bar
        p2Recharge.setX(p2Lives3.getX());
        p2Recharge.setY(p2Lives3.getY() + p2Lives3.getHeight() + 5);
        p2Recharge.setFill(Color.TRANSPARENT);
        p2Recharge.setStroke(Color.BLACK);
        p2Recharge.setStrokeWidth(1);
        root.getChildren().add(p2Recharge);

        // Event Handler
        scene.addEventHandler(KeyEvent.KEY_PRESSED, event -> {
            // Move Up (P1) Event Handler
            if (event.getCode() == KeyCode.W) {
                if ((p1Lives != 0 && p2Lives != 0) && !pause) {
                    p1NewY = p1.getY() - 15;
                    if (p1NewY > map.getY() && p1NewY < map.getY() + map.getHeight() - p1.getHeight()) {
                        p1.setY(p1NewY);
                    }
                }
            }
            // Move Down (P1) Event Handler
            if (event.getCode() == KeyCode.S) {
                if ((p1Lives != 0 && p2Lives != 0) && !pause) {
                    p1NewY = p1.getY() + 15;
                    if (p1NewY > map.getY() && p1NewY < map.getY() + map.getHeight() - p1.getHeight()) {
                        p1.setY(p1NewY);
                    }
                }
            }
            // Move Up (P2) Event Handler
            if (event.getCode() == KeyCode.UP) {
                if ((p1Lives != 0 && p2Lives != 0) && !pause) {
                    p2NewY = p2.getY() - 15;
                    if (p2NewY > map.getY() && p2NewY < map.getY() + map.getHeight() - p2.getHeight()) {
                        p2.setY(p2NewY);
                    }
                }
            }
            // Move Down (P2) Event Handler
            if (event.getCode() == KeyCode.DOWN) {
                if ((p1Lives != 0 && p2Lives != 0) && !pause) {
                    p2NewY = p2.getY() + 15;
                    if (p2NewY > map.getY() && p2NewY < map.getY() + map.getHeight() - p2.getHeight()) {
                        p2.setY(p2NewY);
                    }
                }
            }
            // Player 1 Bullet Event Handler
            if (event.getCode() == KeyCode.D) {
                if ((p1Lives != 0 && p2Lives != 0) && !pause) {
                    p1Bullet(p1BulletCounter);
                    if (p1Ammo == false) {
                        p1BulletRecharge();
                        root.getChildren().add(p1AmmoFill[p1AmmoFillCounter]);
                    }
                }
            }
            // Player 2 Bullet Event Handler
            if (event.getCode() == KeyCode.LEFT) {
                if ((p1Lives != 0 && p2Lives != 0) && !pause) {
                    p2Bullet(p2BulletCounter);
                    if (p2Ammo == false) {
                        p2BulletRecharge();
                        root.getChildren().add(p2AmmoFill[p2AmmoFillCounter]);
                    }
                }
            }
            // Pause Button
            if (event.getCode() == KeyCode.P) {
                if (pause == false) {
                    gameLoop.stop();
                    pause = true;
                    p1BulletTransition.pause();
                    p2BulletTransition.pause();
                    p1RechargeTransition.pause();
                    p2RechargeTransition.pause();
                    pauseBar1.setX(350);
                    pauseBar1.setY(300 - pauseBar1.getHeight() / 2);
                    pauseBar2.setX(500);
                    pauseBar2.setY(300 - pauseBar1.getHeight() / 2);
                    root.getChildren().add(pauseBar1);
                    root.getChildren().add(pauseBar2);
                } else if (pause == true) {
                    gameLoop.start();
                    p1BulletTransition.play();
                    p2BulletTransition.play();
                    p1RechargeTransition.play();
                    p2RechargeTransition.play();
                    pause = false;
                    root.getChildren().remove(pauseBar1);
                    root.getChildren().remove(pauseBar2);
                }
            }

            // Temporary Start Button
            if (event.getCode() == KeyCode.SPACE) {
                gameLoop.start();
                System.out.println("Game loop has started");
            }
            // Test Print Button
            if (event.getCode() == KeyCode.G) {
                System.out.println(screenCenter);
            }
        });

        // Adding the timer
        clock timer = new clock();
        root.getChildren().add(timer);

        // Animation Timer/Game Loop
        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                // P1 Collision Detection
                if (p1.getBoundsInParent().intersects(p2Bullet[p2BulletCounter].getBoundsInParent())) {
                    root.getChildren().remove(p2Bullet[p2BulletCounter]);
                    p2BulletCounter++;
                    p1Lives--;
                }
                // P2 Collision Detection
                if (p2.getBoundsInParent().intersects(p1Bullet[p1BulletCounter].getBoundsInParent())) {
                    root.getChildren().remove(p1Bullet[p1BulletCounter]);
                    p1BulletCounter++;
                    p2Lives--;
                }

                // P1 Bullet Disappear Check
                if (p1Bullet[p1BulletCounter].getTranslateX() == p1DesiredX) {
                    root.getChildren().remove(p1Bullet[p1BulletCounter]);
                    p1BulletCounter++;
                }
                // P2 Bullet Disappear Check
                if (p2Bullet[p2BulletCounter].getTranslateX() == p2DesiredX) {
                    root.getChildren().remove(p2Bullet[p2BulletCounter]);
                    p2BulletCounter++;
                }

                // Bullet Collision (With Each Other)

//				if (p1Bullet[p1BulletCounter].getTranslateX()
//						+ p1Bullet[p1BulletCounter].getWidth() >= p2Bullet[p2BulletCounter].getX()
//								+ p2Bullet[p2BulletCounter].getTranslateX()) {
//					if (p1Bullet[p1BulletCounter].getY() <= p2Bullet[p2BulletCounter].getY()
//							+ p2Bullet[p2BulletCounter].getHeight()) {
//						root.getChildren().remove(p1Bullet[p1BulletCounter]);
//						p1BulletCounter++;
//						root.getChildren().remove(p2Bullet[p2BulletCounter]);
//						p2BulletCounter++;
//					} else if (p2Bullet[p1BulletCounter].getY() <= p1Bullet[p2BulletCounter].getY()
//							+ p1Bullet[p2BulletCounter].getHeight()) {
//						root.getChildren().remove(p1Bullet[p1BulletCounter]);
//						p1BulletCounter++;
//						root.getChildren().remove(p2Bullet[p2BulletCounter]);
//						p2BulletCounter++;
//					}
//				}

                if (p1Bullet[p1BulletCounter].getBoundsInParent()
                        .intersects(p2Bullet[p2BulletCounter].getBoundsInParent())
                        && p1Bullet[p1BulletCounter].getTranslateX() != 0.0) {
                    root.getChildren().remove(p1Bullet[p1BulletCounter]);
                    p1BulletCounter++;
                    root.getChildren().remove(p2Bullet[p2BulletCounter]);
                    p2BulletCounter++;
                }

                // P1 Lives Check
                if (p1Lives == 2) {
                    root.getChildren().remove(p1Lives3);
                } else if (p1Lives == 1) {
                    root.getChildren().remove(p1Lives2);
                } else if (p1Lives == 0) {
                    root.getChildren().remove(p1Lives1);
                }

                // P2 Lives Check
                if (p2Lives == 2) {
                    root.getChildren().remove(p2Lives3);
                } else if (p2Lives == 1) {
                    root.getChildren().remove(p2Lives2);
                } else if (p2Lives == 0) {
                    root.getChildren().remove(p2Lives1);
                }

                // P1 Recharge Animation
                p1AmmoFill[p1AmmoFillCounter].setWidth(p1AmmoLine[p1AmmoLineCounter].getTranslateX());
                if (p1AmmoFill[p1AmmoFillCounter].getWidth() == p1Recharge.getWidth()) {
                    root.getChildren().remove(p1AmmoFill[p1AmmoFillCounter]);
                    p1AmmoLine[p1AmmoLineCounter].setStartX(p1Recharge.getX());
                    p1AmmoLine[p1AmmoLineCounter].setEndX(p1AmmoLine[p1AmmoLineCounter].getStartX());
                    root.getChildren().remove(p1AmmoLine[p1AmmoLineCounter]);
                    p1AmmoFillCounter++;
                    p1AmmoLineCounter++;
                    p1Ammo = true;
                }

                // P2 Recharge Animation
                p2AmmoFill[p2AmmoFillCounter].setWidth(Math.abs(p2AmmoLine[p2AmmoLineCounter].getTranslateX()));
                p2AmmoFill[p2AmmoFillCounter].setX(
                        p2Recharge.getX() + p2Recharge.getWidth() + p2AmmoLine[p2AmmoLineCounter].getTranslateX());
                if (p2AmmoFill[p2AmmoFillCounter].getWidth() == p2Recharge.getWidth()) {
                    root.getChildren().remove(p2AmmoFill[p2AmmoFillCounter]);
                    p2AmmoLine[p2AmmoLineCounter].setStartX(p2Recharge.getX() + p2Recharge.getWidth());
                    p2AmmoLine[p2AmmoLineCounter].setEndX(p2AmmoLine[p2AmmoLineCounter].getStartX());
                    root.getChildren().remove(p2AmmoLine[p2AmmoLineCounter]);
                    p2AmmoFillCounter++;
                    p2AmmoLineCounter++;
                    p2Ammo = true;
                }

                // Game Over
                if (p1Lives == 0) {
                    gameLoop.stop();
                } else if (p2Lives == 0) {
                    gameLoop.stop();
                }

            }
        };

    }

    public static void p1Bullet(int num) {
        if (p1Ammo) {
            p1Bullet[num].setX(p1.getX() + p1.getWidth() / 2 - p1Bullet[num].getWidth() / 2);
            p1Bullet[num].setY(p1.getY() + p1.getHeight() / 2 - p1Bullet[num].getHeight() / 2);
            p1Bullet[num].setFill(Color.BLACK);
            root.getChildren().add(p1Bullet[num]);
            p1BulletTransition.setDuration(Duration.millis(1000));
            p1BulletTransition.setNode(p1Bullet[num]);
            p1BulletTransition.setToX(p1DesiredX);
            p1BulletTransition.play();
        }
        p1Ammo = false;
    }

    public static void p2Bullet(int num) {
        if (p2Ammo) {
            p2Bullet[num].setX(p2.getX() + p2.getWidth() / 2 - p2Bullet[num].getWidth() / 2);
            p2Bullet[num].setY(p2.getY() + p2.getHeight() / 2 - p2Bullet[num].getHeight() / 2);
            p2Bullet[num].setFill(Color.BLACK);
            root.getChildren().add(p2Bullet[num]);
            p2BulletTransition.setDuration(Duration.millis(1000));
            p2BulletTransition.setNode(p2Bullet[num]);
            p2BulletTransition.setToX(p2DesiredX);
            p2BulletTransition.play();
        }
        p2Ammo = false;
    }

    public static void p1BulletRecharge() {
        p1AmmoFill[p1AmmoFillCounter].setX(p1Recharge.getX());
        p1AmmoFill[p1AmmoFillCounter].setY(p1Recharge.getY());
        p1AmmoFill[p1AmmoFillCounter].setFill(Color.RED);
        p1RechargeTransition.setDuration(Duration.millis(1500));
        p1RechargeTransition.setNode(p1AmmoLine[p1AmmoLineCounter]);
        p1RechargeTransition.setToX(p1Recharge.getWidth());
        p1RechargeTransition.play();

    }

    public static void p2BulletRecharge() {
        p2AmmoFill[p2AmmoFillCounter]
                .setX(p2Recharge.getX() + p2Recharge.getWidth() - p2AmmoFill[p2AmmoFillCounter].getWidth());
        p2AmmoFill[p2AmmoFillCounter].setY(p2Recharge.getY());
        p2AmmoFill[p2AmmoFillCounter].setFill(Color.BLUE);
        p2RechargeTransition.setDuration(Duration.millis(1500));
        p2RechargeTransition.setNode(p2AmmoLine[p2AmmoLineCounter]);
        p2RechargeTransition.setToX(-p2Recharge.getWidth());
        p2RechargeTransition.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}