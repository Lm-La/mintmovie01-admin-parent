package love.mint.movie.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import love.mint.movie.constant.MintConstant;
import love.mint.movie.entity.Account;
import love.mint.movie.entity.AccountExample;
import love.mint.movie.exception.LoginAcctAlreadyInUseException;
import love.mint.movie.exception.LoginFailedException;
import love.mint.movie.mapper.AccountMapper;
import love.mint.movie.service.api.AccountService;
import love.mint.movie.util.MintUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author toi
 * @date 2020-04-15 17:39:47
 * @description
 */

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    @Override
    public void saveAccount(Account account) {
        // 密码加密
        String userPswd = account.getPassword();
        userPswd = MintUtil.md5(userPswd);
        account.setPassword(userPswd);
        try {
            accountMapper.insert(account);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseException(MintConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
            }
        }
    }

    @Override
    public List<Account> getAll() {
        return accountMapper.selectByExample(new AccountExample());
    }

    @Override
    public Account getAdminByLoginAcct(String loginAcct, String userPswd) {

        // 根据 loginAcct 查询admin 对象
        //adminExample 对象
        AccountExample adminExample = new AccountExample();
        // 创建criteria对象
        AccountExample.Criteria criteria = adminExample.createCriteria();
        // 在 criteria对象中封装查询方法
        criteria.andUsernameEqualTo(loginAcct);
        // 调用accountMapper 的方法执行查询
        List<Account> list = accountMapper.selectByExample(adminExample);
        if (list == null || list.size() == 0) {
            throw new LoginFailedException(MintConstant.MESSAGE_LOGIN_FAILED);
        }
        if (list.size() > 1) {
            throw new RuntimeException(MintConstant.MESSAGE_SYSTEM_ERROR_LOGIN_NOT_UNIQU);
        }
        Account admin = list.get(0);
        if (admin == null) {
            throw new LoginFailedException(MintConstant.MESSAGE_LOGIN_FAILED);
        }
        String userPswdDb = admin.getPassword();
        String userPswdForm = MintUtil.md5(userPswd);
        if (!Objects.equals(userPswdForm, userPswdDb)) {
            throw new LoginFailedException(MintConstant.MESSAGE_LOGIN_FAILED);
        }

        return admin;
    }

    @Override
    public PageInfo<Account> getPageInfo(String keyword, Integer pageNum, Integer pageSize) {

        // 先调用PageHelper的静态方法开启分页功能
        // 这里充分体现了PageHelper的“非侵入式”设计；原本要做的查询不必有任何修改
        PageHelper.startPage(pageNum, pageSize);

        // 执行查询
        List<Account> list = accountMapper.selectAccountByKeyword(keyword);

        // 封装到PageInfo对象中
        return new PageInfo<>(list);
    }

    @Override
    public void remove(Integer accountId) {
        accountMapper.deleteByPrimaryKey(accountId);
    }

    @Override
    public Account getAccountById(Integer accountId) {
        return accountMapper.selectByPrimaryKey(accountId);
    }

    @Override
    public void updateAccount(Account account) {
        // Selective 表示对Account 选择性的更新 对于值为null字段 的不更新
        try {
            accountMapper.updateByPrimaryKeySelective(account);
        } catch (Exception e) {
            if (e instanceof DuplicateKeyException) {
                throw new LoginAcctAlreadyInUseException(MintConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE_UPDATE);
            }
        }
    }
}
