package cn.itcast.travel.web.servlet.impl;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.web.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    private CategoryService categoryService = new CategoryServiceImpl();
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Category> list=  categoryService.findAllSort();
        /*
        [
        {"cid":8,"cname":"全球自由行"},
        {"cid":5,"cname":"国内游"},
        {"cid":4,"cname":"处境游"},
        {"cid":7,"cname":"抱团定制"},
        {"cid":6,"cname":"港澳游"},
        {"cid":2,"cname":"酒店"},
        {"cid":1,"cname":"门票"},
        {"cid":3,"cname":"香港车票"}
        ]

        * */

        writeValue(list,response);
    }

}
