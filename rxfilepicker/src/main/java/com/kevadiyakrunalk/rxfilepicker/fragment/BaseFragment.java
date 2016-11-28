package com.kevadiyakrunalk.rxfilepicker.fragment;

import android.support.v4.app.Fragment;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment {

    /**
     * Instantiates a new Base fragment.
     */
    public BaseFragment() {
        // Required empty public constructor
    }

    /**
     * Fade in.
     *
     * @param view the view
     */
    protected void fadeIn(View view) {
        Animation bottomUp = AnimationUtils.loadAnimation(getContext(),
                android.R.anim.fade_in);

        view.startAnimation(bottomUp);
        view.setVisibility(View.VISIBLE);
    }

    /**
     * Fade out.
     *
     * @param view the view
     */
    protected void fadeOut(View view) {
        Animation bottomUp = AnimationUtils.loadAnimation(getContext(),
                android.R.anim.fade_out);

        view.startAnimation(bottomUp);
        view.setVisibility(View.GONE);
    }
}
