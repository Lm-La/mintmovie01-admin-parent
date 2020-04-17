package love.mint.movie.mvc.interceptor;

import love.mint.movie.constant.MintConstant;
import love.mint.movie.entity.Account;
import love.mint.movie.exception.AccessForbiddenException;
import love.mint.movie.exception.LoginFailedException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author toi
 * @date 2020-04-16 17:05:37
 * @description
 */
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        // 通过request对象获取Session对象
        HttpSession session = request.getSession();

        // 尝试从Session域中获取admin 对象
        Account admin = (Account) session.getAttribute(MintConstant.ATTR_NAME_LOGIN_ADMIN);

        // 判断admin对象是否为空
        if (admin == null) {
            // 抛出异常
            throw new LoginFailedException(MintConstant.MESSAGE_ACCESS_FORBIDEN);
        }

        // 如果admin 不为空 放行 return true
        return true;
    }
}
