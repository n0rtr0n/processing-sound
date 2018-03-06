package Animations;

public class ColorWheel {

    private int red, green, blue;

    private int lastColorChangeAt;

    public void setRed(int red)
    {
        this.red = red;
    }

    public int getRed()
    {
        return this.red;
    }

    public void setGreen(int green)
    {
        this.green = green;
    }

    public int getGreen()
    {
        return this.green;
    }

    public void setBlue(int blue)
    {
        this.blue = blue;
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
