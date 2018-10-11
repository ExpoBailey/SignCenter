package com.explorer.bailey.sc.common;

import com.explorer.bailey.sc.entity.User;
import com.explorer.bailey.sc.utils.SessionUtils;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;

/**
 * @author zhuwj
 * @description
 * @since 2018/10/9
 */
@Configuration
public class UserIdAuditorAware implements AuditorAware<Long> {
    @Override
    public Long getCurrentAuditor() {
        User user = SessionUtils.getUser();
        if (user == null) {
            return null;
        } else {
            return user.getId();
        }
    }
}
