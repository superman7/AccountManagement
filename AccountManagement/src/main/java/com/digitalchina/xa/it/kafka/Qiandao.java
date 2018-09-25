package com.digitalchina.xa.it.kafka;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.web3j.abi.EventEncoder;
import org.web3j.abi.TypeReference;
import org.web3j.abi.datatypes.Address;
import org.web3j.abi.datatypes.Event;
import org.web3j.abi.datatypes.Function;
import org.web3j.abi.datatypes.Type;
import org.web3j.abi.datatypes.generated.Uint256;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.DefaultBlockParameter;
import org.web3j.protocol.core.RemoteCall;
import org.web3j.protocol.core.methods.request.EthFilter;
import org.web3j.protocol.core.methods.response.Log;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.Contract;
import org.web3j.tx.TransactionManager;
import rx.Observable;
import rx.functions.Func1;

/**
 * <p>Auto generated code.
 * <p><strong>Do not modify!</strong>
 * <p>Please use the <a href="https://docs.web3j.io/command_line.html">web3j command line tools</a>,
 * or the org.web3j.codegen.SolidityFunctionWrapperGenerator in the 
 * <a href="https://github.com/web3j/web3j/tree/master/codegen">codegen module</a> to update.
 *
 * <p>Generated with web3j version 3.4.0.
 */
public class Qiandao extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b60008054600160a060020a033316600160a060020a03199091161790556101ce8061003b6000396000f3006060604052600436106100615763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166341c0e1b58114610063578063713ed42d146100765780638da5cb5b1461007e578063e05b6580146100ad575b005b341561006e57600080fd5b6100616100c3565b6100616100ec565b341561008957600080fd5b6100916100ee565b604051600160a060020a03909116815260200160405180910390f35b34156100b857600080fd5b6100616004356100fd565b60005433600160a060020a039081169116146100de57600080fd5b600054600160a060020a0316ff5b565b600054600160a060020a031681565b600160a060020a033016318190101561011557600080fd5b600160a060020a03331681156108fc0282604051600060405180830381858888f19350505050151561014657600080fd5b7f7afb3c68b224dd93d84787cb9da364f24da7cf53f368de25e36dd1d77f3e9a673382426040518084600160a060020a0316600160a060020a03168152602001838152602001828152602001935050505060405180910390a1505600a165627a7a723058204f92e76c5b83c1194d2888ee362b5f0b3d707a689112bebe7b742ab96d2931a70029";

    public static final String FUNC_KILL = "kill";

    public static final String FUNC_CHARGETOCONTRACT = "chargeToContract";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_QIANDAOREWARD = "qiandaoReward";

    public static final Event QIANDAOINFO_EVENT = new Event("qiandaoInfo", 
            Arrays.<TypeReference<?>>asList(),
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    protected Qiandao(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Qiandao(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> kill() {
        final Function function = new Function(
                FUNC_KILL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<TransactionReceipt> chargeToContract(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_CHARGETOCONTRACT, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<Address> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> qiandaoReward(Uint256 _reward) {
        final Function function = new Function(
                FUNC_QIANDAOREWARD, 
                Arrays.<Type>asList(_reward), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public static RemoteCall<Qiandao> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Qiandao.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Qiandao> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Qiandao.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public List<QiandaoInfoEventResponse> getQiandaoInfoEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(QIANDAOINFO_EVENT, transactionReceipt);
        ArrayList<QiandaoInfoEventResponse> responses = new ArrayList<QiandaoInfoEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            QiandaoInfoEventResponse typedResponse = new QiandaoInfoEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.accountB = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.transferValue = (Uint256) eventValues.getNonIndexedValues().get(1);
            typedResponse.date = (Uint256) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<QiandaoInfoEventResponse> qiandaoInfoEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, QiandaoInfoEventResponse>() {
            @Override
            public QiandaoInfoEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(QIANDAOINFO_EVENT, log);
                QiandaoInfoEventResponse typedResponse = new QiandaoInfoEventResponse();
                typedResponse.log = log;
                typedResponse.accountB = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse.transferValue = (Uint256) eventValues.getNonIndexedValues().get(1);
                typedResponse.date = (Uint256) eventValues.getNonIndexedValues().get(2);
                return typedResponse;
            }
        });
    }

    public Observable<QiandaoInfoEventResponse> qiandaoInfoEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(QIANDAOINFO_EVENT));
        return qiandaoInfoEventObservable(filter);
    }

    public static Qiandao load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Qiandao(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Qiandao load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Qiandao(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class QiandaoInfoEventResponse {
        public Log log;

        public Address accountB;

        public Uint256 transferValue;

        public Uint256 date;
    }
}
