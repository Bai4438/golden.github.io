package cn.itcast.travel.service;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface CompetitiveService {
    List<Route> queryPopularity();
    List<Route> queryNewest();
    List<Route> queryTheme();

    List<Route> queryDomestic();

    List<Route> queryOverseas();
}
