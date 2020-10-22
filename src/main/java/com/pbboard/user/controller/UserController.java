package com.pbboard.user.controller;

import com.pbboard.user.domain.UserInfoDTO;
import com.pbboard.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class UserController {
    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/")
    public String main() {
        return "index";
    }

    /* 로그인 */
    @GetMapping("/login")
    public String login(HttpServletRequest request)
    {
        // 현재 요청된 페이지의 링크 이전의 웹 페이지 주소를 저장
        String uri = request.getHeader("Referer");

        // 로그인 실패시 이전페이지가 로그인페이지가 되므로 조건문 설정
        // 요청 페이지가 /login 미포함시에만
        if(!uri.contains("/login")) {
            request.getSession().setAttribute("prePage",
                    request.getHeader("Referer")); // 이전 페이지 다시 저장?
        }

        return "/user/login";
    }

    @PostMapping("/login")
    public String postLogin(HttpServletRequest request,
                            RedirectAttributes rttr) {
        String errorMsg = String.valueOf(request.getAttribute("loginFailMsg"));

        /* 에러 메시지 존재하지 않을 경우 null값, 존재할 경우 에러 메시지 저장 */
        if(errorMsg.isEmpty()) {
            rttr.addFlashAttribute("loginFailMsg", null);
        } else {
            rttr.addFlashAttribute("loginFailMsg", errorMsg);
        }

        /* 새로고침시 post 재요청 방지*/
        return "redirect:/login";
    }

    /* 회원가입 */
    @PostMapping("/user")
    public String signup(UserInfoDTO infoDTO) throws Exception {
        userService.save(infoDTO);
        return "redirect:/login";
    }

    /* 로그아웃 */
    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/index";
    }

    /* 회원가입 페이지 이동 */
    @GetMapping("/signup")
    public String signup() {
        return "/user/registration";
    }

    /* 회원가입 */
    @PostMapping("/saveUser")
    @ResponseBody
    public String signupA(@RequestBody UserInfoDTO userInfoDTO) {
        return userService.save(userInfoDTO);
    }
}
