package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;

public interface SellerDao {
    /**
     * 根据sid查询商家的信息
     *
     */

    public Seller findById(int sid);
}
