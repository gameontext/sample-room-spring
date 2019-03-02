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

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * What goes in, must come out?
 */
@SpringBootTest
public class ChatMessageTest {

    @Rule
    public TestName testName = new TestName();

    @Before
    public void before() {
        System.out.println(" ===== " + testName.getMethodName());
    }

    final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper

    @Test
    public void testInboundChatMessage() throws Exception {
        // room,<roomId>,{
        //     "username": "username",
        //     "userId": "<userId>",
        //     "content": "<message>"
        // }
        String s = "{\"username\": \"username\",\"userId\": \"<userId>\",\"content\": \"<message>\"}";

        ChatCommandMessage inbound = mapper.readValue(s, ChatCommandMessage.class);
        Assert.assertEquals("username", "username", inbound.getUsername());
        Assert.assertEquals("userId", "<userId>", inbound.getUserId());
        Assert.assertEquals("content", "<message>", inbound.getContent());
    }

    @Test
    public void testOutboundChatMessage() throws Exception {
        // player,*,{...}
        // {
        //   "type": "chat",
        //   "username": "username",
        //   "content": "<message>",
        //   "bookmark": "String representing last message seen"
        // }
        ChatCommandMessage outbound = new ChatCommandMessage();
        outbound.setUsername("username");
        outbound.setUserId("<userId>");
        outbound.setContent("<message>");
        outbound.setBookmark("String representing last message seen");

        String s = mapper
        .writerWithView(Views.Outbound.class)
        .writeValueAsString(outbound);

        System.out.println(s);

        assertThat(s, containsString("\"type\":\"chat\""));
        assertThat(s, containsString("\"username\":\"" + outbound.getUsername() + "\""));
        assertThat(s, containsString("\"bookmark\":\"" + outbound.getBookmark() + "\""));
        assertThat(s, not(containsString("userId")));
    }
}