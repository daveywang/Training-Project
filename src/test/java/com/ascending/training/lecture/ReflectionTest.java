/*
 *  Copyright 2019, Liwei Wang <daveywang@live.com>.
 *  All rights reserved.
 *  Author: Liwei Wang
 *  Date: 04/2019
 */

package com.ascending.training.lecture;

import com.ascending.training.model.Department;
import org.junit.Test;

public class ReflectionTest {
    @Test
    public void ShowObjectInfo() throws Exception {
        Utils.printObjectInfo(new Department());
    }
}
