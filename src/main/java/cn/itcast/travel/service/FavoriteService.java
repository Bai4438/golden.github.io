package cn.itcast.travel.service;

public interface FavoriteService {
    //判断是否添加了favorite
    public  boolean isFavorite (int rid,int uid);

    void add(int rid, int uid);
}
