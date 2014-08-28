package wearable.android.zaim.net.adapters;

import android.app.Fragment;
import android.app.FragmentManager;

import wearable.android.zaim.net.fragments.InputAmountFragment;
import wearable.android.zaim.net.fragments.SelectCategoryFragment;
import wearable.android.zaim.net.fragments.SelectPaymentFragment;

public class GridPagerAdapter extends android.support.wearable.view.FragmentGridPagerAdapter {

    public GridPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getFragment(int row, int column) {
        switch (row) {
            case 0:
                return InputAmountFragment.newInstance();
            case 1:
                return SelectCategoryFragment.newInstance();
            case 2:
                return SelectPaymentFragment.newInstance();
            default:
                return null;
        }
    }

    @Override
    public int getRowCount() {
        return 3;
    }

    @Override
    public int getColumnCount(int row) {
        return 1;
    }

}