package com.example.myapplication.data.providers;

import com.example.myapplication.app.App;
import com.example.myapplication.data.db.dao.CartDao;
import com.example.myapplication.data.db.entity.CartEntity;
import com.example.myapplication.data.dto.CartDishDto;

import java.util.ArrayList;
import java.util.List;

public class CartProvider {

    public CartEntity getCart() {
        return App.instance.getDatabase().cartDao().getCart();
    }

    public void removeFromCart(String dishId) {
        CartEntity entity = getCart();
        List<CartDishDto> dtos = entity.dtos;
        dtos.removeIf(current -> current.getDishId().equals(dishId));
    }

    public void addToCart(CartDishDto dishDto) {
        CartDao cartDao = App.getInstance().getDatabase().cartDao();
        CartEntity entity = getCart();
        List<CartDishDto> cartDtos = entity.dtos;

        if (cartDtos.size() > 0) {
            cartDtos.add(dishDto);
            cartDao.update(entity);
        } else {
            List<CartDishDto> dtos = new ArrayList<>();
            dtos.add(dishDto);
            cartDao.insert(new CartEntity(dtos));
        }
    }

    public void incrementDishAmount(CartDishDto dishDto){
        CartEntity entity = getCart();
        List<CartDishDto> dtos = entity.dtos;
        for (int i = 0; i < dtos.size(); i++) {
            CartDishDto current = dtos.get(i);
            if (current.equals(dishDto)){
                int newAmount = Integer.parseInt(current.getAmount()) + 1;
                dtos.set(i, new CartDishDto(
                        current.getDishId(),
                        current.getCategoryId(),
                        String.valueOf(newAmount),
                        current.getToppings(),
                        current.getRequiredChoices(),
                        current.getToRemove()
                ));
                App.getInstance().getDatabase().cartDao().update(entity);
                break;
            }
        }
    }

    public void decrementDishAmount(CartDishDto dishDto){
        CartEntity entity = getCart();
        List<CartDishDto> dtos = entity.dtos;
        for (int i = 0; i < dtos.size(); i++) {
            CartDishDto current = dtos.get(i);
            if (current.equals(dishDto)){
                int newAmount = Integer.parseInt(current.getAmount()) - 1;
                dtos.set(i, new CartDishDto(
                        current.getDishId(),
                        current.getCategoryId(),
                        String.valueOf(newAmount),
                        current.getToppings(),
                        current.getRequiredChoices(),
                        current.getToRemove()
                ));
                App.getInstance().getDatabase().cartDao().update(entity);
                break;
            }
        }
    }
}
