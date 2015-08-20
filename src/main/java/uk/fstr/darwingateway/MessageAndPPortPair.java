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

import uk.fstr.darwingateway.bindings.Pport;

import javax.jms.Message;

/** Pair incorporating a Message and a PPort object for passing around together in queues.
 *
 * @author George Goldberg <george@fasteroute.com>
 */
public class MessageAndPPortPair {
    public final Message message;
    public final Pport pport;

    public MessageAndPPortPair(Message message, Pport pport) {
        this.message = message;
        this.pport = pport;
    }
}
