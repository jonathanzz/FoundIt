package au.edu.unsw.soacourse.founditrest.dao;

import javax.xml.namespace.QName;

import java.util.Properties;

import com.ddtek.xquery3.XQConnection;
import com.ddtek.xquery3.XQException;
import com.ddtek.xquery3.XQExpression;
import com.ddtek.xquery3.XQItemType;
import com.ddtek.xquery3.XQSequence;
import com.ddtek.xquery3.xqj.DDXQDataSource;

public class DataServiceXquery {
	private String filename;
	private String keyWord;
	private String sortBy;

	private DDXQDataSource dataSource;

	private XQConnection conn;

	public DataServiceXquery(String filename, String keyWord, String sortBy) {
		this.filename = filename;
		this.keyWord = keyWord;
		this.sortBy = sortBy;
	}

	public void init() throws XQException {
		dataSource = new DDXQDataSource();
		conn = dataSource.getConnection();
	}

	public String query(String queryString) throws XQException {
		XQExpression expression = conn.createExpression();
		expression.bindString(new QName("docName"), filename,
				conn.createAtomicType(XQItemType.XQBASETYPE_STRING));
		expression.bindString(new QName("keyWord"), keyWord,
				conn.createAtomicType(XQItemType.XQBASETYPE_STRING));
		XQSequence results = expression.executeQuery(queryString);
		return results.getSequenceAsString(new Properties());
	}

	public String run() {
		String resString = "";
		String queryString = "";
		try {
			init();
			if (sortBy != null && !"".equals(sortBy)) {
				queryString = "xquery version \"1.0\";"
						+ " declare variable $docName as xs:string external; "
						+ " declare variable $keyWord as xs:string external; "
						+ " for $job in doc($docName)//Job "
						+ " where every $p in $job satisfies "
						+ " contains($p, $keyWord) " + " order by $job/"
						+ sortBy + " ascending " + " return " + " $job";
			} else {
				queryString = "xquery version \"1.0\";"
						+ " declare variable $docName as xs:string external; "
						+ " declare variable $keyWord as xs:string external; "
						+ " for $job in doc($docName)//Job "
						+ " where every $p in $job satisfies "
						+ " contains($p, $keyWord) " + " return " + " $job";
			}

			resString = query(queryString);
		} catch (Exception e) {
			e.printStackTrace(System.err);
			System.err.println(e.getMessage());
		}
		return resString;
	}
}