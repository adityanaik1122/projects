import java.util.*;
import java.time.LocalDate;


//Done
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

class StudyTracker
{
    // Data structure to hold the data about the study
    private ArrayList <StudyLog> Database = new ArrayList <StudyLog> ();

    public void InsertLog()
    {
        Scanner sc = new Scanner(System.in);
        
        System.out.println("--------------------------------------------------------");
        System.out.println("------Please enter the valid details of your study------");
        System.out.println("--------------------------------------------------------");

        LocalDate dateobj = LocalDate.now();

        System.out.println("Please enter the name of subject you have studied ?");
        String sub = sc.nextLine();

        System.out.println("Please enter the time you have studied ?");
        double dur = sc.nextDouble();
        sc.nextLine();

        System.out.println("Description of subject you have studied ?");
        String desc = sc.nextLine();

        StudyLog StudyObj = new StudyLog(dateobj, sub, dur, desc);
        Database.add(StudyObj);

        System.out.println("-------------Study log stored successfully--------------");
        System.out.println("--------------------------------------------------------");

    }

}

class Study_tracker_553 //StudyTrackerStarter
{
    public static void main(String A[])
    {
        StudyTracker stobj = new StudyTracker();

        stobj.InsertLog();
       
    }
}