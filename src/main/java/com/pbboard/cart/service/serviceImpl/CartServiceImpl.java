package com.pbboard.cart.service.serviceImpl;


import com.pbboard.cart.domain.CartDTO;
import com.pbboard.cart.domain.CartVO;
import com.pbboard.cart.mapper.CartMapper;
import com.pbboard.cart.service.CartService;
import com.pbboard.cart.domain.OrderDetailVO;
import com.pbboard.cart.domain.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    final CartMapper cartMapper;

    @Autowired
    public CartServiceImpl(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    @Override
    public void save(CartDTO cartDTO) {
        cartMapper.save(cartDTO);
    }

    @Override
    public List<CartVO> list(String id) {
        return cartMapper.list(id);
    }

    @Override
    public String delete(CartDTO cartDTO) {
        try {
            cartMapper.delete(cartDTO);
            return "성공";
        } catch (Exception e) {
            return "실패";
        }
    }

    @Override
    public String totalPrice(String id) {
        return cartMapper.totalPrice(id);
    }

    @Override
    public List<CartVO> checkout(String id) {
        return cartMapper.checkout(id);
    }

    @Override
    public void orderInfo(OrderVO orderVO) {
        cartMapper.orderInfo(orderVO);
    }

    @Override
    public void orderInfoDetails(OrderDetailVO orderDetailVO) {
        cartMapper.orderInfoDetails(orderDetailVO);
    }

    @Override
    public void cartAllDelete(String id) {
        cartMapper.cartAllDelete(id);
    }

    @Override
    public OrderVO orderConfirm(OrderVO orderVO) {
        return cartMapper.orderConfirm(orderVO);
    }
}
