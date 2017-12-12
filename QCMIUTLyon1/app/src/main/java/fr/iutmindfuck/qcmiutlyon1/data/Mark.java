package fr.iutmindfuck.qcmiutlyon1.data;

/**
 * Created by sntri on 12/12/2017.
 */

public class Mark
{
    private int idMCQ, idStudent;
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

    public void setIdMCQ(int idMCQ){ this.idMCQ = idMCQ; }
    public void setIdStudent(int idStudent){ this.idStudent = idStudent; }
    public void setValue(float value){ this.value = value; }
}
