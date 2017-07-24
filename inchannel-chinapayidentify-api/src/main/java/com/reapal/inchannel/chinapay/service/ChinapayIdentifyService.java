package com.reapal.inchannel.chinapay.service;

import com.reapal.inchannel.chinapay.model.ChinapayIdentifyRequest;
import com.reapal.inchannel.chinapay.model.ChinapayIdentifyResponse;
import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;
import org.springframework.stereotype.Service;
/**
 * Created by wanghao on 2016/12/11.
 */
@Service
public interface ChinapayIdentifyService {
   
    public InchannelIdentifyResponse identify(InchannelIdentifyRequest req);
}
