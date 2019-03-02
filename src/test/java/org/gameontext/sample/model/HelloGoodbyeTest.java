/*******************************************************************************
 * Copyright (c) 2018 IBM Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/

package org.gameontext.sample.model;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Room Hello/Goodbye/Join/Part are inbound-only messages from the Mediator
 */
@SpringBootTest
public class HelloGoodbyeTest {

    @Rule
    public TestName testName = new TestName();

    @Before
    public void before() {
        System.out.println(" ===== " + testName.getMethodName());
    }

    final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper

    @Test
    public void testParseRoomHello() throws Exception {
        // roomHello,<roomId>,{
        //     "username": "username",
        //     "userId": "<userId>",
        //     "version": 1|2,
        // }
        // roomJoin,<roomId>,{
        //     "username": "username",
        //     "userId": "<userId>",
        //     "version": 2
        // }
        String s = "{\"username\": \"username\",\"userId\": \"<userId>\",\"version\": 2}";
        RoomHello hello = mapper.readValue(s, RoomHello.class);
        Assert.assertEquals("username", "username", hello.getUsername());
        Assert.assertEquals("userId", "<userId>", hello.getUserId());
        Assert.assertEquals("version", 2, hello.getVersion());
    }

    @Test
    public void testParseRoomGoodbye() throws Exception {
        // roomGoodbye,<roomId>,{
        //     "username": "username",
        //     "userId": "<userId>"
        // }
        // roomPart,<roomId>,{
        //     "username": "username",
        //     "userId": "<userId>"
        // }
        String s = "{\"username\": \"username\",\"userId\": \"<userId>\"}";
        BasicMessage basicMessage = mapper.readValue(s, BasicMessage.class);
        Assert.assertEquals("username", "username", basicMessage.getUsername());
        Assert.assertEquals("userId", "<userId>", basicMessage.getUserId());
    }
}