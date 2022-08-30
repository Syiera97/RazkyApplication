package my.edu.utem.ftmk.msmd5113.razkyapplication.Donor.SignUp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.rule.ActivityTestRule;

import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import kotlin.jvm.JvmField;
import my.edu.utem.ftmk.msmd5113.razkyapplication.R;

public class SignUpDonorActivityTest {
    private SignUpDonorActivity signUpDonorActivity = null;

    @Rule
    public ActivityTestRule<SignUpDonorActivity> mActivityTestRule = new ActivityTestRule<SignUpDonorActivity>(SignUpDonorActivity.class);

    @Before
    public void setUp() throws Exception {
        signUpDonorActivity = mActivityTestRule.getActivity();
    }

    @Test
    public void testActivityLaunch(){
        onView(withId(R.id.btn_confirm)).perform(click());
    }

    @Test
    public void testTextInput(){
        onView(withId(R.id.et_name)).perform(typeText("Syahirah"), closeSoftKeyboard());
        onView(withId(R.id.et_id)).perform(typeText("980706209876"), closeSoftKeyboard());
        onView(withId(R.id.et_email)).perform(typeText("syahirah@gmail.com"), closeSoftKeyboard());
        onView(withId(R.id.et_password)).perform(typeText("123456"), closeSoftKeyboard());
        onView(withId(R.id.et_phone)).perform(typeText("017263547897"), closeSoftKeyboard());
        onView(withId(R.id.btn_confirm)).perform(click());
    }

}