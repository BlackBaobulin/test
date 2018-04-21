package cn.blackbao.spark.constant;

public enum JDBC {
	TASKDAO("taskDao"), 
	ADCLICKTRENDDAO("adClickTrendDao"),
	ADPROVINCETOP3DAO("adProvinceTop3Dao"), 
	ADBLACKLISTDAO("adBlacklistDao"),
	ADSTATDAO("adStatDao"), 
	ADUSERCLICKCOUNTDAO("adUserClickCountDao"), 
	AREATOP3PRODUCTDAO("areaTop3ProductDao"), 
	PAGESPLITCONVERTRATEDAO("pageSplitConvertRateDao"), 
	SESSIONAGGRSTATDAO("sessionAggrStatDao"), 
	SESSIONDETAILDAO("sessionDetailDao"), 
	SESSIONRANDOMEXTRACTDAO("sessionRandomExtractDao"), 
	TOP10CATEGORYDAO("top10CategoryDao"), 
	TOP10SESSIONDAO("top10CessionDao");
	
	
	private String name;
	

	private JDBC(String name) {
		this.name = name;
	}


	@Override
	public String toString() {
		return this.name;  
	}
	
	
	
}
