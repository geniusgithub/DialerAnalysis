package com.geniusgithub.dialer.calllog;

import android.graphics.Color;
import android.provider.CallLog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.geniusgithub.dialer.R;

public class CallLogListItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


	public static interface onItemClickListener{
		public void onItemClick(CallDetail detail, int position);
	}

	public TextView mGroupLable;

	public TextView mNumber;
	public TextView mDate;
	public TextView mDuration;
	public TextView mCallType;

	public CallDetail mCallDetail;
	public int mPosition;
	public onItemClickListener mOnItemClickListener;

	
	public CallLogListItemViewHolder(View itemView) {
		super(itemView);
		mNumber = (TextView) itemView.findViewById(R.id.number);
		mDate = (TextView) itemView.findViewById(R.id.date);
		mDuration = (TextView) itemView.findViewById(R.id.duration);
		mCallType = (TextView) itemView.findViewById(R.id.calltype);
		mGroupLable = (TextView) itemView.findViewById(R.id.call_log_day_group_label);

		itemView.setOnClickListener(this);
	}

	public void bindInfo(CallDetail detail, int pos){
		mCallDetail = detail;
		mPosition = pos;
		setNumber(detail.number);
		setDate(detail.date);
		setDuration(detail.duration);
		setCallType(detail.calltype);
		setPosition(mPosition);
	}

	public void setOnItemClickListener(onItemClickListener listener){
		mOnItemClickListener = listener;
	}





	private void setPosition(int pos){
		String value = "position: " + pos;
		mGroupLable.setText(value);
	}


	private void setNumber(String number){
		String value = "number: " + number;
		mNumber.setText(value);
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
			mOnItemClickListener.onItemClick(mCallDetail, mPosition);
		}
	}
}
