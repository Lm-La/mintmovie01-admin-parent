package love.mint.movie.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import love.mint.movie.entity.Role;
import love.mint.movie.mapper.RoleMapper;
import love.mint.movie.service.api.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author toi
 * @date 2020-04-17 12:25:51
 * @description
 */
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword) {

        // 开启分页功能
        PageHelper.startPage(pageNum, pageSize);

        // 执行查询
        List<Role> roleList = roleMapper.selectRoleByKeyword(keyword);

        // 封装为PageInfo对象返回
        return new PageInfo<>(roleList);
    }
}
