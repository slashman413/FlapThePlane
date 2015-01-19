package slashdev.com.flaptheplane.android.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

import slashdev.com.flaptheplane.android.fragment.TutorialFragment;

/**
 * Created by Slimer on 2015/1/18.
 */
public class TutorialFragmentAdapter extends FragmentPagerAdapter {

    public static class ItemData {
        public int imageId;
        public String title;
        public String description;
        public boolean isLastPage = false;

        public ItemData(int id, String t, String d) {
            imageId = id;
            title = t;
            description = d;
        }

        public ItemData(int id, String t, String d, boolean b) {
            this(id, t, d);
            isLastPage = b;
        }
    }

    private ArrayList<ItemData> mItems = new ArrayList<ItemData>();

    public TutorialFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(ArrayList<ItemData> items) {
        mItems.clear();
        mItems.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int position) {
        ItemData data = mItems.get(position);

        return TutorialFragment.newInstance(data.imageId, data.title, data.description, data.isLastPage);
    }

    @Override
    public int getCount() {
        return mItems.size();
    }
}
