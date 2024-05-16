package com.example.myapplication.presentation.home;

import static com.example.myapplication.presentation.utils.Utils.ANONYMOUS;
import static com.example.myapplication.presentation.utils.Utils.APP_PREFERENCES;
import static com.example.myapplication.presentation.utils.Utils.APP_STATE;
import static com.example.myapplication.presentation.utils.Utils.BASIC;
import static com.example.myapplication.presentation.utils.Utils.COMPANY;
import static com.example.myapplication.presentation.utils.Utils.COMPANY_ID;
import static com.example.myapplication.presentation.utils.constants.Restaurant.RESTAURANT;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.presentation.MainActivity;
import com.example.myapplication.presentation.home.anonymousUser.AnonymousUserFragment;
import com.example.myapplication.presentation.home.basicUser.HomeBasisUserFragment;
import com.example.myapplication.presentation.home.companyUser.HomeQueueCompanyUserFragment;
import com.example.myapplication.presentation.home.recycler.stories.StoryAdapter;
import com.example.myapplication.presentation.home.recycler.stories.StoryModel;
import com.example.myapplication.presentation.home.restaurantUser.RestaurantHomeFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initStoriesRecycler();
        setView();
    }

    private void initStoriesRecycler() {

        buildList(new StoryModel[]{
                new StoryModel(1, getString(R.string.features), R.drawable.story_primary_background, R.drawable.quserve_icon_drawable,
                        () -> {
                            openStoriesActivity(new int[]{
                                    R.drawable.quserve_features_page1,
                                    R.drawable.quserve_features_page2,
                                    R.drawable.quserve_features_page3,
                                    R.drawable.quserve_features_page4,
                                    R.drawable.quserve_features_page5
                            });
                        }),
                new StoryModel(1, getString(R.string.services), R.drawable.story_tertiary_background, R.drawable.create_image,
                        () -> {

                        }),
        });
    }

    private void openStoriesActivity(int[] ints) {
        ((MainActivity)requireActivity()).openStoriesActivity(ints);
    }

    private void buildList(StoryModel[] storyModels) {
        StoryAdapter adapter = new StoryAdapter();
        List<StoryModel> models = Arrays.asList(storyModels);
        binding.recyclerView.setAdapter(adapter);
        adapter.submitList(models);
    }

    private void setView() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        String type = sharedPreferences.getString(APP_STATE, ANONYMOUS);
        String companyId;
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

        switch (type) {
            case ANONYMOUS:
                fragmentManager.beginTransaction()
                        .replace(R.id.home_container, AnonymousUserFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
                break;
            case BASIC:
                fragmentManager.beginTransaction()
                        .replace(R.id.home_container, HomeBasisUserFragment.class, null)
                        .setReorderingAllowed(true)
                        .commit();
                break;
            case COMPANY:
                companyId = sharedPreferences.getString(COMPANY_ID, null);

                Bundle bundle = new Bundle();
                bundle.putString(COMPANY_ID, companyId);

                fragmentManager.beginTransaction()
                        .replace(R.id.home_container, HomeQueueCompanyUserFragment.class, bundle)
                        .setReorderingAllowed(true)
                        .commit();
                break;
            case RESTAURANT:
                companyId = sharedPreferences.getString(COMPANY_ID, null);

                Bundle bundleRestaurant = new Bundle();
                bundleRestaurant.putString(COMPANY_ID, companyId);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.home_container, RestaurantHomeFragment.class, bundleRestaurant)
                        .commit();
                break;

        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}