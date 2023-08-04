
/*
 * Use this class to test the betterAddProduct method.
 */ 
public class BetterAddProduct {
    public static void main(String[] args) {
        StdIn.setFile(args[0]);
        StdOut.setFile(args[1]);
        
        Warehouse ware = new Warehouse();

        for(int n = StdIn.readInt();n>0;n--)
        {
            int day = StdIn.readInt();
            int id = StdIn.readInt();
            String name = StdIn.readString();
            int stock = StdIn.readInt();
            int demand = StdIn.readInt();

            ware.betterAddProduct(id, name, stock, day, demand);
        }

        StdOut.print(ware);
    }
}
