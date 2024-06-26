package com.example.myapplication.data.providers;

import com.example.myapplication.App;
import com.example.myapplication.data.db.dao.CartDao;
import com.example.myapplication.data.db.entity.CartEntity;
import com.example.myapplication.data.dto.restaurant.CartDishDto;
import com.example.myapplication.presentation.restaurantOrder.VariantCartModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CartProvider {

    public static CartEntity getCart() {
        return App.instance.getDatabase().cartDao().getCart();
    }

    public static void removeFromCart(String dishId) {
        CartEntity entity = getCart();
        List<CartDishDto> dtos = entity.dtos;
        dtos.removeIf(current -> current.getDishId().equals(dishId));
        App.getInstance().getDatabase().cartDao().update(entity);
    }

    public static void addToCart(String restaurantId, CartDishDto dishDto) {
        CartDao cartDao = App.getInstance().getDatabase().cartDao();

        CartEntity entity = getCart();
        List<CartDishDto> cartDtos = new ArrayList<>();
        if (entity != null) {
            cartDtos = entity.dtos;
        }
        if (!cartDtos.isEmpty()) {
            boolean isAdd = false;

            String dishId = dishDto.getDishId();
            int toRemoveHash = dishDto.getToRemove().hashCode();
            int requireChoiceHash = dishDto.getRequiredChoices().hashCode();

            List<VariantCartModel> toppings = dishDto.getToppings();
            List<String> toppingsNames = new ArrayList<>();
            for (VariantCartModel currentTopping : toppings) {
                toppingsNames.add(currentTopping.getName());
            }
            int toppingsHash = toppingsNames.hashCode();

            for (CartDishDto dto : cartDtos) {
                List<String> currentNames = new ArrayList<>();
                for (VariantCartModel current : dto.getToppings()) {
                    currentNames.add(current.getName());
                }
                int hashTopping = currentNames.hashCode();
                int hashRequire = dto.getRequiredChoices().hashCode();
                int hashRemove = dto.getToRemove().hashCode();

                if (dto.getDishId().equals(dishId) && requireChoiceHash == hashRequire && toRemoveHash == hashRemove && toppingsHash == hashTopping) {
                    int amount = Integer.parseInt(dto.getAmount()) + 1;
                    dto.setAmount(String.valueOf(amount));
                    isAdd = true;
                    break;
                }
            }
            if (!isAdd) {
                cartDtos.add(dishDto);
            }
            cartDao.update(entity);
        } else {
            CartEntity newEntity = new CartEntity(restaurantId, Collections.emptyList());
            cartDao.insert(newEntity);
            CartEntity entityAdded = cartDao.getCart();
            entityAdded.dtos.add(dishDto);
            cartDao.update(entityAdded);
        }
    }

    public static void incrementDishAmount(CartDishDto dishDto) {
        CartDao cartDao = App.getInstance().getDatabase().cartDao();
        CartEntity entity = cartDao.getCart();
        List<CartDishDto> cartDtos = entity.dtos;

        int toRemoveHash = dishDto.getToRemove().hashCode();
        int requireChoiceHash = dishDto.getRequiredChoices().hashCode();
        String dishId = dishDto.getDishId();

        List<VariantCartModel> toppings = dishDto.getToppings();
        List<String> toppingsNames = new ArrayList<>();
        for (VariantCartModel currentTopping : toppings) {
            toppingsNames.add(currentTopping.getName());
        }
        int toppingsHash = toppingsNames.hashCode();

        for (CartDishDto dto : cartDtos) {
            List<String> currentNames = new ArrayList<>();
            for (VariantCartModel current : dto.getToppings()) {
                currentNames.add(current.getName());
            }
            int hashTopping = currentNames.hashCode();
            int hashRequire = dto.getRequiredChoices().hashCode();
            int hashRemove = dto.getToRemove().hashCode();

            if (dto.getDishId().equals(dishId) && requireChoiceHash == hashRequire && toRemoveHash == hashRemove && toppingsHash == hashTopping) {
                int amount = Integer.parseInt(dto.getAmount()) + 1;
                dto.setAmount(String.valueOf(amount));
                cartDao.update(entity);
                break;
            }
        }

    }

    public static void decrementDishAmount(CartDishDto dishDto) {
        CartDao cartDao = App.getInstance().getDatabase().cartDao();
        CartEntity entity = cartDao.getCart();
        List<CartDishDto> cartDtos = entity.dtos;

        int toRemoveHash = dishDto.getToRemove().hashCode();
        int requireChoiceHash = dishDto.getRequiredChoices().hashCode();
        String dishId = dishDto.getDishId();

        List<VariantCartModel> toppings = dishDto.getToppings();
        List<String> toppingsNames = new ArrayList<>();
        for (VariantCartModel currentTopping : toppings) {
            toppingsNames.add(currentTopping.getName());
        }
        int toppingsHash = toppingsNames.hashCode();

        for (CartDishDto dto : cartDtos) {
            List<String> currentNames = new ArrayList<>();
            for (VariantCartModel current : dto.getToppings()) {
                currentNames.add(current.getName());
            }
            int hashTopping = currentNames.hashCode();
            int hashRequire = dto.getRequiredChoices().hashCode();
            int hashRemove = dto.getToRemove().hashCode();

            if (dto.getDishId().equals(dishId) && requireChoiceHash == hashRequire && toRemoveHash == hashRemove && toppingsHash == hashTopping) {
                int amount = Integer.parseInt(dto.getAmount()) - 1;
                dto.setAmount(String.valueOf(amount));
                cartDao.update(entity);
                break;
            }
        }
    }

    public static void deleteCart() {
        CartDao cartDao = App.getInstance().getDatabase().cartDao();
        CartEntity entity = cartDao.getCart();
        cartDao.delete(entity);
    }

}
