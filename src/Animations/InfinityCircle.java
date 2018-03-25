package Animations;

import LightPanelSystem.LightPanelSystem;
import java.util.ArrayList;

public class InfinityCircle implements Animation {
    private LightPanelSystem applet;

    ArrayList<Circle> circles;

    float i = 0f, speed = 5f;
    int numCircles = 6;
    int maxFrame;
    float frameCounter = 0f;
    float stroke = 1f;


    public InfinityCircle(LightPanelSystem applet) {
        this.applet = applet;
    }

    //TODO: refactor so that new objects are not created and destroyed, but just scale up and down and reset to 0 values accordingly
    public void play() {
        applet.background(20);
        applet.stroke(240);
        applet.noFill();
        stroke = 20;// * (frameCounter / maxFrame);
        applet.strokeWeight(stroke);

        for (int j = 0; j < circles.size(); j++) {
            // TODO: figure out how to get strokeweight to align with radius
//            applet.strokeWeight( 40 * (circles.get(j).radius / maxFrame ));
            applet.ellipse(applet.width / 2, applet.height / 2, circles.get(j).radius , circles.get(j).radius);

            circles.get(j).radius += speed;
            if (circles.get(j).radius > maxFrame) {
                System.out.println("removing circle");
                circles.remove(j);
//                circles.add(new Circle());
            }
        }

        frameCounter += speed;

        if ((frameCounter > (maxFrame / numCircles)) && (circles.size() < numCircles)) {
            frameCounter = 0;
            circles.add(new Circle());
        }

    }

    public ColorMode getColorMode() {
        return ColorMode.RGB;
    }

    public void setup() {
        maxFrame = applet.width > applet.height ? applet.width : applet.height;
        maxFrame *= 1.1;
    }

    public void prepare() {
        circles = new ArrayList<Circle>();
        circles.add(new Circle());
    }

    public void cleanup() {
        for (int j = 0; j < circles.size(); j++) {
            circles.remove(j);
        }
    }

    private class Circle
    {
        public int radius = 0;
    }
}