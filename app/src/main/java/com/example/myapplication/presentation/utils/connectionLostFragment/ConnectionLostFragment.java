package com.example.myapplication.presentation.utils.connectionLostFragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentConnectionLostBinding;

public class ConnectionLostFragment extends Fragment {

    FragmentConnectionLostBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentConnectionLostBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}