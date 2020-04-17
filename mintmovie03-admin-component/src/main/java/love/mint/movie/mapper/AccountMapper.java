package love.mint.movie.mapper;

import java.util.List;

import love.mint.movie.entity.Account;
import love.mint.movie.entity.AccountExample;
import org.apache.ibatis.annotations.Param;

public interface AccountMapper {

    long countByExample(AccountExample example);

    int deleteByExample(AccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Account record);

    int insertSelective(Account record);

    List<Account> selectByExample(AccountExample example);

    Account selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByExample(@Param("record") Account record, @Param("example") AccountExample example);

    int updateByPrimaryKeySelective(Account record);

    int updateByPrimaryKey(Account record);

    // 自定义的方法汇总
    // 用户模糊查询
    List<Account> selectAccountByKeyword(String keyword);
}