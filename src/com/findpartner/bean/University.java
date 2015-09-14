package com.findpartner.bean;

/**
 * University entity. @author MyEclipse Persistence Tools
 */

public class University implements java.io.Serializable {

	// Fields

	private Integer id;
	private String universityId;
	private String university;
	private String schoolId;
	private String school;
	private String campus;

	// Constructors

	/** default constructor */
	public University() {
	}

	/** full constructor */
	public University(String universityId, String university, String schoolId,
			String school, String campus) {
		this.universityId = universityId;
		this.university = university;
		this.schoolId = schoolId;
		this.school = school;
		this.campus = campus;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUniversityId() {
		return this.universityId;
	}

	public void setUniversityId(String universityId) {
		this.universityId = universityId;
	}

	public String getUniversity() {
		return this.university;
	}

	public void setUniversity(String university) {
		this.university = university;
	}

	public String getSchoolId() {
		return this.schoolId;
	}

	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}

	public String getSchool() {
		return this.school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getCampus() {
		return this.campus;
	}

	public void setCampus(String campus) {
		this.campus = campus;
	}

}