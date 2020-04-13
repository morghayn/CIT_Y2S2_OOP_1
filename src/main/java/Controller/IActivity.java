package Controller;

import Model.Activity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import fileManager.*;

/**
 * <p>This is the controller in which communication between view and the Activity model is managed.</p>
 */
public class IActivity
{

    private static final Comparator<Activity> WEEK_COMPARATOR = Comparator.comparing(Activity::getWeek);
    private static final Comparator<Activity> ACTIVITY_COMPARATOR = Comparator.comparing(Activity::getActivity);
    private static final Comparator<Activity> POINTS_COMPARATOR = Comparator.comparing(Activity::getPoints);
    private static final Comparator<Activity> DATE_COMPARATOR = Comparator.comparing(Activity::getDate);
    private ArrayList<Activity> activities, predefinedActivities;

    /**
     * <p>Instantiation of the ArrayLists that are used during communication between this controller and the
     * views</p>
     */
    public IActivity()
    {
        activities = new ArrayList<>();
        predefinedActivities = new ArrayList<>();
    }

    /**
     * <p>Creates a new {@link Model.Activity} instance and adds it to the activity ArrayList. Returns a string of data
     * passed.</p>
     *
     * @param week     the week entered in the form submission
     * @param activity the activity entered in the form submission
     * @param points   the points entered in the form submission
     * @param date     the date entered in the date submission
     * @return the new {@link Model.Activity} instance
     */
    public Activity addActivity(int week, String activity, int points, LocalDate date)
    {
        Activity newActivity = new Activity(week, activity, points, date);
        activities.add(newActivity);
        return newActivity;
    }

    /**
     * <p>Removes an {@link Model.Activity} object from the activities ArrayList.</p>
     *
     * @param index the index at which an object will be removed from the activities ArrayList
     */
    public void removeActivity(int index)
    {
        activities.remove(index);
    }

    /**
     * <p>Retrieves the activities ArrayList.</p>
     *
     * @return the activities ArrayList
     */
    public ArrayList<Activity> getActivityArrayList()
    {
        return activities;
    }

    /**
     * <p>Tallies all points across each object in the activities ArrayList.</p>
     *
     * @return a String of the tallied total points across each {@link Model.Activity} object in the activities
     * ArrayList
     */
    public String summarizeActivityPoints()
    {
        int totalPoints = 0;
        for (Activity activity : activities)
        {
            totalPoints += activity.getPoints();
        }
        return ("Your total points are :: " + totalPoints);
    }

    /**
     * <p>Attempts to serialize the activities ArrayList to the text file specified.</p>
     */
    public void saveActivities()
    {
        UtilitySerialize.saveActivities(activities,
                                        getClass().getResource("/Activities.txt").getFile()
                                        // When compiling to shaded jar and pairing with text file, change parameter to this!!!
                                        /* "./Activities.txt" */
        );
    }

    /**
     * <p>Attempts to load the ArrayList of {@link Model.Activity} objects from the text file specified.</p>
     */
    public void loadActivities()
    {
        ArrayList<?> temporary = UtilitySerialize.loadActivities(
                getClass().getResourceAsStream("/Activities.txt")
                // When compiling to shaded jar and pairing with text file, change parameter to this!!!
                /* "./Activities.txt" */
        );

        if (temporary != null)
        {
            activities = (ArrayList<Activity>) temporary;
        }
    }

    /**
     * <p>Attempts to load the ArrayList of {@link Model.Activity} objects from the text file specified.</p>
     *
     * <p>Specific to the view, to populate the comboBox.</p>
     *
     * @return ArrayList of predefinedActivities to populate the views comboBox
     */
    public ArrayList<Activity> loadPredefinedActivities()
    {
        ArrayList<?> temporary = UtilitySerialize.loadActivities(getClass().getResourceAsStream("/DefaultActivities.txt"));

        if (temporary != null)
        {
            predefinedActivities = (ArrayList<Activity>) temporary;
        }

        return predefinedActivities;
    }

    /**
     * <p>Retrieves the corresponding points of the predefinedActivities playlist corresponding to the index passed
     * through.</p>
     *
     * @param index the index of the object in question
     * @return the total points for the corresponding activity
     */
    public int getPredefinedPoints(int index)
    {
        return predefinedActivities.get(index).getPoints();
    }

    /**
     * <p>Orders the activities array depending on the sender parameter.</p>
     *
     * @param sender the sender indicates to the orderList method on which parameter the activities ArrayList should be
     *               sorted
     * @return the ordered ArrayList requested
     */
    public ArrayList<String> orderList(int sender)
    {
        activities.sort(
                sender == 1 ? WEEK_COMPARATOR.reversed() :
                sender == 2 ? DATE_COMPARATOR.reversed() :
                sender == 3 ? POINTS_COMPARATOR.reversed() :
                ACTIVITY_COMPARATOR.reversed()
        );
        return generateList();
    }

    /**
     * <p>Generates an ArrayList of Strings for the activities ArrayList.</p>
     *
     * @return the generated ArrayList of String representing the activities ArrayList
     */
    public ArrayList<String> generateList()
    {
        ArrayList<String> activitiesList = new ArrayList<>();

        for (Activity activity : activities)
        {
            activitiesList.add(
                    "Week :: " + activity.getWeek() + "\t | \t" +
                    "Date :: " + activity.getDate() + "\t | \t" +
                    "Points :: " + activity.getPoints() + "\t | \t" +
                    "Activity :: " + activity.getActivity()
            );
        }

        return activitiesList;
    }

}
