package com.digitalchina.xa.it.util;
import java.util.HashMap;
import java.util.Map;

import javax.wsdl.Definition;
import javax.wsdl.factory.WSDLFactory;
import javax.wsdl.xml.WSDLReader;
import javax.xml.namespace.QName;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;


/**
 * Cxfå®¢æˆ·ç«¯æ–¹å¼è°ƒç”¨webserviceçš„å°è£…ç±»ï¼Œåªè§£å†³å¸¸è§æƒ…å†µ
 * 
 * @ClassName: CxfUtils 
 * @Description: 
 * @author NieYuyang
 * @date 2016å¹?7æœ?21æ—? ä¸‹åˆ5:34:38 
 * @version 1.0 
 *
 */
public class CxfUtils {

	/**
	 * WebServiceåŠ¨æ?è°ƒç”?
	 * 
	 * @param wsdlUrl
	 * @param methodName
	 * @param params
	 * @return
	 */
	public static String CallService(String wsdlUrl, String methodName, Object... params) {
		return CallService(wsdlUrl, new HashMap<String, String>(), methodName, params);
	}
	
	/**
	 * WebServiceåŠ¨æ?è°ƒç”?
	 * 
	 * @param wsdlUrl
	 * @param headers
	 * @param methodName
	 * @param params
	 * @return
	 */
	public static String CallService(String wsdlUrl, Map<String, String> headers, String methodName, Object... params) {
		JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
		Client client = dcf.createClient(wsdlUrl);
		String namespaceURI = getQName(wsdlUrl);
		QName qname = new QName(namespaceURI, methodName);
//		client.getInInterceptors().add(new LoggingInInterceptor());
//		client.getInFaultInterceptors().add(new FaultInterceptor()); 		// å½“Faultå‘ç”Ÿæ—¶ï¼Œæ­¤æ‹¦æˆªå™¨ç”Ÿæ•ˆ
//		client.getOutInterceptors().add(new AddSoapHeaderInterceptor(headers, namespaceURI, "AuthenticationToken"));
		Object[] callback;
		try {
			callback = client.invoke(qname, params);
			return callback[0].toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "";
	}
	
	
	/**
	 * èŽ·å–WSDLæ–‡ä»¶çš„å‘½åç©ºé—´åï¼Œä¹Ÿå°±æ˜¯<wsdl:definitions>å…ƒç´ çš„targetNamespaceå±žæ?§å??
	 * 
	 * @param wsdl		URI
	 * @return
	 */
	public static String getQName(String wsdlUrl) {
		String qName = "";
		try {
			Definition def = null;
			WSDLFactory factory = WSDLFactory.newInstance();
			WSDLReader reader = factory.newWSDLReader();
			def = reader.readWSDL(wsdlUrl);

			if (def!=null) {
				qName = def.getTargetNamespace();
				if (qName == null || qName.equals("")) {
					qName = def.getQName().getNamespaceURI();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return qName;
	}
}

