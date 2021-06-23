package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface CompetitiveDao {
    List<Route> queryPopularity();

    List<Route> queryNewest();

    List<Route> queryTheme();

    List<Route> queryDomestic();

    List<Route> queryOverseas();
}
