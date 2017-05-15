package kazpost.kz.supermarket.data.prefs;

/**
 * Created by root on 4/12/17.
 */

public interface PreferencesHelper {

    void savePostIndex(String postIndex);

    void saveSpinnerPosition(int position);

    int getSpinnerPosition();

    String getPostIndex();
}
