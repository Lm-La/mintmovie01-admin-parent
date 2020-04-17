package love.mint.movie.service.api;

import com.github.pagehelper.PageInfo;
import love.mint.movie.entity.Account;

import java.util.List;

/**
 * @author toi
 * @date 2020-04-15 17:39:35
 * @description
 */
public interface AccountService {

    void saveAccount(Account account);

    List<Account> getAll();

    Account getAdminByLoginAcct(String loginAcct, String userPswd);

    // 用户模糊查询
    PageInfo<Account> getPageInfo(String keyword, Integer pageNum, Integer pageSize);

    void remove(Integer accountId);

    Account getAccountById(Integer accountId);

    void updateAccount(Account account);
}
