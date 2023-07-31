package it.dercole.prototypes.game_login_soap.client;

import org.springframework.stereotype.Component;

@Component
public class SumClient {

//	private static final Logger LOGGER = LoggerFactory.getLogger(Sum.class);
//
//	@Autowired
//	private WebServiceTemplate webServiceTemplate;
//	
//	public int sum(int arg0, int arg1) {
//		
//		ObjectFactory factory = new ObjectFactory();
//		Sum sum = factory.createSum();
//
//		sum.setArg0(arg0);
//		sum.setArg1(arg1);
//
//		LOGGER.info("**** THE 'DefaultUri' IS: {}", webServiceTemplate.getDefaultUri());
//		
//		LOGGER.info("**** CLIENT SENDING 'sum(arg0={},arg1={})'", sum.getArg0(), sum.getArg1());
//
//		SumResponse sumResponse = (SumResponse) webServiceTemplate.marshalSendAndReceive(sum);
//
//		LOGGER.info("**** THE CLIENT RECEIVED THE RESULT OF THE 'sum()' OPERATION = '{}'", sumResponse.getReturn());
//		
//		return sumResponse.getReturn();
//	}
}
