package Animations;

import LightPanelSystem.LightPanelSystem;

import java.awt.*;
import java.util.ArrayList;

public class InfinityRectangle implements Animation {
    private LightPanelSystem applet;

    ArrayList<Rectangle> rectangles;

    float i = 0f, speed = 5f;
    int numRectangles = 12;
    int maxFrame;
    float frameCounter = 0f;
    float stroke = 10f;

    public InfinityRectangle(LightPanelSystem applet) {
        this.applet = applet;
    }

    public void play() {
        applet.background(20);
        applet.stroke(240);
        applet.noFill();
        //stroke = 100 * (frameCounter / maxFrame);
        applet.strokeWeight(stroke);

        for (int j = 0; j < rectangles.size(); j++) {
            // TODO: figure out how to get strokeweight to align with radius
//            applet.strokeWeight( 40 * (circles.get(j).radius / maxFrame ));

            applet.rect(rectangles.get(j).x, rectangles.get(j).y, rectangles.get(j).w , rectangles.get(j).h);

            rectangles.get(j).x -= speed;
            rectangles.get(j).y -= speed * .25;
            rectangles.get(j).w += 2 * speed;
            rectangles.get(j).h += 2 * speed * .25;

//            rectangles.get(j).radius += speed;
            if (rectangles.get(j).x < -15 && rectangles.get(j).y < -15) {
                rectangles.remove(j);
//                rectangles.add(newRectangle());
            }
        }

        frameCounter += speed;

        if ((frameCounter > (maxFrame / numRectangles)) && (rectangles.size() < numRectangles)) {
            frameCounter = 0;
            rectangles.add(newRectangle());
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
        rectangles = new ArrayList<Rectangle>();
        rectangles.add(newRectangle());
    }

    public void cleanup() {
        for (int j = 0; j < rectangles.size(); j++) {
            rectangles.remove(j);
        }
    }

    public Rectangle newRectangle()
    {
        Rectangle rect = new Rectangle();
        rect.x = applet.width / 2;
        rect.y = applet.height / 2;
        rect.w = 4;
        rect.h = 1;
        return rect;
    }

    private class Rectangle
    {
        public float x, y, w, h;
    }
}