package cn.com.weaver.workflow.webservices;

public class CreateTopicServicePortTypeProxy implements cn.com.weaver.workflow.webservices.CreateTopicServicePortType {
  private String _endpoint = null;
  private cn.com.weaver.workflow.webservices.CreateTopicServicePortType createTopicServicePortType = null;
  
  public CreateTopicServicePortTypeProxy() {
    _initCreateTopicServicePortTypeProxy();
  }
  
  public CreateTopicServicePortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initCreateTopicServicePortTypeProxy();
  }
  
  private void _initCreateTopicServicePortTypeProxy() {
    try {
      createTopicServicePortType = (new cn.com.weaver.workflow.webservices.CreateTopicServiceLocator()).getCreateTopicServiceHttpPort();
      if (createTopicServicePortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)createTopicServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)createTopicServicePortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (createTopicServicePortType != null)
      ((javax.xml.rpc.Stub)createTopicServicePortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public cn.com.weaver.workflow.webservices.CreateTopicServicePortType getCreateTopicServicePortType() {
    if (createTopicServicePortType == null)
      _initCreateTopicServicePortTypeProxy();
    return createTopicServicePortType;
  }
  
  public java.lang.String autoTriggerTopic(java.lang.String in0) throws java.rmi.RemoteException{
    if (createTopicServicePortType == null)
      _initCreateTopicServicePortTypeProxy();
    return createTopicServicePortType.autoTriggerTopic(in0);
  }
  
  
}