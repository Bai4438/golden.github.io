package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.CompetitiveDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CompetitiveDaoImpl implements CompetitiveDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    private RouteDao routeDao = new RouteDaoImpl();

    @Override
    public List<Route> queryPopularity() {
        String sql = "SELECT *  FROM   tab_route ORDER BY count DESC LIMIT   4";
        return template.query(sql, new BeanPropertyRowMapper<>(Route.class));
    }

    @Override
    public List<Route> queryNewest() {
        String sql = "SELECT * FROM tab_route ORDER BY rdate DESC LIMIT 4";
        return template.query(sql, new BeanPropertyRowMapper<>(Route.class));
    }

    @Override
    public List<Route> queryTheme() {
        List<Route> list = new ArrayList<>();
        Random random = new Random();
        int num;
        for (int i = 0; i < 4; i++) {
            num = random.nextInt(routeDao.findTotalCount()) + 1;
            String sql = "SELECT *  FROM   tab_route where  rid = ?";
            Route route = template.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class), num);
            list.add(route);
        }
        return list;
    }

    /**
     * 国内游
     *
     * @return
     */
    @Override
    public List<Route> queryDomestic() {
        int cid = 5;
        int totalCount = routeDao .findTotalCount(cid);
        int totalPage = totalCount%8==0 ?totalCount/8:totalCount/8+1;
        Random random = new Random();
        int ran = random.nextInt(totalPage)+1;
        String sql = "SELECT *  FROM   tab_route where  cid = ? limit  ?,?";
        List<Route> query = template.query(sql, new BeanPropertyRowMapper<>(Route.class),cid,(ran-1)*6,6);
        return query;
    }

    @Override
    public List<Route> queryOverseas() {
        int cid1 = 4;
        int cid2 = 8;
        int totalCount = routeDao .findTotalCount(cid1  ,cid2);
        int totalPage = totalCount%8==0 ?totalCount/8:totalCount/8+1;
        Random random = new Random();
        int ran = random.nextInt(totalPage);
        String sql = "SELECT *  FROM   tab_route where  cid =? or cid = ? limit  ?,?";
        List<Route> query = template.query(sql, new BeanPropertyRowMapper<>(Route.class),cid1,cid2,(ran-1)*6,6);
        return query;
    }
}
