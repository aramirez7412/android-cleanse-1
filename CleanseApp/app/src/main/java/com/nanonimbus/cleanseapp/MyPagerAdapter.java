package com.nanonimbus.cleanseapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

// Extend from SmartFragmentStatePagerAdapter now instead for more dynamic ViewPager items
    public class MyPagerAdapter extends SmartFragmentStatePagerAdapter {
    private static int NUM_ITEMS = 10;
		
        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }
        
        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
 
        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
            case 0: // Fragment # 0 - This will show FirstFragment
                return SingleDayFragment.newInstance(0,"Page # 1");
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return SingleDayFragment.newInstance(1,"Page # 2");
            case 2: // Fragment # 1 - This will show SecondFragment
                return SingleDayFragment.newInstance(2,"Page # 3");
            case 3: // Fragment # 1 - This will show SecondFragment
                return SingleDayFragment.newInstance(3,"Page # 4");
            case 4: // Fragment # 1 - This will show SecondFragment
                return SingleDayFragment.newInstance(4,"Page # 5");
            case 5: // Fragment # 1 - This will show SecondFragment
                return SingleDayFragment.newInstance(5,"Page # 6");
            case 6: // Fragment # 1 - This will show SecondFragment
                return SingleDayFragment.newInstance(6,"Page # 7");
            case 7: // Fragment # 1 - This will show SecondFragment
                return SingleDayFragment.newInstance(7,"Page # 8");
            case 8: // Fragment # 1 - This will show SecondFragment
                return SingleDayFragment.newInstance(8,"Page # 9");
            case 9: // Fragment # 1 - This will show SecondFragment
                return SingleDayFragment.newInstance(9,"Page # 1");

            default:
            	return null;
            }
        }
        
        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
        	return "Page " + position;
        }


        
    }

