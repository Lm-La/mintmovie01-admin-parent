package love.mint.movie.mvc.handler;

import love.mint.movie.entity.Account;
import love.mint.movie.service.api.AccountService;
import love.mint.movie.util.MintUtil;
import love.mint.movie.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author toi
 * @date 2020-04-15 19:54:58
 * @description
 */
@Controller
@RequestMapping("/test")
public class TestHandler {

    @Autowired
    private AccountService accountService;

    @RequestMapping("/ssm.html")
    public String testSSM(ModelMap modelMap, HttpServletRequest request) {
        List<Account> list = accountService.getAll();
        modelMap.addAttribute("account", list);
        System.out.println(MintUtil.judgeRequestType(request));
//        int a = 10 / 0;
//        String a = null;
//        System.out.println(a.length());

        return "target";
    }

    @RequestMapping("/arrayOne.html")
    public String testReceiveArrayOne(@RequestParam("array[]") List<Integer> list) {
        for (Integer integer : list) {
            System.out.println("number：" + integer);
        }
        return "target";
    }

    @RequestMapping("/arrayTwo.html")
    public String testReceiveArrayTwo(@RequestBody String body) {
        System.out.println(body);
//        for (Integer integer : array) {
//            System.out.println("number：" + integer);
//        }
        return "target";
    }

}
