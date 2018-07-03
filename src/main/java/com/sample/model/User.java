package com.sample.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zbw
 * @since 2018/6/28 11:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private long id;

    private String name;
}
