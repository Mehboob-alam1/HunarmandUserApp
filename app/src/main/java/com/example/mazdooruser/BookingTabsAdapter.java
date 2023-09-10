package com.example.mazdooruser;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import com.example.mazdooruser.fragments.ActiveFragment;
import com.example.mazdooruser.fragments.CancelledFragment;
import com.example.mazdooruser.fragments.CompletedFragment;


public class BookingTabsAdapter extends FragmentStateAdapter {

    public BookingTabsAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 0:
                return new ActiveFragment();
            case 1:
                return new CompletedFragment();
            case 2:
                return new CancelledFragment();
            default:
                return new ActiveFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
