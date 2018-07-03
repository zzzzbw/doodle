package com.sample.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zbw
 * @since 2018/6/28 12:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {

    T data;

    int code;

    String msg;
}
