package Animations;

import java.awt.*;

public class ColorWheel {

    private int red, green, blue;

    private int lastColorChangeAt;

    public ColorWheel setRed(int red)
    {
        this.red = red;
        return this;
    }

    public int getRed()
    {
        return this.red;
    }

    public ColorWheel setGreen(int green)
    {
        this.green = green;
        return this;
    }

    public int getGreen()
    {
        return this.green;
    }

    public ColorWheel setBlue(int blue)
    {
        this.blue = blue;
        return this;
    }

    public int getBlue()
    {
        return this.blue;
    }

    public void setLastColorChangeAt(int lastColorChangeAt)
    {
        this.lastColorChangeAt = lastColorChangeAt;
    }

    public int getLastColorChangeAt()
    {
        return this.lastColorChangeAt;
    }

}
