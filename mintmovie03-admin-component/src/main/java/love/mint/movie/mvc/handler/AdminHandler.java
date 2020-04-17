package love.mint.movie.mvc.handler;

import com.github.pagehelper.PageInfo;
import love.mint.movie.constant.MintConstant;
import love.mint.movie.entity.Account;
import love.mint.movie.exception.LoginAcctAlreadyInUseException;
import love.mint.movie.service.api.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author toi
 * @date 2020-04-16 15:56:48
 * @description
 */
@Controller
@RequestMapping("/admin")
public class AdminHandler {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/update/page.html")
    public String updateAccount(Account account) {
        accountService.updateAccount(account);
        return "admin-edit";
    }

    @RequestMapping("/to/edit/page.html")
    public String toEditPage(@RequestParam("accountId") Integer accountId, ModelMap modelMap) {
        Account account = accountService.getAccountById(accountId);
        modelMap.addAttribute(MintConstant.ATTR_NAME_ACCOUNT, account);
        return "admin-edit";
    }

    @RequestMapping("/save.html")
    public String saveAccount(Account account) {
        accountService.saveAccount(account);
        return "redirect:/admin/to/add/page.html";
    }

    /**
     * 删除 用户
     *
     * @param accountId 被删用户的id
     * @param pageNum   当前页面的页数（删除后再重定向回去）
     * @param keyword   当前存在的关键字
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping("/remove/{accountId}/{pageNum}/{keyword}.html")
    public String remove(
            @PathVariable("accountId") Integer accountId,
            @PathVariable("pageNum") Integer pageNum,
            @PathVariable("keyword") String keyword) throws UnsupportedEncodingException {

        keyword = URLEncoder.encode(keyword, MintConstant.ATTR_NAME_ENCODING);
        //执行删除
        accountService.remove(accountId);
        // 方案1：会无法显示分页的数据
        //  return "admin-page";
        // 方案2：转发到admin-page.jsp 一旦刷新页面会重复执行删除，浪费性能
        // return "forward:admin-page";
        // 方案3：重定向到admin-page.jsp,同时为了保持原本所在的页面和查询关键词再附加pageNum和keyword
        return "redirect:/admin/get/page.html?pageNum=" + pageNum + "&keyword=" + keyword;
    }

    /**
     * 用户 显示获取、分页
     *
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param modelMap
     * @return
     */
    @RequestMapping("/get/page.html")
    public String getPageInfo(
            // 使用@RequestParam注解的defaultValue属性，指定默认值，在请求中没有携带对应参数
            // 时使用默认值
            @RequestParam(value = "keyword", defaultValue = "") String keyword,

            // pageNum 默认使用 1
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,

            // pageNum 默认使用 5
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            ModelMap modelMap) {

        modelMap.addAttribute(MintConstant.ATTR_NAME_SEARCH_KEYWORD, keyword);
        // 调用service方法获取PageInfo
        PageInfo<Account> pageInfo = accountService.getPageInfo(keyword, pageNum, pageSize);
        // 将pageInfo对象存入模型
        modelMap.addAttribute(MintConstant.ATTR_NAME_PAGE_INFO, pageInfo);
        return "admin-page";
    }

    /**
     * 退出
     *
     * @param session
     * @return
     */
    @RequestMapping("/do/logout.html")
    public String doLogout(HttpSession session) {
        // 强制session失效
        session.invalidate();
        //重定向到登录界面
        return "redirect:/admin/to/login/page.html";
    }

    /**
     * 登录
     *
     * @param loginAcct 表单账号
     * @param userPswd  表单密码
     * @param session   登录成功后的admin对象保存到session 域
     * @return
     */
    @RequestMapping("/do/login.html")
    public String doLogin(@RequestParam String loginAcct,
                          @RequestParam String userPswd,
                          HttpSession session) {

        // 调用Service 方法执行检查
        Account admin = accountService.getAdminByLoginAcct(loginAcct, userPswd);

        // 将登录成功返回的admin对象存入到Session域
        session.setAttribute(MintConstant.ATTR_NAME_LOGIN_ADMIN, admin);
        // 为了避免到后台主页面再刷新浏览器导致重复提交登录表单，重定向到目标页面，
        // 所以用下面方法（需要在springmvc 配置view-controller）
//        return "admin-main";
        return "redirect:/admin/to/main/page.html";
    }

    @RequestMapping("/do/add/page.html")
    public String addAccount() {

//        for (int i = 0; i < 237; i++) {
//            Account account = new Account();
//            account.setUsername("18814055357");
//            account.setNickname("宝宝" + i);
//            account.setPassword("123456");
//            account.setSign("我叫宝宝！");
//            accountService.saveAccount(account);
//        }

        return "target";
    }

}
