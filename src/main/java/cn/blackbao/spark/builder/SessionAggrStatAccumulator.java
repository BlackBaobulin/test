package cn.blackbao.spark.builder;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.spark.util.AccumulatorV2;

import cn.blackbao.spark.utills.StringUtils;

/**
 * session聚合统计Accumulator
 * 
 * 大家可以看到
 * 其实使用自己定义的一些数据格式，比如String，甚至说，我们可以自己定义model，自己定义的类（必须可序列化）
 * 然后呢，可以基于这种特殊的数据格式，可以实现自己复杂的分布式的计算逻辑
 * 各个task，分布式在运行，可以根据你的需求，task给Accumulator传入不同的值
 * 根据不同的值，去做复杂的逻辑
 * 
 * Spark Core里面很实用的高端技术
 * 
 * @author Administrator
 *
 */
public class SessionAggrStatAccumulator extends AccumulatorV2<String, Map<String, Long>> {

	private static final long serialVersionUID = 6311074555136039130L;
	Map<String, Long> result = new Hashtable<String, Long>();
	
	/**
	 * zero方法，其实主要用于数据的初始化
	 * 那么，我们这里，就返回一个值，就是初始化中，所有范围区间的数量，都是0
	 * 各个范围区间的统计数量的拼接，还是采用一如既往的key=value|key=value的连接串的格式
	 */
	@Override
	public void add(String arr) {
		if(StringUtils.isBlank(arr))
			return;
		String[] split = arr.split("\\|");
		if(split.length==2) {
			Long temp = Long.valueOf(split[1]);
			if(result.containsKey(split[0])) {
				result.put(split[0], result.get(split[0])+ temp);
			}else {
				result.put(split[0], temp);
			}
		}else if (split.length==1) {
			if(result.containsKey(split[0])) {
				result.put(split[0], result.get(split[0])+ 1);
			}else {
				result.put(split[0], 1l);
			}
		}
		
	}
	@Override
	public AccumulatorV2<String, Map<String, Long>> copy() {
		SessionAggrStatAccumulator sessionAggrStatAccumulator = new SessionAggrStatAccumulator();
		sessionAggrStatAccumulator.result = new Hashtable<>(this.result);
		return sessionAggrStatAccumulator;
	}
	@Override
	public boolean isZero() {
		return result.isEmpty();
	}
	@Override
	public void merge(AccumulatorV2<String, Map<String, Long>> ac) {
		Iterator<Entry<String, Long>> it = ac.value().entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Long> entry =it.next();
			String key = entry.getKey();
			Long value = entry.getValue();
			if(result.containsKey(key)) {
				result.put(key, result.get(key)+ (value==null?1:value));
			}else {
				result.put(key, value==null?1:value);
			}
		}
	}
	@Override
	public void reset() {
		 result.clear();
		
	}
	@Override
	public Map<String, Long> value() {
		return result;
	} 

}
