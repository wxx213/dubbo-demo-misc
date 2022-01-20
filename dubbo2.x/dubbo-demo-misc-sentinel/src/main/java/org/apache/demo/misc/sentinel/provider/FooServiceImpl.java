/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.demo.misc.sentinel.provider;

import org.apache.demo.misc.sentinel.FooService;
import org.apache.dubbo.config.annotation.Service;
import org.apache.dubbo.rpc.RpcContext;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;

@Service
public class FooServiceImpl implements FooService {

    @Override
    public String sayHello(String name) {
        String ip = "null";
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return String.format("Hello, %s at %s, from %s", name, LocalDateTime.now(), ip);
    }

    @Override
    public String doAnother() {
        return LocalDateTime.now().toString();
    }
}
