package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;

import java.util.List;

public interface UserDao {
    /**
     * 根据用户名查询用户信息
     * @param username
     * @return
     */
    public User findByUsername(String username);

    /**
     * 将用户的信息保存数据库中
     * @param user
     */
    public void save(User user );

    User findByCode(String code);

    void updateStatus(User user);

    User findUsernameAndPassword(String username, String password);

    List<Route> myfavorite(int uid);
}
