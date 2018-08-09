package com.digitalchina.xa.it.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;

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
import org.web3j.protocol.core.methods.request.Transaction;
import org.web3j.protocol.core.methods.response.EthGetTransactionCount;
import org.web3j.protocol.core.methods.response.EthSendTransaction;
import org.web3j.protocol.core.methods.response.EthTransaction;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.protocol.http.HttpService;
import org.web3j.utils.Numeric;

import com.alibaba.fastjson.JSONObject;
import com.digitalchina.xa.it.contract.Transfer;

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
//		
//		System.out.println("PrivateKey:" + ecKeyPair.getPrivateKey().toString(16));
//
//        System.out.println("PublicKey:" + ecKeyPair.getPublicKey().toString(16));
//        System.out.println(Keys.getAddress(ecKeyPair));
//        try {
//			WalletFile walletFile = Wallet.createStandard("test1", ecKeyPair);
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
	
	public static void main(String[] args) {
    try{
		String tempFilePath = "C://temp/";
		String keystoreName = "keystore.json";
		String keystore = "{\"address\":\"44b8c62b73a77df17ac4a194c5a68a723aea356a\",\"id\":\"8c2b0b9a-b71a-4dd3-b918-9fd3f76e1654\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"1da057b206f250b0bf92056b1d349dbbd3f279d1b495f64b3efc73bc3d9bbd3f\",\"kdfparams\":{\"p\":1,\"r\":8,\"salt\":\"36ac0d9cf7e63b19fd7714055f155e207e6af333025b4cec02377c82b410936b\",\"dklen\":32,\"n\":262144},\"cipherparams\":{\"iv\":\"f3c2ad317a3018f77a99f5f32e2eaa53\"},\"kdf\":\"scrypt\",\"mac\":\"adecd6a46337120c8d86916ec0ef5a25378657900a7ade55c1f5fc26fba48011\"}}";
//		String keystore = "{\"address\":\"3981c0a6a05a1a112395bef12cedf33ea324a901\",\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"6cbde339012f1d3910d27f25b39863a38afda79edb18b8abfca58ef726ceef65\",\"cipherparams\":{\"iv\":\"7bd132e8f993d128c29433461261d2d5\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"474f1c7c5ae09e6c3772c8a1a71eeab15a481f80f60ca951396a653755bc74d8\"},\"mac\":\"01269a43e2ff2ee36a8c20304f5f3314dfdbd446c2376022a95db6a451a9c408\"},\"id\":\"3948a772-8259-4a71-a5d3-05e9380d6c13\",\"version\":3}";
//		String keystore = "{\"address\":\"5f9e6806342bfa4cdf4f3796c4cba73acdaca751\",\"id\":\"b20152ce-1779-42a7-9c6a-904d8f8775f7\",\"version\":3,\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"e71e6aeb4bf8d5eaf31f61a2a90cdb01544111cd8af0357b26b065d3d0109919\",\"kdfparams\":{\"p\":1,\"r\":8,\"salt\":\"e879d5e76a328c49e052de5478305c570b4894c7e7afcdce33ecb64a72f33f0f\",\"dklen\":32,\"n\":262144},\"cipherparams\":{\"iv\":\"025033568dea89ebdf26a23209f5c19a\"},\"kdf\":\"scrypt\",\"mac\":\"1b0163044f46c490fee09f76a3dadbcf1273a7ab609366000f2889b38e8d8792\"}}";
//		String keystore = "{\"address\":\"4a5237213423ea6e5994e338d1628cf76e215ca3\",\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"2bf0c50a7fdaf53d2da9a11b171f971ead28d38d5dadc04ffb1d531a93d8f501\",\"cipherparams\":{\"iv\":\"8932e77133311db4320cd02e3740c9ab\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"ee54aa13826e0eaf0339c2b085f5acec131921142d21293551311423bc8a06e8\"},\"mac\":\"ca4cf1a8347c48324fd917e0a3844ce70480e0d9300ca5dec491aa35475f1dcb\"},\"id\":\"ebd28d3a-3886-4299-849e-43574521c8a5\",\"version\":3}";
//		String keystore = "{\"address\":\"189abcd4cb82534d9d7b2ee181b28bcc86c64853\",\"crypto\":{\"cipher\":\"aes-128-ctr\",\"ciphertext\":\"92030d37f698f10ceb08b65e8cf8fcb048d75b81437d7feda004008eb3fa69c8\",\"cipherparams\":{\"iv\":\"2ef3f82c19ee50efebd9f555f6a22fc5\"},\"kdf\":\"scrypt\",\"kdfparams\":{\"dklen\":32,\"n\":262144,\"p\":1,\"r\":8,\"salt\":\"5bee728b4c1a470d9514faab1ab17eaacbdfe8692d042b50ff37f5eacc6d288e\"},\"mac\":\"f094bbc466a969bf939e755ea58900002ffaa7911b36cc4541222f01234b23e4\"},\"id\":\"c337c7c8-df9d-42f8-b59f-176ee9a04d81\",\"version\":3}";
		File file = new File(tempFilePath + keystoreName);
        //if file doesnt exists, then create it
        if(!file.exists()){
         file.createNewFile();
        }
        
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(keystore);
        bw.close();
        System.out.println("创建keystore。。。");
        Credentials credentials;
		try {
			Web3j web3j =Web3j.build(new HttpService(ip));
			credentials = WalletUtils.loadCredentials("test11", file);
			System.out.println("解锁成功。。。");
			Transfer contract = Transfer.load(address, web3j, credentials, BigInteger.valueOf(2200000000L), BigInteger.valueOf(4300000L));
			
			String tocount = "0x8a950e851344715a51036567ca1b44aab3f15110";
			contract.transferAToB(new Address(tocount), BigInteger.valueOf(100000000000000000L)).observable().subscribe(x -> {
				System.out.println(x.getBlockHash());
				System.out.println(x.getBlockNumber());
				System.out.println(x.getCumulativeGasUsed());
				System.out.println(x.getGasUsed());
				System.out.println(x.getStatus());
				System.out.println(x.getTransactionHash());
			});
//			EthTransaction aa = web3j.ethGetTransactionByHash("0x6544243581596e37f272d2b98599dfabc5422d573c72ef1434b245063d2ad7e9").send();
//			System.out.println("xxx");
//			System.out.println(aa.getResult().getBlockNumberRaw());
//			System.out.println(aa.getResult().getBlockNumber());
//			System.out.println(aa.getResult().getBlockHash());
		} catch (Exception e) {	
			e.printStackTrace();
		}
        System.out.println("删除临时keystore：" + file.delete());
       }catch(IOException e){
        e.printStackTrace();
       }
	}
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
