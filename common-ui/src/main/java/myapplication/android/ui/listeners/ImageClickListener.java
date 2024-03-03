package myapplication.android.ui.listeners;

import androidx.activity.result.ActivityResultLauncher;

import io.reactivex.rxjava3.core.Completable;

public interface ImageClickListener {
    Completable onClick();
}
