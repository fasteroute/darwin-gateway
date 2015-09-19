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

import org.apache.commons.net.PrintCommandListener;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.fstr.darwingateway.bindings.Association;
import uk.fstr.darwingateway.bindings.Pport;
import uk.fstr.darwingateway.bindings.Schedule;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import java.io.*;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.zip.GZIPInputStream;

/** Class that handles fetching snapshot files from the Darwin FTP server and splitting them up
 * into SnapshotMessageComponents for processing and passing on.
 *
 * @author George Goldberg <george@fasteroute.com>
 */
public class SnapshotFetcher implements Runnable {
    private final Logger log = LoggerFactory.getLogger(SnapshotFetcher.class);

    private final String ftpHost;
    private final String ftpUser;
    private final String ftpPass;
    private final BlockingQueue<SnapshotMessageComponent> outputQueue;

    private HashSet<String> processedSnapshots = new HashSet<String>();
    private final JAXBContext jaxbContext;

    public SnapshotFetcher(String ftpHost, String ftpUser, String ftpPass, BlockingQueue outputQueue) {
        this.ftpHost = ftpHost;
        this.ftpUser = ftpUser;
        this.ftpPass = ftpPass;
        this.outputQueue = outputQueue;

        JAXBContext jbc = null;
        try {
            jbc = JAXBContext.newInstance("uk.fstr.darwingateway.bindings");
        } catch (Exception e) {
            log.error("Could not build JAXB context");
        }
        jaxbContext = jbc;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(60000);

                FTPClient client = new FTPClient();

                try {
                    client.addProtocolCommandListener(new PrintCommandListener(new PrintWriter(System.out), true));
                    client.connect(ftpHost);
                    client.login(ftpUser, ftpPass);
                    client.enterLocalPassiveMode();
                    client.setFileType(FTP.BINARY_FILE_TYPE);
                    client.changeWorkingDirectory("snapshot");

                    FTPFile[] files = client.listFiles();
                    for (FTPFile file : files) {
                        if (!processedSnapshots.contains(file.getName())) {
                            log.info("New snapshot found: " + file.getName());
                            if (file.getType() == FTPFile.FILE_TYPE) {
                                InputStream fileStream = client.retrieveFileStream(file.getName());
                                GZIPInputStream gzInStream = new GZIPInputStream(fileStream);
                                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                                Reader reader = new InputStreamReader(gzInStream, "UTF-8");
                                try {
                                    Pport pport = (Pport) unmarshaller.unmarshal(reader);
                                    if (pport != null && pport.getSR() != null && pport.getSR().getSchedule() != null && pport.getSR().getAssociation() != null) {
                                        for (Schedule s : pport.getSR().getSchedule()) {
                                            outputQueue.add(new SnapshotMessageComponent(
                                                    pport.getTs().toString(),
                                                    pport.getVersion(),
                                                    s,
                                                    null));
                                        }
                                        for (Association a : pport.getSR().getAssociation()) {
                                            outputQueue.add(new SnapshotMessageComponent(
                                                    pport.getTs().toString(),
                                                    pport.getVersion(),
                                                    null,
                                                    a
                                            ));
                                        }
                                        processedSnapshots.add(file.getName());
                                    } else {
                                        log.error("Something is null in the snapshot message");
                                    }
                                } finally {
                                    reader.close();
                                }
                            }
                        }

                    }
                    client.logout();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        client.disconnect();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                log.error("An exception occurred.", e);
            }
        }
    }
}
