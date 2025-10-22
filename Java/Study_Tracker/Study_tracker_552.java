import java.util.*;
import java.time.LocalDate;

class StudyLog
{
    public LocalDate Date;
    public String Subject;
    public double Duration;
    public String Desscription;
    

    public StudyLog(LocalDate A,String B, double C, String D)
    {
        this.Date = A;
        this.Subject =B;
        this.Duration = C;
        this.Desscription = D;
    }

  
    @Override
    public String toString()
    {
        return Date+ " | "+Subject+ " | "+Duration+" | "+Desscription;
    }

    public LocalDate getDate()
    {
        return Date;
    } 

    public String getSubject()
    {
        return Subject;
    }
    public double getDuration()
    {
        return Duration;
    }

    public String getDescription()
    {
        return Desscription;
    }
}

class Study_tracker_552
{
    public static void main(String A[])
    {
        
        StudyLog sobj1 = new StudyLog(LocalDate.now(),"C",2.5,"Pointer and Array");
        StudyLog sobj2 = new StudyLog(LocalDate.now(),"C++",3.5,"Polymorphism");
        StudyLog sobj3 = new StudyLog(LocalDate.now(),"Python",4,"Titanic case study");
        StudyLog sobj4 = new StudyLog(LocalDate.now(),"C",4.4,"Pointer and Array");
        StudyLog sobj5 = new StudyLog(LocalDate.now(),"C++",2.8,"Polymorphism");

        ArrayList <StudyLog> aobj = new ArrayList <StudyLog> ();

        aobj.add(sobj1);
        aobj.add(sobj2);
        aobj.add(sobj3);
        aobj.add(sobj4);
        aobj.add(sobj5);

        for( StudyLog sobj : aobj)
        {
            System.out.println("-----------------");
            System.out.println(sobj.getSubject());
            System.out.println(sobj.getDuration());
            System.out.println(sobj.getDate());
            System.out.println("-----------------");
            
        }
    }
}