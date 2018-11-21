package com.digitalchina.xa.it.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.digitalchina.xa.it.model.VirtualMachineDomain;

public interface VirtualMachineDAO {
	List<VirtualMachineDomain> selectCashBackUsers(@Param("startTime")String startTime, @Param("endTime")String endTime);
}
