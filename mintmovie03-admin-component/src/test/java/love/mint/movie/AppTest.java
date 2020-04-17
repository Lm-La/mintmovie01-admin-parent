package love.mint.movie;

import love.mint.movie.entity.Account;
import love.mint.movie.mapper.AccountMapper;
import love.mint.movie.service.api.AccountService;
import love.mint.movie.util.MintUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Unit test for simple App.
 */

@Service
public class AppTest {

    @Test
    public void shouldAnswerWithTrue() {
        String source = "123456";
        String encoded = MintUtil.md5(source);
        System.out.println(encoded);
    }

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private AccountService accountService;

    @Test
    public void testAddAccount() {
        Account account = new Account();
        account.setId(12);
        account.setIcon("ssss");
        account.setAdmin(0);
        account.setNickname("宝宝");
        account.setUsername("18814055357");
        account.setSign("我是宝宝");
        accountMapper.insertSelective(account);
    }
}
