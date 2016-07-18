package com.geniusgithub.dialer.calllog;

import android.graphics.Color;
import android.provider.CallLog;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.geniusgithub.dialer.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CallLogListItemViewHolder extends RecyclerView.ViewHolder{

	public TextView mNumber;
	public TextView mDate;
	public TextView mDuration;
	public TextView mCallType;
	
	public CallLogListItemViewHolder(View itemView) {
		super(itemView);
		mNumber = (TextView) itemView.findViewById(R.id.number);
		mDate = (TextView) itemView.findViewById(R.id.date);
		mDuration = (TextView) itemView.findViewById(R.id.duration);
		mCallType = (TextView) itemView.findViewById(R.id.calltype);
	}


	public void setNumber(String number){
		String value = "number: " + number;
		mNumber.setText(value);
	}

	public void setDate(long date){
		String value = "date: " + getStringDate(date);
		mDate.setText(value);
	}

	public void setDuration(long duration){
		String value = "duration: " + formatteDuration(duration);
		mDuration.setText(value);
	}

	public void setCallType(int callType){
		updateCalltype(callType);
	}




	public static String getStringDate(long date) {
		Date currentTime = new Date(date);
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 	HH:mm:ss");
		String dateString = formatter.format(currentTime);
		return dateString;
	}


	private String formatteDuration(long duration){
		return String.valueOf(duration);
	}


	private void updateCalltype(int  calltype){
		mCallType.setTextColor(Color.GRAY);
		String value = "unknown";
		switch( calltype){
			case CallLog.Calls.INCOMING_TYPE:
				value = "INCOMING_TYPE";
				break;
			case CallLog.Calls.OUTGOING_TYPE:
				value =  "OUTGOING_TYPE";
				break;
			case CallLog.Calls.MISSED_TYPE:
				value =  "MISSED_TYPE";
				mCallType.setTextColor(Color.RED);
				break;
			case CallLog.Calls.VOICEMAIL_TYPE:
				value = "VOICEMAIL_TYPE";
				break;
		}
	;
		mCallType.setText("CallType: " + value);
	}
}
