package com.zbw.bean;

import com.zbw.core.annotation.Service;

/**
 * @author zbw
 * @since 2018/6/21 17:51
 */
@Service
public class DoodleServiceImpl implements DoodleService{


    @Override
    public String helloWord() {
        return "hello word";
    }
}
