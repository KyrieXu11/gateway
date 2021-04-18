package com.kyriexu.service;

import com.kyriexu.dto.PanelGroupDataOutput;
import com.kyriexu.dto.ServiceStatOut;

import java.util.Map;

/**
 * @author KyrieXu
 * @since 2021/4/10 22:09
 **/
public interface StatService {

    PanelGroupDataOutput getRemotePanelGroupData(boolean isConsul);

    <V> ServiceStatOut getRemoteServiceStat(Map<String,V> params);
}
