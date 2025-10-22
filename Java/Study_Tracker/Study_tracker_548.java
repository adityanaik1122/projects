import java.util.*;

class StudyLog
{
    public String Subject;
    public double Duration;
    public String Desscription;

    public StudyLog(String A, double B, String C)
    {
        this.Subject =A;
        this.Duration = B;
        this.Desscription = C;
    }

  
    @Override
    public String toString()
    {
        return Subject+ " | "+Duration+" | "+Desscription;
    }
}

class Study_tracker_548
{
    public static void main(String A[])
    {
       StudyLog sobj1 = new StudyLog("C",2.5,"Pointer and Array");
       StudyLog sobj2 = new StudyLog("C++",3.5,"Polymorphism");
       StudyLog sobj3 = new StudyLog("Python",4,"Titanic case study");
       StudyLog sobj4 = new StudyLog("C",4.4,"Pointer and Array");
       StudyLog sobj5 = new StudyLog("C++",2.8,"Polymorphism");

        ArrayList <StudyLog> aobj = new ArrayList <StudyLog> ();

        aobj.add(sobj1);
        aobj.add(sobj2);
        aobj.add(sobj3);
        aobj.add(sobj4);
        aobj.add(sobj5);

        for( StudyLog sobj : aobj)
        {
            if((sobj.Subject).equals("C"))
            System.out.println(sobj);
        }
    }
}