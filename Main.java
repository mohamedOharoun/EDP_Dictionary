public class Main {
    public static void main(String[] args) {
        Dictionary dict = new Dictionary();
        dict.addElement(new Entry(1, 5));
        System.out.println(((Entry) dict.getElement(1)).getValue());
        dict.addElement(new Entry(9, 7));
        dict.addElement(new Entry(807, 12));
        System.out.println(((Entry) dict.getElement(807)).getValue());
        dict.deleteElement(9);
        //Descomentar para comprobar que se borra.
        //System.out.println(((Entry) dict.getElement(9)).getValue());
        System.out.println(((Entry) dict.getElement(807)).getValue());


    }
}
