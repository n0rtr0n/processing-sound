package Animations;

import LightPanelSystem.LightPanelSystem;

public class Spiral implements Animation {

    private LightPanelSystem applet;

    float segments = 12;
    int centX, centY;
    float lastX, lastY;
    int col = 100;
    int colInc = 1;
    float rot = 0.0f; // rotate by this each time
    int interval = 30 * 60;

    public Spiral(LightPanelSystem applet)
    {
        this.applet = applet;
    }

    public void play()
    {
        float offset = 0.0f; // Offset each iteration by this
        applet.background(0);
        float bigRadius = 1;
        float smallRadius = 2;
        applet.stroke(col, 0, 0);
        segments = 8;

        for (int i = 0 ; i <  100; i ++)
        {
            float thetaInc = applet.TWO_PI / segments;
            for (float theta = 0 ; theta < applet.TWO_PI ; theta += thetaInc)
            {
                float x = (float) (centX + Math.sin(theta + offset + rot) * bigRadius);
                float y = (float) (centY + Math.cos(theta + offset + rot) * bigRadius);
                applet.fill(col, 0, applet.random(0, 255));
                applet.stroke(col, 0, applet.random(0, 255));

                //line(lastX, lastY, x, y);
                lastX = x;
                lastY = y;
                applet.ellipse(x, y, smallRadius * 2.0f, smallRadius * 2.0f);
            }
            bigRadius += 10f;
            smallRadius += 0.4f;
            offset += 0.2f;
        }
        if ((col > 255) || (col < 100))
        {
            colInc = - colInc;
        }
        col += colInc;
        rot -= 0.01f; //(float)(mouseY - centY) / ((float) height * 10.0f);
    }

    public ColorMode getColorMode()
    {
        return ColorMode.RGB;
    }

    public void setup()
    {

    }

    public void prepare()
    {

    }

    public void cleanup()
    {

    }

}
