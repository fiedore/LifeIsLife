
package lifeislife.data.constants;


public enum Alphabet {
A(1), B(2), C(3), D(4), E(5), F(6), G(7), H(8), I(9), J(10);
private int number;

    private Alphabet(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
    
    public String getName(){
        return name();
    }

    @Override
    public String toString() {
        return this.name();
    }
    
    

}
