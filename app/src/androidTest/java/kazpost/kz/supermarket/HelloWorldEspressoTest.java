package kazpost.kz.supermarket;

import android.support.test.filters.LargeTest;
import android.support.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import kazpost.kz.supermarket.data.DataManager;
import kazpost.kz.supermarket.ui.scanner.ScanActivity;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertEquals;

/**
 * Created by root on 9/26/17.
 */

//@RunWith(AndroidJUnit4.class)
@RunWith(MockitoJUnitRunner.class)
@LargeTest
public class HelloWorldEspressoTest {

    @Mock
    DataManager dataManager;


    @Rule
    public ActivityTestRule<ScanActivity> mScanActRule = new ActivityTestRule<>(ScanActivity.class);

    @Test
    public void checkTextView() {

        Mockito.when(dataManager.getSpinnerPosition()).thenReturn(3);

        assertEquals("spinner pos is not 3", 3, dataManager.getSpinnerPosition());
//        assertEquals("post index is not 010083, but is " + dataManager1.getPostIndex(), "010083", dataManager1.getPostIndex());
//        onView(withText("Сканируйте или введите данные")).check(matches(isDisplayed()));
        onView(withText(R.string.scan_title)).check(matches(isDisplayed()));
    }


}
