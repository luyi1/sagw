package com.ge.digital.spo.security.service;

import com.ge.digital.spo.chain.infrastructure.user.vo.UserByRoleVo;

public interface AuthValidatorService {

	Boolean testbUIsSameLoginId(UserByRoleVo vo);

}
