package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {
    public Favorite findByRidAndUid(int rid, int uid);

    int countFavoriteByRid(int rid);

    void add(int rid, int uid);

    /**
     * 获取收藏的数量
     * @param rid
     * @return
     */

    void updateRouteCount(int rid);
}
