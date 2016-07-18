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

package com.geniusgithub.dialer.calllog;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.geniusgithub.dialer.R;
import com.geniusgithub.dialer.util.AlwaysLog;


/**
 * Displays a list of call log entries. To filter for a particular kind of call
 * (all, missed or voicemails), specify it in the constructor.
 */
public class CallLogFragment extends Fragment implements CallLogQueryHandler.Listener, View.OnClickListener{
    private static final String TAG = CallLogFragment.class.getSimpleName();

    private Button mBtnQuery;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private CallLogAdapter mAdapter;
    
    private CallLogQueryHandler mCallLogQueryHandler;


    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);

        final Activity activity = getActivity();
        final ContentResolver resolver = activity.getContentResolver();
        
        mCallLogQueryHandler = new CallLogQueryHandler(activity, resolver, this);
        
        setHasOptionsMenu(true);
    }
    
    
    @Override
	public void onDestroy() {

    	mCallLogQueryHandler.cancelListener();
    	
		super.onDestroy();
	}

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedState) {
        View view = inflater.inflate(R.layout.call_log_fragment, container, false);

        mBtnQuery = (Button) view.findViewById(R.id.btn_query);
        mBtnQuery.setOnClickListener(this);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
  

        mAdapter = new CallLogAdapter(getActivity());
        mRecyclerView.setAdapter(mAdapter);


        
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

    public void fetchCalls() {
        mCallLogQueryHandler.fetchCalls();
    }

	@Override
	public boolean onCallsFetched(Cursor cursor) {
        AlwaysLog.i(TAG, "onCallsFetched cursor = " + cursor);
		  if (getActivity() == null || getActivity().isFinishing()) {
	            // Return false; we did not take ownership of the cursor
	            return false;
	        }
		  
		  if (cursor != null){
			  AlwaysLog.i("cursor.getCount = " + cursor.getCount());
		  }
		  
	        mAdapter.changeCursor(cursor);
	        return true;
	}

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_query:
                fetchCalls();
                break;
        }
    }


}
