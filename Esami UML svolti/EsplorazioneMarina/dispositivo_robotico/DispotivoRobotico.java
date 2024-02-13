package dispositivo_robotico;

//import ..

public abstract class DispositivoRobotico {
    protected String nome;
    
    public DispositivoRobotico(String n) {
        this.nome = n;
    }
    
    public String getNome() { return nome; }
}