package Animations;

import LightPanelSystem.LightPanelSystem;

public class BubbleSine implements Animation {

    LightPanelSystem applet;

    // Bubble Sine animation
    int size     = 0;
    int location = 0;
    float opacity = 0;
    float thisloop = 0;
    float ellipsesize = 0;
    float transparency = 0;

    int numloop = 10;

    float counter = 0;

    float[] lastvalue = new float[1000];
    float[] spaceplace = new float[1000];
    int[] lastsmaller = new int[1000];

    float minvalue = 1000;

    float hue = 0;

    int ystart, xstart, xSpeed;

    //TODO: import Math library
    public BubbleSine(LightPanelSystem applet)
    {
        this.applet = applet;
    }

    public void play()
    {
        ystart = applet.height / 6;
        xstart = 10;

        xSpeed = 4;
        applet.background(0);


        counter = (float) (counter + 0.01);

        numloop = (applet.width)/10;

//        colorMode(HSB,360,50,80);

        hue = (float) ( hue + 0.1) ;
        if (hue > 360) {hue = 0;}

        for (int i = 0; i < numloop; i = i+1) {
            thisloop = applet.displayHeight/3 + applet.sin(i*counter)*applet.displayHeight/3;

            if (thisloop < minvalue) {minvalue = thisloop;}

            if (spaceplace[i] == 0) { spaceplace[i] = 1; } // initiate the spaceplace
            if (thisloop < lastvalue[i]) {lastsmaller[i] = 1; } // if this loop is bigger than the last one, set lastsmaller to 1

            lastvalue[i] = thisloop; // set lastvalue;

            transparency = (150 + applet.cos(i*counter) * 105 * spaceplace[i]);

            applet.fill(applet.round(hue),100,100,transparency);


            ellipsesize = (float) (20 + (applet.width/numloop) + applet.cos(i*counter) * 0.5 * (applet.width/numloop) * spaceplace[i]);
            applet.ellipse((float) (xstart+i*(applet.width/numloop)* xSpeed),ystart+thisloop,ellipsesize,ellipsesize);


            if (lastsmaller[i] == 1) // if the previous loop was smaller than the last one..
            {
                if (thisloop > lastvalue[i] ) // and if this loop is bigger than the last one..
                {
                    spaceplace[i] = spaceplace[i]*(-1); // flip the bit
                    lastsmaller[i] = 0;
                }
            }

        }
    }

    public ColorMode getColorMode()
    {
        return ColorMode.RGB;
    }

    public void prepare()
    {

    }

    public void setup()
    {

    }

    public void cleanup()
    {

    }

}
