package com.example.whatsappclone.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.whatsappclone.fragments.Calls_fragment;
import com.example.whatsappclone.fragments.Chat_fragment;
import com.example.whatsappclone.fragments.Status_fragment;


@SuppressWarnings("ALL")
public class FragmentAdapter extends FragmentPagerAdapter {

    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       switch(position){
           case 0:
               return new Chat_fragment();

           case 1:
               return new Status_fragment();

           case 2:
               return new Calls_fragment();

           default:
               return new Chat_fragment();
       }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch(position){
            case 0:
                return "CHATS";

            case 1:
                return "STATUS";

            case 2:
                return "CALLS";

            default:
                return "CHATS";
        }
    }
}
