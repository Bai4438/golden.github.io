package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class UserDaoImpl implements UserDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     */
    @Override
    public User findByUsername(String username) {
        String sql = " select  * from  tab_user where username = ?";
        // 因为只能查询到一个用户 ， 所以 用queryForObject
        User user = null;
        try {
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), username);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }


    /**
     * 将用户的信息保存数据库中
     *
     * @param user
     */
    @Override
    public void save(User user) {
        String sql = "insert  into tab_user (username,password,name,birthday,sex,telephone,email,status,code)" +
                " values (?,?,?,?,?,?,?,?,?)";
        template.update(sql,user.getUsername(),user.getPassword(),user.getName(),user.getBirthday(),
                user.getSex(),user.getTelephone(),user.getEmail(),user.getStatus(),user.getCode());
    }

    /**
     * 根据激活码查询用户状态
     * @param code
     * @return
     */

    @Override
    public User findByCode(String code) {
        String sql = "select * from tab_user where  code = ?";
        User user = null;
        try {
            user= template.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }
//2.调用dao层来修改激活状态
    /**
     * 更改用户状态 将N 改为 Y
     * @param user
     */
    @Override
    public void updateStatus(User user) {
        String sql = " update tab_user set status = ? where code = ?";
        template.update(sql,"Y",user.getCode());
    }

    @Override
    public User findUsernameAndPassword(String username, String password) {
        String sql = "select * from tab_user where  username = ? and password = ?";
        User user = null;
        try {
            user= template.queryForObject(sql,new BeanPropertyRowMapper<>(User.class),username,password);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<Route> myfavorite(int uid) {

        String sql  ="SELECT * FROM  tab_route WHERE tab_route.rid in (SELECT rid FROM tab_favorite WHERE uid = ?);";
        List<Route> myfavorite = null;
        try {
            myfavorite= template.query(sql, new BeanPropertyRowMapper<>(Route.class), uid);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return myfavorite;
    }
}
