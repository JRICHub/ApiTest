package com.fkapi.datebase.dao;

import java.util.List;

import com.fkapi.datebase.domain.p2p_cust_credit_info;

public interface p2p_cust_credit_infoMapper {
    int deleteByCustId(Long custId);

    int insert(List<p2p_cust_credit_info> list);

    int insertSelective(p2p_cust_credit_info record);

    p2p_cust_credit_info selectByPrimaryKey(Long custId);

    int updateByPrimaryKeySelective(p2p_cust_credit_info record);

    int updateByPrimaryKey(p2p_cust_credit_info record);
}