package love.mint.movie.service.api;

import com.github.pagehelper.PageInfo;
import love.mint.movie.entity.Role;

/**
 * @author toi
 * @date 2020-04-17 12:25:35
 * @description
 */
public interface RoleService {

    PageInfo<Role> getPageInfo(Integer pageNum, Integer pageSize, String keyword);
}
