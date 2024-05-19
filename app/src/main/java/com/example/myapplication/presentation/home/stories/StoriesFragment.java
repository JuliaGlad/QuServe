package com.example.myapplication.presentation.home.stories;

import static com.example.myapplication.presentation.utils.Utils.BACKGROUND_IMAGE;
import static com.example.myapplication.presentation.utils.Utils.DRAWABLES;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentQuServeFeaturesBinding;

import jp.shts.android.storiesprogressview.StoriesProgressView;

public class StoriesFragment extends Fragment implements StoriesProgressView.StoriesListener {

    private int PROGRESS_COUNT;
    int[] drawables;
    StoriesViewModel viewModel;
    FragmentQuServeFeaturesBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(StoriesViewModel.class);
        binding = FragmentQuServeFeaturesBinding.inflate(inflater, container, false);
        drawables = requireActivity().getIntent().getIntArrayExtra(DRAWABLES);
        int background = requireActivity().getIntent().getIntExtra(BACKGROUND_IMAGE, 0);
        binding.layout.setBackground(ResourcesCompat.getDrawable(getResources(), background, getActivity().getTheme()));
        if (drawables != null) {
            PROGRESS_COUNT = drawables.length;
        }
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.storyImage.setImageDrawable(ResourcesCompat.getDrawable(getResources(), drawables[0], getActivity().getTheme()));
        initStoriesProgressBar();
        setupObserves();
        initImage();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initImage() {
        binding.storyImage.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (binding.layout.getWidth() / 2.0 < event.getX()){
                    binding.storiesProgressBar.skip();
                } else {
                    binding.storiesProgressBar.reverse();
                }
            }
            return true;
        });
    }


    private void setupObserves() {
        viewModel.page.observe(getViewLifecycleOwner(), page -> {
            if (page != null) {
                binding.storyImage.setImageDrawable(ResourcesCompat.getDrawable(getResources(), drawables[page], getActivity().getTheme()));
            }
        });
    }

    private void initStoriesProgressBar() {
        binding.storiesProgressBar.setStoriesCount(PROGRESS_COUNT);
        binding.storiesProgressBar.setStoryDuration(5000L);
        binding.storiesProgressBar.setStoriesListener(this);
        binding.storiesProgressBar.startStories();
    }

    @Override
    public void onNext() {
        viewModel.onNext(PROGRESS_COUNT);
    }

    @Override
    public void onPrev() {
        viewModel.onPrev();
    }

    @Override
    public void onComplete() {
        requireActivity().finish();
    }

    @Override
    public void onDestroy() {
        binding.storiesProgressBar.destroy();
        super.onDestroy();
    }
}