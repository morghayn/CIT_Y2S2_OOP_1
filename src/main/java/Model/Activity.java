package Model;

import java.io.Serializable;
import java.time.LocalDate;

public class Activity implements Serializable, Comparable<Activity>
{

    private static final long serialVersionUID = -2580924221465043733L;
    private int week;
    private String activity;
    private int points;
    private LocalDate date;

    public Activity(String activity, int points)
    {
        this.activity = activity;
        this.points = points;
        //
        System.out.println("Successfully entered new activity :: " + activity + " " + points);
    }

    /**
     * <p>Instantiates an Model.Activity object.</p>
     *
     * <p>Sets instance variables to the values passed through the parameters of this constructor.</p>
     *
     * <p>Prints a message to console indicating success.</p>
     *
     * @param week     the week submitted through the form submission
     * @param activity the activity submitted through the form submission
     * @param points   the points submitted through the form submission
     * @param date     the date submitted through the form submission
     */
    public Activity(int week, String activity, int points, LocalDate date)
    {
        this.week = week;
        this.activity = activity;
        this.points = points;
        this.date = date;
        //
        System.out.println("Successfully entered new activity :: " + week + " " + activity + " " + points + " " + date);
    }

    public int getWeek()
    {
        return week;
    }

    public void setWeek(int week)
    {
        this.week = week;
    }

    public String getActivity()
    {
        return activity;
    }

    public void setActivity(String activity)
    {
        this.activity = activity;
    }

    public int getPoints()
    {
        return points;
    }

    public void setPoints(int points)
    {
        this.points = points;
    }

    public LocalDate getDate()
    {
        return date;
    }

    public void setDate(LocalDate date)
    {
        this.date = date;
    }

    @Override
    public int compareTo(Activity o)
    {
        return activity.compareTo(o.getActivity());
    }

}
