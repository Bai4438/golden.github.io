package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据cid 进行查询总记录数
     *
     * @param cid
     * @param rname
     * @return
     */
    @Override
    public int findTotalCount(int cid, String rname) {
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
        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    @Override
    public int findTotalCount(int cid1, int cid2) {
        String sql = "select  count(*) from tab_route where cid = ? or cid = ?";
        return template.queryForObject(sql, Integer.class, cid1, cid2);
    }

    @Override
    public int findTotalCount(int cid) {
        String sql = "select  count(*) from tab_route where cid = ? ";
        return template.queryForObject(sql, Integer.class, cid);
    }

    @Override
    public int findTotalCount() {
        String sql = "select  count(*) from tab_route";
        return template.queryForObject(sql, Integer.class);

    }



    /**
     * 根据cid start pageSize 进行查询当前页码需要的数据集合
     *
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    @Override
    public List<Route> findByPage(int cid, int currentPage, int pageSize, String rname) {
//        String sql = "select  * from tab_route where cid = ?  limit ?  ,?";
        String sql = "select  * from tab_route where  1 = 1  ";
        StringBuilder sb = new StringBuilder(sql);
        List<Object> params = new ArrayList<>();
        if (cid != 0) {
            sb.append(" and cid = ?  ");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0) {
            sb.append(" and rname like  ?  ");
            params.add("%" + rname + "%");
        }
        sb.append(" limit ? , ? ");  //分页查询
        params.add((currentPage - 1) * pageSize);
        params.add(pageSize);
        return template.query(sb.toString(), new BeanPropertyRowMapper<>(Route.class), params.toArray());
    }

    @Override
    public Route findOne(int rid) {
        String sql = "select  * from tab_route where rid = ?";

        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class), rid);
    }
}
