package com.example.myapplication.di;

import com.example.myapplication.domain.usecase.restaurant.AddRestaurantSnapshotUseCase;
import com.example.myapplication.domain.usecase.restaurant.AddRestaurantTablesUseCase;
import com.example.myapplication.domain.usecase.restaurant.CreateRestaurantDocumentUseCase;
import com.example.myapplication.domain.usecase.restaurant.CreateRestaurantLocationDocumentUseCase;
import com.example.myapplication.domain.usecase.restaurant.DeleteRestaurantUseCase;
import com.example.myapplication.domain.usecase.restaurant.DeleteTableQrCodeJpgUseCase;
import com.example.myapplication.domain.usecase.restaurant.DeleteTableQrCodePdfUseCase;
import com.example.myapplication.domain.usecase.restaurant.DeleteTableUseCase;
import com.example.myapplication.domain.usecase.restaurant.GetCookQrCodeUseCase;
import com.example.myapplication.domain.usecase.restaurant.GetRestaurantEditModel;
import com.example.myapplication.domain.usecase.restaurant.GetRestaurantLocationsUseCase;
import com.example.myapplication.domain.usecase.restaurant.GetRestaurantTablesUseCase;
import com.example.myapplication.domain.usecase.restaurant.GetSingleRestaurantLogoUseCase;
import com.example.myapplication.domain.usecase.restaurant.GetSingleRestaurantUseCase;
import com.example.myapplication.domain.usecase.restaurant.GetSingleTableByIdUseCase;
import com.example.myapplication.domain.usecase.restaurant.GetTableQrCodeJpgUseCase;
import com.example.myapplication.domain.usecase.restaurant.GetTableQrCodePdfUseCase;
import com.example.myapplication.domain.usecase.restaurant.GetWaiterQrCodeUseCase;
import com.example.myapplication.domain.usecase.restaurant.UpdateRestaurantDataUseCase;
import com.example.myapplication.domain.usecase.restaurant.UploadCookQrCodeUseCase;
import com.example.myapplication.domain.usecase.restaurant.UploadRestaurantLogoUseCase;
import com.example.myapplication.domain.usecase.restaurant.UploadTableQrCodeJpgUseCase;
import com.example.myapplication.domain.usecase.restaurant.UploadTableQrCodePdfUseCase;
import com.example.myapplication.domain.usecase.restaurant.UploadWaiterQrCodeUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.AddDishUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.AddIngredientsToRemoveUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.AddMenuCategoryUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.AddNewRequireChoiceVariantUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.AddRequiredChoicesUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.AddToppingUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.DeleteIngredientToRemoveUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.DeleteRequiredChoiceByIdUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.DeleteRequiredChoiceVariantUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.DeleteToppingImageUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.DeleteToppingUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.GetCategoriesImagesUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.GetCategoriesUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.GetDishesImagesUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.GetDishesMenuOwnerModelsUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.GetIngredientsToRemoveUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.GetRequiredChoicesUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.GetSingleDishByIdUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.GetSingleDishImageUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.GetSingleRequiredChoiceByIdUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.GetToppingsImagesUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.GetToppingsUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.UpdateDishDataUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.UpdateIngredientsToRemoveUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.UpdateRequiredChoiceNameUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.UpdateRequiredChoiceVariantUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.UploadCategoryBytesImageUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.UploadCategoryUriImageUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.UploadDishImageUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.UploadToppingImage;

public class RestaurantDI {
    public static DeleteIngredientToRemoveUseCase deleteIngredientToRemoveUseCase = new DeleteIngredientToRemoveUseCase();
    public static UpdateIngredientsToRemoveUseCase updateIngredientsToRemoveUseCase = new UpdateIngredientsToRemoveUseCase();
    public static UpdateRequiredChoiceNameUseCase updateRequiredChoiceNameUseCase = new UpdateRequiredChoiceNameUseCase();
    public static AddNewRequireChoiceVariantUseCase addNewRequireChoiceVariantUseCase = new AddNewRequireChoiceVariantUseCase();
    public static UpdateRequiredChoiceVariantUseCase updateRequiredChoiceVariantUseCase = new UpdateRequiredChoiceVariantUseCase();
    public static DeleteRequiredChoiceVariantUseCase deleteRequiredChoiceVariantUseCase = new DeleteRequiredChoiceVariantUseCase();
    public static DeleteRequiredChoiceByIdUseCase deleteRequiredChoiceByIdUseCase = new DeleteRequiredChoiceByIdUseCase();
    public static GetSingleRequiredChoiceByIdUseCase getSingleRequiredChoiceByIdUseCase = new GetSingleRequiredChoiceByIdUseCase();
    public static DeleteToppingUseCase deleteToppingUseCase = new DeleteToppingUseCase();
    public static DeleteToppingImageUseCase deleteToppingImageUseCase = new DeleteToppingImageUseCase();
    public static UpdateDishDataUseCase updateDishDataUseCase = new UpdateDishDataUseCase();
    public static GetSingleDishImageUseCase getSingleDishImageUseCase = new GetSingleDishImageUseCase();
    public static GetSingleDishByIdUseCase getSingleDishByIdUseCase = new GetSingleDishByIdUseCase();
    public static GetCookQrCodeUseCase getCookQrCodeUseCase = new GetCookQrCodeUseCase();
    public static GetWaiterQrCodeUseCase getWaiterQrCodeUseCase = new GetWaiterQrCodeUseCase();
    public static UploadCookQrCodeUseCase uploadCookQrCodeUseCase = new UploadCookQrCodeUseCase();
    public static UploadWaiterQrCodeUseCase uploadWaiterQrCodeUseCase = new UploadWaiterQrCodeUseCase();
    public static AddRestaurantSnapshotUseCase addRestaurantSnapshotUseCase = new AddRestaurantSnapshotUseCase();
    public static CreateRestaurantDocumentUseCase createRestaurantDocumentUseCase = new CreateRestaurantDocumentUseCase();
    public static GetSingleRestaurantUseCase getSingleRestaurantUseCase = new GetSingleRestaurantUseCase();
    public static GetSingleRestaurantLogoUseCase getSingleRestaurantLogoUseCase = new GetSingleRestaurantLogoUseCase();
    public static GetRestaurantEditModel getRestaurantEditModel = new GetRestaurantEditModel();
    public static UpdateRestaurantDataUseCase updateRestaurantDataUseCase = new UpdateRestaurantDataUseCase();
    public static UploadRestaurantLogoUseCase uploadRestaurantLogoUseCase = new UploadRestaurantLogoUseCase();
    public static DeleteRestaurantUseCase deleteRestaurantUseCase = new DeleteRestaurantUseCase();
    public static GetRestaurantLocationsUseCase getRestaurantLocationsUseCase = new GetRestaurantLocationsUseCase();
    public static CreateRestaurantLocationDocumentUseCase createRestaurantLocationDocumentUseCase = new CreateRestaurantLocationDocumentUseCase();
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
    public static AddDishUseCase addDishUseCase = new AddDishUseCase();
    public static AddMenuCategoryUseCase addMenuCategoryUseCase = new AddMenuCategoryUseCase();
    public static GetCategoriesImagesUseCase getCategoriesImagesUseCase = new GetCategoriesImagesUseCase();
    public static GetCategoriesUseCase getCategoriesUseCase = new GetCategoriesUseCase();
    public static GetDishesImagesUseCase getDishesImagesUseCase = new GetDishesImagesUseCase();
    public static GetDishesMenuOwnerModelsUseCase getDishesMenuOwnerModelsUseCase = new GetDishesMenuOwnerModelsUseCase();
    public static GetToppingsImagesUseCase getToppingsImagesUseCase = new GetToppingsImagesUseCase();
    public static UploadCategoryBytesImageUseCase uploadCategoryBytesImageUseCase = new UploadCategoryBytesImageUseCase();
    public static UploadDishImageUseCase uploadDishImageUseCase = new UploadDishImageUseCase();
    public static UploadToppingImage uploadToppingImage = new UploadToppingImage();
    public static UploadCategoryUriImageUseCase uploadCategoryUriImageUseCase = new UploadCategoryUriImageUseCase();
    public static GetToppingsUseCase getToppingsUseCase = new GetToppingsUseCase();
    public static GetIngredientsToRemoveUseCase getIngredientsToRemoveUseCase = new GetIngredientsToRemoveUseCase();
    public static GetRequiredChoicesUseCase getRequiredChoicesUseCase = new GetRequiredChoicesUseCase();
    public static AddToppingUseCase addToppingUseCase = new AddToppingUseCase();
    public static AddIngredientsToRemoveUseCase addIngredientToRemoveUseCase = new AddIngredientsToRemoveUseCase();
    public static AddRequiredChoicesUseCase addRequiredChoicesUseCase = new AddRequiredChoicesUseCase();
}
