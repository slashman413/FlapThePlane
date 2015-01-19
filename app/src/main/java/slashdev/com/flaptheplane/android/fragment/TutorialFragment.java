package slashdev.com.flaptheplane.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import slashdev.com.flaptheplane.R;
import slashdev.com.flaptheplane.android.GameScreenActivity;

/**
 * Created by Slimer on 2015/1/18.
 */
public class TutorialFragment extends Fragment {
    private int mImageResourceId;
    private String mTitle;
    private String mDescription;
    private boolean mIsLastPage;

    public static TutorialFragment newInstance(int imageResourceId, String title, String description, boolean last) {
        TutorialFragment fragment = new TutorialFragment();

        fragment.mImageResourceId = imageResourceId;
        fragment.mTitle = title;
        fragment.mDescription = description;
        fragment.mIsLastPage = last;

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.fragment_tutorial, null);
        ImageView imageView = (ImageView) layout.findViewById(R.id.image);
        TextView titleView = (TextView) layout.findViewById(R.id.title);
        TextView descriptionView = (TextView) layout.findViewById(R.id.description);

        imageView.setImageResource(mImageResourceId);
        titleView.setText(mTitle);
        descriptionView.setText(mDescription);

        if (mIsLastPage) {
            Button finishButton = new Button(getActivity());

            finishButton.setText(R.string.finish);
            finishButton.setLayoutParams(new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT));
            finishButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), GameScreenActivity.class);

                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
            layout.addView(finishButton);
        }

        return layout;
    }
}
