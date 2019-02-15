package com.digitalchina.xa.it.util;

import java.util.Map;

public class PushWeaverNotificationUtils {
	
	public static String winPage = "/mobile/plugin/dch/SZBLotteryNotification/Winner.jsp";
	public static String losePage = "/mobile/plugin/dch/SZBLotteryNotification/NotWin.jsp";
	//url = 1为中奖，map中
	public void pushWeaverNotification(String reviceIdsStr, Integer winType, String messageTitleStr, Map<String, String> lotteryInfo){
		
		String ip = "http://10.0.20.51";
		String url = ip + "/mobile/plugin/dch/SZBLotteryNotification/message.jsp";
		String userUrl = "";
		String lotteryInfoStr = lotteryInfo.get("lotteryId") + "-" + lotteryInfo.get("reward") + "-" + lotteryInfo.get("winItcode") + "-" + lotteryInfo.get("winTicket");
		
		if(winType - 1 < 0){
			userUrl = losePage + "?lotteryInfo=" + lotteryInfoStr; 
		} else {
			userUrl = winPage + "?lotteryInfo=" + lotteryInfoStr; 
		}
		
		System.err.println(userUrl);
		String postParam = "reviceIdsStr=" + reviceIdsStr + "&urlStr=" + userUrl + "&messageTitleStr=" + messageTitleStr;
//		http://10.0.20.51/mobile/plugin/dch/SZBLotteryNotification/message.jsp?reviceIdsStr=fannl&urlStr=/mobile/plugin/dch/SZBLotteryNotification/Winner.jsp?lotteryInfo=54-XXX-ZZZ&messageTitleStr=%E7%A5%9E%E5%B7%9E%E5%B8%81%E5%A4%BA%E5%AE%9D%E5%BC%80%E5%A5%96%E6%8F%90%E9%86%92
		HttpRequest.sendGet(url, postParam);
	}
}
