package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CompetitiveDao;
import cn.itcast.travel.dao.impl.CompetitiveDaoImpl;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.CompetitiveService;

import java.util.List;

public class CompetitiveServiceImpl implements CompetitiveService {
    //1.这是人气旅游，应该查询数据库中，哪些旅游线路收藏的最多，哪些就最受欢迎
    //2.只需要查询一个数据库就可以了，tab_route 这张表

    private CompetitiveDao competitiveDao = new CompetitiveDaoImpl();
    @Override
    public List<Route> queryPopularity() {
        return competitiveDao.queryPopularity();
    }

    @Override
    public List<Route> queryNewest() {
        return competitiveDao.queryNewest();
    }

    @Override
    public List<Route> queryTheme() {
        return competitiveDao.queryTheme();
    }

    @Override
    public List<Route> queryDomestic() {
        return competitiveDao.queryDomestic();
    }

    @Override
    public List<Route> queryOverseas() {
        return competitiveDao.queryOverseas();
    }
}
