package cn.blackbao.spark.dao;

import java.util.List;

import cn.blackbao.spark.entity.AdBlacklist;

/**
 * 广告黑名单DAO接口
 * @author Administrator
 *
 */
public interface IAdBlacklistDao extends IDao {

	/**
	 * 批量插入广告黑名单用户
	 * @param adBlacklists
	 */
	void insertBatch(List<AdBlacklist> adBlacklists);
	
	/**
	 * 查询所有广告黑名单用户
	 * @return
	 */
	List<AdBlacklist> findAll();
	
}
