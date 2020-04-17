package love.mint.movie.util;

import love.mint.movie.constant.MintConstant;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author toi
 * @date 2020-04-16 13:10:08
 * @description Mint 通用工具类
 */
public class MintUtil {

    /**
     * 对明文字符串进行md5加密
     *
     * @param souce 要加密的明文字符串
     * @return 加密结果
     */
    public static String md5(String souce) {

        // 1.判断souce是否有效
        if (souce == null || souce.length() == 0) {

            // 2.如果不是有效的字符串抛出异常
            throw new RuntimeException(MintConstant.MESSAGE_STRING_INVALIDATE);
        }

        // 3.获取MessageDigest对象
        String algorithm = "md5";
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);

            // 4.获取明文字符串对应的字节数组
            byte[] input = souce.getBytes();

            // 5.执行加密
            byte[] output = messageDigest.digest(input);

            // 6.创建BigInteger
            int signum = 1;
            BigInteger bigInteger = new BigInteger(signum, output);

            // 7.按照16进制bigInteger的值转换为字符串
            int radix = 16;
            return bigInteger.toString(radix).toUpperCase();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 判断当前请求是否为Ajax
     *
     * @param request 请求对象
     * @return true:当前请求是Ajax
     * false:当前请求不是Ajax
     */
    public static boolean judgeRequestType(HttpServletRequest request) {
        // 1.获取请求消息头
        String acceptHeader = request.getHeader("Accept");
        String xRequestHeader = request.getHeader("X-Requested-With");
        return (
                (acceptHeader != null && acceptHeader.contains("application/json"))
                        ||
                        (xRequestHeader != null && xRequestHeader.equals("XMLHttpRequest"))
        );
    }

}
