// AST.java
// AST for S
import java.util.*;

abstract class Command {
    // Command = Decl | Function | Stmt
    Type type =Type.UNDEF;
    public void display(int level){ }
}

class Indent {
    public static void display(int level, String s){
        String tab = "";
        System.out.println();
        for(int i = 0; i < level; i++)
            tab = tab + "   ";
        System.out.print(tab + s);
    }
}

class Decls extends ArrayList<Decl> {
    // Decls = Decl*
    public ArrayList<Decl> decls = new ArrayList<Decl>();
    Decls() { super(); };
    Decls(Decl d) {this.add(d);}
    public void display(int level){
        Indent.display(level, "Decls");
        for(int i = 0; i< decls.size(); i++) {
            decls.get(i).display(level + 1);
        }
        }
}

class Decl extends Command {
    // Decl = Type type; Identifier id 
    Identifier id;
    Expr expr = null;
    int arraysize = 0;

    Decl (String s, Type t) {
        id = new Identifier(s); type = t;
    } // declaration 

    Decl (String s, Type t, int n) {
        id = new Identifier(s); type = t; arraysize = n;
    } // array declaration 

    Decl (String s, Type t, Expr e) {
        id = new Identifier(s); type = t; expr = e;
    } // declaration
    public void display(int level){
        Indent.display(level, "Decl");
        type.display(level + 1);
        id.display(level + 1);
        if(arraysize != 0)
            Indent.display(level+1, Integer.toString(arraysize));
        if(expr !=null)
            expr.display(level + 1);

    }
}

class Functions extends ArrayList<Function> {
    // Functions = Function*
    public void display(int level){
        Indent.display(level, "Functions");
    }
}

class Function extends Command  {
    // Function = Type type; Identifier id; Decls params; Stmt stmt
    Identifier id;
    Decls params;
    Stmt stmt;
   
    Function(String s, Type t) { 
        id = new Identifier(s); type = t; params = null; stmt = null;
    }

    public String toString ( ) { 
       return id.toString()+params.toString(); 
    }

    public void display(int level){
        Indent.display(level, "Function");
        id.display(level + 1);
        type.display(level + 1);
        // params, stmt 생락
    }

}

class Type {
    // Type = int | bool | string | fun | array | except | void
    final static Type INT = new Type("int");
    final static Type BOOL = new Type("bool");
    final static Type STRING = new Type("string");
    final static Type VOID = new Type("void");
    final static Type FUN = new Type("fun");
    final static Type ARRAY = new Type("array");
    final static Type EXC = new Type("exc");
    final static Type RAISEDEXC = new Type("raisedexc");
    final static Type UNDEF = new Type("undef");
    final static Type ERROR = new Type("error");
    
    protected String id;
    protected Type(String s) { id = s; }
    public String toString ( ) { return id; }
    public void display(int level){
        Indent.display(level, id);
    }
}

class ProtoType extends Type {
   // defines the type of a function and its parameters
   Type result;  
   Decls params;
   ProtoType (Type t, Decls ds) {
      super(t.id);
      result = t;
      params = ds;
   }
}

abstract class Stmt extends Command {
    // Stmt = Empty | Stmts | Assignment | If  | While | Let | Read | Print | Dowhile

}

class Empty extends Stmt {

}

class Stmts extends Stmt {
    // Stmts = Stmt*
    public ArrayList<Stmt> stmts = new ArrayList<Stmt>();
    private Stmt stmt;
    Assignment as;


    Stmts() {
	    super(); 
    }

    Stmts(Stmt s) {
	     stmts.add(s);
    }


    public void display(int level){
        Indent.display(level, "Stmts");
        for(int i = 0; i< stmts.size(); i++)
            stmts.get(i).display(level+1);

    }
    

}

class Assignment extends Stmt {
    // Assignment = Identifier id; Expr expr
    Identifier id;
    Array ar = null;
    Expr expr;
    //Expr expr2 = 2;

    Assignment (Identifier t, Expr e) {
        id = t;
        expr = e;
    }
    /*Assignment(Identifier t, Expr e1, Expr e2){
        id = t;
        expr = e1;
        expr2 = e2;
    }*/
    Assignment (Array a, Expr e) {
        ar = a;
        expr = e;
    }
    public void display(int level){
        Indent.display(level, "Assignment");
        if(ar == null)
            id.display(level + 1);
        if(ar != null)
            ar.display(level + 1);
        expr.display(level + 1);
    }
}

class If extends Stmt {
    // If = Expr expr; Stmt stmt1, stmt2;
    Expr expr;
    Stmt stmt1, stmt2;
    
    If (Expr t, Stmt tp) {
        expr = t; stmt1 = tp; stmt2 = new Empty( );
    }
    
    If (Expr t, Stmt tp, Stmt ep) {
        expr = t; stmt1 = tp; stmt2 = ep;
    }
    public void display(int level){
        Indent.display(level, "If");
        expr.display(level + 1);
        stmt1.display(level + 1);
        stmt2.display(level + 1);
    }
}

class While extends Stmt {
    // While = Expr expr; Stmt stmt;
    Expr expr;
    Stmt stmt;

    While (Expr t, Stmt b) {
        expr = t; stmt = b;
    }
    public void display(int level){
        Indent.display(level, "While");
        expr.display(level + 1);
        stmt.display(level + 1);
    }
}

class Dowhile extends Stmt {
    Expr expr;
    Stmt stmt;

    Dowhile (Expr t, Stmt b) {
        expr = t; stmt = b;
    }
    public void display(int level){
        Indent.display(level, "Dowhile");
        stmt.display(level + 1);
        expr.display(level + 1);
    }
}

class For extends Stmt{
    Identifier id1;
    Identifier id2;
    Expr expr1;
    Expr expr2;
    Expr expr3;
    Stmt stmt;

    For(Type t, Identifier i1, Expr e1, Expr e2, Identifier i2, Expr e3, Stmt s){
        type = t; id1 = i1; expr1 = e1; expr2 = e2; id2 = i2; expr3 = e3; stmt = s;
    }
    public void display(int level){
        Indent.display(level, "For");
        type.display(level + 1);
        id1.display(level + 1);
        expr1.display(level + 1);
        expr2.display(level + 1);
        id2.display(level + 1);
        expr3.display(level + 1);
        stmt.display(level + 1);
    }
}

class Let extends Stmt {
    // Let = Decls decls; Functions funs; Stmts stmts;
    Decls decls;
    Functions funs;
    Stmts stmts;

    Let(Decls ds, Stmts ss) {
        decls = ds;
		funs = null;
        stmts = ss;
    }

    Let(Decls ds, Functions fs, Stmts ss) {
        decls = ds;
	    funs = fs;
        stmts = ss;
    }
    public void display(int level){
        Indent.display(level, "Let");
        decls.display(level+1);
        if(funs!=null)
            funs.display(level + 1);
        stmts.display(level+1);
    }
}

class Read extends Stmt {
    // Read = Identifier id
    Identifier id;

    Read (Identifier v) {
        id = v;
    }

    public void display(int level) {
        Indent.display(level, "Read");
        id.display(level + 1);
    }
}

class Print extends Stmt {
    // Print =  Expr expr
    Expr expr;

    Print (Expr e) {
        expr = e;
    }

    public void display(int level) {
        Indent.display(level, "Print");
        expr.display(level + 1);
    }
}

class Return extends Stmt {
    Identifier fid;
    Expr expr;

    Return (String s, Expr e) {
        fid = new Identifier(s);
        expr = e;
    }
    public void display(int level) {
        Indent.display(level, "Return");
        fid.display(level + 1);
        expr.display(level + 1);
    }

}

class Try extends Stmt {
    // Try = Identifier id; Stmt stmt1; Stmt stmt2; 
    Identifier eid;
    Stmt stmt1; 
    Stmt stmt2; 

    Try(Identifier id, Stmt s1, Stmt s2) {
        eid = id; 
        stmt1 = s1;
        stmt2 = s2;
    }
    public void display(int level) {
        Indent.display(level, "Try");
        stmt1.display(level + 1);
        stmt2.display(level + 1);
    }
}

class Raise extends Stmt {
    Identifier eid;

    Raise(Identifier id) {
        eid = id;
    }

    public void display(int level) {
        Indent.display(level, "Raise");
        eid.display(level + 1);
    }
}

class Exprs extends ArrayList<Expr> {
    // Exprs = Expr*
    public ArrayList<Expr> exprs = new ArrayList<Expr>();
    Exprs() { super(); };
    Exprs(Expr e) { this.add(e);}


    public void display(int level){
        Indent.display(level, "Exprs");
        for(int i = 0; i< exprs.size(); i++)
            exprs.get(i).display(level+1);
    }
}

abstract class Expr extends Stmt {
    // Expr = Identifier | Value | Binary | Unary | Call

}

class Call extends Expr { 
    Identifier fid;  
    Exprs args;

    Call(Identifier id, Exprs a) {
       fid = id;
       args = a;
    }
    public void display(int level){
        Indent.display(level, "Call");
        fid.display(level + 1);
        args.display(level + 1);
    }
}

class Identifier extends Expr {
    // Identifier = String id
    private String id;

    Identifier(String s) { id = s; }

    public String toString( ) { return id; }
    
    public boolean equals (Object obj) {
        String s = ((Identifier) obj).id;
        return id.equals(s);
    }
    public void display(int level){
        Indent.display(level, id);
    }

}

class Array extends Expr {
    // Array = Identifier id; Expr expr
    Identifier id;
    Expr expr = null;

    Array(Identifier s, Expr e) {id = s; expr = e;}

    public String toString( ) { return id.toString(); }
    
    public boolean equals (Object obj) {
        String s = ((Array) obj).id.toString();
        return id.equals(s);
    }
    public void display(int level){
        Indent.display(level, "Array");
        id.display(level + 1);
        expr.display(level + 1);
    }
}

class Value extends Expr {
    // Value = int | bool | string | array | function 
    protected boolean undef = true;
    Object value = null; // Type type;
    
    Value(Type t) {
        type = t;  
        if (type == Type.INT) value = new Integer(0);
        if (type == Type.BOOL) value = new Boolean(false);
        if (type == Type.STRING) value = "";
        undef = false;
    }

    Value(Object v) {
        if (v instanceof Integer) type = Type.INT;
        if (v instanceof Boolean) type = Type.BOOL;
        if (v instanceof String) type = Type.STRING;
        if (v instanceof Function) type = Type.FUN;
        if (v instanceof Value[]) type = Type.ARRAY;
        value = v; undef = false; 
    }

    Object value() { return value; }

    int intValue( ) { 
        if (value instanceof Integer) 
            return ((Integer) value).intValue(); 
        else return 0;
    }
    
    boolean boolValue( ) { 
        if (value instanceof Boolean) 
            return ((Boolean) value).booleanValue(); 
        else return false;
    } 

    String stringValue ( ) {
        if (value instanceof String) 
            return (String) value; 
        else return "";
    }

    Function funValue ( ) {
        if (value instanceof Function) 
            return (Function) value; 
        else return null;
    }

    Value[] arrValue ( ) {
        if (value instanceof Value[]) 
            return (Value[]) value; 
        else return null;
    }

    Type type ( ) { return type; }

    public String toString( ) {
        //if (undef) return "undef";
        if (type == Type.INT) return "" + intValue(); 
        if (type == Type.BOOL) return "" + boolValue();
	    if (type == Type.STRING) return "" + stringValue();
        if (type == Type.FUN) return "" + funValue();
        if (type == Type.ARRAY) return "" + arrValue();
        return "undef";
    }

    public void display(int level){
        Indent.display(level, value.toString());
    }
}

class Binary extends Expr {
// Binary = Operator op; Expr expr1; Expr expr2;
    Operator op;
    Expr expr1, expr2;

    Binary (Operator o, Expr e1, Expr e2) {
        op = o; expr1 = e1; expr2 = e2;
    } // binary
    public void display(int level){
        Indent.display(level, "Binary");

        op.display(level+1);
        expr1.display(level+1);
        expr2.display(level+1);
    }
}

class Unary extends Expr {
    // Unary = Operator op; Expr expr
    Operator op;
    Expr expr;

    Unary (Operator o, Expr e) {
        op = o; //(o.val == "-") ? new Operator("neg"): o; 
        expr = e;
    } // unary
    public void display(int level){
        Indent.display(level, "Unary");
        op.display(level + 1);
        expr.display(level + 1);
    }
}

class Operator {
    String val;
    
    Operator (String s) { 
	val = s; 
    }

    public String toString( ) { 
	return val; 
    }

    public boolean equals(Object obj) { 
	return val.equals(obj); 
    }
    public void display(int level){
        Indent.display(level, val);

    }
}