public class Grid
{
    private int width=0;
    private int height=0;
    private Tile[][] tiles;
    private Tile[][] original;
    private int numOfMines=0;
    private int mines=0;
    private String gameTheme;
    public Grid(int width, int height, int numOfMines, String gameTheme)
    {
        this.width = width;
        this.height = height;
        tiles = new Tile[width][height];
        mines = numOfMines;
        this.numOfMines = numOfMines;
        this.gameTheme=gameTheme;
    }
    public void setTiles()
    {
        for(int x=0;x<width;x++)
        {
            for(int y=0;y<height;y++)
            {
                tiles[x][y] = new Tile();
                tiles[x][y].setState("Empty");
                tiles[x][y].setNumber(0);
            }
        }
    }
    private void setBombs(int x, int y)
    {  
        tiles[x][y].setState("Empty");
        while(numOfMines>0)
        {
            int r = (int)(Math.random()*(width));
            int c = (int)(Math.random()*(height));
            if(tiles[r][c].getState().equals("Empty") && (Math.abs(x-r)>1 || Math.abs(y-c)>1))
            {
                tiles[r][c].setState("Bomb");
                tiles[r][c].setNumber(-1);
                numOfMines--;
            }
        }
        original = tiles;
    }
    public String getTheme()
    {
        return gameTheme;
    }
    public void setWidth(int width)
    {
        this.width = width;
    }
    public void setHeight(int height)
    {
        this.height=height;
    }
    public int getWidth()
    {
        return width;
    }
    public int getHeight()
    {
        return height;
    }
    public void setMines(int numOfMines)
    {
        this.numOfMines=numOfMines;
    }
    public int getMines()
    {
        return mines;
    }
    public void setNumbers(int x, int y)
    {
        setBombs(x, y);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (original[i][j].isBomb()) {
                        for (int k = -1; k <= 1 ; k++) {
                        for (int l = -1; l <= 1; l++) {
                            // In boundary cases
                            try {
                                if (!original[i+k][j+l].isBomb()) {
                                    tiles[i+k][j+l].setNumber(tiles[i+k][j+l].getNumber()+1);
                                }
                            }
                            catch (Exception e) {
                                // Do nothing
                            }
                        }
                        }
                }
            }
            }
    }
    public Tile[][] getTiles()
    {
        return tiles;
    }
}