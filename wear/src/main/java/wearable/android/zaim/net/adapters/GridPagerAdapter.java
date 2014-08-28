package wearable.android.zaim.net.adapters;

import android.app.Fragment;
import android.app.FragmentManager;

import wearable.android.zaim.net.fragments.InputAmountFragment;

public class GridPagerAdapter extends android.support.wearable.view.FragmentGridPagerAdapter {

    public GridPagerAdapter(final FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getFragment(final int row, final int column) {
        switch (row) {
            case 1:
                return InputAmountFragment.newInstance();
            case 2:
                return InputAmountFragment.newInstance();
            case 3:
                return InputAmountFragment.newInstance();
            default:
                return InputAmountFragment.newInstance();
        }
    }

    @Override
    public int getRowCount() {
        return 3;
    }

    @Override
    public int getColumnCount(final int row) {
        return 1;
    }

}
