package com.digitalchina.xa.it.job;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class MoveBeatCardJob  {
    @Autowired
   	private JdbcTemplate jdbc;

	@Transactional
	@Scheduled(cron="00 00 08 * * ?")
	public void moveBeatCard()throws Exception {
		   Connection conn = null;  
		   List<Object[]> argList = new ArrayList<Object[]>();
			   //获取连接  
         String driverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";  //加载JDBC驱动   
         String dbURL = "jdbc:sqlserver://10.1.128.93:1433;DatabaseName=HRESSDB";  //连接服务器和数据库sample
         //运行SQL语句  
         String userName = "XiAnJava";  //默认用户名 
         String userPwd = "1234@qwer";  
         Class.forName(driverName);
         conn = DriverManager.getConnection(dbURL, userName, userPwd);
        if(conn!=null)
        {
        	
            Statement stmt = null; //一个封装和管理SQL语句的java对象
            ResultSet rs = null; //一个封装了数据对象 的 无序集合对
            
        try {
            System.out.println("Connection Successful!");  //如果连接成功 控制台输出
              stmt = conn.createStatement();
              rs = stmt.executeQuery("SELECT * from CardView  where  beat_card_date=CONVERT(varchar(100),DATEADD(DAY,-1,GETDATE()),112) order by beat_card_date desc");
             while (rs.next()) {
            	 Object [] obe = new  Object[4];
            	 Map map=new HashMap<>();
            	 obe[0]= rs.getString("UserNo");
            	 obe[1]= rs.getString("beat_card_date");
            	 obe[2]=rs.getString("beat_card_firsttime");
            	 obe[3]= rs.getString("beat_card_lasttime");
            	 argList.add(obe);
             }
             jdbc.execute("delete from beat_card");
              jdbc.batchUpdate("insert beat_card (id,UserNo,beat_card_date,beat_card_firsttime,beat_card_lasttime) values(null,?,?,?,?)", argList);
          
        }catch (Exception e) {
        	e.printStackTrace();
			// TODO: handle exception
		}finally {
		    if(rs!=null) rs.close();
             if(conn!=null) conn.close();
             if(stmt!=null) stmt.close();
		}
        System.out.println("同步完成!");  
        }else{
              System.out.println("Connection fail!");  
         }
		               
		    
		                 
}
}
