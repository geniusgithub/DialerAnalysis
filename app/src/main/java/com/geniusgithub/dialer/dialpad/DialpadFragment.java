/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.geniusgithub.dialer.dialpad;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.geniusgithub.dialer.R;
import com.geniusgithub.dialer.util.AlwaysLog;


/**
 * Displays a list of call log entries. To filter for a particular kind of call
 * (all, missed or voicemails), specify it in the constructor.
 */
public class DialpadFragment extends Fragment {
    private static final String TAG = DialpadFragment.class.getSimpleName();
    private boolean isPortraitFragment = true;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        final Activity activity = getActivity();
        final ContentResolver resolver = activity.getContentResolver();
        
        setHasOptionsMenu(true);
    }
    
    
    @Override
	public void onDestroy() {

    	
		super.onDestroy();
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.dialpad_fragment, container, false);


        
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }



    @Override
    public void onResume() {
        super.onResume();
        AlwaysLog.i(TAG, "onResume");

    }

    @Override
    public void onPause() {
        super.onPause();
        
        
    }

    public void setOrientationPortatit(boolean flag){
        isPortraitFragment = flag;
    }

    public void showDialpadFragment() {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.show(this);
        ft.commitAllowingStateLoss();
    }

    public void hideDialpadFragment() {
        final FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.hide(this);
        ft.commitAllowingStateLoss();

    }



}
