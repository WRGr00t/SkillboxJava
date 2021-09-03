package com.example.blogapp.service;

import com.example.blogapp.api.response.SettingsResponse;
import com.example.blogapp.entity.GlobalSetting;
import com.example.blogapp.repository.SettingsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SettingsService {
    @Autowired
    private SettingsRepo settingsRepo;

    public SettingsResponse getGlobalSettings() {
        String multiuserMode = "MULTIUSER_MODE";
        String postPremoderation= "POST_PREMODERATION";
        String statisticsIsPublic= "STATISTICS_IS_PUBLIC";
        
        SettingsResponse settingsResponse = new SettingsResponse();
        settingsResponse.setMultiuserMode(getSettingMode(multiuserMode));
        settingsResponse.setPostPremoderation(getSettingMode(postPremoderation));
        settingsResponse.setStatisticIsPublic(getSettingMode(statisticsIsPublic));

        return settingsResponse;
    }

    private boolean getSettingMode(String code) {

        GlobalSetting setting= settingsRepo.findGlobalSettingByCode(code);
        if (setting != null) {
            return setting.getValue().equals("YES");
        } else {
            return false;
        }
    }
}
