import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnPath;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.NotFoundException;
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class CassandraInsertExample {
	public static final String UTF8 = “UTF8″;

	private static Long hiId = new Long(1);

	public static void main(String[] args) throws UnsupportedEncodingException,
	InvalidRequestException, UnavailableException, TimedOutException,
	TException, NotFoundException {

		CassandraInsertExample cassandraInsertHInfo = new CassandraInsertExample();
		cassandraInsertHInfo.insertHistoricalInfo(hiId);
	}

	public void insertHistoricalInfo(Long hiId) {
		System.out.println(“Stating of class…………………………..”);
		try {

			TTransport tr = new TSocket(“localhost”, 9160);
			TProtocol proto = new TBinaryProtocol(tr);
			Cassandra.Client client = new Cassandra.Client(proto);
			tr.open();


			String keyspace = “Employee”;
			String columnFamily = “Employee_Details”;

			String keyUserID = hiId.toString();

			// insert data
			long timestamp = System.currentTimeMillis();

			ColumnPath colPathhiId = new ColumnPath(columnFamily);
			colPathhiId.setColumn(“hiId”.getBytes(UTF8));
			client.insert(keyspace, keyUserID, colPathhiId, hiId.toString().getBytes(UTF8), timestamp, ConsistencyLevel.ONE);

			//Fetching of single row
			Column col = client.get(keyspace, keyUserID, colPathhiId, ConsistencyLevel.ONE).getColumn();

			System.out.println(“column name: ” + new String(col.name, UTF8));
			System.out.println(“column value: ” + new String(col.value, UTF8));
			System.out.println(“column timestamp: ” + new Date(col.timestamp));

			tr.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (InvalidRequestException e) {
			e.printStackTrace();
		} catch (TTransportException e) {
			e.printStackTrace();
		} catch (UnavailableException e) {
			e.printStackTrace();
		} catch (TimedOutException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}
}
