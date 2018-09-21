package com.digitalchina.xa.it.contract;

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
public class Lesson extends Contract {
    private static final String BINARY = "606060405260008054600160a060020a033316600160a060020a0319909116179055610129806100306000396000f30060606040526004361060485763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416638da5cb5b8114604d578063d89e8aeb146086575b600080fd5b3415605757600080fd5b605d608e565b60405173ffffffffffffffffffffffffffffffffffffffff909116815260200160405180910390f35b608c60aa565b005b60005473ffffffffffffffffffffffffffffffffffffffff1681565b7f19562e9e04d2d22a4cfee2e05875c98dfff826ca2de42375593e0669db488c64333460405173ffffffffffffffffffffffffffffffffffffffff909216825260208201526040908101905180910390a15600a165627a7a7230582079008d34ba5da7fcd9143e49dde952ac30d043d00226b1364f622458fa7f580f0029";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_BUYCHAPTER = "buyChapter";

    public static final Event BUYCHAPTEREVENT_EVENT = new Event("buyChapterEvent", 
            Arrays.<TypeReference<?>>asList(),
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    protected Lesson(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Lesson(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<Address> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> buyChapter(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_BUYCHAPTER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public static RemoteCall<Lesson> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployRemoteCall(Lesson.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static RemoteCall<Lesson> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployRemoteCall(Lesson.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public List<BuyChapterEventEventResponse> getBuyChapterEventEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(BUYCHAPTEREVENT_EVENT, transactionReceipt);
        ArrayList<BuyChapterEventEventResponse> responses = new ArrayList<BuyChapterEventEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            BuyChapterEventEventResponse typedResponse = new BuyChapterEventEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<BuyChapterEventEventResponse> buyChapterEventEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, BuyChapterEventEventResponse>() {
            @Override
            public BuyChapterEventEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(BUYCHAPTEREVENT_EVENT, log);
                BuyChapterEventEventResponse typedResponse = new BuyChapterEventEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public Observable<BuyChapterEventEventResponse> buyChapterEventEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(BUYCHAPTEREVENT_EVENT));
        return buyChapterEventEventObservable(filter);
    }

    public static Lesson load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Lesson(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Lesson load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Lesson(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class BuyChapterEventEventResponse {
        public Log log;

        public Address sender;

        public Uint256 value;
    }
}
