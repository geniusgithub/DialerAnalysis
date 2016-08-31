package com.geniusgithub.dialer.dialpad;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.os.Bundle;

import com.geniusgithub.dialer.util.AlwaysLog;


public class DialpadControlCenter{

    private static final String TAG = DialpadControlCenter.class.getSimpleName();
	private Activity mActivity;
    private int mContainerID;

    private DialpadFragment        mDialpadViewFragmentPortrait;
    public static final String         TAG_DIALPADVIEW_FRAGMENT_PORTRAIT = "tag_dialpadview_fragment_portrait";

    private DialpadFragment        mDialpadViewFragmentLandScape;
    public static final String         TAG_DIALPADVIEW_FRAGMENT_LANDSCAPE = "tag_dialpadview_fragment_landscape";


	private boolean isPortraitDialpadViewShow = false;
	private boolean isHorizontalDialpadViewShow = false;


	public DialpadControlCenter(Activity parentActivity, int containerID){
		mActivity = parentActivity;
		mContainerID = containerID;
	}
    

	public void onCreate(Bundle savedInstanceState) {
		removeDialpadviewFragment();
		addDialpadviewFragment();
		updateData();
	}

    public void onConfigurationChanged(Configuration newConfig) {
	  addDialpadviewFragment();
		updateData();
    }

	public void updateData(){
		if (isOrientationPortatit()){
			setPortraitDialpadViewShowStatus(true);
			setHorizontalDialpadViewShowStatus(false);
		}else{
			setPortraitDialpadViewShowStatus(false);
			setHorizontalDialpadViewShowStatus(true);
		}
	}




	public DialpadFragment getHorizontalDialpadViewFragment(){
		return mDialpadViewFragmentLandScape;
	}
	
	
	public DialpadFragment getPortraitDialpadViewFragment(){
		return mDialpadViewFragmentPortrait;
	}

	public void setPortraitDialpadViewShowStatus(boolean bShow){
		isPortraitDialpadViewShow = bShow;
	}


	public void setHorizontalDialpadViewShowStatus(boolean bShow){
		isHorizontalDialpadViewShow = bShow;
	}

	public boolean getPortraitDialpadViewShowStatus(){
		return isPortraitDialpadViewShow;
	}


	public boolean getHorizontalDialpadViewShowStatus(){
		return isHorizontalDialpadViewShow;
	}


    public boolean isOrientationPortatit(){
    	boolean flag = (mActivity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT);
    	return flag;
    }

	public void updateDialpadViewShowStatusSelf(){
		if (isOrientationPortatit()){
			hideHorizontalFragment();
			if (isPortraitDialpadViewShow){
				showPortatitFragment();
			}
			
		}else{
			hidePortatitFragment();
			if (isHorizontalDialpadViewShow){
				showHorizontalFragment();
			}
		}
	}
	


	private void removeDialpadviewFragment(){
		  FragmentManager fragmentManager = mActivity.getFragmentManager();
		  final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		  
		
		  mDialpadViewFragmentPortrait = (DialpadFragment) fragmentManager.findFragmentByTag(TAG_DIALPADVIEW_FRAGMENT_PORTRAIT);
		  if (mDialpadViewFragmentPortrait != null){
			  fragmentTransaction.remove(mDialpadViewFragmentPortrait);
			  mDialpadViewFragmentPortrait = null;
		  }

		  mDialpadViewFragmentLandScape = (DialpadFragment) fragmentManager.findFragmentByTag(TAG_DIALPADVIEW_FRAGMENT_LANDSCAPE);
		  if (mDialpadViewFragmentLandScape != null){
			  fragmentTransaction.remove(mDialpadViewFragmentLandScape);
			  mDialpadViewFragmentLandScape = null;
		  }
		
       
          fragmentTransaction.commitAllowingStateLoss();
          fragmentManager.executePendingTransactions();
		
	}

	public void addDialpadviewFragment(){
		FragmentManager fragmentManager = mActivity.getFragmentManager();


		if (isOrientationPortatit()){
			mDialpadViewFragmentPortrait = (DialpadFragment) fragmentManager.findFragmentByTag(TAG_DIALPADVIEW_FRAGMENT_PORTRAIT);
			AlwaysLog.i(TAG, "mDialpadViewFragmentPortrait try add? object = " + mDialpadViewFragmentPortrait);
			if (mDialpadViewFragmentPortrait == null){
				final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				mDialpadViewFragmentPortrait = new DialpadFragment();
				mDialpadViewFragmentPortrait.setOrientationPortatit(true);
				fragmentTransaction.add(mContainerID, mDialpadViewFragmentPortrait, TAG_DIALPADVIEW_FRAGMENT_PORTRAIT);
				fragmentTransaction.hide(mDialpadViewFragmentPortrait);
				AlwaysLog.i(TAG, "mDialpadViewFragmentPortrait add complete");
				fragmentTransaction.commitAllowingStateLoss();
				fragmentManager.executePendingTransactions();
			}
		}else{
			mDialpadViewFragmentLandScape = (DialpadFragment) fragmentManager.findFragmentByTag(TAG_DIALPADVIEW_FRAGMENT_LANDSCAPE);
			AlwaysLog.i(TAG, "mDialpadViewFragmentLandScape try add? object = " + mDialpadViewFragmentLandScape);
			if (mDialpadViewFragmentLandScape == null){
				final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
				mDialpadViewFragmentLandScape = new DialpadFragment();
				mDialpadViewFragmentLandScape.setOrientationPortatit(false);
				fragmentTransaction.add(mContainerID, mDialpadViewFragmentLandScape, TAG_DIALPADVIEW_FRAGMENT_LANDSCAPE);
				fragmentTransaction.hide(mDialpadViewFragmentLandScape);
				AlwaysLog.i(TAG, "mDialpadViewFragmentLandScape add complete");
				fragmentTransaction.commitAllowingStateLoss();
				fragmentManager.executePendingTransactions();
			}
		}



	}


	public DialpadFragment showPortatitFragment(){
		FragmentManager mFragmentManager = mActivity.getFragmentManager();
		mDialpadViewFragmentPortrait = (DialpadFragment) mFragmentManager.findFragmentByTag(TAG_DIALPADVIEW_FRAGMENT_PORTRAIT);
		AlwaysLog.i(TAG, "showPortatitFragment = " + mDialpadViewFragmentPortrait);
		if (mDialpadViewFragmentPortrait == null){
			FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
			mDialpadViewFragmentPortrait = new DialpadFragment();
			mDialpadViewFragmentPortrait.setOrientationPortatit(true);
			fragmentTransaction.add(mContainerID, mDialpadViewFragmentPortrait, TAG_DIALPADVIEW_FRAGMENT_PORTRAIT);
			fragmentTransaction.commitAllowingStateLoss();
			mFragmentManager.executePendingTransactions();
		}

		mDialpadViewFragmentPortrait.showDialpadFragment();

		return mDialpadViewFragmentPortrait;
	}

	public DialpadFragment hidePortatitFragment(){
		FragmentManager mFragmentManager = mActivity.getFragmentManager();
		mDialpadViewFragmentPortrait = (DialpadFragment) mFragmentManager.findFragmentByTag(TAG_DIALPADVIEW_FRAGMENT_PORTRAIT);
		AlwaysLog.i(TAG, "hidePortatitFragment = " + mDialpadViewFragmentPortrait);
		if (mDialpadViewFragmentPortrait != null){
			mDialpadViewFragmentPortrait.hideDialpadFragment();
		}

		return mDialpadViewFragmentPortrait;
	}

	public DialpadFragment showHorizontalFragment(){
		FragmentManager mFragmentManager = mActivity.getFragmentManager();

		mDialpadViewFragmentLandScape = (DialpadFragment) mFragmentManager.findFragmentByTag(TAG_DIALPADVIEW_FRAGMENT_LANDSCAPE);
		AlwaysLog.i(TAG, "showHorizontalFragmentt = " + mDialpadViewFragmentLandScape);
		if (mDialpadViewFragmentLandScape == null){
			FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
			mDialpadViewFragmentLandScape = new DialpadFragment();
			mDialpadViewFragmentLandScape.setOrientationPortatit(false);
			fragmentTransaction.add(mContainerID, mDialpadViewFragmentLandScape, TAG_DIALPADVIEW_FRAGMENT_LANDSCAPE);
			fragmentTransaction.commitAllowingStateLoss();
			mFragmentManager.executePendingTransactions();
		}
		mDialpadViewFragmentLandScape.showDialpadFragment();

		return mDialpadViewFragmentLandScape;
	}

	public DialpadFragment hideHorizontalFragment(){
		FragmentManager mFragmentManager = mActivity.getFragmentManager();
		mDialpadViewFragmentLandScape = (DialpadFragment) mFragmentManager.findFragmentByTag(TAG_DIALPADVIEW_FRAGMENT_LANDSCAPE);
		AlwaysLog.i(TAG, "hideHorizontalFragment = " + mDialpadViewFragmentLandScape);
		if (mDialpadViewFragmentLandScape != null){
			mDialpadViewFragmentLandScape.hideDialpadFragment();
		}

		return mDialpadViewFragmentLandScape;
	}



}
