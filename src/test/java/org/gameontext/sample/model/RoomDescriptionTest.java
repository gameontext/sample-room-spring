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
public class RoomDescriptionTest {

    @Rule
    public TestName testName = new TestName();

    @Before
    public void before() {
        System.out.println(" ===== " + testName.getMethodName());
    }

    final ObjectMapper mapper = new ObjectMapper(); // jackson's objectmapper

    @Test
    public void testCreateLocationMessageMinimal() throws Exception {
        RoomDescription roomDescription = new RoomDescription();

        String s = mapper.writeValueAsString(roomDescription);
        System.out.println(s);

        assertThat(s, containsString("\"type\":\"location\""));
        assertThat(s, containsString("\"name\":\"" + roomDescription.getName() + "\""));
        assertThat(s, containsString("\"fullName\":\"" + roomDescription.getFullName() + "\""));
        assertThat(s, containsString("\"description\":\"" + roomDescription.getDescription() + "\""));

        RoomDescription d2 = mapper.readValue(s, RoomDescription.class);
        Assert.assertEquals(roomDescription.toString(), d2.toString());
    }

    @Test
    public void testCreateLocationMessageFailNullName() throws Exception {
        RoomDescription roomDescription = new RoomDescription();
        roomDescription.setName(null);
        roomDescription.setFullName("b");
        roomDescription.setDescription("c");

        String s = mapper.writeValueAsString(roomDescription);
        System.out.println(s);

        assertThat(s, containsString("\"type\":\"location\""));
        assertThat(s, containsString("\"name\":\"" + roomDescription.getName() + "\""));
        assertThat(s, containsString("\"fullName\":\"b\""));
        assertThat(s, containsString("\"description\":\"c\""));

        RoomDescription d2 = mapper.readValue(s, RoomDescription.class);
        Assert.assertEquals(roomDescription.toString(), d2.toString());
    }

    @Test
    public void testCreateLocationMessageFailNullFullName() throws Exception {
        RoomDescription roomDescription = new RoomDescription();
        roomDescription.setName("a");
        roomDescription.setFullName(null);
        roomDescription.setDescription("c");

        String s = mapper.writeValueAsString(roomDescription);
        System.out.println(s);

        assertThat(s, containsString("\"type\":\"location\""));
        assertThat(s, containsString("\"name\":\"a\""));
        assertThat(s, containsString("\"fullName\":\"" + roomDescription.getFullName() + "\""));
        assertThat(s, containsString("\"description\":\"c\""));

        RoomDescription d2 = mapper.readValue(s, RoomDescription.class);
        Assert.assertEquals(roomDescription.toString(), d2.toString());
    }

    @Test
    public void testCreateLocationMessageFailNullDescription() throws Exception {
        RoomDescription roomDescription = new RoomDescription();
        roomDescription.setName("a");
        roomDescription.setFullName("b");
        roomDescription.setDescription(null);

        String s = mapper.writeValueAsString(roomDescription);
        System.out.println(s);

        assertThat(s, containsString("\"type\":\"location\""));
        assertThat(s, containsString("\"name\":\"a\""));
        assertThat(s, containsString("\"fullName\":\"b\""));
        assertThat(s, containsString("\"description\":\"" + roomDescription.getDescription() + "\""));

        RoomDescription d2 = mapper.readValue(s, RoomDescription.class);
        Assert.assertEquals(roomDescription.toString(), d2.toString());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCreateLocationMessageBadCommands() throws Exception {
        RoomDescription roomDescription = new RoomDescription();
        roomDescription.addCommand("/command", null);
    }

    @Test
    public void testCreateLocationMessageCommands() throws Exception {
        RoomDescription roomDescription = new RoomDescription();
        roomDescription.addCommand("/command", "description");

        String s = mapper.writeValueAsString(roomDescription);
        System.out.println(s);

        assertThat(s, containsString("\"type\":\"location\""));
        assertThat(s, containsString("\"name\":\"" + roomDescription.getName() + "\""));
        assertThat(s, containsString("\"fullName\":\"" + roomDescription.getFullName() + "\""));
        assertThat(s, containsString("\"description\":\"" + roomDescription.getDescription() + "\""));
        assertThat(s, containsString("\"commands\":{\"/command\":\"description\"}"));

        RoomDescription d2 = mapper.readValue(s, RoomDescription.class);
        Assert.assertEquals(roomDescription.toString(), d2.toString());
    }


    @Test
    public void testCreateLocationMessageInventory() throws Exception {
        RoomDescription roomDescription = new RoomDescription();
        roomDescription.addItem("Squashy Chair");

        String s = mapper.writeValueAsString(roomDescription);
        System.out.println(s);

        assertThat(s, containsString("\"type\":\"location\""));
        assertThat(s, containsString("\"name\":\"" + roomDescription.getName() + "\""));
        assertThat(s, containsString("\"fullName\":\"" + roomDescription.getFullName() + "\""));
        assertThat(s, containsString("\"description\":\"" + roomDescription.getDescription() + "\""));
        assertThat(s, containsString("\"roomInventory\":[\"Squashy Chair\"]"));

        RoomDescription d2 = mapper.readValue(s, RoomDescription.class);
        Assert.assertEquals(roomDescription.toString(), d2.toString());
    }
}
