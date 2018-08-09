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
public class Transfer extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b60008054600160a060020a033316600160a060020a03199091161790556101ac8061003b6000396000f3006060604052600436106100615763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166341c0e1b58114610063578063713ed42d146100765780638da5cb5b1461007e578063bb765ca7146100ad575b005b341561006e57600080fd5b6100616100c1565b6100616100ea565b341561008957600080fd5b6100916100ec565b604051600160a060020a03909116815260200160405180910390f35b610061600160a060020a03600435166100fb565b60005433600160a060020a039081169116146100dc57600080fd5b600054600160a060020a0316ff5b565b600054600160a060020a031681565b600160a060020a0381163480156108fc0290604051600060405180830381858888f19350505050151561012d57600080fd5b7ffdb3653d4e3c31d914950c02530d1b181d34c3e41c2cb93bddfb4c6aae224058338234604051600160a060020a039384168152919092166020820152604080820192909252606001905180910390a1505600a165627a7a72305820404cb8511d722321568a390d02aea2b24f7181274d2a893a1a563a5e582d7b050029";

    public static final String FUNC_KILL = "kill";

    public static final String FUNC_CHARGETOCONTRACT = "chargeToContract";

    public static final String FUNC_OWNER = "owner";

    public static final String FUNC_TRANSFERATOB = "transferAToB";

    public static final Event TRANSFERINFO_EVENT = new Event("transferInfo", 
            Arrays.<TypeReference<?>>asList(),
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    protected Transfer(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Transfer(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
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

    public RemoteCall<TransactionReceipt> transferAToB(Address _accountB, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_TRANSFERATOB, 
                Arrays.<Type>asList(_accountB), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public static RemoteCall<Transfer> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Transfer.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Transfer> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Transfer.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public List<TransferInfoEventResponse> getTransferInfoEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(TRANSFERINFO_EVENT, transactionReceipt);
        ArrayList<TransferInfoEventResponse> responses = new ArrayList<TransferInfoEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            TransferInfoEventResponse typedResponse = new TransferInfoEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.accountA = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.accountB = (Address) eventValues.getNonIndexedValues().get(1);
            typedResponse.transferValue = (Uint256) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<TransferInfoEventResponse> transferInfoEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, TransferInfoEventResponse>() {
            @Override
            public TransferInfoEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(TRANSFERINFO_EVENT, log);
                TransferInfoEventResponse typedResponse = new TransferInfoEventResponse();
                typedResponse.log = log;
                typedResponse.accountA = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse.accountB = (Address) eventValues.getNonIndexedValues().get(1);
                typedResponse.transferValue = (Uint256) eventValues.getNonIndexedValues().get(2);
                return typedResponse;
            }
        });
    }

    public Observable<TransferInfoEventResponse> transferInfoEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(TRANSFERINFO_EVENT));
        return transferInfoEventObservable(filter);
    }

    public static Transfer load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Transfer(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Transfer load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Transfer(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class TransferInfoEventResponse {
        public Log log;

        public Address accountA;

        public Address accountB;

        public Uint256 transferValue;
    }
}
