package fr.iutmindfuck.qcmiutlyon1.data;

public class Mark
{
    private final int idMCQ;
    private final String username;
    private final float value;

    public Mark(int idMCQ, String username, float value)
    {
        this.idMCQ = idMCQ;
        this.username = username;
        this.value = value;
    }

    public int getIdMCQ(){ return idMCQ; }
    public String getUsername(){ return username; }
    public float getValue(){ return value; }
}
