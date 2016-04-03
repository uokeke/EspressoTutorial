package com.espressotutorial.ui.list;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;

import com.espressotutorial.note.R;
import com.espressotutorial.utils.Bootstrapper;
import com.espressotutorial.utils.Dependency;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.hamcrest.Matchers.not;


/**
 * Created by Uche on 2016-04-03.
 */
@RunWith(JUnit4.class)
public class ListActivityTestCase {

    private ActivityTestRule<ListActivity> activityTestRule = new ActivityTestRule<>(ListActivity.class, true, false);

    @Before
    public void setUp(){
        Bootstrapper.registerAllDependencies(Dependency.getInstance());
    }

    @After
    public void tearDown(){
        Bootstrapper.unregisterAllDependencies(Dependency.getInstance());
    }

    @Test
    public void when_adding_a_new_note_the_note_list_should_include_the_new_note() {
        activityTestRule.launchActivity(new Intent());

        //add the note
        onView(withId(R.id.add_note_button)).perform(click());

        //update the note
        onView(withId(R.id.edit_text_view)).perform(typeText("Awesome note"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.done_button)).perform(click());

        //verify that the note exists
        onView(withId(R.id.list_view)).check(matches(hasDescendant(withText("Awesome note"))));
    }

    @Test
    public void when_deleting_a_note_it_should_be_removed_from_the_note_list() {
        activityTestRule.launchActivity(new Intent());

        //add the note
        onView(withId(R.id.add_note_button)).perform(click());

        //update the note
        onView(withId(R.id.edit_text_view)).perform(typeText("Secrets to the universe"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.done_button)).perform(click());

        //delete the note
        onData(anything()).atPosition(0).perform(longClick());
        onView(withText(equalToIgnoringCase("ok"))).perform(click());

        //verify that it's deleted
        onView(withId(R.id.list_view)).check(matches(not(hasDescendant(withText("Secrets to the universe")))));
    }
}
