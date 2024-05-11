package com.example.myapplication.di.restaurant;

import com.example.myapplication.data.repository.restaurant.RestaurantOrderRepository;
import com.example.myapplication.domain.usecase.restaurant.order.AddReadyDishesSnapshotUseCase;
import com.example.myapplication.domain.usecase.restaurant.menu.images.GetDishesImagesByIdsUseCase;
import com.example.myapplication.domain.usecase.restaurant.order.AddDishToCartUseCase;
import com.example.myapplication.domain.usecase.restaurant.order.AddToActiveOrdersUseCase;
import com.example.myapplication.domain.usecase.restaurant.order.AddToReadyDishesUseCase;
import com.example.myapplication.domain.usecase.restaurant.order.AddToTableListOrdersUseCase;
import com.example.myapplication.domain.usecase.restaurant.order.DecrementDishAmountUseCase;
import com.example.myapplication.domain.usecase.restaurant.order.DeleteOrderUseCase;
import com.example.myapplication.domain.usecase.restaurant.order.FinishOrderByPathUseCase;
import com.example.myapplication.domain.usecase.restaurant.order.GetActiveOrdersUseCase;
import com.example.myapplication.domain.usecase.restaurant.order.GetCartUseCase;
import com.example.myapplication.domain.usecase.restaurant.order.GetOrderByIdsUseCase;
import com.example.myapplication.domain.usecase.restaurant.order.GetOrderByPathUseCase;
import com.example.myapplication.domain.usecase.restaurant.order.GetOrderModelByPathUseCase;
import com.example.myapplication.domain.usecase.restaurant.order.GetOrderRestaurantIdUseCase;
import com.example.myapplication.domain.usecase.restaurant.order.GetOrderWithNotTakenDishesUseCase;
import com.example.myapplication.domain.usecase.restaurant.order.GetReadyDishesToWaiterUseCase;
import com.example.myapplication.domain.usecase.restaurant.order.IncrementDishAmountUseCase;
import com.example.myapplication.domain.usecase.restaurant.order.OnDishServedUseCase;
import com.example.myapplication.domain.usecase.restaurant.order.RemoveFromCartUseCase;
import com.example.myapplication.domain.usecase.restaurant.order.TakeOrderByCookUseCase;

public class RestaurantOrderDI {

    public static RestaurantOrderRepository restaurantOrderRepository = new RestaurantOrderRepository();

    public static GetOrderByIdsUseCase getOrderByIdsUseCase = new GetOrderByIdsUseCase();
    public static OnDishServedUseCase onDishServedUseCase = new OnDishServedUseCase();
    public static AddReadyDishesSnapshotUseCase addReadyDishesSnapshotUseCase = new AddReadyDishesSnapshotUseCase();
    public static GetReadyDishesToWaiterUseCase getReadyDishesToWaiterUseCase = new GetReadyDishesToWaiterUseCase();
    public static FinishOrderByPathUseCase finishOrderByPathUseCase = new FinishOrderByPathUseCase();
    public static GetOrderWithNotTakenDishesUseCase getOrderWithNotTakenDishesUseCase = new GetOrderWithNotTakenDishesUseCase();
    public static AddToReadyDishesUseCase addToReadyDishesUseCase = new AddToReadyDishesUseCase();
    public static TakeOrderByCookUseCase takeOrderByCookUseCase = new TakeOrderByCookUseCase();
    public static GetOrderModelByPathUseCase getOrderModelByPathUseCase = new GetOrderModelByPathUseCase();
    public static GetActiveOrdersUseCase getActiveOrdersUseCase = new GetActiveOrdersUseCase();
    public static GetOrderByPathUseCase getOrderByPathUseCase = new GetOrderByPathUseCase();
    public static AddToActiveOrdersUseCase addToActiveOrdersUseCase = new AddToActiveOrdersUseCase();
    public static AddToTableListOrdersUseCase addToTableListOrdersUseCase = new AddToTableListOrdersUseCase();
    public static GetDishesImagesByIdsUseCase getDishesImagesByIdsUseCase = new GetDishesImagesByIdsUseCase();
    public static DeleteOrderUseCase deleteOrderUseCase = new DeleteOrderUseCase();
    public static GetOrderRestaurantIdUseCase getOrderRestaurantIdUseCase = new GetOrderRestaurantIdUseCase();
    public static RemoveFromCartUseCase removeFromCartUseCase = new RemoveFromCartUseCase();
    public static DecrementDishAmountUseCase decrementDishAmountUseCase = new DecrementDishAmountUseCase();
    public static IncrementDishAmountUseCase incrementDishAmountUseCase = new IncrementDishAmountUseCase();
    public static GetCartUseCase getCartUseCase = new GetCartUseCase();
    public static AddDishToCartUseCase addDishToCartUseCase = new AddDishToCartUseCase();
}
