package com.yupi.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * 用户登录请求体
 *
 * @author LHT
 */
@Data
public class UserLoginRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 739627104975423897L;
    private String userAccount;
    private String userPassword;

}
