package cn.itcast.travel.web.servlet.impl;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.CompetitiveService;
import cn.itcast.travel.service.impl.CompetitiveServiceImpl;
import cn.itcast.travel.web.servlet.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/competitive/*")
public class CompetitiveServlet extends BaseServlet {
    private CompetitiveService competitiveService = new CompetitiveServiceImpl();
    public void popularity(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.这是人气旅游，应该查询数据库中，哪些旅游线路收藏的最多，哪些就最受欢迎
        //2.只需要查询一个数据库就可以了，tab_route 这张表
         List<Route> popularityList=  competitiveService.queryPopularity();
         writeValue(popularityList,response);

        List<Route> newestList = competitiveService.queryNewest();
        writeValue(newestList,response);

        List<Route> themeList = competitiveService.queryTheme();
        writeValue(themeList,response);

    }
    public void newest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.这是人气旅游，应该查询数据库中，哪些旅游线路收藏的最多，哪些就最受欢迎
        //2.只需要查询一个数据库就可以了，tab_route 这张表

        List<Route> newestList = competitiveService.queryNewest();
        writeValue(newestList,response);


    }
    public void theme(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //1.这是人气旅游，应该查询数据库中，哪些旅游线路收藏的最多，哪些就最受欢迎
        //2.只需要查询一个数据库就可以了，tab_route 这张表

        List<Route> themeList = competitiveService.queryTheme();
        writeValue(themeList,response);

    }

    /**
     * 国内游
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void domestic(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> domesticList = competitiveService.queryDomestic();
        writeValue(domesticList,response);

    }

    public void overseas(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Route> overseasList = competitiveService.queryOverseas();
        writeValue(overseasList,response);

    }


}
