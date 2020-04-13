package Model;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ActivityTest
{
    private Activity activity = new Activity(30, "Eat Apple", 50, LocalDate.now());

    @Test
    public void getActivity()
    {
        assertEquals("Eat Apple", activity.getActivity());
    }

    @Test
    public void getWeek()
    {
        assertEquals(30, activity.getWeek());
    }

    @Test
    public void getPoints()
    {
        assertEquals(50, activity.getPoints());
    }
}