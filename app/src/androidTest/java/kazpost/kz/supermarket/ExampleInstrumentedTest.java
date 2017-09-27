package kazpost.kz.supermarket;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import kazpost.kz.supermarket.data.DataManager;
import kazpost.kz.supermarket.ui.scanner.ScanActivity;
import kazpost.kz.supermarket.utils.EspressoIdlingResource;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("kazpost.kz.supermarket", appContext.getPackageName());
    }

    // Preferred JUnit 4 mechanism of specifying the activity to be launched before each test
    @Rule
    public ActivityTestRule<ScanActivity> mActivityTestRule =
            new ActivityTestRule<>(ScanActivity.class);


    @Test
    public void checkTextView() {
        onView(withText(R.string.scan_title)).check(matches(isDisplayed()));
    }

    @Test
    public void checkStrings() {

        Espresso.registerIdlingResources(EspressoIdlingResource.getIdlingResource());
        onView(withId(R.id.et_postcode)).perform(typeText("KZ123456789KZ"));
        onView(withId(R.id.et_row)).perform(typeText("12045"));
        mActivityTestRule.getActivity().setStrings("KZ123456789KZ");
        mActivityTestRule.getActivity().setStrings("12045");
        assertEquals("Barcode is not KZ123456789KZ", mActivityTestRule.getActivity().getBarcode(), "KZ123456789KZ");
        assertEquals("Row is not 12", mActivityTestRule.getActivity().getRow(), "12");
        assertEquals("Cell is not 045", mActivityTestRule.getActivity().getCell(), "045");

        onView(withId(R.id.btn_send)).perform(click());


        onView(withId(R.id.et_postcode)).perform(clearText());
        onView(withId(R.id.et_postcode)).perform(typeText("5088"));
        onView(withId(R.id.et_row)).perform(clearText());
        onView(withId(R.id.et_row)).perform(typeText("AA123456789AA"));
        mActivityTestRule.getActivity().setStrings("5088");
        mActivityTestRule.getActivity().setStrings("AA123456789AA");
        assertEquals("Barcode is not AA123456789AA", mActivityTestRule.getActivity().getBarcode(), "AA123456789AA");
        assertEquals("Row is not 5", mActivityTestRule.getActivity().getRow(), "5");
        assertEquals("Cell is not 088", mActivityTestRule.getActivity().getCell(), "088");
    }

    @org.mockito.Mock
    DataManager dataManager;


}
