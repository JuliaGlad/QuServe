package com.example.myapplication.presentation.dialogFragments.chooseCity;

import static com.example.myapplication.presentation.utils.constants.Utils.CITY_KEY;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.DialogFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FullScreenChooseCityDialogBinding;
import com.example.myapplication.presentation.dialogFragments.chooseCity.cityRecyclerItem.CityItemAdapter;
import com.example.myapplication.presentation.dialogFragments.chooseCity.cityRecyclerItem.CityItemModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import myapplication.android.ui.listeners.DialogDismissedListener;

public class ChooseCityFullScreenDialog extends DialogFragment {

    List<CityItemModel> cities = new ArrayList<>();
    CityItemAdapter adapter = new CityItemAdapter();
    FullScreenChooseCityDialogBinding binding;
    private DialogDismissedListener listener;
    private String name = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FullScreenChooseCityDialogBinding.inflate(getLayoutInflater());

        initRecycler();
        initCloseButton();
        initSearchView();

        return binding.getRoot();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    public void onDismissListener(DialogDismissedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (listener != null && name != null) {

            Bundle bundle = new Bundle();
            bundle.putString(CITY_KEY, name);
            ;
            listener.handleDialogClose(bundle);
        }
    }

    private void initRecycler() {
        try {
            InputStream inputStream = requireContext().getResources().openRawResource(R.raw.russian_cities);
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String json;

            json = new String(buffer, StandardCharsets.UTF_8);
            JSONArray jsonArray = new JSONArray(json);
            cities.add(new CityItemModel(0, getString(R.string.all_cities), () -> {
                this.name = getString(R.string.all_cities);
                dismiss();
            }));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String name = jsonObject.getString("name");
                cities.add(new CityItemModel(i, name, () -> {
                    this.name = name;
                    dismiss();
                }));
            }
            adapter.submitList(cities);
            binding.recyclerView.setAdapter(adapter);


        } catch (IOException | JSONException e) {
            throw new RuntimeException(e);
        }

    }

    private void initCloseButton() {
        binding.buttonClose.setOnClickListener(v -> {
            dismiss();
        });
    }

    private void initSearchView() {

        binding.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });

    }

    private void filterList(String newText) {
        if (!newText.isEmpty()) {
            List<CityItemModel> filteredList = new ArrayList<>();

            for (CityItemModel model : cities) {
                if (model.getName().toLowerCase().contains(newText.toLowerCase())) {
                    filteredList.add(model);
                }
            }
            setFilteredList(filteredList);
        } else {
            adapter.submitList(cities);
        }
    }

    private void setFilteredList(List<CityItemModel> models) {
        adapter.submitList(models);
    }

}