package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImageDao;
import cn.itcast.travel.dao.SellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImageDaoImpl;
import cn.itcast.travel.dao.impl.SellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao routeDao = new RouteDaoImpl();
    private RouteImageDao routeImageDao = new RouteImageDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        PageBean <Route> pb = new PageBean<>();
        int totalCount = routeDao.findTotalCount(cid,rname);
        pb.setTotalCount(totalCount);
        int totalPage = totalCount%pageSize==0?totalCount/pageSize:totalCount/pageSize+1;
        pb.setTotalPage(totalPage);
        pb.setCurrentPage(currentPage);
        pb.setPageSize(pageSize);
        List<Route> byPage = routeDao.findByPage(cid, currentPage, pageSize,rname);
        pb.setList(byPage);
        return pb;
    }

    /**
     * 根据 rid查询 线路详情信息
     * @param rid
     * @return
     */
    @Override
    public Route findOne(int rid) {
        //1.根据id 去route 表中查询route对象
        Route route = routeDao.findOne(rid);
        //2.根据route的id 查询图片的集合信息
        List<RouteImg> image = routeImageDao.findByRid(rid);
        route.setRouteImgList(image);
        //3.根据route的sid 查询商家的信息
        Seller seller = sellerDao.findById(route.getSid());
        route.setSeller(seller);
        //4.查询收藏次数
        int count =  favoriteDao.countFavoriteByRid(rid);
        route.setCount(count);
        return route;
    }
}
