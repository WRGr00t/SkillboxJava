package com.example.blogapp.repository;

import com.example.blogapp.entity.GlobalSetting;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SettingsRepo extends CrudRepository<GlobalSetting, Integer> {

    GlobalSetting findGlobalSettingByCode(@Param("code") String code);

    Iterable<GlobalSetting> findAll();
}
