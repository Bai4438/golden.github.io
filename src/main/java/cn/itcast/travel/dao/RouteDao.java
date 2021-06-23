package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

/**
 * 线路
 */
public interface RouteDao {

    /**
     * 根据cid查询总记录数
     *
     * @return
     */
    public int findTotalCount(int cid, String rname);
    public int findTotalCount(int cid1,int cid2);
    public int findTotalCount(int cid);
    public int findTotalCount();


    /**
     * 根据cid， start  ， pageSize 查询当前页码需要的数据集合
     *
     * @return
     */
    public List<Route> findByPage(int cid, int currentPage, int pageSize, String rname);

    /**
     *
     */
    public Route findOne(int rid);
}
