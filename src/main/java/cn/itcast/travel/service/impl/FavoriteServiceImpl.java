package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {

    private FavoriteDao favoriteDao = new FavoriteDaoImpl();
    /**
     * 判断是否收藏了Favorite
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public boolean isFavorite(int rid, int uid) {
        Favorite favorite = favoriteDao.findByRidAndUid(rid, uid);
        if (favorite==null){
            //表示没有收藏过
            return false;
        }else {
            //表示已经收藏过了
            return true;
        }
    }

    /**
     * 添加收藏 ， 不仅要在tab_favorite 中添加数据， 还要更新tab_route 中的数据
     * @param rid
     * @param uid
     */
    @Override
    public void add(int rid, int uid) {
        favoriteDao.add(rid,uid);
        favoriteDao.updateRouteCount(rid);
    }
}
