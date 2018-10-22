/**
 * CreateTopicServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package cn.com.weaver.workflow.webservices;

public class CreateTopicServiceLocator extends org.apache.axis.client.Service implements cn.com.weaver.workflow.webservices.CreateTopicService {

    public CreateTopicServiceLocator() {
    }


    public CreateTopicServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CreateTopicServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CreateTopicServiceHttpPort
    private java.lang.String CreateTopicServiceHttpPort_address = "http://10.0.20.51/services/CreateTopicService";

    public java.lang.String getCreateTopicServiceHttpPortAddress() {
        return CreateTopicServiceHttpPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CreateTopicServiceHttpPortWSDDServiceName = "CreateTopicServiceHttpPort";

    public java.lang.String getCreateTopicServiceHttpPortWSDDServiceName() {
        return CreateTopicServiceHttpPortWSDDServiceName;
    }

    public void setCreateTopicServiceHttpPortWSDDServiceName(java.lang.String name) {
        CreateTopicServiceHttpPortWSDDServiceName = name;
    }

    public cn.com.weaver.workflow.webservices.CreateTopicServicePortType getCreateTopicServiceHttpPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CreateTopicServiceHttpPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCreateTopicServiceHttpPort(endpoint);
    }

    public cn.com.weaver.workflow.webservices.CreateTopicServicePortType getCreateTopicServiceHttpPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            cn.com.weaver.workflow.webservices.CreateTopicServiceHttpBindingStub _stub = new cn.com.weaver.workflow.webservices.CreateTopicServiceHttpBindingStub(portAddress, this);
            _stub.setPortName(getCreateTopicServiceHttpPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCreateTopicServiceHttpPortEndpointAddress(java.lang.String address) {
        CreateTopicServiceHttpPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (cn.com.weaver.workflow.webservices.CreateTopicServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                cn.com.weaver.workflow.webservices.CreateTopicServiceHttpBindingStub _stub = new cn.com.weaver.workflow.webservices.CreateTopicServiceHttpBindingStub(new java.net.URL(CreateTopicServiceHttpPort_address), this);
                _stub.setPortName(getCreateTopicServiceHttpPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CreateTopicServiceHttpPort".equals(inputPortName)) {
            return getCreateTopicServiceHttpPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("webservices.workflow.weaver.com.cn", "CreateTopicService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("webservices.workflow.weaver.com.cn", "CreateTopicServiceHttpPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("CreateTopicServiceHttpPort".equals(portName)) {
            setCreateTopicServiceHttpPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
