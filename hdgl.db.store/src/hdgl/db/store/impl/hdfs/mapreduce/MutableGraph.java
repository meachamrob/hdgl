package hdgl.db.store.impl.hdfs.mapreduce;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import hdgl.db.conf.GraphConf;
import hdgl.db.graph.Entity;
import hdgl.db.graph.HGraphIds;
import hdgl.db.task.AsyncResult;
import hdgl.util.StringHelper;

import java.io.IOException;

public class MutableGraph implements hdgl.db.graph.MutableGraph {
	
	private FSDataOutputStream outputStream;
	private FileSystem hdfs;
	private long vertex = 0;
	private long edge = 0;
	
	public MutableGraph(Configuration conf, int sessionId)
	{
		try
		{
			hdfs = FileSystem.get(conf);
			Path dfs = new Path(GraphConf.getGraphSessionRoot(conf, sessionId));
			outputStream = hdfs.create(dfs, true);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public void close() 
	{
		try 
		{
			outputStream.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	private long createVertex()
	{
		vertex++;
		StringBuffer line = new StringBuffer("[add vertex ");
		line.append(vertex);
		line.append(":]\n");
		byte[] buff = line.toString().getBytes();
		try
		{
			outputStream.write(buff, 0, buff.length);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return vertex;
	}
	private long createEdge(long vertex1, long vertex2)
	{
		edge--;
		StringBuffer line = new StringBuffer("[add edge ");
		line.append(edge);
		line.append(":");
		line.append(vertex1 + " - " + vertex2);
		line.append("]\n");
		byte[] buff = line.toString().getBytes();
		try
		{
			outputStream.write(buff, 0, buff.length);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return edge;
	}
	private void setVertexLabel(long vertex, String name, byte[] value_b)
	{
		String value = StringHelper.bytesToString(value_b);
		StringBuffer line = new StringBuffer("[add label vertex ");
		line.append(vertex);
		line.append(":" + name + " = ");
		line.append(value);
		line.append("]\n");
		byte[] buff = line.toString().getBytes();
		try
		{
			outputStream.write(buff, 0, buff.length);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	private void setEdgeLabel(long edge, String name, byte[] value_b)
	{
		String value = StringHelper.bytesToString(value_b);
		StringBuffer line = new StringBuffer("[add label edge ");
		line.append(edge);
		line.append(":" + name + " = ");
		line.append(value);
		line.append("]\n");
		byte[] buff = line.toString().getBytes();
		try
		{
			outputStream.write(buff, 0, buff.length);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws Exception
	{
		Configuration conf = new GraphConf().getDefault();
		MutableGraph mg = new MutableGraph(conf, 0);
		mg.close();
	}
	
	@Override
	public AsyncResult<Boolean> commit() {
		return null;
	}
	@Override
	public AsyncResult<Boolean> abort() {
		return null;
	}
	
	@Override
	public long createVertex(String type) {
		long id = createVertex();
		setVertexLabel(id, "type", type.getBytes());
		return id;
	}
	@Override
	public long createEdge(String type, long start, long end) {
		long id=createEdge(start, end);
		setEdgeLabel(id, "type", type.getBytes());
		return id;
	}
	@Override
	public void setLabel(long entity, String name, byte[] value) {
		if(HGraphIds.isEdgeId(entity)){
			setEdgeLabel(entity, name, value);
		}else{
			setVertexLabel(entity, name, value);
		}		
	}
	@Override
	public void deleteEntity(Entity e) {
		
	}
	@Override
	public void deleteLabel(Entity e, String name) {
		
	}
}