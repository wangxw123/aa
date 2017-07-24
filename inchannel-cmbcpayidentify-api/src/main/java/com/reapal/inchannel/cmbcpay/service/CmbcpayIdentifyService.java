package com.reapal.inchannel.cmbcpay.service;

import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;
import org.springframework.stereotype.Service;

/**
 * Created by huangg on 2015/2/4.
 */
@Service
public interface CmbcpayIdentifyService {

    public InchannelIdentifyResponse identify(InchannelIdentifyRequest req);
}
