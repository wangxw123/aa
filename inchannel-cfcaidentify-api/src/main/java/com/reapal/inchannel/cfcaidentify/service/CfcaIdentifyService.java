package com.reapal.inchannel.cfcaidentify.service;

import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;
import org.springframework.stereotype.Service;


@Service
public interface CfcaIdentifyService {
   
    public InchannelIdentifyResponse identify(InchannelIdentifyRequest req);

    public InchannelIdentifyResponse  realName(InchannelIdentifyRequest req);
}
