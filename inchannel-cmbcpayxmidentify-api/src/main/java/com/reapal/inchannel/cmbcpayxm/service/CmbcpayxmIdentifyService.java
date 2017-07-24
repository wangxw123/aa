package com.reapal.inchannel.cmbcpayxm.service;

import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;

/**
 * Created by huangg on 2015/2/4.
 */
public interface CmbcpayxmIdentifyService {

    public InchannelIdentifyResponse identify(InchannelIdentifyRequest req);
}
