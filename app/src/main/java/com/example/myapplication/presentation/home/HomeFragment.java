package com.example.myapplication.presentation.home;

import static android.os.Build.VERSION.SDK_INT;
import static com.example.myapplication.presentation.utils.Utils.FINE_PERMISSION_CODE;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.multidex.BuildConfig;
import androidx.navigation.fragment.NavHostFragment;

import com.example.myapplication.R;
import com.example.myapplication.databinding.FragmentHomeBinding;
import com.example.myapplication.presentation.dialogFragments.chooseCity.ChooseCityFullScreenDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.protobuf.Api;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private ActivityResultLauncher<Intent> launcher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.button5.setOnClickListener(v -> {
            Random rand = new Random();
            String code = String.valueOf(rand.nextInt());

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {

                BitMatrix bitMatrix = multiFormatWriter.encode(code, BarcodeFormat.QR_CODE, 300, 300);
                BarcodeEncoder encoder = new BarcodeEncoder();
                Bitmap qrCode = encoder.createBitmap(bitMatrix);

                createPdfDocument(qrCode, code);

            } catch (WriterException e) {
                e.printStackTrace();
            }
        });
    }

    private void createPdfDocument(Bitmap qrCode, String code) {
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(qrCode.getWidth(), qrCode.getHeight(), 1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        page.getCanvas().drawBitmap(qrCode, 0, 0, null);

        pdfDocument.finishPage(page);

        if (askPermission()) {

            File root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            File folder = new File(root.getAbsolutePath());

            if (!folder.exists()) {
                folder.mkdirs();
            }

            File file = new File(folder.getAbsolutePath() + "/" + "QR-CODE.pdf");

            try {
                if (file.exists()) {
                    FileOutputStream fos = new FileOutputStream(file);
                    pdfDocument.writeTo(fos);
                    pdfDocument.close();
                    fos.close();

                } else {
                    Log.e("NOT EXIST!", "File does not exists");
                }

            } catch (FileNotFoundException e) {
                Log.e("FileNotFoundException", e.getMessage());
            } catch (IOException e) {
                Log.e("IOEXCEPTION", e.getMessage());
            }
        }
    }

    private boolean askPermission() {
        boolean permission = false;
        if (SDK_INT >= 30) {
            if (!Environment.isExternalStorageManager()) {
                try {

                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", requireContext().getApplicationContext().getPackageName())));
                    startActivity(intent);

                } catch (Exception e) {

                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    startActivity(intent);

                }

            } else {
                permission = true;
            }
        } else {
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, FINE_PERMISSION_CODE);
            }
            if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, FINE_PERMISSION_CODE);
            }
            permission = true;
        }
        return permission;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}