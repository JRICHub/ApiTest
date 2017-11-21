package com.fkapi.datebase.domain;

import java.util.Date;

public class p2p_cooperation_employee_record {
    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p2p_cooperation_employee_record.id
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p2p_cooperation_employee_record.company_id
     *
     * @mbggenerated
     */
    private Long companyId;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p2p_cooperation_employee_record.company_name
     *
     * @mbggenerated
     */
    private String companyName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p2p_cooperation_employee_record.cust_name
     *
     * @mbggenerated
     */
    private String custName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p2p_cooperation_employee_record.cert_no
     *
     * @mbggenerated
     */
    private String certNo;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p2p_cooperation_employee_record.tel
     *
     * @mbggenerated
     */
    private String tel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p2p_cooperation_employee_record.family_addr
     *
     * @mbggenerated
     */
    private String familyAddr;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p2p_cooperation_employee_record.emergency_contacts_name
     *
     * @mbggenerated
     */
    private String emergencyContactsName;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p2p_cooperation_employee_record.emergency_contacts_relation
     *
     * @mbggenerated
     */
    private String emergencyContactsRelation;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p2p_cooperation_employee_record.emergency_contacts_mobile
     *
     * @mbggenerated
     */
    private String emergencyContactsMobile;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p2p_cooperation_employee_record.company_tel
     *
     * @mbggenerated
     */
    private String companyTel;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p2p_cooperation_employee_record.work_province
     *
     * @mbggenerated
     */
    private String workProvince;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p2p_cooperation_employee_record.work_city
     *
     * @mbggenerated
     */
    private String workCity;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p2p_cooperation_employee_record.work_addr
     *
     * @mbggenerated
     */
    private String workAddr;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p2p_cooperation_employee_record.entry_date
     *
     * @mbggenerated
     */
    private Date entryDate;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p2p_cooperation_employee_record.labor_relations_type
     *
     * @mbggenerated
     */
    private Integer laborRelationsType;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p2p_cooperation_employee_record.work_status
     *
     * @mbggenerated
     */
    private Integer workStatus;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p2p_cooperation_employee_record.monthly_salary_range
     *
     * @mbggenerated
     */
    private String monthlySalaryRange;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p2p_cooperation_employee_record.monthly_salary_max
     *
     * @mbggenerated
     */
    private Integer monthlySalaryMax;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p2p_cooperation_employee_record.monthly_salary_min
     *
     * @mbggenerated
     */
    private Integer monthlySalaryMin;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p2p_cooperation_employee_record.create_time
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column p2p_cooperation_employee_record.update_time
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p2p_cooperation_employee_record.id
     *
     * @return the value of p2p_cooperation_employee_record.id
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p2p_cooperation_employee_record.id
     *
     * @param id the value for p2p_cooperation_employee_record.id
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p2p_cooperation_employee_record.company_id
     *
     * @return the value of p2p_cooperation_employee_record.company_id
     *
     * @mbggenerated
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p2p_cooperation_employee_record.company_id
     *
     * @param companyId the value for p2p_cooperation_employee_record.company_id
     *
     * @mbggenerated
     */
    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p2p_cooperation_employee_record.company_name
     *
     * @return the value of p2p_cooperation_employee_record.company_name
     *
     * @mbggenerated
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p2p_cooperation_employee_record.company_name
     *
     * @param companyName the value for p2p_cooperation_employee_record.company_name
     *
     * @mbggenerated
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p2p_cooperation_employee_record.cust_name
     *
     * @return the value of p2p_cooperation_employee_record.cust_name
     *
     * @mbggenerated
     */
    public String getCustName() {
        return custName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p2p_cooperation_employee_record.cust_name
     *
     * @param custName the value for p2p_cooperation_employee_record.cust_name
     *
     * @mbggenerated
     */
    public void setCustName(String custName) {
        this.custName = custName == null ? null : custName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p2p_cooperation_employee_record.cert_no
     *
     * @return the value of p2p_cooperation_employee_record.cert_no
     *
     * @mbggenerated
     */
    public String getCertNo() {
        return certNo;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p2p_cooperation_employee_record.cert_no
     *
     * @param certNo the value for p2p_cooperation_employee_record.cert_no
     *
     * @mbggenerated
     */
    public void setCertNo(String certNo) {
        this.certNo = certNo == null ? null : certNo.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p2p_cooperation_employee_record.tel
     *
     * @return the value of p2p_cooperation_employee_record.tel
     *
     * @mbggenerated
     */
    public String getTel() {
        return tel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p2p_cooperation_employee_record.tel
     *
     * @param tel the value for p2p_cooperation_employee_record.tel
     *
     * @mbggenerated
     */
    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p2p_cooperation_employee_record.family_addr
     *
     * @return the value of p2p_cooperation_employee_record.family_addr
     *
     * @mbggenerated
     */
    public String getFamilyAddr() {
        return familyAddr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p2p_cooperation_employee_record.family_addr
     *
     * @param familyAddr the value for p2p_cooperation_employee_record.family_addr
     *
     * @mbggenerated
     */
    public void setFamilyAddr(String familyAddr) {
        this.familyAddr = familyAddr == null ? null : familyAddr.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p2p_cooperation_employee_record.emergency_contacts_name
     *
     * @return the value of p2p_cooperation_employee_record.emergency_contacts_name
     *
     * @mbggenerated
     */
    public String getEmergencyContactsName() {
        return emergencyContactsName;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p2p_cooperation_employee_record.emergency_contacts_name
     *
     * @param emergencyContactsName the value for p2p_cooperation_employee_record.emergency_contacts_name
     *
     * @mbggenerated
     */
    public void setEmergencyContactsName(String emergencyContactsName) {
        this.emergencyContactsName = emergencyContactsName == null ? null : emergencyContactsName.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p2p_cooperation_employee_record.emergency_contacts_relation
     *
     * @return the value of p2p_cooperation_employee_record.emergency_contacts_relation
     *
     * @mbggenerated
     */
    public String getEmergencyContactsRelation() {
        return emergencyContactsRelation;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p2p_cooperation_employee_record.emergency_contacts_relation
     *
     * @param emergencyContactsRelation the value for p2p_cooperation_employee_record.emergency_contacts_relation
     *
     * @mbggenerated
     */
    public void setEmergencyContactsRelation(String emergencyContactsRelation) {
        this.emergencyContactsRelation = emergencyContactsRelation == null ? null : emergencyContactsRelation.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p2p_cooperation_employee_record.emergency_contacts_mobile
     *
     * @return the value of p2p_cooperation_employee_record.emergency_contacts_mobile
     *
     * @mbggenerated
     */
    public String getEmergencyContactsMobile() {
        return emergencyContactsMobile;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p2p_cooperation_employee_record.emergency_contacts_mobile
     *
     * @param emergencyContactsMobile the value for p2p_cooperation_employee_record.emergency_contacts_mobile
     *
     * @mbggenerated
     */
    public void setEmergencyContactsMobile(String emergencyContactsMobile) {
        this.emergencyContactsMobile = emergencyContactsMobile == null ? null : emergencyContactsMobile.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p2p_cooperation_employee_record.company_tel
     *
     * @return the value of p2p_cooperation_employee_record.company_tel
     *
     * @mbggenerated
     */
    public String getCompanyTel() {
        return companyTel;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p2p_cooperation_employee_record.company_tel
     *
     * @param companyTel the value for p2p_cooperation_employee_record.company_tel
     *
     * @mbggenerated
     */
    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel == null ? null : companyTel.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p2p_cooperation_employee_record.work_province
     *
     * @return the value of p2p_cooperation_employee_record.work_province
     *
     * @mbggenerated
     */
    public String getWorkProvince() {
        return workProvince;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p2p_cooperation_employee_record.work_province
     *
     * @param workProvince the value for p2p_cooperation_employee_record.work_province
     *
     * @mbggenerated
     */
    public void setWorkProvince(String workProvince) {
        this.workProvince = workProvince == null ? null : workProvince.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p2p_cooperation_employee_record.work_city
     *
     * @return the value of p2p_cooperation_employee_record.work_city
     *
     * @mbggenerated
     */
    public String getWorkCity() {
        return workCity;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p2p_cooperation_employee_record.work_city
     *
     * @param workCity the value for p2p_cooperation_employee_record.work_city
     *
     * @mbggenerated
     */
    public void setWorkCity(String workCity) {
        this.workCity = workCity == null ? null : workCity.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p2p_cooperation_employee_record.work_addr
     *
     * @return the value of p2p_cooperation_employee_record.work_addr
     *
     * @mbggenerated
     */
    public String getWorkAddr() {
        return workAddr;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p2p_cooperation_employee_record.work_addr
     *
     * @param workAddr the value for p2p_cooperation_employee_record.work_addr
     *
     * @mbggenerated
     */
    public void setWorkAddr(String workAddr) {
        this.workAddr = workAddr == null ? null : workAddr.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p2p_cooperation_employee_record.entry_date
     *
     * @return the value of p2p_cooperation_employee_record.entry_date
     *
     * @mbggenerated
     */
    public Date getEntryDate() {
        return entryDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p2p_cooperation_employee_record.entry_date
     *
     * @param entryDate the value for p2p_cooperation_employee_record.entry_date
     *
     * @mbggenerated
     */
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p2p_cooperation_employee_record.labor_relations_type
     *
     * @return the value of p2p_cooperation_employee_record.labor_relations_type
     *
     * @mbggenerated
     */
    public Integer getLaborRelationsType() {
        return laborRelationsType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p2p_cooperation_employee_record.labor_relations_type
     *
     * @param laborRelationsType the value for p2p_cooperation_employee_record.labor_relations_type
     *
     * @mbggenerated
     */
    public void setLaborRelationsType(Integer laborRelationsType) {
        this.laborRelationsType = laborRelationsType;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p2p_cooperation_employee_record.work_status
     *
     * @return the value of p2p_cooperation_employee_record.work_status
     *
     * @mbggenerated
     */
    public Integer getWorkStatus() {
        return workStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p2p_cooperation_employee_record.work_status
     *
     * @param workStatus the value for p2p_cooperation_employee_record.work_status
     *
     * @mbggenerated
     */
    public void setWorkStatus(Integer workStatus) {
        this.workStatus = workStatus;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p2p_cooperation_employee_record.monthly_salary_range
     *
     * @return the value of p2p_cooperation_employee_record.monthly_salary_range
     *
     * @mbggenerated
     */
    public String getMonthlySalaryRange() {
        return monthlySalaryRange;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p2p_cooperation_employee_record.monthly_salary_range
     *
     * @param monthlySalaryRange the value for p2p_cooperation_employee_record.monthly_salary_range
     *
     * @mbggenerated
     */
    public void setMonthlySalaryRange(String monthlySalaryRange) {
        this.monthlySalaryRange = monthlySalaryRange == null ? null : monthlySalaryRange.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p2p_cooperation_employee_record.monthly_salary_max
     *
     * @return the value of p2p_cooperation_employee_record.monthly_salary_max
     *
     * @mbggenerated
     */
    public Integer getMonthlySalaryMax() {
        return monthlySalaryMax;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p2p_cooperation_employee_record.monthly_salary_max
     *
     * @param monthlySalaryMax the value for p2p_cooperation_employee_record.monthly_salary_max
     *
     * @mbggenerated
     */
    public void setMonthlySalaryMax(Integer monthlySalaryMax) {
        this.monthlySalaryMax = monthlySalaryMax;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p2p_cooperation_employee_record.monthly_salary_min
     *
     * @return the value of p2p_cooperation_employee_record.monthly_salary_min
     *
     * @mbggenerated
     */
    public Integer getMonthlySalaryMin() {
        return monthlySalaryMin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p2p_cooperation_employee_record.monthly_salary_min
     *
     * @param monthlySalaryMin the value for p2p_cooperation_employee_record.monthly_salary_min
     *
     * @mbggenerated
     */
    public void setMonthlySalaryMin(Integer monthlySalaryMin) {
        this.monthlySalaryMin = monthlySalaryMin;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p2p_cooperation_employee_record.create_time
     *
     * @return the value of p2p_cooperation_employee_record.create_time
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p2p_cooperation_employee_record.create_time
     *
     * @param createTime the value for p2p_cooperation_employee_record.create_time
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column p2p_cooperation_employee_record.update_time
     *
     * @return the value of p2p_cooperation_employee_record.update_time
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column p2p_cooperation_employee_record.update_time
     *
     * @param updateTime the value for p2p_cooperation_employee_record.update_time
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}