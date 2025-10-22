import java.util.*;
import java.time.*;

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

class Study_tracker_550
{
    public static void main(String A[])
    {
      LocalDate lobj = LocalDate.now();

      System.out.println(lobj);
    }
}