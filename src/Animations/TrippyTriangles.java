package Animations;

import LightPanelSystem.LightPanelSystem;
import java.util.ArrayList;
import processing.core.PVector;
import processing.core.PConstants;

public class TrippyTriangles implements Animation {

    private LightPanelSystem applet;

    final int NB_PARTICLES = 130;
    ArrayList<Triangle> triangles;
    Particle[] parts = new Particle[NB_PARTICLES];
    MyColor myColor;

    public TrippyTriangles(LightPanelSystem applet)
    {
        this.applet = applet;
        this.myColor = new MyColor(this.applet);
    }

    public void play()
    {
        myColor.update();
        applet.noStroke();
        applet.fill(0);
        applet.background(0);
        triangles = new ArrayList<Triangle>();
        Particle p1, p2;

        for (int i = 0; i < NB_PARTICLES; i++)
        {
            parts[i].move();
        }

        for (int i = 0; i < NB_PARTICLES; i++)
        {
            p1 = parts[i];
            p1.neighboors = new ArrayList<Particle>();
            p1.neighboors.add(p1);
            for (int j = i+1; j < NB_PARTICLES; j++)
            {
                p2 = parts[j];
                float d = PVector.dist(p1.pos, p2.pos);
                if (d > 0 && d < Particle.DIST_MAX)
                {
                    p1.neighboors.add(p2);
                }
            }
            if(p1.neighboors.size() > 1)
            {
                addTriangles(p1.neighboors);
            }
        }
        drawTriangles();
    }

    void drawTriangles()
    {
        applet.noStroke();
        applet.fill(myColor.R, myColor.G, myColor.B, 60);
        applet.stroke(Math.max(myColor.R-15, 0), Math.max(myColor.G-15, 0), Math.max(myColor.B-15, 0), 60);
        //noFill();
        applet.beginShape(PConstants.TRIANGLES);
        for (int i = 0; i < triangles.size(); i ++)
        {
            Triangle t = triangles.get(i);
            t.display();
        }
        applet.endShape();
    }

    void addTriangles(ArrayList<Particle> p_neighboors)
    {
        int s = p_neighboors.size();
        if (s > 2)
        {
            for (int i = 1; i < s-1; i ++)
            {
                for (int j = i+1; j < s; j ++)
                {
                    triangles.add(new Triangle(this.applet, p_neighboors.get(0).pos, p_neighboors.get(i).pos, p_neighboors.get(j).pos));
                }
            }
        }
    }

    public ColorMode getColorMode()
    {
        return ColorMode.RGB;
    }

    public void setup()
    {
        for (int i = 0; i < NB_PARTICLES; i++)
        {
            parts[i] = new Particle(this.applet);
        }
    }

    public void prepare()
    {

    }

    public void cleanup()
    {

    }

}

class MyColor
{
    float R, G, B, Rspeed, Gspeed, Bspeed;
    final static float minSpeed = .7f;
    final static float maxSpeed = 1.5f;


    LightPanelSystem applet;
    MyColor(LightPanelSystem applet)
    {
        this.applet = applet;
        init();
    }

    public void init()
    {
        R = applet.random(255);
        G = applet.random(255);
        B = applet.random(255);
        Rspeed = (applet.random(1) > .5 ? 1 : -1) * applet.random(minSpeed, maxSpeed);
        Gspeed = (applet.random(1) > .5 ? 1 : -1) * applet.random(minSpeed, maxSpeed);
        Bspeed = (applet.random(1) > .5 ? 1 : -1) * applet.random(minSpeed, maxSpeed);
    }

    public void update()
    {
        Rspeed = ((R += Rspeed) > 255 || (R < 0)) ? -Rspeed : Rspeed;
        Gspeed = ((G += Gspeed) > 255 || (G < 0)) ? -Gspeed : Gspeed;
        Bspeed = ((B += Bspeed) > 255 || (B < 0)) ? -Bspeed : Bspeed;
    }
}

class Particle
{
    final static float RAD = 4f;
    final static float BOUNCE = -1f;
    final static float SPEED_MAX = 2.2f;
    final static float DIST_MAX = 50f;
    PVector speed;
    PVector acc = new PVector(0, 0);
    PVector pos;
    //neighboors contains the particles within DIST_MAX distance, as well as itself
    ArrayList<Particle> neighboors;

    LightPanelSystem applet;
    public Particle(LightPanelSystem applet)
    {
        this.applet = applet;
        pos = new PVector (applet.random(applet.width), applet.random(applet.height));
        speed = new PVector(applet.random(-SPEED_MAX, SPEED_MAX), applet.random(-SPEED_MAX, SPEED_MAX));
    }

    public void move()
    {
        pos.add(speed);

        acc.mult(0);

        if (pos.x < 0)
        {
            pos.x = 0;
            speed.x *= BOUNCE;
        }
        else if (pos.x > applet.width)
        {
            pos.x = applet.width;
            speed.x *= BOUNCE;
        }
        if (pos.y < 0)
        {
            pos.y = 0;
            speed.y *= BOUNCE;
        }
        else if (pos.y > applet.height)
        {
            pos.y = applet.height;
            speed.y *= BOUNCE;
        }
    }

    public void display()
    {
        applet.fill(255, 25);
        applet.ellipse(pos.x, pos.y, RAD, RAD);
    }
}

class Triangle
{
    PVector A, B, C;

    LightPanelSystem applet;

    Triangle(LightPanelSystem applet, PVector p1, PVector p2, PVector p3)
    {
        this.applet = applet;
        A = p1;
        B = p2;
        C = p3;
    }

    public void display()
    {
        applet.vertex(A.x, A.y);
        applet.vertex(B.x, B.y);
        applet.vertex(C.x, C.y);
    }
}
