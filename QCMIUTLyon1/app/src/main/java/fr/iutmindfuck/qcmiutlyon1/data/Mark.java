package fr.iutmindfuck.qcmiutlyon1.data;

public class Mark
{
    private int idMCQ;
    private int idStudent;
    private float value;

    public Mark(int idMCQ, int idStudent, float value)
    {
        this.idStudent = idStudent;
        this.idMCQ = idMCQ;
        this.value = value;
    }

    public int getIdMCQ(){ return idMCQ; }
    public int getIdStudent(){ return idStudent; }
    public float getValue(){ return value; }
}
