package cn.itcast.travel.test;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class App {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    private RouteDao routeDao = new RouteDaoImpl();
    /*@Test
    public void test(){
        String  sql = "select * from  tab_user";
        List<User> query = template.query(sql, new BeanPropertyRowMapper<>(User.class));
        for (User user : query) {
            System.out.println(user);
        }
    }

    @Test
    public void pageQuery(){
        PageBean<Route> pb = new PageBean<>();
        int cid = 5;
        int currentPage = 5;
        int pageSize = 10;
        int totalCount = routeDao.findTotalCount(cid, rname);
        pb.setTotalCount(totalCount);
        int totalPage = totalCount%currentPage==0?totalCount/currentPage:totalCount/currentPage+1;
        pb.setTotalPage(totalPage);
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        List<Route> byPage = routeDao.findByPage(cid, currentPage, pageSize, rname);
        pb.setList(byPage);
        System.out.println(pb);
    }*/

    @Test
    public void test_1() {
        int t = 10 % 3 == 0 ? 10 / 3 : 10 / 3 + 1;
        System.out.println(t);
    }

    @Test
    public void findTotalCount() {
        int cid = 5;
        String rname = "西安";
//        String sql = "select  count(*) from tab_route cid = ?";
        String sql = "select  count(*) from tab_route where 1 =1 ";
        StringBuilder sb = new StringBuilder(sql);
        List<Object> params = new ArrayList<>();
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0) {
            sb.append(" and rname like  ?  ");
            params.add("%" + rname + "%");
        }
        sql = sb.toString();
        System.out.println(sql);
        Integer count = template.queryForObject(sql, Integer.class, params.toArray());
        System.out.println(count);
    }

    @Test
    public void findEnptyCount() {
        int cid = 5;
        String rname = "";
//        String sql = "select  count(*) from tab_route cid = ?";
        String sql = "select  count(*) from tab_route where 1 =1 ";
        StringBuilder sb = new StringBuilder(sql);
        List<Object> params = new ArrayList<>();
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0 && !rname.equals("")) {
            sb.append(" and rname like  ?  ");
            params.add("%" + rname + "%");
        }
        sql = sb.toString();
        System.out.println(sql);
        Integer count = template.queryForObject(sql, Integer.class, params.toArray());
        System.out.println(count);
    }

    @Test
    public void test_newest() {
        List<Route> list = new ArrayList<>();
        Random random = new Random();
        int num;
        for (int i = 0; i < 4; i++) {
            num = random.nextInt(8) + 1;
            String sql = "SELECT *  FROM   tab_route where  cid = ?";
            Route route = template.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class), num);
            list.add(route);
        }

    }

    @Test
    public void test_newest_A() {
        int cid = 5;
        int totalCount = routeDao.findTotalCount(cid);
        int totalPage = totalCount % 8 == 0 ? totalCount / 8 : totalCount / 8 + 1;
        Random random = new Random();
        int ran = random.nextInt(totalPage) + 1;
        String sql = "SELECT *  FROM   tab_route where  cid = ? limit  ?,?";
        List<Route> query = template.query(sql, new BeanPropertyRowMapper<>(Route.class), cid, (ran - 1) * 6, 6);
        System.out.println(query);

    }

    @Test
    public void test_domestic_overseas() {
        int cid1 = 4;
        int cid2 = 8;
        int totalCount = routeDao.findTotalCount(cid1, cid2);
        int totalPage = totalCount % 8 == 0 ? totalCount / 8 : totalCount / 8 + 1;
        Random random = new Random();
        int ran = random.nextInt(totalPage);
        String sql = "SELECT *  FROM   tab_route where  cid =? or cid = ? limit  ?,?";
        List<Route> query = template.query(sql, new BeanPropertyRowMapper<>(Route.class), cid1, cid2, (ran - 1) * 6, 6);
        System.out.println(query);
    }

    @Test
    public void test_favorite() {

        int uid = 28;
        String sql = "SELECT * FROM  tab_route WHERE tab_route.rid in (SELECT rid FROM tab_favorite WHERE uid = ?);";
        List<Route> myfavorite = null;
        try {
            myfavorite = template.query(sql, new BeanPropertyRowMapper<>(Route.class), uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        for (Route route : myfavorite) {
            System.out.println(route);
        }
    }


}
