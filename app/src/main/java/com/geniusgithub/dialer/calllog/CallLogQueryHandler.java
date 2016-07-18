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

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog.Calls;

import com.geniusgithub.dialer.util.AlwaysLog;

import java.lang.ref.WeakReference;

/** Handles asynchronous queries to the call log. */
public class CallLogQueryHandler extends AsyncQueryHandler {

    /** Listener to completion of various queries. */
    public interface Listener {
        boolean onCallsFetched(Cursor combinedCursor);
    }

    private static final String TAG = "CallLogQueryHandler";
    private static final int NUM_LOGS_TO_DISPLAY = 100;
    private static final int QUERY_CALLLOG_TOKEN = 54;
    private final Context mContext;

    private WeakReference<Listener> mListener;
    
    public CallLogQueryHandler(Context context, ContentResolver contentResolver, Listener listener) {
        super(contentResolver);
        mContext = context.getApplicationContext();
        mListener = new WeakReference<Listener>(listener);
    }
    
    public void cancelListener(){
    	mListener = null;
    }
    
//    /**
//     * Simple handler that wraps background calls to catch
//     * {@link SQLiteException}, such as when the disk is full.
//     */
//    protected class CatchingWorkerHandler extends AsyncQueryHandler.WorkerHandler {
//        public CatchingWorkerHandler(Looper looper) {
//            super(looper);
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            try {
//                // Perform same query while catching any exceptions
//                super.handleMessage(msg);
//            } catch (SQLiteDiskIOException e) {
//                Log.w(TAG, "Exception on background worker thread", e);
//            } catch (SQLiteFullException e) {
//                Log.w(TAG, "Exception on background worker thread", e);
//            } catch (SQLiteDatabaseCorruptException e) {
//                Log.w(TAG, "Exception on background worker thread", e);
//            } catch (IllegalArgumentException e) {
//                Log.w(TAG, "ContactsProvider not present on device", e);
//            } catch (SecurityException e) {
//                // Shouldn't happen if we are protecting the entry points correctly,
//                // but just in case.
//                Log.w(TAG, "No permission to access ContactsProvider.", e);
//            }
//        }
//    }
//
//    @Override
//    protected Handler createHandler(Looper looper) {
//        // Provide our special handler that catches exceptions
//        return new CatchingWorkerHandler(looper);
//    }




    public void fetchCalls() {
    	cancelOperation(QUERY_CALLLOG_TOKEN);
        fetchCalls(QUERY_CALLLOG_TOKEN);
    }


    /** Fetches the list of calls in the call log. */
    private void fetchCalls(int token) {

        Uri uri = Calls.CONTENT_URI.buildUpon()
                .appendQueryParameter(Calls.LIMIT_PARAM_KEY, Integer.toString(NUM_LOGS_TO_DISPLAY))
                .build();
       
        startQuery(token, null, uri,
                CallLogQuery._PROJECTION, null, null, Calls.DEFAULT_SORT_ORDER);
        AlwaysLog.i(TAG, "fetchCalls uri = " + uri.toString());
    }

    @Override
    protected synchronized void onQueryComplete(int token, Object cookie,
            Cursor cursor) {
    	   AlwaysLog.i(TAG, "onQueryComplete token = " + token + ", cursor = " + cursor);
        if (cursor == null) {
            return;
        }
     
        try {
            if (token == QUERY_CALLLOG_TOKEN) {
                if (updateAdapterData(cursor)) {
                    cursor = null;
                }
            } 
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private boolean updateAdapterData(Cursor cursor) {
        final Listener listener = mListener.get();
        if (listener != null) {
            return listener.onCallsFetched(cursor);
        }
        return false;

    }

}
