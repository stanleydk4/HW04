package com.systex.hw4.controller;

import java.net.http.HttpResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.systex.hw4.model.Users;
import com.systex.hw4.service.UserService;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String showLoginPage() {
        return "loginUser";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model,HttpSession session) {
        if (userService.login(username, password)) {
        	session.setAttribute("username", username);
            model.addAttribute("username", username);
            return "redirect:/index";
        } else {
            model.addAttribute("error", "輸入的帳號密碼錯誤");
            return "loginUser";
        }
    }

    @GetMapping("/register")
    public String showRegisterPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute Users user, Model model,RedirectAttributes redirectAttributes) {
        if (userService.register(user)) {
        	//model.addAttribute("error", "註冊成功!");
            //return "redirect:/login";
        	return "redirect:/login?error=regiSuccess";
        } else {
            model.addAttribute("error", "帳號名稱已被使用");
            return "register";
        }
    }
    
    @GetMapping("/index")
    public String index(HttpSession session, RedirectAttributes redirectAttributes) {
        if (session.getAttribute("username") == null) {
            return "redirect:/login";
        }
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
