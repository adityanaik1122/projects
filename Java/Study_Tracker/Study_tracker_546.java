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

class Study_tracker_546
{
    public static void main(String A[])
    {
       StudyLog sobj1 = new StudyLog("C",2.5,"Pointer and Array");
       StudyLog sobj2 = new StudyLog("C++",3.5,"Polymorphism");
       StudyLog sobj3 = new StudyLog("Python",4,"Titanic case study");

       System.out.println(sobj1);
       System.out.println(sobj2);
       System.out.println(sobj3);
    }
}