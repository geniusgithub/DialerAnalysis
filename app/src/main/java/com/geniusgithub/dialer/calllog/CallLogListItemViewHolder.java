package com.geniusgithub.dialer.calllog;

import android.graphics.Color;
import android.provider.CallLog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.geniusgithub.dialer.R;

public class CallLogListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


	public static interface onItemClickListener{
		public void onItemClick(CallLogListItemViewHolder viewHolder, int position);
	}

	public TextView mGroupLable;

	public TextView mNumberAndNumber;
	public TextView mDate;
	public TextView mDuration;
	public TextView mCallType;

	public CallDetail mCallDetail;
	public int mPosition;

	public ViewStub mViewStub;
	public View mStubRootView;
	public TextView mExtraTV;

	public onItemClickListener mOnItemClickListener;

	
	public CallLogListItemViewHolder(View itemView) {
		super(itemView);
		mNumberAndNumber = (TextView) itemView.findViewById(R.id.number_name);
		mDate = (TextView) itemView.findViewById(R.id.date);
		mDuration = (TextView) itemView.findViewById(R.id.duration);
		mCallType = (TextView) itemView.findViewById(R.id.calltype);
		mGroupLable = (TextView) itemView.findViewById(R.id.call_log_day_group_label);
		mViewStub = (ViewStub) itemView.findViewById(R.id.call_log_entry_actions_stub);

		itemView.setOnClickListener(this);
	}

	public void bindInfo(CallDetail detail, int pos){
		mCallDetail = detail;
		mPosition = pos;
		setNumberName(detail.number, detail.name);
		setDate(detail.date);
		setDuration(detail.duration);
		setCallType(detail.calltype);
		setPosition(mPosition);
		if (mExtraTV != null){
			mExtraTV.setText(detail.extraDetail());
		}
	}

	public CallDetail getCallDetail() {
		return mCallDetail;
	}
	public void setOnItemClickListener(onItemClickListener listener){
		mOnItemClickListener = listener;
	}

	public void showViewStub(boolean bShow){

		if (bShow){
			if (mStubRootView == null){
				mStubRootView = mViewStub.inflate();
				mExtraTV = (TextView) mStubRootView.findViewById(R.id.extra_tv);
				mExtraTV.setText(mCallDetail.extraDetail());
			}
			mStubRootView.setVisibility(View.VISIBLE);
		}else{
			if (mStubRootView != null){
				mStubRootView.setVisibility(View.GONE);
			}
		}

	}



	private void setPosition(int pos){
		String value = "position: " + pos;
		mGroupLable.setText(value);
	}


	private void setNumberName(String number, String name){
		String value = "number: " + number + ",   name = "  + name;
		mNumberAndNumber.setText(value);
	}

	private void setDate(long date){
		String value = "date: " + CallLogUtil.getStringDate(date);
		mDate.setText(value);
	}

	private void setDuration(long duration){
		String value = "duration: " + CallLogUtil.formatteDuration(duration);
		mDuration.setText(value);
	}

	private void setCallType(int callType){
		if (callType == CallLog.Calls.MISSED_TYPE){
			mCallType.setTextColor(Color.RED);
		}else{
			mCallType.setTextColor(Color.GRAY);
		}

		mCallType.setText("CallType: " + CallLogUtil.getCalltype(callType));
	}


	@Override
	public void onClick(View v) {
		if (mOnItemClickListener != null){
			mOnItemClickListener.onItemClick(this, mPosition);
		}
	}
}
