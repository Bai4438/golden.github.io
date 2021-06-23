package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Favorite findByRidAndUid(int rid, int uid) {
        Favorite favorite = null;
        try {
            String sql = "select * from tab_favorite where rid = ? and uid = ?";
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return favorite;
    }

    @Override
    public int countFavoriteByRid(int rid) {
        String sql = "select count  from tab_route where rid = ? ";
        return template.queryForObject(sql,Integer.class,rid);
    }

    /**
     * 添加收藏
     * @param rid
     * @param uid
     */
    @Override
    public void add(int rid, int uid) {
        String sql = "insert  into tab_favorite values (?,?,?)";
        template.update(sql,rid,new Date(),uid);
    }


    /**
     * 更新 tab_route 表中的数量
     * @param rid
     */
    @Override
    public void updateRouteCount(int rid) {
        //1.首先查询一共有多少条记录
        String totalCount = "select count from  tab_route where rid = ? ";
        int num =  template.queryForObject(totalCount,Integer.class,rid);
        String updateCount = "update tab_route set count = ? where rid = ?";
        template.update(updateCount,num+1 ,rid);
    }
}
