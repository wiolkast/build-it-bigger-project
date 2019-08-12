package com.udacity.gradle.builditbigger;

import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.IdlingResource;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.udacity.gradle.builditbigger.paid.MainActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class AsyncTaskTest {
    private IdlingResource idlingResource;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Before
    public void registerIdlingResource(){
        idlingResource = activityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(idlingResource);
    }

    @Test
    public void asyncTaskTest() {
        onView(withId(R.id.button)).perform(click());
        onView(withId(R.id.jokeTV)).check(matches(not(withText(""))));
    }

    @After
    public void unregisterIdlingResource(){
        if(idlingResource != null){
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }
}