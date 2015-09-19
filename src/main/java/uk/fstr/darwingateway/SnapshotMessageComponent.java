/**
 * Copyright 2015 Faster Route Limited <hello@fasteroute.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package uk.fstr.darwingateway;

import uk.fstr.darwingateway.bindings.Association;
import uk.fstr.darwingateway.bindings.Schedule;

/** Represents a single piece of a Snapshot Message.Used to break down the otherwise enormous messages
 * to allow for them to be processed piecewise.
 *
 * @author George Goldberg <george@fasteroute.com>
 */
public class SnapshotMessageComponent {
    private final String timestamp;
    private final String version;
    private final Schedule schedule;
    private final Association association;

    public SnapshotMessageComponent(String timestamp, String version, Schedule schedule, Association association) {
        this.timestamp = timestamp;
        this.version = version;
        this.schedule = schedule;
        this.association = association;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getVersion() {
        return version;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Association getAssociation() {
        return association;
    }
}
