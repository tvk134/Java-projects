public class Restock {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        Warehouse ware = new Warehouse();

        for(int i = StdIn.readInt();i>0;i--)
        {
            if(StdIn.readString().equals("add"))
            {
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int stock = StdIn.readInt();
                int demand = StdIn.readInt();

                ware.addProduct(id, name, stock, day, demand);
            }

            else
            {
                int id = StdIn.readInt();
                int amount = StdIn.readInt();
                
                ware.restockProduct(id, amount);
            }
        }

        StdOut.print(ware);
	
    }
}
