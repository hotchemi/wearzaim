package wearable.android.zaim.net.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.wearable.view.WearableListView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import wearable.android.zaim.net.R;
import wearable.android.zaim.net.views.SeekArc;

public class SelectCategoryFragment extends Fragment implements
        SeekArc.OnSeekArcChangeListener, WearableListView.ClickListener {

    @InjectView(R.id.wearable_list_view)
    WearableListView wearableListView;

    public static SelectCategoryFragment newInstance() {
        return new SelectCategoryFragment();
    }

    public SelectCategoryFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_select_category, container, false);
        ButterKnife.inject(this, view);
        wearableListView.setAdapter(new DemoAdapter(getActivity()));
        wearableListView.setClickListener(this);
        return view;
    }

    @Override
    public void onClick(WearableListView.ViewHolder viewHolder) {
    }

    @Override
    public void onTopEmptyRegionClick() {
    }

    @Override
    public void onProgressChanged(SeekArc seekArc, int progress, boolean fromUser) {
    }

    @Override
    public void onStartTrackingTouch(SeekArc seekArc) {
    }

    @Override
    public void onStopTrackingTouch(SeekArc seekArc) {
    }

    private class DemoAdapter extends WearableListView.Adapter {

        private final LayoutInflater inflater;

        private DemoAdapter(Context context) {
            inflater = LayoutInflater.from(context);
        }

        @Override
        public WearableListView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new WearableListView.ViewHolder(
                    inflater.inflate(R.layout.item_wearable_list, null));
        }

        @Override
        public void onBindViewHolder(WearableListView.ViewHolder h, int i) {
            TextView textView = (TextView) h.itemView.findViewById(R.id.text);
            ImageView imageView
                    = (ImageView) h.itemView.findViewById(R.id.image);
            imageView.setImageResource(R.drawable.wl_circle);
        }

        @Override
        public int getItemCount() {
            return 10;
        }

    }

}
