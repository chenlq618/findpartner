package com.findpartner.bean;

/**
 * Teammenber entity. @author MyEclipse Persistence Tools
 */

public class TeamMenber implements java.io.Serializable {

	// Fields

	private Integer teamId;
	private String teamName;
	private String menber;
	private Integer isCheck;

	// Constructors

	/** default constructor */
	public TeamMenber() {
	}

	/** minimal constructor */
	public TeamMenber(Integer teamId) {
		this.teamId = teamId;
	}

	/** full constructor */
	public TeamMenber(Integer teamId, String teamName, String menber,
			Integer isCheck) {
		this.teamId = teamId;
		this.teamName = teamName;
		this.menber = menber;
		this.isCheck = isCheck;
	}

	// Property accessors

	public Integer getTeamId() {
		return this.teamId;
	}

	public void setTeamId(Integer teamId) {
		this.teamId = teamId;
	}

	public String getTeamName() {
		return this.teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}

	public String getMenber() {
		return this.menber;
	}

	public void setMenber(String menber) {
		this.menber = menber;
	}

	public Integer getIsCheck() {
		return this.isCheck;
	}

	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}

}