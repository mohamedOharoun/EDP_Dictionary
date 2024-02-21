public class Main {
    public static void main(String[] args) {
        Dictionary dict = new Dictionary();
        dict.addElement(new Entry("a", 1));
        try {
            System.out.println(((Entry) dict.getElement("a")).getValue());
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
        dict.addElement(new Entry("a", 8));
        try {
            System.out.println(((Entry) dict.getElement("a")).getValue());
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e);
        }
    }
}
