package com.geniusgithub.dialer.incall;

import android.content.ComponentName;

import java.util.Objects;

public class InCallServiceInfo {
    private final ComponentName mComponentName;
    private boolean mIsExternalCallsSupported;
    private boolean mIsSelfManagedCallsSupported;
    private final int mType;

    public InCallServiceInfo(ComponentName componentName,
                             boolean isExternalCallsSupported,
                             boolean isSelfManageCallsSupported,
                             int type) {
        mComponentName = componentName;
        mIsExternalCallsSupported = isExternalCallsSupported;
        mIsSelfManagedCallsSupported = isSelfManageCallsSupported;
        mType = type;
    }

    public ComponentName getComponentName() {
        return mComponentName;
    }

    public boolean isExternalCallsSupported() {
        return mIsExternalCallsSupported;
    }

    public boolean isSelfManagedCallsSupported() {
        return mIsSelfManagedCallsSupported;
    }

    public int getType() {
        return mType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        InCallServiceInfo that = (InCallServiceInfo) o;

        if (mIsExternalCallsSupported != that.mIsExternalCallsSupported) {
            return false;
        }
        if (mIsSelfManagedCallsSupported != that.mIsSelfManagedCallsSupported) {
            return false;
        }
        return mComponentName.equals(that.mComponentName);

    }

    @Override
    public int hashCode() {
        return Objects.hash(mComponentName, mIsExternalCallsSupported,
                mIsSelfManagedCallsSupported);
    }

    @Override
    public String toString() {
        return "[" + mComponentName + " supportsExternal? " + mIsExternalCallsSupported +
                " supportsSelfMg?" + mIsSelfManagedCallsSupported + "]";
    }
}