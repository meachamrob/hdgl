package hdgl.db.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.UnknownHostException;
import java.util.Map;
import java.util.regex.Pattern;

import hdgl.db.conf.RegionConf;
import hdgl.db.server.protocol.ClientRegionProtocol;
import hdgl.util.ParameterHelper;

import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.ipc.RPC;
import org.apache.hadoop.ipc.Server;


public class HGRegion {

	private static final org.apache.commons.logging.Log Log = LogFactory.getLog(HGRegion.class);
	
	RegionServer regionServer;
	Server server;
	Configuration configuration;
	
	public HGRegion(Configuration conf) {
		this.configuration = conf;
	}
	
	public void start() throws IOException{
		String host= RegionConf.getRegionServerHost(configuration);
		int port = RegionConf.getRegionServerPort(configuration);
		Log.info("Starting HGRegion at " + host+":" + port);
		regionServer = new RegionServer(host,port,configuration);
		regionServer.start();
		server = RPC.getServer(ClientRegionProtocol.class, regionServer, host, port, configuration);
		server.start();
	}
	
	public void stop(){
		if(server!=null){
			server.stop();
		}
		if(regionServer!=null){
			regionServer.stop();
		}
	}
	
	public static void main(String[] args) throws IOException {
		HGRegion region = new HGRegion(new Configuration());
		region.start();
	}
	
}
