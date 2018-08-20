package com.dc.contract;

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
public class ForTest extends Contract {
    private static final String BINARY = "606060405260018055341561001357600080fd5b60008054600160a060020a033316600160a060020a03199091161790556101ff8061003f6000396000f30060606040526004361061006c5763ffffffff7c01000000000000000000000000000000000000000000000000000000006000350416632f9a110981146100715780633fa4f2451461007b57806355241077146100a0578063713ed42d146100b65780638da5cb5b146100be575b600080fd5b6100796100fa565b005b341561008657600080fd5b61008e610148565b60405190815260200160405180910390f35b34156100ab57600080fd5b61007960043561014e565b61007961017b565b34156100c957600080fd5b6100d16101b7565b60405173ffffffffffffffffffffffffffffffffffffffff909116815260200160405180910390f35b3373ffffffffffffffffffffffffffffffffffffffff166108fc600154670de0b6b3a7640000029081150290604051600060405180830381858888f19350505050151561014657600080fd5b565b60015481565b6000543373ffffffffffffffffffffffffffffffffffffffff90811691161461017657600080fd5b600155565b7f26e541ecf2214797cb7f544b365bcdd71887f3343c0254284659ed0b26bcb2f6423460405191825260208201526040908101905180910390a1565b60005473ffffffffffffffffffffffffffffffffffffffff16815600a165627a7a7230582052f84ea6d19931ba543c5662e7e71c50bd1d2c2bc755784dfdbdf094b01c8af90029";

    public static final String FUNC_REWORDUSER = "rewordUser";

    public static final String FUNC_VALUE = "value";

    public static final String FUNC_SETVALUE = "setValue";

    public static final String FUNC_CHARGETOCONTRACT = "chargeToContract";

    public static final String FUNC_OWNER = "owner";

    public static final Event CHARGEINFO_EVENT = new Event("chargeInfo", 
            Arrays.<TypeReference<?>>asList(),
            Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));
    ;

    protected ForTest(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    protected ForTest(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public RemoteCall<TransactionReceipt> rewordUser(BigInteger weiValue) {
        final Function function = new Function(
                FUNC_REWORDUSER, 
                Arrays.<Type>asList(), 
                Collections.<TypeReference<?>>emptyList());
        return executeRemoteCallTransaction(function, weiValue);
    }

    public RemoteCall<Uint256> value() {
        final Function function = new Function(FUNC_VALUE, 
                Arrays.<Type>asList(), 
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeRemoteCallSingleValueReturn(function);
    }

    public RemoteCall<TransactionReceipt> setValue(Uint256 _value) {
        final Function function = new Function(
                FUNC_SETVALUE, 
                Arrays.<Type>asList(_value), 
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

    public static RemoteCall<ForTest> deploy(Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ForTest.class, web3j, credentials, gasPrice, gasLimit, BINARY, "");
    }

    public static RemoteCall<ForTest> deploy(Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return deployRemoteCall(ForTest.class, web3j, transactionManager, gasPrice, gasLimit, BINARY, "");
    }

    public List<ChargeInfoEventResponse> getChargeInfoEvents(TransactionReceipt transactionReceipt) {
        List<Contract.EventValuesWithLog> valueList = extractEventParametersWithLog(CHARGEINFO_EVENT, transactionReceipt);
        ArrayList<ChargeInfoEventResponse> responses = new ArrayList<ChargeInfoEventResponse>(valueList.size());
        for (Contract.EventValuesWithLog eventValues : valueList) {
            ChargeInfoEventResponse typedResponse = new ChargeInfoEventResponse();
            typedResponse.log = eventValues.getLog();
            typedResponse.time = (Uint256) eventValues.getNonIndexedValues().get(0);
            typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(1);
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
                typedResponse.time = (Uint256) eventValues.getNonIndexedValues().get(0);
                typedResponse.value = (Uint256) eventValues.getNonIndexedValues().get(1);
                return typedResponse;
            }
        });
    }

    public Observable<ChargeInfoEventResponse> chargeInfoEventObservable(DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        EthFilter filter = new EthFilter(startBlock, endBlock, getContractAddress());
        filter.addSingleTopic(EventEncoder.encode(CHARGEINFO_EVENT));
        return chargeInfoEventObservable(filter);
    }

    public static ForTest load(String contractAddress, Web3j web3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new ForTest(contractAddress, web3j, credentials, gasPrice, gasLimit);
    }

    public static ForTest load(String contractAddress, Web3j web3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new ForTest(contractAddress, web3j, transactionManager, gasPrice, gasLimit);
    }

    public static class ChargeInfoEventResponse {
        public Log log;

        public Uint256 time;

        public Uint256 value;
    }
}
