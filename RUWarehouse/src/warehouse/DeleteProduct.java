
/*
 * Use this class to test the deleteProduct method.
 */ 
public class DeleteProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        Warehouse ware = new Warehouse();

        for(int i = StdIn.readInt();i>0;i--)
        {
            if(StdIn.readString().equals("delete"))
            {
                int id = StdIn.readInt();
                ware.deleteProduct(id);
            }
            
            else
            {
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                String name = StdIn.readString();
                int stock = StdIn.readInt();
                int demand = StdIn.readInt();

                ware.addProduct(id, name, stock, day, demand);
            }

           
        }

        StdOut.print(ware);
    }
}
