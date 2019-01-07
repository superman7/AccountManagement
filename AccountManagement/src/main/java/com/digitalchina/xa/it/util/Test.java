package com.digitalchina.xa.it.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.concurrent.ExecutionException;

import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.generated.Uint160;
import org.web3j.crypto.CipherException;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Wallet;
import org.web3j.crypto.WalletFile;
import org.web3j.crypto.WalletUtils;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.admin.Admin;
import org.web3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.web3j.protocol.core.DefaultBlockParameterName;
import org.web3j.protocol.core.Request;
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthGetTransactionReceipt;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import com.alibaba.fastjson.JSONObject;
import com.digitalchina.xa.it.contract.Transfer;
import com.digitalchina.xa.it.service.PaidVoteDetailService;

import scala.util.Random;

public class Test {

	//905E32565C95D7E38BD5A1539C2726E5CB3DAB51650FA2F381F7CD14932AC229A2C1C19C105A4C836FD1DC064AEA88034724759E3EFC22900AF321383BB8CC0E203E08B17B7FEDA0B22F50EB77E5F017B512CE737D656B85FB31CD081B87699E29088220054A8B91E3CCC79F48A6E6F89FA6D621D35EE8BF
//	public static void main(String[] args) {
//		Encrypt encrypt = new EncryptImpl();
////    	String decrypt = null;
//    	try {
//			System.out.println(encrypt.encrypt("0x189abcd4cb82534d9d7b2ee181b28bcc86c64853-0x70fd069aed8f9e363a0746a8d241ddac3e00809a-test-600-fannl-alexshen"));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
	
	private static String ip = "http://10.7.10.124:8545";
	private static String address = "0x024a3c0d945739237eedf78c80c6ae5daf22c010";
	private static String[] appList = {"BRIDGE","Manta Flow","PrediCX","Wrike","Cluvio","Plecto","SysAid","Salesforce Sales Cloud","Marketing 360","Peek Pro Tour Operators","monday.com","Zoho CRM","Campaign Monitor","Tableau Software","Sisense","ProsperWorks CRM","TEAMGATE","Brand24","Adaptive Insights","Nutshell CRM","Grow.com","Prisync","Looker","Insightly","AgencyAnalytics","ClicData","Hubble","Zoho Reports","Springboard Retail","Attendify","Tagetik","Livestorm","Datadog","Logicbox","NUVI","LogicMonitor","Style Intelligence","Halo","SE Ranking","Site Search 360","NetSuite","Property Matrix","Logz.io","Windward Solution","Izenda","TapClicks","Geckoboard","Scribe Online","Pyze","WebEngage","Chartio","Caspio","Wave: Salesforce Analytics Cloud","Weekdone","Host Analytics EPM Suite","iAuditor","Incompetitor","Attribution","Cooladata","FullContact","AnswerRocket","dexi.io","ESM Suite","Nexternal Solutions","Bronto","Heap","Yellowfin","ReportGarden","OneStream XF","BluLogix","PlanGuru","Anodot","9 Spokes","prevero","CaptureFast","Systum","Spreadsheet Server","Convertize","Funnel Science","Marketing Optimizer","Presynct_OnDemand","Radar","EngageIP Billing/OSS","Allocable","Dataccuity","Float Cash Flow","Funnel","IntelliFront BI","VelocityEHS","MuniLogic","Swiftype Site Search","Revulytics","Validis","Appsee Mobile Analytics","SQL-RD","LandVision","CRD","Panoply","Zoho PageSense","Avarea Analytics for Marketing","Control","Corporater","Directorpoint","ERES Cloud","Etleap","Host Analytics","Process Director","Questica OpenBook","Synup","Tick","Hootsuite","Google Analytics","Databox","Klipfolio","Followup CRM","ProjectManager.com","SEMrush","BOARD","Smartlook","Dundas BI","Moz","Salesforce Social Studio","Base CRM","Adobe SiteCatalyst","Vivantio ITSM","ITM Platform","GlobiFlow for Podio","Domo","Mothernode CRM","Qlik Sense","WebFOCUS","QlikView","InsightSquared","Bullhorn CRM","Exponea","Optimizely","SAP Business Objects","Revel Systems POS","Hotjar","NetBase","Leadfeeder","Firefish","Tubular","Microsoft Power BI","Leady","Panorama Necto","Wdesk","Cognos","Phocas","Ahrefs","WooRank","Cyfe","SpyFu","VisitorTrack","Azurepath","Docparser","Rebrandly","Alteryx","Numetric","Mozenda","Aviso","Locowise","DataPlay","Vidyard","SAP Analytics Cloud","FinPro","Mixpanel","Prolo","Workmates","CleverTap","Acquisio","SAS BI & Analytics","CiviCRM","Datanyze","Birst","MicroStrategy","Hootsuite Insights","TIBCO Spotfire","ForceManager","Pentaho","PeopleFluent","HasOffers by TUNE","Notion","Crazy Egg","Spinify","Brightedge","FacilityDude GIS","Alexa","ChartMogul","Crimson Hexagon","Hoopla","Poimapper","Periscope","Visual Website Optimizer","Simply Measured","SimilarWeb PRO","Easy Insight","Visme","LiveHive","Slemma","Jaspersoft","BIME by Zendesk","Piwik PRO","Raven","Gainsight","Segment","FORGE","Inspectlet","Predictive intelligence","Predixion","Priceforge","Pro Profs Poll Maker","Proof","Ptengine","Regression Forecasting","Retail Advantage","Rocket Fuel","Roosboard","SAP Predictive Analytics","Salescast","Salesvue","SearchMan","SeeVolution","Semantria","SessionCam","Share a Refund","Sift Science","SimpleFeed","SpatialKey","Splunk Cloud","Statsbot","Surveyi2i","TPO Planner","Taveo","Tidemark","Transera","Trend Miner","Upsight","VUE Software","Velocity","Versium","Vigil","Visual KPI","VueData","Web Maven","Webtrends","WildFire","Windward Arrow","Wise","Wizdee","WordStream SEO for FireFox","XLReporting","Yurbi","ipapi","mailspice","pcFinancials","theBillingBridge","webKPI","whatusersdo.com","SalesChoice","FusionCharts","ReportPlus","CMO COMPLIANCE","CanvasJS Charts","Megalytic","Wink Reports","Mentimeter","YUNO","RESULTS.com","SEO PowerSuite","RapidMiner","SABRE","MoData","Sysomos","GitPrime","MACH Energy","PUBLITRAC","Truedash","Zenput Mobile","Dasheroo","Quantcast","Certent Disclosure Management","DemandJump","Quantum Metric","Roambi Analytics","quintly","Analytics Plus","Brandwatch","Purchase Control","DBHawk","ThoughtBuzz","Kissmetrics","Envisio","Tealium IQ","Albacross","FUTRLI","InviteManager","Knowledge Vault","Qubole Data Service","SalesPredict","ScribbleLive","SumAll","Unomy","Gooddata","Crashlytics","Evergage","Intellicus","Sumo Logic","Mouseflow","Radius","Siteimprove","Authoritas","CloverETL","Majestic","Practi","Amazon Cloud Search","Colibri SEO","Grepsr","Intelex","Librato","PriceGrid","Logi Info","AquaCRM Software","Artesian","Atomic Reach","BI Office","Bonsai.io","Botify","Business Valuation","Centius Qi","Clixtell","Contour BI","Decibel Insight","EMAsphere","EverString","Fathom","Financial Dashboard and Business Intelligence","Leftronic","Localytics","LookBookHQ","ManageEngine Exchange Reporter Plus","OWOX BI","Pixability","Polymita BPMS","RJMetrics","Ranktab","Ruler Analytics","Spotlight Reporting","Unbxd","Xtremepush","Zoola Analytics","intelligentXchange","sales-i","Sailthru","DataHero","Amplitude","Freshmarketer","OverOps","Akita","AppAnnie","AppNeta","Circonus","Contactology","CustomerGauge","DataCycle Reporting","Flurry","Swydo","InsideSales","Datameer","Marin Software","Cloud9","ComScore","Mi360","Webtrekk","Woopra","InPhase","Keen IO","Lex Machina","3PMobile","AB Tasty","AT Internet Web Analytics","Agile Reporting Services","AlchemyAPI","Alooma","Anchor Metrics","Applause Analytics","Apptopia","Attivio","AutoTag","Aviatrix","BEYABLE","BIRT onDemand","Badgeville","Baremetrics","Beckon","Belladati","BluePlanner","Bluekai","Bluenose","Bounce Exchange","Bravo Reporting","Chartbeat","ChurnSpotter","Clarabridge","Cleo Integration Cloud","ClickTale","CloudLock for Google Apps","CloudPhysics","Compete","ConversionSweeper","Criteo","CustomerCradle","Custora","Cxense Insight","DataTap","Datahug","EdgeSpring","EmbeddedAnalytics","Entytle","Etail Solutions","FICO Falcon Fraud Manager","Fiserv","Fliptop","Foresight Intelligence Center","Full Circle Response Management","Futurelytics","GroupQuality","GuestCentric","Happy Apps","Hitwise","Hohli","HomeCare Accounting","HookFeed","Infer","InfoCaptor Dashboard","Jibestream","Juicebox","KnowledgeSCORE","Kognitio Cloud","Kompyte","Lattice","Leadspace","LevelEleven","Loader","Logi Vision","Lumiary","Magnetic","ManageEngine ADAudit Plus","MetricPulse","MonkeyData","Now Assistant","Numerify","OLAP Reporting Tool","OmniContext","OneAll","OpenText Actuate Analytics Designers","OpenText Actuate Information Hub","Operational Insights","Oribi","PPC Keyword","PRO(a)ACT","PhoneTools","Planning In A Box","Platfora","PointPin","PostHelpers","Adaptive Insights"};
//	private static String ip = "http://10.125.9.21:8545";
//	private static String address = "0x601f2e867002401cf3f6d1f8e02798d603743170";
    static final int PUBLIC_KEY_SIZE = 64;
	static final int PUBLIC_KEY_LENGTH_IN_HEX = PUBLIC_KEY_SIZE << 1;
//	public static void main(String[] args) {
//		Admin admin = Admin.build(new HttpService(ip));
//		admin.personalNewAccount("testCreateAccount").observable().subscribe( x -> {
//			System.out.println(x.getAccountId());
//			System.out.println(x.getResult());
//		});
//	}
	
//	public static void main(String[] args) {
//		String aa = "asdfxxxzxcvdf4ewrdsfgbxvcxv";
//		ECKeyPair ecKeyPair= ECKeyPair.create(getSHA2HexValue(aa));
//		System.out.println("PrivateKey:" + ecKeyPair.getPrivateKey().toString(16));
//        System.out.println("PublicKey:" + ecKeyPair.getPublicKey().toString(16));
//        System.out.println(Keys.getAddress(ecKeyPair));
//        try {
//			WalletFile walletFile = Wallet.createStandard("getapps", ecKeyPair);
//
//	        System.out.println("walletFile:" + walletFile);
//	        System.out.println("address:" + walletFile.getAddress());
//	        System.out.println("id:" + walletFile.getId());
//	        System.out.println("version:" + walletFile.getVersion());
//	        System.out.println("hashCode:" + walletFile.hashCode());
//	        System.out.println("crypto:" + walletFile.getCrypto());
//	        
//	        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(walletFile);
//			System.out.println("keystore json:" + jsonObject.toJSONString());
//		}catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    }
//	public static void main(String[] args) {
//		Admin admin = Admin.build(new HttpService(ip));
//		Web3j web3j = Web3j.build(new HttpService(ip));
//		
//		String account = "0x746105fb3a6331ab23a05ace2cda662de4883e0c";
//		EthGetTransactionCount ethGetTransactionCount;
//		try {
//			ethGetTransactionCount = web3j.ethGetTransactionCount(account, DefaultBlockParameterName.LATEST).sendAsync().get();
//			BigInteger nonce = ethGetTransactionCount.getTransactionCount();
//			System.out.println(nonce);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			e.printStackTrace();
//		}
//		
//	}
	//测试webservice
//	public static void main(String[] args) {
//		String aaa = CxfUtils.CallService("http://10.0.20.51/services/CreateTopicService?wsdl", "AutoTriggerTopic", "测试--fannl--op1##op2");
//		System.out.println(aaa);
//	}
	
	public static void main(String[] args) {
		String[] aaa = {};
//		String s = "";
//		try {
//			s = URLEncoder.encode(aaa,"utf-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		for(String s : aaa)
		{
			String result = HttpRequest.sendGet("http://localhost:8082/eth/getBalance", "itcode=" + s);
			System.out.println(result);
		}
	}
	
//	public static void main(String[] args) {
//    try{
//		String tempFilePath = "C://temp/";
//		String keystoreName = "keystore.json";
//		String keystore = "{\"address\":\"44b8c62b73a77df17ac4a194c5a68a723aea356a\",\"id\":\"8c2b0b9a-b71a-4dd3-b918-9fd3f76e1654\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"1da057b206f250b0bf92056b1d349dbbd3f279d1b495f64b3efc73bc3d9bbd3f\",\"kdfparams\":{\"p\":1,\"r\":8,\"salt\":\"36ac0d9cf7e63b19fd7714055f155e207e6af333025b4cec02377c82b410936b\",\"dklen\":32,\"n\":262144},\"cipherparams\":{\"iv\":\"f3c2ad317a3018f77a99f5f32e2eaa53\"},\"kdf\":\"scrypt\",\"mac\":\"adecd6a46337120c8d86916ec0ef5a25378657900a7ade55c1f5fc26fba48011\"}}";
//		File file = new File(tempFilePath + keystoreName);
//        //if file doesnt exists, then create it
//        if(!file.exists()){
//         file.createNewFile();
//        }
//        
//        FileWriter fw = new FileWriter(file.getAbsoluteFile());
//        BufferedWriter bw = new BufferedWriter(fw);
//        bw.write(keystore);
//        bw.close();
//        System.out.println("创建keystore。。。");
//        Credentials credentials;
//		try {
//			Web3j web3j =Web3j.build(new HttpService(ip));
//			credentials = WalletUtils.loadCredentials("test11", file);
//			System.out.println("解锁成功。。。");
//			Transfer contract = Transfer.load(address, web3j, credentials, BigInteger.valueOf(2200000000L), BigInteger.valueOf(4300000L));
//			
//			String tocount = "0x8a950e851344715a51036567ca1b44aab3f15110";
//			contract.transferAToB(new Address(tocount), BigInteger.valueOf(100000000000000000L)).observable().subscribe(x -> {
//				System.out.println(x.getBlockHash());
//				System.out.println(x.getBlockNumber());
//				System.out.println(x.getCumulativeGasUsed());
//				System.out.println(x.getGasUsed());
//				System.out.println(x.getStatus());
//				System.out.println(x.getTransactionHash());
//			});
//		} catch (Exception e) {	
//			e.printStackTrace();
//		}
//        System.out.println("删除临时keystore：" + file.delete());
//       }catch(IOException e){
//        e.printStackTrace();
//       }
//	}
	public static byte[] getSHA2HexValue(String str) {
        byte[] cipher_byte;
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(str.getBytes());
            cipher_byte = md.digest();
            return cipher_byte;
        } catch (Exception e) {
                e.printStackTrace();
        }
        
        return null;
  }
}
