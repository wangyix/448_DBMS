/* Generated By:JavaCC: Do not edit this line. SQLParser.java */
        package parser;

        import java.util.*;
        import ast.*;
        import schema.*;

        public class SQLParser implements SQLParserConstants {

// Grammar --------------------------------------------------------------------
  final public Command Command() throws ParseException {
        Command ret;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case KW_CREATE:
      ret = CreateTable();
      break;
    case KW_DROP:
      ret = DropTable();
      break;
    case KW_SELECT:
      ret = Select();
      break;
    case KW_INSERT:
      ret = Insert();
      break;
    case KW_DELETE:
      ret = Delete();
      break;
    case KW_UPDATE:
      ret = Update();
      break;
    default:
      jj_la1[0] = jj_gen;
      if (jj_2_1(2)) {
        ret = HelpTables();
      } else if (jj_2_2(2)) {
        ret = HelpDescribe();
      } else {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case KW_HELP:
          ret = HelpCommand();
          break;
        case KW_QUIT:
          ret = Quit();
          {if (true) return ret;}
          break;
        default:
          jj_la1[1] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    }
    throw new Error("Missing return statement in function");
  }

// CREATE TABLE ( ... ) -------------------------------------------------------
  final public CreateTableCommand CreateTable() throws ParseException {
        String table;
        List<Attribute> attributes = new ArrayList<Attribute>();
        List<String> primaryKey = new ArrayList<String>();
        Map<String, Attribute> foreignKeys = new HashMap<String, Attribute>();

        Attribute attribute;

        // for foreign key stuff
        String name;
        String foreignTable;
        String foreignName;
    jj_consume_token(KW_CREATE);
    jj_consume_token(KW_TABLE);
    jj_consume_token(IDENTIFIER);
          table = token.image;
    jj_consume_token(47);
    label_1:
    while (true) {
      attribute = AttrDecl();
                                                       attributes.add(attribute.setTable(table));
      jj_consume_token(48);
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case IDENTIFIER:
        ;
        break;
      default:
        jj_la1[2] = jj_gen;
        break label_1;
      }
    }
    jj_consume_token(KW_PRIMARY);
    jj_consume_token(KW_KEY);
    jj_consume_token(47);
    jj_consume_token(IDENTIFIER);
                                                   primaryKey.add(token.image);
    label_2:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 48:
        ;
        break;
      default:
        jj_la1[3] = jj_gen;
        break label_2;
      }
      jj_consume_token(48);
      jj_consume_token(IDENTIFIER);
                                                       primaryKey.add(token.image);
    }
    jj_consume_token(49);
    label_3:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 48:
        ;
        break;
      default:
        jj_la1[4] = jj_gen;
        break label_3;
      }
      jj_consume_token(48);
      jj_consume_token(KW_FOREIGN);
      jj_consume_token(KW_KEY);
      jj_consume_token(47);
      jj_consume_token(IDENTIFIER);
                                                                             name = token.image;
      jj_consume_token(49);
      jj_consume_token(KW_REFERENCES);
      jj_consume_token(IDENTIFIER);
                                                             foreignTable = token.image;
      jj_consume_token(47);
      jj_consume_token(IDENTIFIER);
                                               foreignName = token.image;
      jj_consume_token(49);
                          foreignKeys.put(name, new Attribute(foreignTable, foreignName));
    }
    jj_consume_token(50);
          {if (true) return new CreateTableCommand(token, table, attributes, primaryKey, foreignKeys);}
    throw new Error("Missing return statement in function");
  }

  final public Attribute AttrDecl() throws ParseException {
        String name;
        Attribute.Type type;
        int length = -1;
        Exp constraint = null;
    jj_consume_token(IDENTIFIER);
                           name = token.image;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case KW_INT:
      jj_consume_token(KW_INT);
                                     type = Attribute.Type.INT;
      break;
    case KW_CHAR:
      jj_consume_token(KW_CHAR);
      jj_consume_token(47);
      jj_consume_token(INT_LITERAL);
                                                          length=Integer.parseInt(token.image);
      jj_consume_token(49);
                          type = Attribute.Type.CHAR;
      break;
    case KW_DECIMAL:
      jj_consume_token(KW_DECIMAL);
                                         type = Attribute.Type.DECIMAL;
      break;
    default:
      jj_la1[5] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case KW_CHECK:
      jj_consume_token(KW_CHECK);
      jj_consume_token(47);
      constraint = Expression();
      jj_consume_token(49);
      break;
    default:
      jj_la1[6] = jj_gen;
      ;
    }
          {if (true) return new Attribute(null, type, length, name, constraint);}
    throw new Error("Missing return statement in function");
  }

// DROP TABLE table -----------------------------------------------------------
  final public DropTableCommand DropTable() throws ParseException {
    jj_consume_token(KW_DROP);
    jj_consume_token(KW_TABLE);
    jj_consume_token(IDENTIFIER);
    jj_consume_token(51);
          {if (true) return new DropTableCommand(token, token.image);}
    throw new Error("Missing return statement in function");
  }

// SELECT attr0,... FROM table0,... WHERE conditions --------------------------
  final public SelectCommand Select() throws ParseException {
        List<AttributeExp> attributes = new ArrayList<AttributeExp>();
        List<String> tables = new ArrayList<String>();
        Exp conditions;
    jj_consume_token(KW_SELECT);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case IDENTIFIER:
      jj_consume_token(IDENTIFIER);
                                           attributes.add(new AttributeExp(token, token.image));
      label_4:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case 48:
          ;
          break;
        default:
          jj_la1[7] = jj_gen;
          break label_4;
        }
        jj_consume_token(48);
        jj_consume_token(IDENTIFIER);
                                               attributes.add(new AttributeExp(token, token.image));
      }
      break;
    case SYM_STAR:
      jj_consume_token(SYM_STAR);
      break;
    default:
      jj_la1[8] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(KW_FROM);
    jj_consume_token(IDENTIFIER);
                           tables.add(token.image);
    label_5:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 48:
        ;
        break;
      default:
        jj_la1[9] = jj_gen;
        break label_5;
      }
      jj_consume_token(48);
      jj_consume_token(IDENTIFIER);
                               tables.add(token.image);
    }
    jj_consume_token(KW_WHERE);
    conditions = Expression();
    jj_consume_token(51);
          {if (true) return new SelectCommand(token, attributes, tables, conditions);}
    throw new Error("Missing return statement in function");
  }

// INSERT INTO table VALUES(val1,...) -----------------------------------------
  final public InsertCommand Insert() throws ParseException {
        String table;
        List<Exp> values = new ArrayList<Exp>();

        Exp value;
    jj_consume_token(KW_INSERT);
    jj_consume_token(KW_INTO);
    jj_consume_token(IDENTIFIER);
                                                   table = token.image;
    jj_consume_token(KW_VALUES);
    jj_consume_token(47);
    value = Expression();
                                     values.add(value);
    label_6:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 48:
        ;
        break;
      default:
        jj_la1[10] = jj_gen;
        break label_6;
      }
      jj_consume_token(48);
      value = Expression();
                                         values.add(value);
    }
    jj_consume_token(50);
          {if (true) return new InsertCommand(token, table, values);}
    throw new Error("Missing return statement in function");
  }

// DELETE FROM table WHERE conditions -----------------------------------------
  final public DeleteCommand Delete() throws ParseException {
        String table;
        Exp conditions;
    jj_consume_token(KW_DELETE);
    jj_consume_token(KW_FROM);
    jj_consume_token(IDENTIFIER);
                         table = token.image;
    jj_consume_token(KW_WHERE);
    conditions = Expression();
    jj_consume_token(51);
          {if (true) return new DeleteCommand(token, table, conditions);}
    throw new Error("Missing return statement in function");
  }

// UPDATE table SET attr1=val1, ... WHERE conditions --------------------------
  final public UpdateCommand Update() throws ParseException {
        String table;
        List<AttributeAssign > assignments = new ArrayList<AttributeAssign>();
        Exp conditions;

        AttributeAssign assignment;
    jj_consume_token(KW_UPDATE);
    jj_consume_token(IDENTIFIER);
                                       table = token.image;
    jj_consume_token(KW_SET);
    assignment = AttributeAssignr();
                                                           assignments.add(assignment);
    label_7:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case 48:
        ;
        break;
      default:
        jj_la1[11] = jj_gen;
        break label_7;
      }
      jj_consume_token(48);
      assignment = AttributeAssignr();
                                                    assignments.add(assignment);
    }
    jj_consume_token(KW_WHERE);
    conditions = Expression();
    jj_consume_token(51);
          {if (true) return new UpdateCommand(token, table, assignments, conditions);}
    throw new Error("Missing return statement in function");
  }

  final public AttributeAssign AttributeAssignr() throws ParseException {
        AttributeExp target;
        Exp value;
    jj_consume_token(IDENTIFIER);
                         target = new AttributeExp(token, token.image);
    jj_consume_token(SYM_EQUAL);
    value = Expression();
          {if (true) return new AttributeAssign(token, target, value);}
    throw new Error("Missing return statement in function");
  }

// HELP TABLES ----------------------------------------------------------------
  final public HelpTablesCommand HelpTables() throws ParseException {
    jj_consume_token(KW_HELP);
    jj_consume_token(KW_TABLES);
    jj_consume_token(51);
          {if (true) return new HelpTablesCommand(token);}
    throw new Error("Missing return statement in function");
  }

// HELP DESCRIBE table --------------------------------------------------------
  final public HelpDescribeCommand HelpDescribe() throws ParseException {
    jj_consume_token(KW_HELP);
    jj_consume_token(KW_DESCRIBE);
    jj_consume_token(IDENTIFIER);
    jj_consume_token(51);
          {if (true) return new HelpDescribeCommand(token, token.image);}
    throw new Error("Missing return statement in function");
  }

// HELP command ---------------------------------------------------------------
  final public HelpCommandCommand HelpCommand() throws ParseException {
        HelpCommandCommand.Type type;
    jj_consume_token(KW_HELP);
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case KW_CREATE:
      jj_consume_token(KW_CREATE);
      jj_consume_token(KW_TABLE);
                          type = HelpCommandCommand.Type.CREATE_TABLE;
      break;
    case KW_DROP:
      jj_consume_token(KW_DROP);
      jj_consume_token(KW_TABLE);
                          type = HelpCommandCommand.Type.DROP_TABLE;
      break;
    case KW_SELECT:
      jj_consume_token(KW_SELECT);
                          type = HelpCommandCommand.Type.SELECT;
      break;
    case KW_INSERT:
      jj_consume_token(KW_INSERT);
                          type = HelpCommandCommand.Type.INSERT;
      break;
    case KW_DELETE:
      jj_consume_token(KW_DELETE);
                          type = HelpCommandCommand.Type.DELETE;
      break;
    case KW_UPDATE:
      jj_consume_token(KW_UPDATE);
                          type = HelpCommandCommand.Type.UPDATE;
      break;
    default:
      jj_la1[12] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
    jj_consume_token(51);
          {if (true) return new HelpCommandCommand(token, type);}
    throw new Error("Missing return statement in function");
  }

// QUIT -----------------------------------------------------------------------
  final public QuitCommand Quit() throws ParseException {
    jj_consume_token(KW_QUIT);
    jj_consume_token(51);
          {if (true) return new QuitCommand(token);}
    throw new Error("Missing return statement in function");
  }

// Expression *****************************************************************
  final public Exp Expression() throws ParseException {
        Exp ret;
          Exp.clearGlobalExpString();
    ret = LogicOp();
          ret.saveExpString();  {if (true) return ret;}
    throw new Error("Missing return statement in function");
  }

// CmpOp AND CmpOp OR CmpOp AND CmpOp ... left-binding
  final public Exp LogicOp() throws ParseException {
        Exp ret;
        BinaryExp postfix;
    ret = CmpOp();
    label_8:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case KW_AND:
      case KW_OR:
        ;
        break;
      default:
        jj_la1[13] = jj_gen;
        break label_8;
      }
      postfix = LogicOp_postfix();
                  postfix.setLeft(ret);  ret = postfix;
    }
          {if (true) return ret;}
    throw new Error("Missing return statement in function");
  }

  final public BinaryExp LogicOp_postfix() throws ParseException {
        Token op;
        Exp right;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case KW_AND:
      jj_consume_token(KW_AND);
      break;
    case KW_OR:
      jj_consume_token(KW_OR);
      break;
    default:
      jj_la1[14] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
          op = token;  Exp.appendToGlobalExpString(token.image.toUpperCase()+" ");
    right = CmpOp();
          {if (true) return new BinaryExp(null, op, right);}
    throw new Error("Missing return statement in function");
  }

// AddOp < AddOp = AddOp != AddOp >= AddOp ... left-binding
  final public Exp CmpOp() throws ParseException {
        Exp ret;
        BinaryExp postfix;
    ret = AddOp();
    label_9:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case SYM_EQUAL:
      case SYM_EXCLEQUAL:
      case SYM_LESS:
      case SYM_MORE:
      case SYM_LESSEQUAL:
      case SYM_MOREEQUAL:
        ;
        break;
      default:
        jj_la1[15] = jj_gen;
        break label_9;
      }
      postfix = CmpOp_postfix();
                  postfix.setLeft(ret);  ret = postfix;
    }
          {if (true) return ret;}
    throw new Error("Missing return statement in function");
  }

  final public BinaryExp CmpOp_postfix() throws ParseException {
        Token op;
        Exp right;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case SYM_LESS:
      jj_consume_token(SYM_LESS);
      break;
    case SYM_LESSEQUAL:
      jj_consume_token(SYM_LESSEQUAL);
      break;
    case SYM_EQUAL:
      jj_consume_token(SYM_EQUAL);
      break;
    case SYM_EXCLEQUAL:
      jj_consume_token(SYM_EXCLEQUAL);
      break;
    case SYM_MORE:
      jj_consume_token(SYM_MORE);
      break;
    case SYM_MOREEQUAL:
      jj_consume_token(SYM_MOREEQUAL);
      break;
    default:
      jj_la1[16] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
          op =  token;  Exp.appendToGlobalExpString(token.image);
    right = AddOp();
          {if (true) return new BinaryExp(null, op, right);}
    throw new Error("Missing return statement in function");
  }

// MulOp + MulOp - MulOp ... left-binding 
  final public Exp AddOp() throws ParseException {
        Exp ret;
        BinaryExp postfix;
    ret = MulOp();
    label_10:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case SYM_PLUS:
      case SYM_MINUS:
        ;
        break;
      default:
        jj_la1[17] = jj_gen;
        break label_10;
      }
      postfix = AddOp_postfix();
                  postfix.setLeft(ret);  ret = postfix;
    }
          {if (true) return ret;}
    throw new Error("Missing return statement in function");
  }

  final public BinaryExp AddOp_postfix() throws ParseException {
        Token op;
        Exp right;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case SYM_PLUS:
      jj_consume_token(SYM_PLUS);
      break;
    case SYM_MINUS:
      jj_consume_token(SYM_MINUS);
      break;
    default:
      jj_la1[18] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
          op = token;  Exp.appendToGlobalExpString(token.image);
    right = MulOp();
          {if (true) return new BinaryExp(null, op, right);}
    throw new Error("Missing return statement in function");
  }

// UnaryOp * UnaryOp / UnaryOp * UnaryOp ... left-binding
  final public Exp MulOp() throws ParseException {
        Exp ret;
        BinaryExp postfix;
    ret = UnaryOp();
    label_11:
    while (true) {
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case SYM_STAR:
      case SYM_SLASH:
        ;
        break;
      default:
        jj_la1[19] = jj_gen;
        break label_11;
      }
      postfix = MulOp_postfix();
                  postfix.setLeft(ret);  ret = postfix;
    }
          {if (true) return ret;}
    throw new Error("Missing return statement in function");
  }

  final public BinaryExp MulOp_postfix() throws ParseException {
        Token op;
        Exp right;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case SYM_STAR:
      jj_consume_token(SYM_STAR);
      break;
    case SYM_SLASH:
      jj_consume_token(SYM_SLASH);
      break;
    default:
      jj_la1[20] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
          op = token;  Exp.appendToGlobalExpString(token.image);
    right = UnaryOp();
          {if (true) return new BinaryExp(null, op, right);}
    throw new Error("Missing return statement in function");
  }

// +PrimaryExp, -PrimaryExp
  final public Exp UnaryOp() throws ParseException {
        Exp ret;
        Exp sub;
        String op;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case SYM_PLUS:
    case SYM_MINUS:
      switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
      case SYM_PLUS:
        jj_consume_token(SYM_PLUS);
        break;
      case SYM_MINUS:
        jj_consume_token(SYM_MINUS);
        break;
      default:
        jj_la1[21] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
                          op = token.image;  Exp.appendToGlobalExpString(token.image);
      sub = PrimaryExp();
                          ret = new UnaryExp(token, op, sub);
      break;
    case IDENTIFIER:
    case INT_LITERAL:
    case STRING_LITERAL:
    case 47:
      ret = PrimaryExp();
      break;
    default:
      jj_la1[22] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
          {if (true) return ret;}
    throw new Error("Missing return statement in function");
  }

  final public Exp PrimaryExp() throws ParseException {
        Exp ret;
    switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
    case INT_LITERAL:
      jj_consume_token(INT_LITERAL);
                          ret = new IntLiteralExp(token, Integer.parseInt(token.image));  Exp.appendToGlobalExpString(token.image);
      break;
    case STRING_LITERAL:
      jj_consume_token(STRING_LITERAL);
                          ret = new StringLiteralExp(token, token.image);  Exp.appendToGlobalExpString(token.image);
      break;
    case IDENTIFIER:
      jj_consume_token(IDENTIFIER);
                          ret = new AttributeExp(token, token.image);  Exp.appendToGlobalExpString(token.image);
      break;
    case 47:
      jj_consume_token(47);
                              Exp.appendToGlobalExpString('(');
      ret = LogicOp();
      jj_consume_token(49);
                              Exp.appendToGlobalExpString(')');
      break;
    default:
      jj_la1[23] = jj_gen;
      jj_consume_token(-1);
      throw new ParseException();
    }
          {if (true) return ret;}
    throw new Error("Missing return statement in function");
  }

  private boolean jj_2_1(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_1(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(0, xla); }
  }

  private boolean jj_2_2(int xla) {
    jj_la = xla; jj_lastpos = jj_scanpos = token;
    try { return !jj_3_2(); }
    catch(LookaheadSuccess ls) { return true; }
    finally { jj_save(1, xla); }
  }

  private boolean jj_3_2() {
    if (jj_3R_13()) return true;
    return false;
  }

  private boolean jj_3_1() {
    if (jj_3R_12()) return true;
    return false;
  }

  private boolean jj_3R_12() {
    if (jj_scan_token(KW_HELP)) return true;
    if (jj_scan_token(KW_TABLES)) return true;
    return false;
  }

  private boolean jj_3R_13() {
    if (jj_scan_token(KW_HELP)) return true;
    if (jj_scan_token(KW_DESCRIBE)) return true;
    return false;
  }

  /** Generated Token Manager. */
  public SQLParserTokenManager token_source;
  JavaCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private Token jj_scanpos, jj_lastpos;
  private int jj_la;
  private int jj_gen;
  final private int[] jj_la1 = new int[24];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
      jj_la1_init_0();
      jj_la1_init_1();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x3250400,0x48000000,0x0,0x0,0x0,0x80000000,0x800,0x0,0x0,0x0,0x0,0x0,0x3250400,0x300,0x300,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,0x0,};
   }
   private static void jj_la1_init_1() {
      jj_la1_1 = new int[] {0x0,0x0,0x1000,0x10000,0x10000,0x3,0x0,0x10000,0x1010,0x10000,0x10000,0x10000,0x0,0x0,0x0,0xfc0,0xfc0,0xc,0xc,0x30,0x30,0xc,0xf00c,0xf000,};
   }
  final private JJCalls[] jj_2_rtns = new JJCalls[2];
  private boolean jj_rescan = false;
  private int jj_gc = 0;

  /** Constructor with InputStream. */
  public SQLParser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public SQLParser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new JavaCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new SQLParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor. */
  public SQLParser(java.io.Reader stream) {
    jj_input_stream = new JavaCharStream(stream, 1, 1);
    token_source = new SQLParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Constructor with generated Token Manager. */
  public SQLParser(SQLParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  /** Reinitialise. */
  public void ReInit(SQLParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 24; i++) jj_la1[i] = -1;
    for (int i = 0; i < jj_2_rtns.length; i++) jj_2_rtns[i] = new JJCalls();
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      if (++jj_gc > 100) {
        jj_gc = 0;
        for (int i = 0; i < jj_2_rtns.length; i++) {
          JJCalls c = jj_2_rtns[i];
          while (c != null) {
            if (c.gen < jj_gen) c.first = null;
            c = c.next;
          }
        }
      }
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }

  static private final class LookaheadSuccess extends java.lang.Error { }
  final private LookaheadSuccess jj_ls = new LookaheadSuccess();
  private boolean jj_scan_token(int kind) {
    if (jj_scanpos == jj_lastpos) {
      jj_la--;
      if (jj_scanpos.next == null) {
        jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
      } else {
        jj_lastpos = jj_scanpos = jj_scanpos.next;
      }
    } else {
      jj_scanpos = jj_scanpos.next;
    }
    if (jj_rescan) {
      int i = 0; Token tok = token;
      while (tok != null && tok != jj_scanpos) { i++; tok = tok.next; }
      if (tok != null) jj_add_error_token(kind, i);
    }
    if (jj_scanpos.kind != kind) return true;
    if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
    return false;
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;
  private int[] jj_lasttokens = new int[100];
  private int jj_endpos;

  private void jj_add_error_token(int kind, int pos) {
    if (pos >= 100) return;
    if (pos == jj_endpos + 1) {
      jj_lasttokens[jj_endpos++] = kind;
    } else if (jj_endpos != 0) {
      jj_expentry = new int[jj_endpos];
      for (int i = 0; i < jj_endpos; i++) {
        jj_expentry[i] = jj_lasttokens[i];
      }
      jj_entries_loop: for (java.util.Iterator<?> it = jj_expentries.iterator(); it.hasNext();) {
        int[] oldentry = (int[])(it.next());
        if (oldentry.length == jj_expentry.length) {
          for (int i = 0; i < jj_expentry.length; i++) {
            if (oldentry[i] != jj_expentry[i]) {
              continue jj_entries_loop;
            }
          }
          jj_expentries.add(jj_expentry);
          break jj_entries_loop;
        }
      }
      if (pos != 0) jj_lasttokens[(jj_endpos = pos) - 1] = kind;
    }
  }

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[52];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 24; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
          if ((jj_la1_1[i] & (1<<j)) != 0) {
            la1tokens[32+j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 52; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    jj_endpos = 0;
    jj_rescan_token();
    jj_add_error_token(0, 0);
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

  private void jj_rescan_token() {
    jj_rescan = true;
    for (int i = 0; i < 2; i++) {
    try {
      JJCalls p = jj_2_rtns[i];
      do {
        if (p.gen > jj_gen) {
          jj_la = p.arg; jj_lastpos = jj_scanpos = p.first;
          switch (i) {
            case 0: jj_3_1(); break;
            case 1: jj_3_2(); break;
          }
        }
        p = p.next;
      } while (p != null);
      } catch(LookaheadSuccess ls) { }
    }
    jj_rescan = false;
  }

  private void jj_save(int index, int xla) {
    JJCalls p = jj_2_rtns[index];
    while (p.gen > jj_gen) {
      if (p.next == null) { p = p.next = new JJCalls(); break; }
      p = p.next;
    }
    p.gen = jj_gen + xla - jj_la; p.first = token; p.arg = xla;
  }

  static final class JJCalls {
    int gen;
    Token first;
    int arg;
    JJCalls next;
  }

        }
