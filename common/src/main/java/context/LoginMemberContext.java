package context;

import com.heyu.train.common.resp.MemberDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;

/**
 * 功能:
 * 作者:何宇
 * 日期：2024/3/31 19:53
 */
@Slf4j
@Component
public class LoginMemberContext {
    private static   final ThreadLocal<MemberDTO> threadLocal = new ThreadLocal<>();
    public static MemberDTO getMember(){
return threadLocal.get();
    }

    public static void setMember(MemberDTO member){
        threadLocal.set(member);
    }
    public  static Long getId(){
        try {
            Long id = getMember().getId();
            return id;
        } catch (Exception e) {
            log.info("获取用户id异常",e);
            throw  e;
        }

    }
}
