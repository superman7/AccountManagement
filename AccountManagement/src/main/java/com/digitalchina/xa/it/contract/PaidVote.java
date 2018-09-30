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
public class PaidVote extends Contract {
    private static final String BINARY = "606060405260008054600160a060020a033316600160a060020a031990911617905561016a806100306000396000f3006060604052600436106100565763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166304304a59811461005b57806341c0e1b5146100715780638da5cb5b14610084575b600080fd5b61006f600160a060020a03600435166100b3565b005b341561007c57600080fd5b61006f610106565b341561008f57600080fd5b61009761012f565b604051600160a060020a03909116815260200160405180910390f35b7f33c5e5ec216dec2b56810667f1f21d091f1122648d48add789b691819d621d1e338234604051600160a060020a039384168152919092166020820152604080820192909252606001905180910390a150565b60005433600160a060020a0390811691161461012157600080fd5b600054600160a060020a0316ff5b600054600160a060020a0316815600a165627a7a72305820b74495af0ba5be204d1e5bcef287538790dacdadae779358a0a0021d5ce8289d0029";

    public static final String FUNC_BUYCHAPTER = "buyChapter";

    public static final String FUNC_KILL = "kill";

    public static final String FUNC_OWNER = "owner";

    public static final Event PAIDVOTERECORD_EVENT = new Event("paidVoteRecord", 
            Arrays.<TypeReference<?>>asList(),
            Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}, new TypeReference<Address>() {}, new TypeReference<Uint256>() {}));
    ;

    protected PaidVote(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected PaidVote(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> buyChapter(Address _beVoted, BigInteger weiValue) {
        final Function function = new Function(
                FUNC_BUYCHAPTER, 
                Arrays.<Type>asList(_beVoted), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<TransactionReceipt> kill() {
        final Function function = new Function(
                FUNC_KILL, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function);
    }

    public RemoteCall<Address> owner() {
        final Function function = new Function(FUNC_OWNER, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Address>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public static RemoteCall<PaidVote> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployRemoteCall(PaidVote.class, web3j, credentials, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public static RemoteCall<PaidVote> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialWeiValue) {
        return deployRemoteCall(PaidVote.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialWeiValue);
    }

    public List<PaidVoteRecordEventResponse> getPaidVoteRecordEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(PAIDVOTERECORD_EVENT, transactionReceipt);
        ArrayList<PaidVoteRecordEventResponse> responses = new ArrayList<PaidVoteRecordEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            PaidVoteRecordEventResponse typedResponse = new PaidVoteRecordEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.sender = (Address) eventValues.getNonIndexedValues().get(0);
            typedResponse.beVoted = (Address) eventValues.getNonIndexedValues().get(1);
            typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(2);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<PaidVoteRecordEventResponse> paidVoteRecordEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, PaidVoteRecordEventResponse>() {
            @Override
            public PaidVoteRecordEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(PAIDVOTERECORD_EVENT, log);
                PaidVoteRecordEventResponse typedResponse = new PaidVoteRecordEventResponse();
                typedResponse.log = log;
                typedResponse.sender = (Address) eventValues.getNonIndexedValues().get(0);
                typedResponse.beVoted = (Address) eventValues.getNonIndexedValues().get(1);
                typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(2);
                return typedResponse;
            }
        });
    }

    public Observable<PaidVoteRecordEventResponse> paidVoteRecordEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(PAIDVOTERECORD_EVENT));
        return paidVoteRecordEventObservable(filter);
    }

    public static PaidVote load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new PaidVote(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static PaidVote load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new PaidVote(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class PaidVoteRecordEventResponse {
        public Log log;

        public Address sender;

        public Address beVoted;

        public Uint256 value;
    }
}
