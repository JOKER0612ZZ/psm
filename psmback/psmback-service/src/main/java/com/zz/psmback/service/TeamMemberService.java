package com.zz.psmback.service;

import com.zz.psmback.common.result.CommonResult;

public interface TeamMemberService {
    CommonResult<?> removeMember(int teamId,int memberId);
}
