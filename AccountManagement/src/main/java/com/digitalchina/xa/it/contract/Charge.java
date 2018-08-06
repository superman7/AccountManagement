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
public class Charge extends Contract {
    private static final String BINARY = "6060604052341561000f57600080fd5b60008054600160a060020a033316600160a060020a031990911617905561018c8061003b6000396000f3006060604052600436106100565763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166341c0e1b58114610058578063713ed42d1461006b5780638da5cb5b14610073575b005b341561006357600080fd5b6100566100af565b6100566100f2565b341561007e57600080fd5b610086610144565b60405173ffffffffffffffffffffffffffffffffffffffff909116815260200160405180910390f35b6000543373ffffffffffffffffffffffffffffffffffffffff9081169116146100d757600080fd5b60005473ffffffffffffffffffffffffffffffffffffffff16ff5b7fefa1a7f4c86fb40882ad7dae0ca230a4c32ee2a263d06df417762629287725be343360405191825273ffffffffffffffffffffffffffffffffffffffff1660208201526040908101905180910390a1565b60005473ffffffffffffffffffffffffffffffffffffffff16815600a165627a7a7230582075d4a7b2c2d6424f6eee4a5c838b01305049cbc4e6ed59ad5a9d46f5c9484f1d0029";

    public static final String FUNC_KILL = "kill";

    public static final String FUNC_CHARGETOCONTRACT = "chargeToContract";

    public static final String FUNC_OWNER = "owner";

    public static final Event CHARGEINFO_EVENT = new Event("chargeInfo", 
            Arrays.<TypeReference<?>>asList(),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Address>() {}));
    ;

    protected Charge(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected Charge(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
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

    public static RemoteCall<Charge> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Charge.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<Charge> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(Charge.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public List<ChargeInfoEventResponse> getChargeInfoEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CHARGEINFO_EVENT, transactionReceipt);
        ArrayList<ChargeInfoEventResponse> responses = new ArrayList<ChargeInfoEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChargeInfoEventResponse typedResponse = new ChargeInfoEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.chargeValue = (Uint256) eventValues.getNonIndexedValues().get(0);
            typedResponse.account = (Address) eventValues.getNonIndexedValues().get(1);
            responses.add(typedResponse);
        }
        return responses;
    }

    public Observable<ChargeInfoEventResponse> chargeInfoEventObservable(EthFilter filter) {
        return web3j.ethLogObservable(filter).map(new Func1<Log, ChargeInfoEventResponse>() {
            @Override
            public ChargeInfoEventResponse call(Log log) {
                Contract.EventValuesWithLog eventValues = extractEventParametersWithLog(CHARGEINFO_EVENT, log);
                ChargeInfoEventResponse typedResponse = new ChargeInfoEventResponse();
                typedResponse.log = log;
                typedResponse.chargeValue = (Uint256) eventValues.getNonIndexedValues().get(0);
                typedResponse.account = (Address) eventValues.getNonIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public Observable<ChargeInfoEventResponse> chargeInfoEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHARGEINFO_EVENT));
        return chargeInfoEventObservable(filter);
    }

    public static Charge load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new Charge(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static Charge load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new Charge(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class ChargeInfoEventResponse {
        public Log log;

        public Uint256 chargeValue;

        public Address account;
    }
}
