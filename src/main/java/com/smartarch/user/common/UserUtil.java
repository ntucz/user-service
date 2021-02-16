package com.smartarch.user.common;

import java.text.MessageFormat;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

public class UserUtil {
	
	public static String getStrFormat(String format, String... strs) {
		return MessageFormat.format(format, strs);
	}
	
	public static Boolean isCollectionEmpty(Collection collection) {
		if(collection==null || collection.isEmpty()) {
			return true;
		}
		return false;
	}
	
    /**
     * 获取request
     *
     * @return
     */
    public static HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes();
        return requestAttributes == null ? null : requestAttributes.getRequest();
    }

    /**
     * 生成Token
     * @param userId
     * @param userName
     * @return
     * @throws Exception
     */
	/*
	 * public static String createToken(String userId, String userName, String
	 * passwords) throws Exception { Calendar nowTime = Calendar.getInstance();
	 * nowTime.add(Calendar.SECOND, IConst.TOKEN_EXPIRE); Date expireDate =
	 * nowTime.getTime();
	 * 
	 * Map<String, Object> map = new HashMap<>(); map.put("alg", "HS256");
	 * map.put("typ", "JWT");
	 * 
	 * String token = JWT.create() .withHeader(map)//头 .withClaim("userId", userId)
	 * .withClaim("userName", userName) .withIssuedAt(new Date())//签名时间
	 * .withExpiresAt(expireDate)//过期时间 .sign(Algorithm.HMAC256(passwords));//签名
	 * return token; }
	 */

}
