package com.reapal.inchannel.ahzxidentify.service;

import com.reapal.inchannel.model.InchannelIdentifyRequest;
import com.reapal.inchannel.model.InchannelIdentifyResponse;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/3/20.
 */
@Service
public interface AhzxIdentifyService {
    public InchannelIdentifyResponse identify(InchannelIdentifyRequest req);

}
