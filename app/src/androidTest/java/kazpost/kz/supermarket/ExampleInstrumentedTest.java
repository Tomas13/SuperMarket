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
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExampleInstrumentedTest {


    @org.mockito.Mock
    DataManager dataManager;


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
        onView(withId(R.id.et_row)).perform(typeText("1204"));
        assertEquals("Barcode is not KZ123456789KZ", mActivityTestRule.getActivity().getBarcode(), "KZ123456789KZ");
        assertEquals("Row is not 12", "1", mActivityTestRule.getActivity().getRow());
        assertEquals("Cell is not 045", "204", mActivityTestRule.getActivity().getCell());
        onView(withId(R.id.btn_send)).perform(click());


//        onView(withId(R.id.et_postcode)).perform(typeText("KZ123456789KZ"));
        onView(withId(R.id.et_row)).perform(replaceText("12045"));
        assertEquals("Barcode is not KZ123456789KZ", mActivityTestRule.getActivity().getBarcode(), "KZ123456789KZ");
        assertEquals("Row is not 12", "12", mActivityTestRule.getActivity().getRow());
        assertEquals("Cell is not 045", "045", mActivityTestRule.getActivity().getCell());
        onView(withId(R.id.btn_send)).perform(click());


        onView(withId(R.id.et_postcode)).perform(clearText());
        onView(withId(R.id.et_postcode)).perform(typeText("5088"));
        onView(withId(R.id.et_row)).perform(clearText());
        onView(withId(R.id.et_row)).perform(typeText("AA123456789AA"));
        assertEquals("Barcode is not AA123456789AA", mActivityTestRule.getActivity().getBarcode(), "AA123456789AA");
        assertEquals("Row is not 5", "5", mActivityTestRule.getActivity().getRow());
        assertEquals("Cell is not 088", "088", mActivityTestRule.getActivity().getCell());

        onView(withId(R.id.btn_send)).perform(click());
    }


    @Test
    public void checkToastOnWrongPostCode() {
        onView(withId(R.id.et_postcode)).perform(clearText());
        onView(withId(R.id.et_postcode)).perform(typeText("50"));
        onView(withId(R.id.et_row)).perform(clearText());
        onView(withId(R.id.et_row)).perform(typeText("AA123456789AA"));

        onView(withId(R.id.btn_send)).perform(click());

        onView(withText(R.string.nonvalid_data))
                .inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        assertEquals("Barcode is not AA123456789AA", mActivityTestRule.getActivity().getBarcode(), "AA123456789AA");
        assertNotEquals("Row is 5", "5", mActivityTestRule.getActivity().getRow());
        assertNotEquals("Cell is 0", "0", mActivityTestRule.getActivity().getCell());
    }

    @Test
    public void checkToastOnWrongBarcodeCode() {
        onView(withId(R.id.et_postcode)).perform(replaceText("7889"));
        onView(withId(R.id.et_row)).perform(clearText());
        onView(withId(R.id.et_row)).perform(typeText("AA123456789"));

        onView(withId(R.id.btn_send)).perform(click());

        onView(withText(R.string.nonvalid_data))
                .inRoot(withDecorView(not(is(mActivityTestRule.getActivity().getWindow().getDecorView()))))
                .check(matches(isDisplayed()));

        assertNotEquals("Barcode is AA123456789", "AA123456789", mActivityTestRule.getActivity().getBarcode());
        assertEquals("Row is not 7", "7", mActivityTestRule.getActivity().getRow());
        assertEquals("Cell is not 889", "889", mActivityTestRule.getActivity().getCell());
    }


    @Test
    public void zSendAfterTryingWithWrongArguments() {
        onView(withId(R.id.et_postcode)).perform(clearText());
        onView(withId(R.id.et_postcode)).perform(typeText("5088"));
        onView(withId(R.id.et_row)).perform(clearText());
        onView(withId(R.id.et_row)).perform(typeText("AA123456789AA"));
        assertEquals("Barcode is not AA123456789AA", mActivityTestRule.getActivity().getBarcode(), "AA123456789AA");
        assertEquals("Row is not 5", "5", mActivityTestRule.getActivity().getRow());
        assertEquals("Cell is not 088", "088", mActivityTestRule.getActivity().getCell());

        onView(withId(R.id.btn_send)).perform(click());
    }
}