import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnOrSuperColumn;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.KeyRange;
import org.apache.cassandra.thrift.KeySlice;
import org.apache.cassandra.thrift.NotFoundException;
import org.apache.cassandra.thrift.SlicePredicate;
import org.apache.cassandra.thrift.SliceRange;
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class CassandraFeatchHInfo {
	public static final String UTF8 = “UTF8″;

	public static void main(String[] args) throws UnsupportedEncodingException,
	InvalidRequestException, UnavailableException, TimedOutException,
	TException, NotFoundException {

		TTransport tr = new TSocket(“192.168.1.204″, 9160);
		TProtocol proto = new TBinaryProtocol(tr);
		Cassandra.Client client = new Cassandra.Client(proto);

		tr.open();

		String keyspace = “Historical_Info”;
		String columnFamily = “Historical_Info_Column”;
		//String keyUserID = “3”;

		// read entire row
		SlicePredicate predicate = new SlicePredicate();
		SliceRange sliceRange = new SliceRange();
		sliceRange.setStart(new byte[0]);
		sliceRange.setFinish(new byte[0]);
		predicate.setSlice_range(sliceRange);

		KeyRange keyrRange = new KeyRange();
		keyrRange.setStart_key(“1″);
		keyrRange.setEnd_key(“”);
		//keyrRange.setCount(100);

		ColumnParent parent = new ColumnParent(columnFamily);

		List < KeySlice > ls = client.get_range_slices(keyspace, parent, predicate, keyrRange, ConsistencyLevel.ONE);

		for (KeySlice result: ls) {
			List < ColumnOrSuperColumn > column = result.columns;
			for (ColumnOrSuperColumn result2: column) {
				Column column2 = result2.column;
				System.out.println(new String(column2.name, UTF8) + ” - > ” + new String(column2.value, UTF8));
			}
		}

		tr.close();
	}
}