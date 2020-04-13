package Controller;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.Assert.*;

class IActivityTest
{
    IActivity controller = new IActivity();

    @Test
    void addActivity()
    {
        controller.addActivity(30, "Eat Apple", 50, LocalDate.now());
        assertEquals(1, controller.getActivityArrayList().size());
    }

    @Test
    void removeActivity()
    {
        // :: adding 1
        controller.addActivity(30, "Eat Apple", 50, LocalDate.now());
        assertEquals(1, controller.getActivityArrayList().size());

        // :: removing 1
        controller.removeActivity(0);
        assertEquals(0, controller.getActivityArrayList().size());
    }

    @Test
    void getActivityArrayList()
    {
        assertNotNull(controller.getActivityArrayList());
    }

    @Test
    void summarizeActivityPoints()
    {
        controller.addActivity(30, "Eat Apple", 50, LocalDate.now());
        controller.addActivity(30, "Eat Apple", 50, LocalDate.now());
        assertEquals("Your total points are :: 100", controller.summarizeActivityPoints());
    }

    @Test
    void loadActivities()
    {
        assertEquals(0, controller.generateList().size());

        controller.loadActivities();
        System.out.println(controller.generateList());
    }

    @Test
    void loadPredefinedActivities()
    {
        assertNotNull(controller.loadPredefinedActivities());
    }

}