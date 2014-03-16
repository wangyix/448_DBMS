
import java.io.*;

import database.*;
import parser.*;
import ast.*;
import value.*;

public class FrontEnd {
	public static void main(String[] args) {
		
		
		// test: read create table commands, write and read back each attribute
		SQLParser parser = new SQLParser(System.in);
		try {
			while(true) {
				
				CreateTableCommand command = parser.CreateTable();
				
				System.out.println("table name: "+command.getTableName());
				System.out.println("primary key attr names: ");
				for (String s : command.getPrimaryKeyAttrNames())
					System.out.println("\t"+s);
				for (CreateTableCommand.ForeignKeyDescriptor d : command.getForeignKeyDescriptors()) {
					System.out.println("foreign key:");
					for (String s : d.getLocalAttrNames())
						System.out.println("\t"+s);
					System.out.println("referencing table "+d.getRefTableName()+":");
					for (String s : d.getRefAttrNames())
						System.out.println("\t"+s);
				}
				System.out.println("\n");
				
				
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./test.ser"));
				
				int n = command.getAttributes().size();
				for (Attribute a : command.getAttributes()) {
					oos.writeObject(a);
				}
				oos.close();
				
				
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./test.ser"));
				for (int i=0; i<n; ++i) {
					Attribute a = (Attribute)ois.readObject();
					System.out.println("\nposition: "+a.getPosition());
					System.out.println("name: "+a.getName());
					System.out.println("type: "+a.getType().toString());
					System.out.println("length: "+a.getLength());
					if (a.getConstraint() != null)
						System.out.println("constraint: "+a.getConstraint().getExpString());
					else
						System.out.println("constraint: (NONE)");
				}
				ois.close();
			}
		} catch (ParseException | IOException | ClassNotFoundException ex) {
			System.out.println(ex.getMessage());
			return;
		}
	}
}
