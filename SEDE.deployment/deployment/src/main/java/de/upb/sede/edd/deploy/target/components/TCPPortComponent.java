package de.upb.sede.edd.deploy.target.components;

import com.fasterxml.jackson.annotation.JsonCreator;
import de.upb.sede.edd.DirLockAlreadyAcquiredException;
import de.upb.sede.edd.LockableDir;
import de.upb.sede.util.SystemSettingLookup;
import de.upb.sede.edd.deploy.DeploymentException;
import de.upb.sede.util.FileUtil;
import de.upb.sede.util.DynTypeObject;
import de.upb.sede.util.MutableOptionalField;
import de.upb.sede.util.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class TCPPortComponent extends TargetComponent{

    private final static Logger logger = LoggerFactory.getLogger(TCPPortComponent.class);

    private LockableDir portsDir;

    private MutableOptionalField<Port> allocatedTcpPort = MutableOptionalField.empty();

    public static final SystemSettingLookup DEFAULT_PORT_RANGE =
        new SystemSettingLookup("9090-19090", "de.upb.sede.edd.tcpPortRange",
                    "EDD_TCP_PORT_RANGE");

    public TCPPortComponent(String displayName, LockableDir portsDir) {
        this.portsDir = Objects.requireNonNull(portsDir);
        this.setDisplayName(displayName);
    }

    public synchronized boolean allocatePort() {
        logger.info("{}: allocating a free port.", getDisplayName());
        try(AutoCloseable portsLock = portsDir.lockDir(false)) {
            Iterable<Port> ports;
            Optional<PortMappings> portMappings = readPortMappings();
            if(portMappings.isPresent()) {
                logger.info("{}: reading from port mapping file: {}", getDisplayName(),  getPortMappingFile());
                ports = portMappings.get().ports;
            } else  {
                logger.info("{}: using default port range: {}", getDisplayName(),  DEFAULT_PORT_RANGE.lookup());
                ports = defaultPortRangeIterator();
            }
            for(Port port : ports) {
                if(WebUtil.isFreePort(port.localPort)) {
                    logger.info("{}: found free port: {}:{}", getDisplayName(), port.localPort, port.advertisedPort);
                    allocatedTcpPort.set(port);
                    return true;
                }
            }
        } catch (DirLockAlreadyAcquiredException e) {
            logger.error("{}: cannot lock port mapping dir {}.", getDisplayName(), portsDir.toFile().toString(), e);
        } catch (Exception e) {
            logger.error("{}: error trying to allocate a free port.", getDisplayName(), e);
        }
        return false;
    }

    public synchronized Optional<Port> getAllocatedTcpPort() {
        return allocatedTcpPort.opt();
    }

    public synchronized void deallocatePort() {
        allocatedTcpPort.unset();
    }

    private File getPortMappingFile() {
        return new File(portsDir.toFile(), "PortMappings.json");
    }

    private Optional<PortMappings> readPortMappings() {
        File portMappingsFile  =  getPortMappingFile();
        if(! portMappingsFile.exists()) {
            return Optional.empty();
        }
        PortMappings mappings;
        try {
            String mappingsString = FileUtil.readFileAsString(portMappingsFile.getPath());
            mappings = DynTypeObject.fromJson(mappingsString).cast(PortMappings.class);
        } catch(Exception ex) {
            logger.error("{}: error trying to read port mappings: ", getDisplayName(), ex);
            return Optional.empty();
        }
        return Optional.of(mappings);
    }

    private Iterable<Port> defaultPortRangeIterator() {
        final int portStart, portEnd;
        String portRange = DEFAULT_PORT_RANGE.lookup();
        try {
            if (portRange.contains("-")) {
                String[] portRangeArr = portRange.split("-");
                if (portRangeArr.length != 2) {
                    throw new IllegalArgumentException();
                }
                portStart = Integer.parseInt(portRangeArr[0]);
                portEnd = Integer.parseInt(portRangeArr[1]);
                if(portStart < 100|| portEnd <= portStart || portEnd > 65535) {
                    throw new IllegalArgumentException("Bad port range.");
                }
            } else {
                throw new IllegalArgumentException("No '-'");
            }
        } catch (IllegalArgumentException  ex) {
            throw new DeploymentException(getDisplayName() + ": port range has bad format: " + portRange, ex);
        }
        return () -> new Iterator<Port>(){
            int currentPort = portStart;
            @Override
            public boolean hasNext() {
                return (currentPort < portEnd);
            }

            @Override
            public Port next() {
                int tmp = currentPort;
                currentPort++;
                return new Port(tmp);
            }
        };
    }

    public static class Port {
        public final int localPort, advertisedPort;

        @JsonCreator
        public Port(int localPort, int advertisedPort) {
            this.localPort = localPort;
            this.advertisedPort = advertisedPort;
        }

        public Port(int localPort) {
            this(localPort, localPort);
        }
    }

    public static class PortMappings  {
        private List<Port> ports = new ArrayList<>();

        public List<Port> getPorts() {
            return ports;
        }

        public void setPorts(List<Object> ports) {
            this.ports = ports.stream().map(port -> {
                if(port instanceof Number) {
                    return new Port(((Number) port).intValue());
                } else if(port instanceof List){
                    List<Object> portMappingList = (List<Object>) port;
                    if(portMappingList.size() != 2 ||
                        (!(portMappingList.get(0) instanceof Number)) ||
                        (!(portMappingList.get(1) instanceof Number))) {
                        throw new RuntimeException("Port mapping not recognized: " + port.toString());
                    }
                    return new Port(((Number) portMappingList.get(0)).intValue(), ((Number) portMappingList.get(1)).intValue());
                } else if(port instanceof Map) {
                    Map<String, Object> portMapping = (Map<String, Object>) port;
                    DynTypeObject object = new DynTypeObject(portMapping);
                    return object.cast(Port.class);
                } else {
                    throw new RuntimeException("Port mapping not recognized: " + port.toString());
                }
            }).collect(Collectors.toList());
        }
    }
}
