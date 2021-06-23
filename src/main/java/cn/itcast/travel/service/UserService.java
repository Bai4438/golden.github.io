package cn.itcast.travel.service;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;

import java.util.List;

public interface UserService {
    boolean register(User user);

    boolean active(String code);


    User login(String username, String password);


    List<Route> myfavorite(int uid);
}
