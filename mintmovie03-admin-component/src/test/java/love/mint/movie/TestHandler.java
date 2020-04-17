package love.mint.movie;

import love.mint.movie.entity.Account;
import love.mint.movie.service.api.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author toi
 * @date 2020-04-15 19:26:11
 * @description
 */
@Controller
@RequestMapping("/test")
public class TestHandler {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/ssm")
    public String testSSM(ModelMap modelMap) {
        System.out.println("--------------------------");
        List<Account> list = accountService.getAll();
        System.out.println("到这了" + list.size());
        modelMap.addAttribute("account", list);
        return "target";
    }

}
