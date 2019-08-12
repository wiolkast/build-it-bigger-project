package com.udacity.gradle.builditbigger;

import androidx.annotation.Nullable;
import androidx.test.espresso.IdlingResource;

import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleIdlingResource implements IdlingResource {
    @Nullable
    private volatile IdlingResource.ResourceCallback resourceCallback;
    private final AtomicBoolean isIddleNow = new AtomicBoolean(true);

    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public boolean isIdleNow() {
        return isIddleNow.get();
    }

    @Override
    public void registerIdleTransitionCallback(ResourceCallback callback) {
        this.resourceCallback = callback;
    }

    public void setIdleState(boolean isIdleNow){
        this.isIddleNow.set(isIdleNow);
        if(isIdleNow && resourceCallback != null){
            resourceCallback.onTransitionToIdle();
        }
    }
}
