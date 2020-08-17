public class Tile
{
    private String state;
    private int number;
    private String originalState;
    public Tile()
    {

    }
    public void setState(String state)
    {
        this.state = state;
        if(!isFlagged())
            originalState = state;
    }
    public String getState()
    {
        return state;
    }
    public void setNumber(int number)
    {
        this.number = number;
    }
    public int getNumber()
    {
        return number;
    }
    public String getOriginalState()
    {
        return originalState;
    }
    public boolean isBomb()
    {
        return (getState().equals("Bomb"));
    }
    public boolean isNumbered()
    {
        return (getNumber()>0 && !isBomb());
    }
    public boolean isEmpty()
    {
        return (!isNumbered() && !isBomb());
    }
    public boolean isFlagged()
    {
        return (getState().equals("Flag"));
    }
}