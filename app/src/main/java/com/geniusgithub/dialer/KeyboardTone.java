package com.geniusgithub.dialer;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;


public class KeyboardTone {
    private static final String TAG = "KeyboardTone";
	private final Context mContext;
    
    private SoundPool mSoundPool;
	private int[] mKeySounds = new int[12];

	private int[] mPianoSoundRes = {R.raw.zero, R.raw.one, R.raw.two, R.raw.three, R.raw.four,
			R.raw.five, R.raw.tst};
/*    private int[] mPianoSoundRes = {R.raw.zero, R.raw.one, R.raw.two, R.raw.three, R.raw.four,
    		R.raw.five, R.raw.six, R.raw.seven, R.raw.eight, R.raw.nine, R.raw.star, R.raw.pound};*/

    
    /** The length of DTMF tones in milliseconds */
    private static final int TONE_LENGTH_MS = 150;

    /** Stream type used to play the DTMF tones off call, and mapped to the volume control keys */
    private int DIAL_TONE_STREAM_TYPE;
    
	public KeyboardTone(Context context) {
		mContext = context;

		DIAL_TONE_STREAM_TYPE = AudioManager.STREAM_SYSTEM;
		DIAL_TONE_STREAM_TYPE = AudioManager.STREAM_MUSIC;
		Log.i(TAG, "DIAL_TONE_STREAM_TYPE = " + DIAL_TONE_STREAM_TYPE);
	}

	public void initSoundPool() {
		Log.d(TAG, "initSoundPool maxStream = 5");
		int maxStream = 5;			//modify for AIOM-1846
	
		if(mSoundPool == null) {

			SoundPool.Builder builder = new SoundPool.Builder();
			builder.setMaxStreams(maxStream);
			AudioAttributes.Builder builder1 = new AudioAttributes.Builder();
			builder1.setLegacyStreamType(DIAL_TONE_STREAM_TYPE);

			builder.setAudioAttributes(builder1.build());
			mSoundPool = builder.build();
			if (mSoundPool == null){
				Log.e(TAG, "mSoundPool = ? unbelieve...return now");
				return ;
			}
			for (int i = 0; i < mPianoSoundRes.length; i++) {
				mKeySounds[i] = mSoundPool.load(mContext, mPianoSoundRes[i], 0);
			}
		}else{
			Log.d(TAG, "initSoundPool exist, so donoting");
		}
	}

	public void releasePool(){
		if (mSoundPool != null){
			mSoundPool.release();;
		}
	}

	public void playSoundPool(int tone, float volum, boolean isLoop){
		  Log.d(TAG, "playSoundPool  tone = " + tone + ", volum = " + volum);
		if (mSoundPool == null){
			Log.e(TAG, "mSoundPool = null ? unbelieve...");
			return ;
		}

		if(tone < mKeySounds.length) {
			long time1 = System.currentTimeMillis();
			mSoundPool.play(mKeySounds[tone], volum, volum, 0, isLoop ? -1 : 0, 1);
			long time2 = System.currentTimeMillis();
			Log.d(TAG, "playSoundPool cost time:" + (time2 - time1));
		}
	}

	public void stopPool(){
		if (mSoundPool != null){
			mSoundPool.stop(5);;
		}
	}

}

