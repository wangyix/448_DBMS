
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


import parser.*;
import ast.*;
import schema.*;

public class FrontEnd {
	public static void main(String[] args) {
		
		SQLParser parser = new SQLParser(System.in);

		// test: read create table commands, write and read back each attribute
		try {
			while(true) {
				CreateTableCommand command = parser.CreateTable();
				
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("./attributes.ser"));
				
				int n = command.getAttributes().size();
				for (Attribute a : command.getAttributes().values()) {
					oos.writeObject(a);
				}
				oos.close();
				
				
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream("./attributes.ser"));
				for (int i=0; i<n; ++i) {
					Attribute a = (Attribute)ois.readObject();
					System.out.println("\ntable: "+a.getTable());
					System.out.println("type: "+a.getType().toString());
					System.out.println("length: "+a.getLength());
					System.out.println("name: "+a.getName());
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
