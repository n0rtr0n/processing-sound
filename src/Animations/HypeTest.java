package Animations;

import LightPanelSystem.LightPanelSystem;
import hype.H;
import hype.HRect;
import hype.extended.behavior.HRotate;

public class HypeTest implements Animation {
    private LightPanelSystem applet;
    private HRect d1, d2, d3, d4, d5;

    public HypeTest(LightPanelSystem applet) {
        this.applet = applet;
    }

    public void play() {
        H.drawStage();
    }

    public ColorMode getColorMode() {
        return ColorMode.RGB;
    }

    public void setup() {
        d1 = new HRect();
        d1.size(50)
            .rotation(45)
            .anchorAt(H.CENTER)
            .loc(100, applet.height / 2);
        HRotate r1 = new HRotate().target(d1).speedZ(-1);
        H.add(d1);

        d2 = new HRect();
        d2.size(50)
            .rotation(45)
            .anchorAt(H.CENTER)
            .loc(200, applet.height / 2);
        HRotate r2 = new HRotate().target(d2).speedZ(1);
        H.add(d2);

        d3 = new HRect();
        d3.size(50)
            .rotation(45)
            .anchorAt(H.CENTER)
            .loc(300, applet.height / 2);
        HRotate r3 = new HRotate().target(d3).speedZ(2);
        H.add(d3);

        d4 = new HRect();
        d4.size(50)
            .rotation(45)
            .anchorAt(H.CENTER)
            .loc(400, applet.height / 2);
        HRotate r4 = new HRotate().target(d4).speedZ(3);
        H.add(d4);
    }

    public void prepare()
    {

    }

    public void cleanup() {

    }
}