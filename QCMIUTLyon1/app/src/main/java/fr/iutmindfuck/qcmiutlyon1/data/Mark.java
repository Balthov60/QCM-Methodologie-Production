package fr.iutmindfuck.qcmiutlyon1.data;

public class Mark
{
    private int idMCQ;
    private String username;
    private float value;

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
