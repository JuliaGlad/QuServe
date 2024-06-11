package com.example.myapplication.di.restaurant;

import com.example.myapplication.data.repository.restaurant.RestaurantTablesRepository;
import com.example.myapplication.domain.usecase.restaurant.tables.AddRestaurantTablesUseCase;
import com.example.myapplication.domain.usecase.restaurant.tables.CheckTableOrderUseCase;
import com.example.myapplication.domain.usecase.restaurant.tables.DeleteTableUseCase;
import com.example.myapplication.domain.usecase.restaurant.tables.GetRestaurantTablesUseCase;
import com.example.myapplication.domain.usecase.restaurant.tables.GetSingleTableByIdUseCase;
import com.example.myapplication.domain.usecase.restaurant.tables.GetTableIdByOrderPathAndTableIdUseCase;
import com.example.myapplication.domain.usecase.restaurant.tables.GetTableIdByPathUseCase;
import com.example.myapplication.domain.usecase.restaurant.tables.images.DeleteTableQrCodeJpgUseCase;
import com.example.myapplication.domain.usecase.restaurant.tables.images.DeleteTableQrCodePdfUseCase;
import com.example.myapplication.domain.usecase.restaurant.tables.images.GetTableQrCodeJpgUseCase;
import com.example.myapplication.domain.usecase.restaurant.tables.images.GetTableQrCodePdfUseCase;
import com.example.myapplication.domain.usecase.restaurant.tables.images.UploadTableQrCodeJpgUseCase;
import com.example.myapplication.domain.usecase.restaurant.tables.images.UploadTableQrCodePdfUseCase;

public class RestaurantTableDI {

    public static RestaurantTablesRepository restaurantTablesRepository = new RestaurantTablesRepository();
    public static RestaurantTablesRepository.TableImages tableImages = new RestaurantTablesRepository.TableImages();

    public static GetTableIdByOrderPathAndTableIdUseCase tableIdByOrderPathAndTableIdUseCase = new GetTableIdByOrderPathAndTableIdUseCase();
    public static GetTableIdByPathUseCase getTableIdByPathUseCase = new GetTableIdByPathUseCase();
    public static CheckTableOrderUseCase checkTableOrderUseCase = new CheckTableOrderUseCase();
    public static AddRestaurantTablesUseCase addRestaurantTablesUseCase = new AddRestaurantTablesUseCase();
    public static UploadTableQrCodeJpgUseCase uploadTableQrCodeJpgUseCase = new UploadTableQrCodeJpgUseCase();
    public static UploadTableQrCodePdfUseCase uploadTableQrCodePdfUseCase = new UploadTableQrCodePdfUseCase();
    public static GetRestaurantTablesUseCase getRestaurantTablesUseCase = new GetRestaurantTablesUseCase();
    public static GetTableQrCodeJpgUseCase getTableQrCodeJpgUseCase = new GetTableQrCodeJpgUseCase();
    public static GetSingleTableByIdUseCase getSingleTableByIdUseCase = new GetSingleTableByIdUseCase();
    public static GetTableQrCodePdfUseCase getTableQrCodePdfUseCase = new GetTableQrCodePdfUseCase();
    public static DeleteTableUseCase deleteTableUseCase = new DeleteTableUseCase();
    public static DeleteTableQrCodeJpgUseCase deleteTableQrCodeJpgUseCase = new DeleteTableQrCodeJpgUseCase();
    public static DeleteTableQrCodePdfUseCase deleteTableQrCodePdfUseCase = new DeleteTableQrCodePdfUseCase();
}
