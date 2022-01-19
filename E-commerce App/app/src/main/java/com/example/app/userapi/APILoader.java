package com.example.app.userapi;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import org.jetbrains.annotations.NotNull;

public class APILoader extends AsyncTaskLoader<String> {

    public APILoader(@NonNull @NotNull Context context) {
        super(context);
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.ExtractData();
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}
