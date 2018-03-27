package com.relay2.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.StopWatch;

@Repository("mysqlDao")
public class MysqlDaoImpl {
	
	private static Logger logger = LoggerFactory.getLogger(MysqlDaoImpl.class);
	
	@Resource(name="jdbcTemplate")
	private JdbcTemplate jdbcT ;
	
	
	
	/**
	 *  INSERT  VALUES lists 速度测试..  1000 大约  650 ms, 10000条数据 大约 2712 ms (将近3秒钟)  
	 */
	public void insertLists(){
		StopWatch sw = new StopWatch();
		sw.start();
		String sql =  " INSERT INTO CsDwellTime(idNMSAccount,idClientStationMacAddr,isVisitor,firstSeen,lastSeen) "
			    + " VALUES";
		List<Object> batchArgs = new ArrayList<>();
		for(int i=0; i<10000; i++){
			
			long ts = System.currentTimeMillis();
			//Object[] args = new Object[]{18,"02:91:EA:ED:D2:C7", false, ts,ts};
			//batchArgs.add(args);
			//sql += "(18,'02:91:EA:ED:D2:C7',0,"+String.valueOf(ts)+","+String.valueOf(ts)+")";
			sql += "(?,?,?,?,?)";
			batchArgs.add(18);
			batchArgs.add("02:91:EA:ED:D2:C7");
			batchArgs.add(0);
			batchArgs.add(ts);
			batchArgs.add(ts);
			if(i!=9999){
				sql+=",";
			}
		}
		int count = jdbcT.update(sql,batchArgs.toArray());
		//jdbcT.batchUpdate(sql)
		logger.debug("the update count is {}",count);
		sw.stop();
		logger.debug("insert CsDwellTime table cost in millis: {} ms.", sw.getTotalTimeMillis());
	}
	
	
	public void queryTest(){
		StopWatch sw = new StopWatch();
		sw.start();
		String sql =  " SELECT * FROM CsDwellTime WHERE idCsDwellTime = ?";
		for(int i=1; i<10000; i++){
			Map<String,Object> map = jdbcT.queryForMap(sql, new Object[]{i});
			logger.debug("the query resutl is {}", map);
		}
		sw.stop();
		logger.debug("query CsDwellTime table cost in millis: {} ms.", sw.getTotalTimeMillis());
	}
	
	/**
	 * need to set sql_mode = 'ansi';
	 * ANSI mode also causes the server to return an error for queries where a set function S 
	 * with an outer reference S(outer_ref) cannot be aggregated in the outer query against which the outer reference has been resolved.
	 * This is such a query:  SELECT * FROM t1 WHERE t1.a IN (SELECT MAX(t1.b) FROM t2 WHERE ...);
	 */
	public void testSqlModeAnsi(){
		StopWatch sw = new StopWatch();
		sw.start();
		String sql =  " SELECT * FROM sql_mode_ansi_outer_ref_st s where avg_age in "
				    + " (SELECT MAX(s.avg_age) from sql_mode_ansi_outer_ref_class)";
		jdbcT.queryForList(sql);
		sw.stop();
		logger.debug("query outer_ref sql_mode_ansi cost in millis: {} ms.", sw.getTotalTimeMillis());
	}
	
	/**
	 * need to set sql_mode = 'ONLY_FULL_GROUP_BY';
	 * The default SQL mode in MySQL 5.7 includes these modes: ONLY_FULL_GROUP_BY, STRICT_TRANS_TABLES, NO_ZERO_IN_DATE,
	 * NO_ZERO_DATE, ERROR_FOR_DIVISION_BY_ZERO, NO_AUTO_CREATE_USER, and NO_ENGINE_SUBSTITUTION. 
	 * ONLY_FULL_GROUP_BY
	 * Reject queries for which the select list, HAVING condition, or ORDER BY list refer to nonaggregated columns 
	 * that are neither named in the GROUP BY clause nor are functionally dependent on (uniquely determined by) GROUP BY columns. 
	 * 
	 * SQL99 and later permits such nonaggregates per optional feature T301 if they are functionally dependent on GROUP BY columns: 
	 * If such a relationship exists between name and custid, the query is legal. 
	 * SQL92  不允许select 的列 , HAVING 条件的 列， 还有 order by 的列， 不在 group by 的列表中 
	 * SQL99 允许group by 后面的列是主键或者 unique NOT NULL 的列，是允许select 列不出现在group by 的列表中
	 * 
	 * SELECT name, address, MAX(age) FROM t GROUP BY name;
	 * The query is valid if name is a primary key of t or is a unique NOT NULL column. In such cases, 
	 * MySQL recognizes that the selected column is functionally dependent on a grouping column. 
	 */
	public void testSqlModeOnlyFullGroupBy(){
		String sql =  " select name from sql_mode_only_group_by group by age_col";
		jdbcT.queryForList(sql);
	}
	
	
	/**
	 * need to set sql_mode = 'ONLY_FULL_GROUP_BY';
	 */
	public void testSqlModeOnlyFullGroupByPK(){
		String sql =  "select name,age_col from sql_mode_only_group_by group by id_group_by";
		jdbcT.queryForList(sql);
	}
	
	
	/**
	 *need to set sql_mode = 'ONLY_FULL_GROUP_BY'; 
	 */
	public void testSqlModeOnlyFullGroupByUniqueCol(){
		String sql =  "select name,age_col from sql_mode_only_group_by group by name";
		jdbcT.queryForList(sql);
	}
	
	/**
	 * need to set sql_mode = 'ONLY_FULL_GROUP_BY';
	 */
	public void testSqlModeOnlyFullGroupByUniqueColNull(){
		String sql =  "select name_null,age_col from sql_mode_only_group_by group by name_null";
		jdbcT.queryForList(sql);
	}
	
	/**
	 * need to set sql_mode = 'ONLY_FULL_GROUP_BY';
	 */
	public void testSqlModeOnlyFullGroupBySelectNoAggCol(){
		String sql =  "select age_col, name, MAX(int_col) from sql_mode_only_group_by group by age_col";
		jdbcT.queryForList(sql);
	}
	
	/**
	 * need to set sql_mode = 'ONLY_FULL_GROUP_BY';
	 */
	public void testSqlModeOnlyFullGroupByOrderbyNoAggCol(){
		String sql =  "select age_col, MAX(int_col) from sql_mode_only_group_by group by age_col order by name desc";
		jdbcT.queryForList(sql);
	}
	
	/**
	 * need to set sql_mode = 'ONLY_FULL_GROUP_BY';
	 * If a query has aggregate functions and no GROUP BY clause, it cannot have nonaggregated columns in the select list, 
	 * HAVING condition,  or ORDER BY list with ONLY_FULL_GROUP_BY enabled:
	 * 
	 * Without GROUP BY, there is a single group and it is indeterminate which name value to choose for the group. 
	 * Here, too, ANY_VALUE() can be used, if it is immaterial which name value MySQL chooses:
	 * select ANY_VALUE(name), MAX(age_col) from sql_mode_only_group_by
	 */
	public void testSqlModeOnlyFullGroupByWithoutGroupby(){
		String sql =  "select name, MAX(age_col) from sql_mode_only_group_by ";
		jdbcT.queryForList(sql);
	}
	
	/**
	 * need to set sql_mode = 'ONLY_FULL_GROUP_BY';
	 * In MySQL 5.7.5 and higher, ONLY_FULL_GROUP_BY also affects handling of queries that use DISTINCT and ORDER BY.
	 * 如果order by 表达式不满足下面条件，将无效
	 * if any ORDER BY expression does not satisfy at least one of these conditions:
	 * 1# The expression is equal to one in the select list 
	 * 
	 * 2# All columns referenced by the expression and belonging to the query's selected tables are elements of the select list
	 */
	public void testSqlModeOnlyFullGroupByDistinctOrderby(){
		String sql =  "select distinct age_col, int_col from sql_mode_only_group_by order by name desc";
		jdbcT.queryForList(sql);
	}
	
	
	/**
	 * As of MySQL 5.7.4, the ERROR_FOR_DIVISION_BY_ZERO, NO_ZERO_DATE, and NO_ZERO_IN_DATE SQL modes are deprecated. 
	 * From MySQL 5.7.4 though 5.7.7, these modes do nothing when named explicitly. 
	 * Instead, their effects are included in the effects of strict SQL mode (STRICT_ALL_TABLES or STRICT_TRANS_TABLES)
	 * Although in MySQL 5.7.8 and later ERROR_FOR_DIVISION_BY_ZERO, NO_ZERO_DATE, and NO_ZERO_IN_DATE can be used separately from strict mode, 
	 * it is intended that they be used together. As a reminder, a warning occurs if they are enabled without 
	 * also enabling strict mode or vice versa.
	 * 1# mysql 5.7.4 到 5.7.7 设置 
	 *  set sql_mode = 'STRICT_TRANS_TABLES' 将抛出异常.error
	 *  
	 *  2# 如果是5.7.4 版本以前 和 5.7.8 版本后 set sql_mode='STRICT_TRANS_TABLES, NO_ZERO_DATE' 将抛出异常 error.
	 *  
	 */
	public void testSqlModeStrictNoZeroDate(){
		String sql =  "INSERT INTO sql_mode_strict(date_col,date_time_col) values ('0000-00-00','0000-00-00 00:00:00')";
		jdbcT.update(sql);
	}
	
	/**
	 *  1# mysql 5.7.4 到 5.7.7 设置 
	 *  set sql_mode = 'STRICT_TRANS_TABLES' 将抛出异常.error, 没有设置 date_col 将会设置成'0000-00-00'
	 *  
	 *  2# 如果是5.7.4 版本以前 和 5.7.8 版本以后 set sql_mode='STRICT_TRANS_TABLES, NO_ZERO_DATE' 将抛出异常 error.
	 *  如果 只设置 STRICT_TRANS_TABLES, 将抛出异常 error.
	 *  如果 只设置 NO_ZERO_DATE, date_col 将会设置成'0000-00-00' 但会有警告.
	 *  The server requires that month and day values be valid, and not merely in the range 1 to 12 and 1 to 31, 
	 *  respectively. With strict mode disabled, invalid dates such as '2004-04-31' are converted to '0000-00-00' and a warning is generated. 
	 *  With strict mode enabled, invalid dates generate an error. To permit such dates, enable ALLOW_INVALID_DATES.
	 */
	public void testSqlModeStrictUnCorrectDate(){
		String sql =  "INSERT INTO sql_mode_strict(date_col,date_time_col) values ('2016-02-30','2016-12-20 12:20:00')";
		jdbcT.update(sql);
	}
	
	
	/**
	 *  1# mysql 5.7.4 到 5.7.7 设置 
	 *  set sql_mode = 'STRICT_TRANS_TABLES' 将抛出异常.error, 没有设置 int_col 被设置成NULL
	 *  
	 *  2#5.7.8 版本以后 set sql_mode='STRICT_TRANS_TABLES, ERROR_FOR_DIVISION_BY_ZERO' 将抛出异常 error.
	 *  如果 只设置 STRICT_TRANS_TABLES, int_col 被设置成NUL,不会有警告
	 *  如果 只设置 ERROR_FOR_DIVISION_BY_ZERO,  int_col 被设置成NUL 但会有警告. 
	 */
	public void testSqlModeStrictDivisiobyZero(){
		String sql =  "INSERT INTO sql_mode_strict(date_col,date_time_col,int_col) values ('2016-02-20','2016-12-20 12:20:00',12/0)";
		jdbcT.update(sql);
	}
	
	
	/**
	 * NO_AUTO_VALUE_ON_ZERO affects handling of AUTO_INCREMENT columns. 
	 * Normally, you generate the next sequence number for the column by inserting either NULL or 0 into it. 
	 * NO_AUTO_VALUE_ON_ZERO suppresses this behavior for 0 so that only NULL generates the next sequence number.
	 *  
	 * set sql_mode='NO_AUTO_VALUE_ON_ZERO' 设置后  id_no_auto_on_zero 将是 0
	 * 如果没有设置, id_no_auto_on_zero 将是  next sequence
	 * 
	 */
	public int testSqlModeNoAutoValueOnZero(){
		String sql =  "INSERT INTO sql_mode_no_auto_on_zero(id_no_auto_on_zero,name) values (0,'test')";
		jdbcT.update(sql);
		sql = "SELECT * from sql_mode_no_auto_on_zero WHERE id_no_auto_on_zero = 0";
		List<Map<String, Object>> list = jdbcT.queryForList(sql);
		return list.size();
	}
	
	/**
	 * Make MySQL behave like a “traditional” SQL database system. 
	 * A simple description of this mode is “give an error instead of a warning” when inserting an incorrect value into a column.
	 * Before MySQL 5.7.4, and in MySQL 5.7.8 and later, 
	 * TRADITIONAL is equivalent to STRICT_TRANS_TABLES, STRICT_ALL_TABLES, NO_ZERO_IN_DATE, NO_ZERO_DATE, ERROR_FOR_DIVISION_BY_ZERO, 
	 * NO_AUTO_CREATE_USER, and NO_ENGINE_SUBSTITUTION.  
	 */
	public void testSqlModeTranditional(){
		
	}
	
	/**
	 *  Control automatic substitution of the default storage engine 
	 *  when a statement such as CREATE TABLE or ALTER TABLE specifies a storage engine that is disabled or not compiled in.
	 * The default SQL mode includes NO_ENGINE_SUBSTITUTION.
     * Because storage engines can be pluggable at runtime, unavailable engines are treated the same way:
     * With NO_ENGINE_SUBSTITUTION disabled, for CREATE TABLE the default engine is used and a warning occurs if the desired engine is unavailable. For ALTER TABLE, a warning occurs and the table is not altered.
     * With NO_ENGINE_SUBSTITUTION enabled, an error occurs and the table is not created or altered if the desired engine is unavailable. 
	 */
	public void testSqlModeNoEngineSubstitution(){
		//String sql = "ALTER TABLE `r2db`.`sql_mode_no_auto_on_zero` ENGINE = Spider";
	}
	
	/**
	 * set sql_mode = 'NO_AUTO_CREATE_USER';
	 * Prevent the GRANT statement from automatically creating new user accounts if it would otherwise do so, unless authentication information is specified. 
	 * The statement must specify a nonempty password using IDENTIFIED BY or an authentication plugin using IDENTIFIED WITH.
	 * 
	 * 如果设置改模式。  grant 语句如果用户不存在，将不会自动创建用户{除非指定密码(IDENTIFIED BY)}。
	 * mysql> grant all on *.* to  test3;
	 * ERROR 1133 (42000): Can't find any matching row in the user table 
	 * 
	 * 指定密码：但会有warnings;
	 * mysql> grant all on *.* to  test3 identified by 'mysql';
     * Query OK, 0 rows affected, 1 warning (0.04 sec)
     * It is preferable to create MySQL accounts with CREATE USER rather than GRANT. As of MySQL 5.7.6, 
     * NO_AUTO_CREATE_USER is deprecated. As of 5.7.7 the default SQL mode includes NO_AUTO_CREATE_USER 
	 */
	public void testSqlModeNoAutoCreateUser(){
		
	}
	
	/**
	 * For transactional tables, an error occurs for invalid or missing values in a data-change statement 
	 * when either STRICT_ALL_TABLES or STRICT_TRANS_TABLES is enabled. The statement is aborted and rolled back.
	 * For nontransactional tables, the behavior is the same for either mode if the bad value occurs in the first row to be inserted or updated: 
	 * The statement is aborted and the table remains unchanged. 
	 * If the statement inserts or modifies multiple rows and the bad value occurs in the second or later row, the result depends on which strict mode is enabled:
	 * 
	 * For STRICT_ALL_TABLES, MySQL returns an error and ignores the rest of the rows. However, because the earlier rows have been inserted or updated, the result is a partial update. 
	 * To avoid this, use single-row statements, which can be aborted without changing the table. 
	 * 
	 * For STRICT_TRANS_TABLES, MySQL converts an invalid value to the closest valid value for the column and inserts the adjusted value. 
	 * If a value is missing, MySQL inserts the implicit default value for the column data type. 
	 * In either case, MySQL generates a warning rather than an error and continues processing the statement.
	 * 
	 *  如果table 的ENGINE 为MyISAM
	 *  isVisitor 的类型 是TINYINT(1)
	 *  INSERT INTO CsDwellTime_TEMP(idNMSAccount,idClientStationMacAddr,isVisitor,firstSeen,lastSeen)
     *  VALUES (18,'02:91:EA:ED:D2:C7',0,1482296174000,1482296174000),(18,'02:91:EA:ED:D2:C7',655367,1482296174000,1482296174000);
     *  第二条数据有问题：
     *  如果sql_mode ='STRICT_TRANS_TABLES'
     *  两条数据将插入。 第二条数数据会调整为：
     *  '2', '18', '02:91:EA:ED:D2:C7', '127', '1482296174000', '1482296174000'
     *  但会有警告
     *  
     *  
     *  如果sql_mode ='STRICT_ALL_TABLES'
     *  第一条数据会成功，第二条数据将失败
     *  ERROR 1264 (22003): Out of range value for column 'isVisitor' at row 2
     *  
	 */
	public void testSqlModeStrictAllTables(){
		
	}
	
	
	public void testFabricInsert(){
		for(int i =0; i<100; i++){
			String sql =  "INSERT INTO city(Name,CountryCode,District) values ('Lushan"+i+"','CHN','Jiangxi')";
			jdbcT.update(sql);
		}
	}
	
	public String testFabricQuery(){
		String sql =  "SELECT @@server_uuid";
			
		String uuid = 	jdbcT.queryForObject(sql, String.class);
		
		sql  = "SELECT Name FROM city where ID=4091";
		
		String name = jdbcT.queryForObject(sql, String.class);
		System.out.println("uuid="+uuid);
		System.out.println("name="+name);
		return uuid;
	}
}
