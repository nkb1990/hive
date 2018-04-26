/**
 * Autogenerated by Thrift Compiler (0.9.3)
 *
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *  @generated
 */
package org.apache.hadoop.hive.metastore.api;

import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;

import org.apache.thrift.scheme.TupleScheme;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.protocol.TProtocolException;
import org.apache.thrift.EncodingUtils;
import org.apache.thrift.TException;
import org.apache.thrift.async.AsyncMethodCallback;
import org.apache.thrift.server.AbstractNonblockingServer.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.EnumMap;
import java.util.Set;
import java.util.HashSet;
import java.util.EnumSet;
import java.util.Collections;
import java.util.BitSet;
import java.nio.ByteBuffer;
import java.util.Arrays;
import javax.annotation.Generated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)")
@org.apache.hadoop.classification.InterfaceAudience.Public @org.apache.hadoop.classification.InterfaceStability.Stable public class AllocateTableWriteIdsRequest implements org.apache.thrift.TBase<AllocateTableWriteIdsRequest, AllocateTableWriteIdsRequest._Fields>, java.io.Serializable, Cloneable, Comparable<AllocateTableWriteIdsRequest> {
  private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("AllocateTableWriteIdsRequest");

  private static final org.apache.thrift.protocol.TField DB_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("dbName", org.apache.thrift.protocol.TType.STRING, (short)1);
  private static final org.apache.thrift.protocol.TField TABLE_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("tableName", org.apache.thrift.protocol.TType.STRING, (short)2);
  private static final org.apache.thrift.protocol.TField TXN_IDS_FIELD_DESC = new org.apache.thrift.protocol.TField("txnIds", org.apache.thrift.protocol.TType.LIST, (short)3);
  private static final org.apache.thrift.protocol.TField REPL_POLICY_FIELD_DESC = new org.apache.thrift.protocol.TField("replPolicy", org.apache.thrift.protocol.TType.STRING, (short)4);
  private static final org.apache.thrift.protocol.TField SRC_TXN_TO_WRITE_ID_LIST_FIELD_DESC = new org.apache.thrift.protocol.TField("srcTxnToWriteIdList", org.apache.thrift.protocol.TType.LIST, (short)5);
  private static final org.apache.thrift.protocol.TField CAT_NAME_FIELD_DESC = new org.apache.thrift.protocol.TField("catName", org.apache.thrift.protocol.TType.STRING, (short)6);

  private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
  static {
    schemes.put(StandardScheme.class, new AllocateTableWriteIdsRequestStandardSchemeFactory());
    schemes.put(TupleScheme.class, new AllocateTableWriteIdsRequestTupleSchemeFactory());
  }

  private String dbName; // required
  private String tableName; // required
  private List<Long> txnIds; // optional
  private String replPolicy; // optional
  private List<TxnToWriteId> srcTxnToWriteIdList; // optional
  private String catName; // optional

  /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
  public enum _Fields implements org.apache.thrift.TFieldIdEnum {
    DB_NAME((short)1, "dbName"),
    TABLE_NAME((short)2, "tableName"),
    TXN_IDS((short)3, "txnIds"),
    REPL_POLICY((short)4, "replPolicy"),
    SRC_TXN_TO_WRITE_ID_LIST((short)5, "srcTxnToWriteIdList"),
    CAT_NAME((short)6, "catName");

    private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

    static {
      for (_Fields field : EnumSet.allOf(_Fields.class)) {
        byName.put(field.getFieldName(), field);
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, or null if its not found.
     */
    public static _Fields findByThriftId(int fieldId) {
      switch(fieldId) {
        case 1: // DB_NAME
          return DB_NAME;
        case 2: // TABLE_NAME
          return TABLE_NAME;
        case 3: // TXN_IDS
          return TXN_IDS;
        case 4: // REPL_POLICY
          return REPL_POLICY;
        case 5: // SRC_TXN_TO_WRITE_ID_LIST
          return SRC_TXN_TO_WRITE_ID_LIST;
        case 6: // CAT_NAME
          return CAT_NAME;
        default:
          return null;
      }
    }

    /**
     * Find the _Fields constant that matches fieldId, throwing an exception
     * if it is not found.
     */
    public static _Fields findByThriftIdOrThrow(int fieldId) {
      _Fields fields = findByThriftId(fieldId);
      if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
      return fields;
    }

    /**
     * Find the _Fields constant that matches name, or null if its not found.
     */
    public static _Fields findByName(String name) {
      return byName.get(name);
    }

    private final short _thriftId;
    private final String _fieldName;

    _Fields(short thriftId, String fieldName) {
      _thriftId = thriftId;
      _fieldName = fieldName;
    }

    public short getThriftFieldId() {
      return _thriftId;
    }

    public String getFieldName() {
      return _fieldName;
    }
  }

  // isset id assignments
  private static final _Fields optionals[] = {_Fields.TXN_IDS,_Fields.REPL_POLICY,_Fields.SRC_TXN_TO_WRITE_ID_LIST,_Fields.CAT_NAME};
  public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
  static {
    Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
    tmpMap.put(_Fields.DB_NAME, new org.apache.thrift.meta_data.FieldMetaData("dbName", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TABLE_NAME, new org.apache.thrift.meta_data.FieldMetaData("tableName", org.apache.thrift.TFieldRequirementType.REQUIRED, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.TXN_IDS, new org.apache.thrift.meta_data.FieldMetaData("txnIds", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I64))));
    tmpMap.put(_Fields.REPL_POLICY, new org.apache.thrift.meta_data.FieldMetaData("replPolicy", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    tmpMap.put(_Fields.SRC_TXN_TO_WRITE_ID_LIST, new org.apache.thrift.meta_data.FieldMetaData("srcTxnToWriteIdList", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.ListMetaData(org.apache.thrift.protocol.TType.LIST, 
            new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRUCT            , "TxnToWriteId"))));
    tmpMap.put(_Fields.CAT_NAME, new org.apache.thrift.meta_data.FieldMetaData("catName", org.apache.thrift.TFieldRequirementType.OPTIONAL, 
        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
    metaDataMap = Collections.unmodifiableMap(tmpMap);
    org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(AllocateTableWriteIdsRequest.class, metaDataMap);
  }

  public AllocateTableWriteIdsRequest() {
  }

  public AllocateTableWriteIdsRequest(
    String dbName,
    String tableName)
  {
    this();
    this.dbName = dbName;
    this.tableName = tableName;
  }

  /**
   * Performs a deep copy on <i>other</i>.
   */
  public AllocateTableWriteIdsRequest(AllocateTableWriteIdsRequest other) {
    if (other.isSetDbName()) {
      this.dbName = other.dbName;
    }
    if (other.isSetTableName()) {
      this.tableName = other.tableName;
    }
    if (other.isSetTxnIds()) {
      List<Long> __this__txnIds = new ArrayList<Long>(other.txnIds);
      this.txnIds = __this__txnIds;
    }
    if (other.isSetReplPolicy()) {
      this.replPolicy = other.replPolicy;
    }
    if (other.isSetSrcTxnToWriteIdList()) {
      List<TxnToWriteId> __this__srcTxnToWriteIdList = new ArrayList<TxnToWriteId>(other.srcTxnToWriteIdList.size());
      for (TxnToWriteId other_element : other.srcTxnToWriteIdList) {
        __this__srcTxnToWriteIdList.add(other_element);
      }
      this.srcTxnToWriteIdList = __this__srcTxnToWriteIdList;
    }
    if (other.isSetCatName()) {
      this.catName = other.catName;
    }
  }

  public AllocateTableWriteIdsRequest deepCopy() {
    return new AllocateTableWriteIdsRequest(this);
  }

  @Override
  public void clear() {
    this.dbName = null;
    this.tableName = null;
    this.txnIds = null;
    this.replPolicy = null;
    this.srcTxnToWriteIdList = null;
    this.catName = null;
  }

  public String getDbName() {
    return this.dbName;
  }

  public void setDbName(String dbName) {
    this.dbName = dbName;
  }

  public void unsetDbName() {
    this.dbName = null;
  }

  /** Returns true if field dbName is set (has been assigned a value) and false otherwise */
  public boolean isSetDbName() {
    return this.dbName != null;
  }

  public void setDbNameIsSet(boolean value) {
    if (!value) {
      this.dbName = null;
    }
  }

  public String getTableName() {
    return this.tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public void unsetTableName() {
    this.tableName = null;
  }

  /** Returns true if field tableName is set (has been assigned a value) and false otherwise */
  public boolean isSetTableName() {
    return this.tableName != null;
  }

  public void setTableNameIsSet(boolean value) {
    if (!value) {
      this.tableName = null;
    }
  }

  public int getTxnIdsSize() {
    return (this.txnIds == null) ? 0 : this.txnIds.size();
  }

  public java.util.Iterator<Long> getTxnIdsIterator() {
    return (this.txnIds == null) ? null : this.txnIds.iterator();
  }

  public void addToTxnIds(long elem) {
    if (this.txnIds == null) {
      this.txnIds = new ArrayList<Long>();
    }
    this.txnIds.add(elem);
  }

  public List<Long> getTxnIds() {
    return this.txnIds;
  }

  public void setTxnIds(List<Long> txnIds) {
    this.txnIds = txnIds;
  }

  public void unsetTxnIds() {
    this.txnIds = null;
  }

  /** Returns true if field txnIds is set (has been assigned a value) and false otherwise */
  public boolean isSetTxnIds() {
    return this.txnIds != null;
  }

  public void setTxnIdsIsSet(boolean value) {
    if (!value) {
      this.txnIds = null;
    }
  }

  public String getReplPolicy() {
    return this.replPolicy;
  }

  public void setReplPolicy(String replPolicy) {
    this.replPolicy = replPolicy;
  }

  public void unsetReplPolicy() {
    this.replPolicy = null;
  }

  /** Returns true if field replPolicy is set (has been assigned a value) and false otherwise */
  public boolean isSetReplPolicy() {
    return this.replPolicy != null;
  }

  public void setReplPolicyIsSet(boolean value) {
    if (!value) {
      this.replPolicy = null;
    }
  }

  public int getSrcTxnToWriteIdListSize() {
    return (this.srcTxnToWriteIdList == null) ? 0 : this.srcTxnToWriteIdList.size();
  }

  public java.util.Iterator<TxnToWriteId> getSrcTxnToWriteIdListIterator() {
    return (this.srcTxnToWriteIdList == null) ? null : this.srcTxnToWriteIdList.iterator();
  }

  public void addToSrcTxnToWriteIdList(TxnToWriteId elem) {
    if (this.srcTxnToWriteIdList == null) {
      this.srcTxnToWriteIdList = new ArrayList<TxnToWriteId>();
    }
    this.srcTxnToWriteIdList.add(elem);
  }

  public List<TxnToWriteId> getSrcTxnToWriteIdList() {
    return this.srcTxnToWriteIdList;
  }

  public void setSrcTxnToWriteIdList(List<TxnToWriteId> srcTxnToWriteIdList) {
    this.srcTxnToWriteIdList = srcTxnToWriteIdList;
  }

  public void unsetSrcTxnToWriteIdList() {
    this.srcTxnToWriteIdList = null;
  }

  /** Returns true if field srcTxnToWriteIdList is set (has been assigned a value) and false otherwise */
  public boolean isSetSrcTxnToWriteIdList() {
    return this.srcTxnToWriteIdList != null;
  }

  public void setSrcTxnToWriteIdListIsSet(boolean value) {
    if (!value) {
      this.srcTxnToWriteIdList = null;
    }
  }

  public String getCatName() {
    return this.catName;
  }

  public void setCatName(String catName) {
    this.catName = catName;
  }

  public void unsetCatName() {
    this.catName = null;
  }

  /** Returns true if field catName is set (has been assigned a value) and false otherwise */
  public boolean isSetCatName() {
    return this.catName != null;
  }

  public void setCatNameIsSet(boolean value) {
    if (!value) {
      this.catName = null;
    }
  }

  public void setFieldValue(_Fields field, Object value) {
    switch (field) {
    case DB_NAME:
      if (value == null) {
        unsetDbName();
      } else {
        setDbName((String)value);
      }
      break;

    case TABLE_NAME:
      if (value == null) {
        unsetTableName();
      } else {
        setTableName((String)value);
      }
      break;

    case TXN_IDS:
      if (value == null) {
        unsetTxnIds();
      } else {
        setTxnIds((List<Long>)value);
      }
      break;

    case REPL_POLICY:
      if (value == null) {
        unsetReplPolicy();
      } else {
        setReplPolicy((String)value);
      }
      break;

    case SRC_TXN_TO_WRITE_ID_LIST:
      if (value == null) {
        unsetSrcTxnToWriteIdList();
      } else {
        setSrcTxnToWriteIdList((List<TxnToWriteId>)value);
      }
      break;

    case CAT_NAME:
      if (value == null) {
        unsetCatName();
      } else {
        setCatName((String)value);
      }
      break;

    }
  }

  public Object getFieldValue(_Fields field) {
    switch (field) {
    case DB_NAME:
      return getDbName();

    case TABLE_NAME:
      return getTableName();

    case TXN_IDS:
      return getTxnIds();

    case REPL_POLICY:
      return getReplPolicy();

    case SRC_TXN_TO_WRITE_ID_LIST:
      return getSrcTxnToWriteIdList();

    case CAT_NAME:
      return getCatName();

    }
    throw new IllegalStateException();
  }

  /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
  public boolean isSet(_Fields field) {
    if (field == null) {
      throw new IllegalArgumentException();
    }

    switch (field) {
    case DB_NAME:
      return isSetDbName();
    case TABLE_NAME:
      return isSetTableName();
    case TXN_IDS:
      return isSetTxnIds();
    case REPL_POLICY:
      return isSetReplPolicy();
    case SRC_TXN_TO_WRITE_ID_LIST:
      return isSetSrcTxnToWriteIdList();
    case CAT_NAME:
      return isSetCatName();
    }
    throw new IllegalStateException();
  }

  @Override
  public boolean equals(Object that) {
    if (that == null)
      return false;
    if (that instanceof AllocateTableWriteIdsRequest)
      return this.equals((AllocateTableWriteIdsRequest)that);
    return false;
  }

  public boolean equals(AllocateTableWriteIdsRequest that) {
    if (that == null)
      return false;

    boolean this_present_dbName = true && this.isSetDbName();
    boolean that_present_dbName = true && that.isSetDbName();
    if (this_present_dbName || that_present_dbName) {
      if (!(this_present_dbName && that_present_dbName))
        return false;
      if (!this.dbName.equals(that.dbName))
        return false;
    }

    boolean this_present_tableName = true && this.isSetTableName();
    boolean that_present_tableName = true && that.isSetTableName();
    if (this_present_tableName || that_present_tableName) {
      if (!(this_present_tableName && that_present_tableName))
        return false;
      if (!this.tableName.equals(that.tableName))
        return false;
    }

    boolean this_present_txnIds = true && this.isSetTxnIds();
    boolean that_present_txnIds = true && that.isSetTxnIds();
    if (this_present_txnIds || that_present_txnIds) {
      if (!(this_present_txnIds && that_present_txnIds))
        return false;
      if (!this.txnIds.equals(that.txnIds))
        return false;
    }

    boolean this_present_replPolicy = true && this.isSetReplPolicy();
    boolean that_present_replPolicy = true && that.isSetReplPolicy();
    if (this_present_replPolicy || that_present_replPolicy) {
      if (!(this_present_replPolicy && that_present_replPolicy))
        return false;
      if (!this.replPolicy.equals(that.replPolicy))
        return false;
    }

    boolean this_present_srcTxnToWriteIdList = true && this.isSetSrcTxnToWriteIdList();
    boolean that_present_srcTxnToWriteIdList = true && that.isSetSrcTxnToWriteIdList();
    if (this_present_srcTxnToWriteIdList || that_present_srcTxnToWriteIdList) {
      if (!(this_present_srcTxnToWriteIdList && that_present_srcTxnToWriteIdList))
        return false;
      if (!this.srcTxnToWriteIdList.equals(that.srcTxnToWriteIdList))
        return false;
    }

    boolean this_present_catName = true && this.isSetCatName();
    boolean that_present_catName = true && that.isSetCatName();
    if (this_present_catName || that_present_catName) {
      if (!(this_present_catName && that_present_catName))
        return false;
      if (!this.catName.equals(that.catName))
        return false;
    }

    return true;
  }

  @Override
  public int hashCode() {
    List<Object> list = new ArrayList<Object>();

    boolean present_dbName = true && (isSetDbName());
    list.add(present_dbName);
    if (present_dbName)
      list.add(dbName);

    boolean present_tableName = true && (isSetTableName());
    list.add(present_tableName);
    if (present_tableName)
      list.add(tableName);

    boolean present_txnIds = true && (isSetTxnIds());
    list.add(present_txnIds);
    if (present_txnIds)
      list.add(txnIds);

    boolean present_replPolicy = true && (isSetReplPolicy());
    list.add(present_replPolicy);
    if (present_replPolicy)
      list.add(replPolicy);

    boolean present_srcTxnToWriteIdList = true && (isSetSrcTxnToWriteIdList());
    list.add(present_srcTxnToWriteIdList);
    if (present_srcTxnToWriteIdList)
      list.add(srcTxnToWriteIdList);

    boolean present_catName = true && (isSetCatName());
    list.add(present_catName);
    if (present_catName)
      list.add(catName);

    return list.hashCode();
  }

  @Override
  public int compareTo(AllocateTableWriteIdsRequest other) {
    if (!getClass().equals(other.getClass())) {
      return getClass().getName().compareTo(other.getClass().getName());
    }

    int lastComparison = 0;

    lastComparison = Boolean.valueOf(isSetDbName()).compareTo(other.isSetDbName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetDbName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.dbName, other.dbName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTableName()).compareTo(other.isSetTableName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTableName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.tableName, other.tableName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetTxnIds()).compareTo(other.isSetTxnIds());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetTxnIds()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.txnIds, other.txnIds);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetReplPolicy()).compareTo(other.isSetReplPolicy());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetReplPolicy()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.replPolicy, other.replPolicy);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetSrcTxnToWriteIdList()).compareTo(other.isSetSrcTxnToWriteIdList());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetSrcTxnToWriteIdList()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.srcTxnToWriteIdList, other.srcTxnToWriteIdList);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    lastComparison = Boolean.valueOf(isSetCatName()).compareTo(other.isSetCatName());
    if (lastComparison != 0) {
      return lastComparison;
    }
    if (isSetCatName()) {
      lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.catName, other.catName);
      if (lastComparison != 0) {
        return lastComparison;
      }
    }
    return 0;
  }

  public _Fields fieldForId(int fieldId) {
    return _Fields.findByThriftId(fieldId);
  }

  public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
    schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
  }

  public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
    schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("AllocateTableWriteIdsRequest(");
    boolean first = true;

    sb.append("dbName:");
    if (this.dbName == null) {
      sb.append("null");
    } else {
      sb.append(this.dbName);
    }
    first = false;
    if (!first) sb.append(", ");
    sb.append("tableName:");
    if (this.tableName == null) {
      sb.append("null");
    } else {
      sb.append(this.tableName);
    }
    first = false;
    if (isSetTxnIds()) {
      if (!first) sb.append(", ");
      sb.append("txnIds:");
      if (this.txnIds == null) {
        sb.append("null");
      } else {
        sb.append(this.txnIds);
      }
      first = false;
    }
    if (isSetReplPolicy()) {
      if (!first) sb.append(", ");
      sb.append("replPolicy:");
      if (this.replPolicy == null) {
        sb.append("null");
      } else {
        sb.append(this.replPolicy);
      }
      first = false;
    }
    if (isSetSrcTxnToWriteIdList()) {
      if (!first) sb.append(", ");
      sb.append("srcTxnToWriteIdList:");
      if (this.srcTxnToWriteIdList == null) {
        sb.append("null");
      } else {
        sb.append(this.srcTxnToWriteIdList);
      }
      first = false;
    }
    if (isSetCatName()) {
      if (!first) sb.append(", ");
      sb.append("catName:");
      if (this.catName == null) {
        sb.append("null");
      } else {
        sb.append(this.catName);
      }
      first = false;
    }
    sb.append(")");
    return sb.toString();
  }

  public void validate() throws org.apache.thrift.TException {
    // check for required fields
    if (!isSetDbName()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'dbName' is unset! Struct:" + toString());
    }

    if (!isSetTableName()) {
      throw new org.apache.thrift.protocol.TProtocolException("Required field 'tableName' is unset! Struct:" + toString());
    }

    // check for sub-struct validity
  }

  private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
    try {
      write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
    try {
      read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
    } catch (org.apache.thrift.TException te) {
      throw new java.io.IOException(te);
    }
  }

  private static class AllocateTableWriteIdsRequestStandardSchemeFactory implements SchemeFactory {
    public AllocateTableWriteIdsRequestStandardScheme getScheme() {
      return new AllocateTableWriteIdsRequestStandardScheme();
    }
  }

  private static class AllocateTableWriteIdsRequestStandardScheme extends StandardScheme<AllocateTableWriteIdsRequest> {

    public void read(org.apache.thrift.protocol.TProtocol iprot, AllocateTableWriteIdsRequest struct) throws org.apache.thrift.TException {
      org.apache.thrift.protocol.TField schemeField;
      iprot.readStructBegin();
      while (true)
      {
        schemeField = iprot.readFieldBegin();
        if (schemeField.type == org.apache.thrift.protocol.TType.STOP) { 
          break;
        }
        switch (schemeField.id) {
          case 1: // DB_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.dbName = iprot.readString();
              struct.setDbNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 2: // TABLE_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.tableName = iprot.readString();
              struct.setTableNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 3: // TXN_IDS
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list650 = iprot.readListBegin();
                struct.txnIds = new ArrayList<Long>(_list650.size);
                long _elem651;
                for (int _i652 = 0; _i652 < _list650.size; ++_i652)
                {
                  _elem651 = iprot.readI64();
                  struct.txnIds.add(_elem651);
                }
                iprot.readListEnd();
              }
              struct.setTxnIdsIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 4: // REPL_POLICY
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.replPolicy = iprot.readString();
              struct.setReplPolicyIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 5: // SRC_TXN_TO_WRITE_ID_LIST
            if (schemeField.type == org.apache.thrift.protocol.TType.LIST) {
              {
                org.apache.thrift.protocol.TList _list653 = iprot.readListBegin();
                struct.srcTxnToWriteIdList = new ArrayList<TxnToWriteId>(_list653.size);
                TxnToWriteId _elem654;
                for (int _i655 = 0; _i655 < _list653.size; ++_i655)
                {
                  _elem654 = new TxnToWriteId();
                  _elem654.read(iprot);
                  struct.srcTxnToWriteIdList.add(_elem654);
                }
                iprot.readListEnd();
              }
              struct.setSrcTxnToWriteIdListIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          case 6: // CAT_NAME
            if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
              struct.catName = iprot.readString();
              struct.setCatNameIsSet(true);
            } else { 
              org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
            }
            break;
          default:
            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
        }
        iprot.readFieldEnd();
      }
      iprot.readStructEnd();
      struct.validate();
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot, AllocateTableWriteIdsRequest struct) throws org.apache.thrift.TException {
      struct.validate();

      oprot.writeStructBegin(STRUCT_DESC);
      if (struct.dbName != null) {
        oprot.writeFieldBegin(DB_NAME_FIELD_DESC);
        oprot.writeString(struct.dbName);
        oprot.writeFieldEnd();
      }
      if (struct.tableName != null) {
        oprot.writeFieldBegin(TABLE_NAME_FIELD_DESC);
        oprot.writeString(struct.tableName);
        oprot.writeFieldEnd();
      }
      if (struct.txnIds != null) {
        if (struct.isSetTxnIds()) {
          oprot.writeFieldBegin(TXN_IDS_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I64, struct.txnIds.size()));
            for (long _iter656 : struct.txnIds)
            {
              oprot.writeI64(_iter656);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.replPolicy != null) {
        if (struct.isSetReplPolicy()) {
          oprot.writeFieldBegin(REPL_POLICY_FIELD_DESC);
          oprot.writeString(struct.replPolicy);
          oprot.writeFieldEnd();
        }
      }
      if (struct.srcTxnToWriteIdList != null) {
        if (struct.isSetSrcTxnToWriteIdList()) {
          oprot.writeFieldBegin(SRC_TXN_TO_WRITE_ID_LIST_FIELD_DESC);
          {
            oprot.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.srcTxnToWriteIdList.size()));
            for (TxnToWriteId _iter657 : struct.srcTxnToWriteIdList)
            {
              _iter657.write(oprot);
            }
            oprot.writeListEnd();
          }
          oprot.writeFieldEnd();
        }
      }
      if (struct.catName != null) {
        if (struct.isSetCatName()) {
          oprot.writeFieldBegin(CAT_NAME_FIELD_DESC);
          oprot.writeString(struct.catName);
          oprot.writeFieldEnd();
        }
      }
      oprot.writeFieldStop();
      oprot.writeStructEnd();
    }

  }

  private static class AllocateTableWriteIdsRequestTupleSchemeFactory implements SchemeFactory {
    public AllocateTableWriteIdsRequestTupleScheme getScheme() {
      return new AllocateTableWriteIdsRequestTupleScheme();
    }
  }

  private static class AllocateTableWriteIdsRequestTupleScheme extends TupleScheme<AllocateTableWriteIdsRequest> {

    @Override
    public void write(org.apache.thrift.protocol.TProtocol prot, AllocateTableWriteIdsRequest struct) throws org.apache.thrift.TException {
      TTupleProtocol oprot = (TTupleProtocol) prot;
      oprot.writeString(struct.dbName);
      oprot.writeString(struct.tableName);
      BitSet optionals = new BitSet();
      if (struct.isSetTxnIds()) {
        optionals.set(0);
      }
      if (struct.isSetReplPolicy()) {
        optionals.set(1);
      }
      if (struct.isSetSrcTxnToWriteIdList()) {
        optionals.set(2);
      }
      if (struct.isSetCatName()) {
        optionals.set(3);
      }
      oprot.writeBitSet(optionals, 4);
      if (struct.isSetTxnIds()) {
        {
          oprot.writeI32(struct.txnIds.size());
          for (long _iter658 : struct.txnIds)
          {
            oprot.writeI64(_iter658);
          }
        }
      }
      if (struct.isSetReplPolicy()) {
        oprot.writeString(struct.replPolicy);
      }
      if (struct.isSetSrcTxnToWriteIdList()) {
        {
          oprot.writeI32(struct.srcTxnToWriteIdList.size());
          for (TxnToWriteId _iter659 : struct.srcTxnToWriteIdList)
          {
            _iter659.write(oprot);
          }
        }
      }
      if (struct.isSetCatName()) {
        oprot.writeString(struct.catName);
      }
    }

    @Override
    public void read(org.apache.thrift.protocol.TProtocol prot, AllocateTableWriteIdsRequest struct) throws org.apache.thrift.TException {
      TTupleProtocol iprot = (TTupleProtocol) prot;
      struct.dbName = iprot.readString();
      struct.setDbNameIsSet(true);
      struct.tableName = iprot.readString();
      struct.setTableNameIsSet(true);
      BitSet incoming = iprot.readBitSet(4);
      if (incoming.get(0)) {
        {
          org.apache.thrift.protocol.TList _list660 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.I64, iprot.readI32());
          struct.txnIds = new ArrayList<Long>(_list660.size);
          long _elem661;
          for (int _i662 = 0; _i662 < _list660.size; ++_i662)
          {
            _elem661 = iprot.readI64();
            struct.txnIds.add(_elem661);
          }
        }
        struct.setTxnIdsIsSet(true);
      }
      if (incoming.get(1)) {
        struct.replPolicy = iprot.readString();
        struct.setReplPolicyIsSet(true);
      }
      if (incoming.get(2)) {
        {
          org.apache.thrift.protocol.TList _list663 = new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, iprot.readI32());
          struct.srcTxnToWriteIdList = new ArrayList<TxnToWriteId>(_list663.size);
          TxnToWriteId _elem664;
          for (int _i665 = 0; _i665 < _list663.size; ++_i665)
          {
            _elem664 = new TxnToWriteId();
            _elem664.read(iprot);
            struct.srcTxnToWriteIdList.add(_elem664);
          }
        }
        struct.setSrcTxnToWriteIdListIsSet(true);
      }
      if (incoming.get(3)) {
        struct.catName = iprot.readString();
        struct.setCatNameIsSet(true);
      }
    }
  }

}

