import java.util.*;
import java.time.LocalDate;
import java.io.*;


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

    public void DisplayLog()
    {
        System.out.println("--------------------------------------------------------");
        if(Database.isEmpty())
        {
            System.out.println("Nothing to display as datbase is empty.");
            System.out.println("--------------------------------------------------------");
            return;
        }
        System.out.println("--------------------------------------------------------");
        System.out.println("Log Report from Marvellous study tracker");   
        System.out.println("--------------------------------------------------------");

        for(StudyLog sobj : Database)
        {
            System.out.println(sobj);
        }
        System.out.println("--------------------------------------------------------");

    }

    public void ExportCSV()
    {
       
        if(Database.isEmpty())
        {
            System.out.println("--------------------------------------------------------");
            System.out.println("Nothing to export as datbase is empty.");
            System.out.println("--------------------------------------------------------");
            return;
        }

        String FileName = "Marvellous.csv";

        //create new CSV file
        try(FileWriter fwobj = new FileWriter(FileName))
        {
            // write CSV header
            fwobj.write("Date,Subject,Duration,Description\n");

            for(StudyLog sobj : Database)
            {
                //write each record in CSV
                fwobj.write(sobj.getDate() + "," +
                            sobj.getSubject().replace(","," ") + "," +
                            sobj.getDuration() + "," +
                            sobj.getDescription().replace(","," ") + "\n"
                            );
            }
            System.out.println("log created successfull.");       
        }
        catch (Exception eobj)
        {
            System.out.println("Exceptption occoured while creating the csv");
            System.out.println("Report this issue to Marvellous Infosystem");
        }
    }

    public void SummaryByDate()
    {
        System.out.println("--------------------------------------------------------");
        if(Database.isEmpty())
        {
            System.out.println("Nothing to display as datbase is empty.");
            System.out.println("--------------------------------------------------------");
            return;
        }
        System.out.println("--------------------------------------------------------");
        System.out.println("-----Summary by date from Marvellous study tracker------");   
        System.out.println("--------------------------------------------------------");

        TreeMap <LocalDate, Double> tobj = new TreeMap <LocalDate, Double> ();

        LocalDate lobj = null;
        double d,old;

        for(StudyLog sobj : Database)
        {
            lobj = sobj.getDate();
            d = sobj.getDuration();

            if(tobj.containsKey(lobj))
            {
                old =tobj.get(lobj);
                tobj.put(lobj,d+old);
            }
            else
            {
                tobj.put(lobj,d);
            }
        
        }
        //Display the dat as per date
        for(LocalDate ldobj : tobj.keySet())
        {
            System.out.println("Date : "+ldobj+" Total Study "+tobj.get(ldobj));
        }
        System.out.println("******************************************************");

    }

    public void SummaryBySubject()
    {
        System.out.println("--------------------------------------------------------");
        if(Database.isEmpty())
        {
            System.out.println("Nothing to display as datbase is empty.");
            System.out.println("--------------------------------------------------------");
            return;
        }
        System.out.println("--------------------------------------------------------");
        System.out.println("-----Summary by subject from Marvellous study tracker------");   
        System.out.println("--------------------------------------------------------");

        TreeMap <String, Double> tobj = new TreeMap <String, Double> ();

        String s;
        double d,old;

        for(StudyLog sobj : Database)
        {
            s = sobj.getSubject();
            d = sobj.getDuration();

            if(tobj.containsKey(s))
            {
                old =tobj.get(s);
                tobj.put(s,d+old);
            }
            else
            {
                tobj.put(s,d);
            }
        
        }
        //Display the dat as per subject
        for(String str : tobj.keySet())
        {
            System.out.println("Subject : "+str+" Total Study "+tobj.get(str));
        }
        System.out.println("******************************************************");
    }

}

class Study_tracker_557 //StudyTrackerStarter
{
    public static void main(String A[])
    {

        StudyTracker stobj = new StudyTracker();

        Scanner sobj = new Scanner(System.in);

        int iChoice = 0;

        System.out.println("---------------------------------------------------------");
        System.out.println("---Welcome to the marvellous study tracker application---");   
        System.out.println("---------------------------------------------------------"); 

        do
        {
            System.out.println("Please select appropriate option : ");
            System.out.println("1 : Insert new log into database");
            System.out.println("2 : View all study log");
            System.out.println("3 : Sumamry of studyLog by date");
            System.out.println("4 : Summary of studylog by subject");
            System.out.println("5 : Export studylog to CSV");
            System.out.println("6 : Exit the application");

            iChoice = sobj.nextInt();

            switch(iChoice)
            {
                case 1:     // 1 : Insert new log into database
                    stobj.InsertLog();
                    break;

                case 2:     // 2 : View all study log
                    stobj.DisplayLog();
                    break;

                case 3:     // 3 : Sumamry of studyLog by date
                    stobj.SummaryByDate();
                    break;

                case 4:     // 4 : Summary of studylog by subject
                    stobj.SummaryBySubject();
                    break;

                case 5:     // 5 : Export studylog to CSV
                    stobj.ExportCSV();
                    break;

                case 6:     // 6 : Exit the application
                    System.out.println("---------------------------------------------------------"); 
                    System.out.println("Thank you for using marvellous study log application");
                    System.out.println("---------------------------------------------------------"); 
                    break;

                default:
                    System.out.println("******************************"); 
                    System.out.println("Please enter the valid option");
                    System.out.println("******************************"); 
                


            }

        }   while(iChoice != 6);   
    }
}