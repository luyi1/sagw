package com.ge.digital.spo.chain.infrastructure.user.vo;

import com.ge.digital.spo.chain.infrastructure.user.model.User;

public class UserVo extends User{
	private String newPassword;

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	

}
