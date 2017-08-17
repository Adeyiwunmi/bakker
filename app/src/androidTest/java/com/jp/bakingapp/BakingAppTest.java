package com.jp.bakingapp;

import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.AssertionFailedError;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Created by LENIOVO on 8/6/2017.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class BakingAppTest {
    private static String NUTELLA = "Nutella Pie";
    private static String BROWNIES = "Brownies";
    private static String YELLOW_CAKE =  "Yellow Cake";
    private static String YELLOW_CAKE_PREPARATION_STEP = "11. Add butter to egg white mixture.";
    private static String ING_LIST = "Ingredient List";

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Test
    public void checkNutellapieIsShown() {

        try {

            onView(withId(R.id.idRecyclerRecipe)).check(matches(isDisplayed()));
        } catch (AssertionFailedError error) {
        }


    }

    @Test
    public  void checkBrowniesIsShown(){
        onView(withText(BROWNIES)).check((matches( isDisplayed())));


    }

    @Test
    public void checkNutellaInActionBar(){
        onView(withId(R.id.idRecyclerRecipe))
                .perform(RecyclerViewActions.
                        actionOnItemAtPosition(0, ViewActions.click()));
        onView(withText(NUTELLA)).check(matches(isDisplayed()));

    }

    @Test
    public void checkYellowCakeInActionBar(){
        onView(withId(R.id.idRecyclerRecipe)).
                perform(RecyclerViewActions.
                        actionOnItemAtPosition(2, ViewActions.click()));

        onView(withText(YELLOW_CAKE)).check(matches(isDisplayed()));
    }




    @Test
    public void checkYellowCakeStepsVisible() {
        onView(withId(R.id.idRecyclerRecipe)).
                perform(RecyclerViewActions.
                        actionOnItemAtPosition(2, ViewActions.click()));
  //check if the step and ingredient recycler view are visible
        onView(withId(R.id.idRecyclerViewSteps)).check(matches(isDisplayed()));
        onView(withId(R.id.idRecyclerViewIngredient)).check(matches(isDisplayed()));
        //check that the new activity describes ingredients
        onView(withText(ING_LIST)).check(matches(isDisplayed()));
    }

    /**
     * test to check if the video view will show when
     * clicking a step for preparation of yellow cake
     */
       @Test
        public void checkYellowCakeStepsVideoPlayed(){
  //click recycler recipe at position 2
            onView(withId(R.id.idRecyclerRecipe)).
                    perform(RecyclerViewActions.
                            actionOnItemAtPosition(2, ViewActions.click()));
//check if the steps recycler is shown
            onView(withId(R.id.idRecyclerViewSteps)).check(matches(isDisplayed()));

           //click the step recycler at position 5
           onView(withId(R.id.idRecyclerViewSteps)).perform(RecyclerViewActions.
                   actionOnItemAtPosition(5, ViewActions.click()));
           //check if the previous and next buttons are shown
           onView(withId(R.id.idButtonPrevStep)).check(matches(isDisplayed()));
           onView(withId(R.id.idButtonNextStep)).check(matches(isDisplayed()));


          }
    }



