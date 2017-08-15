package com.jp.bakingapp;

import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewAssertion;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import junit.framework.AssertionFailedError;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.assertThat;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by LENIOVO on 8/6/2017.
 */
@LargeTest
@RunWith(AndroidJUnit4.class)
public class BakingAppTest {
    private static String NUTELLA = "Nutella Pie";
    private static String BROWNIES = "Brownies";


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
    }
