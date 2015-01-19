package slashdev.com.flaptheplane.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.viewpagerindicator.LinePageIndicator;
import com.viewpagerindicator.PageIndicator;

import java.util.ArrayList;

import slashdev.com.flaptheplane.R;
import slashdev.com.flaptheplane.android.adapter.TutorialFragmentAdapter;
import slashdev.com.flaptheplane.gamehelpers.GamePreferences;

public class TutorialActivity extends FragmentActivity {

    TutorialFragmentAdapter mPageAdapter;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;
    PageIndicator mIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themed_lines);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mPageAdapter = new TutorialFragmentAdapter(getSupportFragmentManager());
        ArrayList<TutorialFragmentAdapter.ItemData> pageListData = new ArrayList<>();

        pageListData.add(new TutorialFragmentAdapter.ItemData(R.drawable.tutorial_settings, getString(R.string.tutorial_title_settings), getString(R.string.tutorial_description_settings)));
        pageListData.add(new TutorialFragmentAdapter.ItemData(R.drawable.tutorial_customize, getString(R.string.tutorial_title_customize), getString(R.string.tutorial_description_customize)));
        pageListData.add(new TutorialFragmentAdapter.ItemData(R.drawable.tutorial_record, getString(R.string.tutorial_title_record), getString(R.string.tutorial_description_record)));
        pageListData.add(new TutorialFragmentAdapter.ItemData(R.drawable.tutorial_enjoy, getString(R.string.tutorial_title_enjoy), getString(R.string.tutorial_description_enjoy), true));
        mPageAdapter.setData(pageListData);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mPageAdapter);

        mIndicator = (LinePageIndicator)findViewById(R.id.indicator);
        mIndicator.setViewPager(mViewPager);
    }

    @Override
    protected void onResume() {
        GamePreferences.updateTutorialShown(true);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

//        Intent intent = getBaseContext().getPackageManager()
//                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
//        int mPendingIntentId = 123456;
//        PendingIntent mPendingIntent = PendingIntent.getActivity(this, mPendingIntentId, intent, PendingIntent.FLAG_CANCEL_CURRENT);
//        AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//        mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
//        System.exit(0);


//        super.onDestroy();
//        Intent intent = getBaseContext().getPackageManager()
//                .getLaunchIntentForPackage( getBaseContext().getPackageName() );
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(TutorialActivity.this, GameScreenActivity.class);

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
