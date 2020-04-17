package love.mint.movie.mvc.config;

import com.google.gson.Gson;
import love.mint.movie.constant.MintConstant;
import love.mint.movie.exception.LoginAcctAlreadyInUseException;
import love.mint.movie.exception.LoginFailedException;
import love.mint.movie.util.MintUtil;
import love.mint.movie.util.ResultEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author toi
 * @date 2020-04-16 13:22:16
 * @description 注解的异常处理类
 */
//@ControllerAdvice 表示当前类是一个基于注解的异常处理类
@ControllerAdvice
public class MintExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex) {

        String viewName = null;
        viewName = "system-error";

        // 登录失败的异常
        if (ex instanceof LoginFailedException) {
            viewName = "admin-login";
        } else if (ex instanceof LoginAcctAlreadyInUseException) {
            if (ex.getMessage().equals(MintConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE)) {
                viewName = "admin-add";
            } else {
                viewName = "system-error";
            }
        }
        return commonResolve(viewName, ex, request, response);
    }

    private ModelAndView commonResolve(String viewName, Exception exception, HttpServletRequest request, HttpServletResponse response) {
        //1.判断当前请求类型
        boolean judgeResult = MintUtil.judgeRequestType(request);
        //2.如果是Ajax请求
        if (judgeResult) {
            try {
                //3.创建ResultEntity 对象
                ResultEntity<Object> resultEntity = ResultEntity.failed(exception.getMessage());
                //4.创建Gson 对象
                Gson gson = new Gson();
                //5.将ResultEntity对象转换为JSON字符串
                String json = gson.toJson(resultEntity);
                //6.将JSON字符串作为响应体返回给浏览器
                response.getWriter().write(json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //7.由于上面已经通过原生的response对象返回了响应，所以不提供ModelAndView对象
            return null;
        }
        //8.如果不是Ajax 请求，则新建ModelAndView对象
        ModelAndView modelAndView = new ModelAndView();
        //9.将Exception对象存入模型
        modelAndView.addObject(MintConstant.ATTR_NAME_EXCEPTION, exception);
        //10.设置对应的试图名称
        modelAndView.setViewName(viewName);
        //11.返回modelAndView 对象
        return modelAndView;
    }
}
