public class Loader {
    public static void main(String[] args) {
        int vasyaAge = 25;
        int katyaAge = 22;
        int mishaAge = 35;

        int min = -1;
        int middle = -1;
        int max = -1;

        max = (vasyaAge > katyaAge) ? vasyaAge : katyaAge;
        max = ((max>mishaAge)? max : mishaAge);
        System.out.println("Max = " + max);

        min = (vasyaAge < katyaAge) ? vasyaAge : katyaAge;
        min = (min<mishaAge)? min : mishaAge;
        System.out.println("Min = " + min);

        middle = (vasyaAge+mishaAge+katyaAge) - (min+max);
        System.out.println("Middle = " + middle);
    }
}
