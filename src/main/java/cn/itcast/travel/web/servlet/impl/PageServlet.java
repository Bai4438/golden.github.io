package cn.itcast.travel.web.servlet.impl;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;
import cn.itcast.travel.web.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class PageServlet extends BaseServlet {
    private RouteService routeService = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //int cid, int currentPage, int pageSize
        String currentPage = request.getParameter("currentPage");
        String cid = request.getParameter("cid");
        String pageSize = request.getParameter("pageSize");
        String rname = request.getParameter("rname");
        if (rname != null && rname.length() >0&&!rname.equals("")) {
            rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        }
        if (rname.equals("null")){
            rname="";
        }
        if (currentPage == null || currentPage.equals("")) {
            currentPage = "1";
        }
        if (pageSize == null || pageSize.equals("")) {
            pageSize = "8";
        }
        if (cid == null || "".equals(cid) || cid.length() == 0) {
            cid = "5";
        }
        PageBean<Route> pb = routeService.pageQuery(Integer.parseInt(cid), Integer.parseInt(currentPage), Integer.parseInt(pageSize), rname);
        writeValue(pb, response);
    }
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收参数
        String rid = request.getParameter("rid");
        if (rid!=null&&!"null".equals(rid)){
            Route route =  routeService.findOne(Integer.parseInt(rid));
            writeValue(route,response);
        }

    }

    /**
     * 判断当前登陆用户是否收藏过该线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.获取线路的id
        int rid = Integer.parseInt(request.getParameter("rid"));
        //2.获取当前登陆的用户
        User user = (User) request.getSession().getAttribute("all_user");
        int uid;
        if (user==null){
            //该用户尚未登陆
            return;
        }else {
            //用户已经登陆
            uid = user.getUid();
            //3.调用favoriteService 查询是否收藏
        }
        boolean flag = favoriteService.isFavorite(rid, uid);
        writeValue(flag,response);
    }

    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //添加收藏
        //1.获取线路的id
        int rid = Integer.parseInt(request.getParameter("rid"));
        //2.获取当前用户
        User user = (User) request.getSession().getAttribute("all_user");
        int uid;
        if (user==null){
            //该用户尚未登陆
            return;
        }else {
            //用户已经登陆
            uid = user.getUid();
        }
        favoriteService.add(rid,uid);
    }

}
