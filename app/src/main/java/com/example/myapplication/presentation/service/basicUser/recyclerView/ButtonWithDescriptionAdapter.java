package com.example.myapplication.presentation.service.basicUser.recyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.presentation.service.ScanCode;
import com.journeyapps.barcodescanner.ScanOptions;

import myapplication.android.common_ui.databinding.RecyclerViewButtonWithDescriptionLayoutBinding;

public class ButtonWithDescriptionAdapter extends ListAdapter<ButtonWithDescriptionModel, RecyclerView.ViewHolder> {

    public ButtonWithDescriptionAdapter() {
        super(new ButtonWithDescriptionCallBack());
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(RecyclerViewButtonWithDescriptionLayoutBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder)holder).bind(getItem(position));
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        RecyclerViewButtonWithDescriptionLayoutBinding binding;

        public ViewHolder(RecyclerViewButtonWithDescriptionLayoutBinding _binding) {
            super(_binding.getRoot());
            binding = _binding;
        }

        void bind(ButtonWithDescriptionModel model){
            binding.headLine.setText(model.getTitle());
            binding.description.setText(model.getDescription());
            binding.icon.setImageDrawable(ResourcesCompat.getDrawable(itemView.getResources(), model.getDrawable(), itemView.getContext().getTheme()));
            binding.item.setOnClickListener(v -> {
                setScanOptions(model.getLauncher());
            });
        }

        private void setScanOptions(ActivityResultLauncher<ScanOptions> launcher) {
            ScanOptions scanOptions = new ScanOptions();
            scanOptions.setPrompt("Scan Qr-Code");
            scanOptions.setBeepEnabled(true);
            scanOptions.setCaptureActivity(ScanCode.class);
            scanOptions.setDesiredBarcodeFormats(ScanOptions.QR_CODE);
            launcher.launch(scanOptions);
        }
    }
}
