package Interfaces;

import android.support.v7.widget.RecyclerView;
import android.support.wear.widget.WearableLinearLayoutManager;
import android.view.View;

/**
 * Created by juanmartinez on 3/21/18.
 */

public class CustomScrollingLayoutCallback extends WearableLinearLayoutManager.LayoutCallback {

    /** How much should we scale the icon at most. */
    private static final float MAX_ICON_PROGRESS = 2f;


    @Override
    public void onLayoutFinished(View child, RecyclerView parent) {

        float centerOffset = ((float) child.getHeight() / 2.0f) / (float) parent.getHeight();
        float yRelativeToCenterOffset = (child.getY() / parent.getHeight()) + centerOffset;

        float progresstoCenter = (float) Math.sin(yRelativeToCenterOffset * Math.PI);

        float mProgressToCenter = Math.abs(0.5f - yRelativeToCenterOffset);

        mProgressToCenter = Math.min(mProgressToCenter, MAX_ICON_PROGRESS);

        child.setScaleX(1 - mProgressToCenter);
        child.setScaleY(1 - mProgressToCenter);
        child.setX((1 - progresstoCenter) * 100);
    }
}
