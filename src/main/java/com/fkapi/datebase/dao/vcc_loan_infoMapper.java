package com.fkapi.datebase.dao;

import com.fkapi.datebase.domain.vcc_loan_info;

import java.util.List;

public interface vcc_loan_infoMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vcc_loan_info
     *
     * @mbg.generated
     */
    int deleteByCustId(Long custId);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vcc_loan_info
     *
     * @mbg.generated
     */
    int insert(List<vcc_loan_info> list);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vcc_loan_info
     *
     * @mbg.generated
     */
    int insertSelective(vcc_loan_info record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vcc_loan_info
     *
     * @mbg.generated
     */
    List<vcc_loan_info> selectByCustIdAndStatus(vcc_loan_info record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vcc_loan_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(vcc_loan_info record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table vcc_loan_info
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(vcc_loan_info record);
}