package my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.SignUp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.Rule;
import org.junit.Test;

import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class SignUpDonorActivityTest extends TestCase {

    @Rule
    public ActivityTestRule mActivityRule = new ActivityTestRule(SignUpDonorActivity.class);

    @Test
    public void activityLaunch(){
        onView(withId(R.id.btn_confirm)).perform(click());
    }

    @Test
    public void textInput(){
        onView(withId(R.id.et_name)).perform(typeText("Syahirah"), closeSoftKeyboard());
        onView(withId(R.id.et_id)).perform(typeText("980706209876"), closeSoftKeyboard());
        onView(withId(R.id.et_email)).perform(typeText("syahirah@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.et_phone)).perform(typeText("017263547897"), closeSoftKeyboard());
        onView(withId(R.id.btn_confirm)).perform(click());
    }

}