public class PurchaseProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);

        Warehouse ware = new Warehouse();

        for(int i = StdIn.readInt();i>0;i--)
        {
            if(StdIn.readString().equals("purchase"))
            {
                int day = StdIn.readInt();
                int id = StdIn.readInt();
                int amount = StdIn.readInt();
                ware.purchaseProduct(id, day, amount);
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
