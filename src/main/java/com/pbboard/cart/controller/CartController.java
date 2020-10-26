package com.pbboard.cart.controller;

import com.pbboard.cart.domain.*;
import com.pbboard.cart.service.CartService;
import com.pbboard.user.domain.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.List;

@Controller
public class CartController {
    final CartService cartService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    /* 장바구니 목록 */
    @GetMapping("/cart/list")
    public String cart(Model model) {
        // 로그인 객체에서 id 가져옴
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserInfo userInfo = (UserInfo)principal;
        String userId = userInfo.getUsername();

        model.addAttribute("id", userId);
        model.addAttribute("list", cartService.list(userId));
        model.addAttribute("totalPrice", cartService.totalPrice(userId));

        return "/cart/list";
    }

    /* 장바구니 삭제 */
    @ResponseBody
    @PostMapping("/cart/delete")
    public String delete(@RequestBody CartDTO cartDTO) throws Exception {
        return cartService.delete(cartDTO);
    }

    /* 주문 확인 */
    @GetMapping("/cart/checkout")
    public String payment(Model model) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        model.addAttribute("cartVOList", cartService.checkout(id));
        model.addAttribute("totalPrice", cartService.totalPrice(id));

        return "cart/checkout";
    }

    @PostMapping("/cart/order")
    public String order(OrderVO orderVO, OrderDetailVO orderDetailVO
                        ,Model model) throws Exception {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();

        // 주문번호 생성
        final Long orderSeq = createOrderSeq();

        // 주문 등록
        orderVO.setSeq(orderSeq);
        cartService.orderInfo(orderVO);

        // 장바구니 목록
        List<OrderDetailVO> cartDTOS = cartService.cartList(id);

        // 장바구니 목록 개수 만큼 for문 반복
        for(OrderDetailVO cartDTO : cartDTOS) {
            // 주문 가능할 경우
             if(cartService.stockCheck(cartDTO)) {
                 cartDTO.setOrderStatus("주문완료");
                 cartService.quantityChange(cartDTO);
             } else {
                 cartDTO.setOrderStatus("주문실패");
             }
            // 주문 등록
            cartDTO.setOrderSeq(orderSeq);
            cartService.orderInfoDetails(cartDTO);
        }

        //주문 후 장바구니 목록 삭제
        cartService.cartAllDelete(id);

        // 주문 결과
        OrderVO vo = cartService.orderConfirm(orderVO);
        model.addAttribute("vo", vo);

        return "/cart/order";
    }

    // 년+월+일+랜덤6자리 주문번호 생성
    private Long createOrderSeq() {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        String ym = year + new DecimalFormat("00").format(cal.get(Calendar.MONTH) + 1);
        String ymd = ym + new DecimalFormat("00").format(cal.get(Calendar.DATE));
        String subNum = "";

        for(int i=1; i<=6; i++) {
            subNum += (int)(Math.random() * 10);
        }

        String orderSeq = ymd + subNum;
        Long seq = Long.parseLong(orderSeq);

        return seq;
    }
}
